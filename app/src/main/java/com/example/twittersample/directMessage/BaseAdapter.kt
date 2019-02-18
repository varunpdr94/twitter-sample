package com.example.twittersample.directMessage

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paytmmoney.common.BaseTModel
import java.util.*

open class BaseAdapter<T : BaseTModel> : RecyclerView.Adapter<BaseViewHolder> {

    private var layoutResource = 0
    var list: MutableList<T> = ArrayList()
    var handler: BaseHandler<*>? = null
    private var viewModel: BaseViewModel? = null
    private var isAnimationEnabled: Int? = null
    private var type: String? = null

    /**
     * basic function can make use of all below method if required
     *
     * @param
     */

    val items: List<T>?
        get() = list

    val firstItem: T?
        get() = if (list.size > 0) list[0] else null


    constructor(layoutResource: Int, anim: Int? = null, type: String? = null) {
        this.layoutResource = layoutResource
        this.isAnimationEnabled = anim
        this.type = type
    }

    constructor(layoutResource: Int, handler: BaseHandler<*>?, anim: Int? = null, type: String? = null) {
        this.layoutResource = layoutResource
        this.handler = handler
        this.isAnimationEnabled = anim
        this.type = type
    }


    constructor(layoutResource: Int, viewModel: BaseViewModel?, handler: BaseHandler<*>? = null, anim: Int? = null, type: String? = null) {
        this.layoutResource = layoutResource
        this.viewModel = viewModel
        this.handler = handler
        this.isAnimationEnabled = anim
        this.type = type
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BaseViewHolder {
        try {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    layoutInflater, viewType, parent, false)
            return BaseViewHolder(binding)
        } catch (e: Exception) {
            Log.d("error cause", e.cause.toString())
        }
        return BaseViewHolder(null)
    }

    override fun onBindViewHolder(holder: BaseViewHolder,
                                  position: Int) {
        val obj = list[position]
        holder.bind(obj, handler, viewModel, type, position)
    }


    override fun getItemViewType(position: Int): Int {
        return if (layoutResource != 0) {
            layoutResource
        } else {
            /**
             * for different view types
             */
            list[position].layoutResId
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * adding data for the first time in the list and scroll if required to last position
     *
     * @param li
     */
    open fun updateList(li: List<T>?) {
        if (li != null) {
            this.list.clear()
            this.list = ArrayList(li)
            notifyDataSetChanged()
        }
    }

    open fun replaceObjectAtIndex(obj: T?, index: Int?) {
        if (obj != null && index != null) {
            list.removeAt(index)
            list.add(index, obj)
            notifyItemChanged(index)
        }
    }

    open fun updateObjectAtIndex(obj: T?, index: Int?) {
        if (obj != null && index != null) {
            notifyItemChanged(index, obj)
            notifyItemChanged(index)
        }
    }

    /**
     * insert data at the bottom of the list
     *
     * @param dataList
     */
    fun insertIntoBottomList(dataList: List<T>?) {
        var position = list.size
        if (dataList == null) {
            return
        }
        dataList.forEach { datumList ->
            list.add(position, datumList)
            notifyItemInserted(position)
            position++
        }

    }

    /**
     * inserting list data at the top of the list
     *
     * @param listDatum
     */
    fun insertIntoTopList(listDatum: List<T>?) {
        var position = 0
        if (listDatum == null) {
            return
        }
        listDatum.forEach { datum ->
            list.add(position, datum)
            notifyItemInserted(position)
            position++
        }

    }


    /**
     * inserting object at the top of the list
     *
     * @param datum
     */
    fun insertIntoTopDatum(datum: T?) {

        if (datum != null) {
            list.add(0, datum)
            notifyItemInserted(0)
        }

    }

    /**
     * inserting object at the Bottom of the list
     *
     * @param datum
     */
    fun insertIntoBottomDatum(datum: T?) {
        if (datum != null) {
            list.add(datum)
            notifyItemInserted(list.size - 1)
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }

    fun clearItems() {
        list.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? {
        return if (list.size > position) list[position] else null
    }

    fun isEmpty() = items?.isNotEmpty() != true


}
