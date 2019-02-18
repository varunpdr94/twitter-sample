package com.example.twittersample.directMessage

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twittersample.R
import com.paytmmoney.common.BaseTModel

class MessageListFragment : Fragment(), BaseHandler<BaseTModel> {

    private var binding: com.example.twittersample.databinding.FragmentConversationBinding? = null
    private var viewModel: MessageListFragmentViewModel? = null
    private var baseAdapter: BaseAdapter<BaseTModel>? = null
    private var listener:FragmentInteraction?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversation, container, false)
        binding?.executePendingBindings()
        viewModel = getViewModel()
        startObservingLiveData()
        return binding?.root!!
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener= context as MessageActivity
    }

    private fun getViewModel(): MessageListFragmentViewModel {
        return ViewModelProviders.of(this).get(MessageListFragmentViewModel::class.java)
    }

    private fun startObservingLiveData() {
        viewModel?.messageListLiveData?.observe(this, Observer {
            if (it != null && it.size > 0) {
                setMessageListData(it)
            }
        })

        viewModel?.callConversationApi()
    }


    override fun onClick(view: View?, data: BaseTModel?) {
        if (data is MessageListModel) {
            when (view?.id) {
                R.id.message_item_lyt -> {
                listener?.openConversationDetail(data)
                }
            }
        }
    }


    private fun setMessageListData(it: List<BaseTModel>) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerView?.layoutManager = layoutManager
        baseAdapter = BaseAdapter(R.layout.message_list_item, viewModel, this@MessageListFragment)
        binding?.recyclerView?.adapter = baseAdapter
        baseAdapter?.updateList(it)
        binding?.executePendingBindings()
    }


    companion object {

        fun getInstance(): MessageListFragment {
            return MessageListFragment()
        }
    }

    interface FragmentInteraction {
        fun openConversationDetail(messageListModel: MessageListModel)
    }
}
