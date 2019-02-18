package com.example.twittersample.directMessage;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.example.twittersample.BR;
import com.paytmmoney.common.BaseTModel;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;
    private Object obj;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj, BaseHandler handler, BaseViewModel baseViewModel, String type, Integer position) {
        this.obj = obj;
        if (obj != null && obj instanceof BaseTModel && isRecyclable() != ((BaseTModel) obj).isRecyclable()) {
            setIsRecyclable(((BaseTModel) obj).isRecyclable());
        }
        binding.setVariable(BR.obj, obj);
        binding.setVariable(BR.handlers, handler);
//        binding.setVariable(BR.viewModel, baseViewModel);
//        binding.setVariable(BR.type, type);
//        binding.setVariable(BR.position, position);

        binding.executePendingBindings();
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void onViewDetachedFromWindow() {
        if (obj != null && obj instanceof BaseTModel) {
            ((BaseTModel) obj).onViewDetachedFromWindow();
        }
    }

}