package com.paytmmoney.common

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.example.twittersample.directMessage.BaseHandler

/**
 * Created by madan on 19/11/17.
 */

open class BaseTModel() : BaseObservable(), Parcelable, BaseHandler<BaseTModel> {
    override fun onClick(view: View?, data: BaseTModel?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var layoutResId: Int = 0

    /*
     giving this height when recycler view is nested inside other recycler view
     and inner recycler view height is set to wrap_content instead of fix height.
    */
    var wrappedChildItemHeight: Int = 0

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    constructor(layoutResId: Int) : this() {
        this.layoutResId = layoutResId
    }

    open fun readFromParcel(parcel: Parcel) {
        layoutResId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(layoutResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseTModel> {
        override fun createFromParcel(parcel: Parcel): BaseTModel {
            return BaseTModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseTModel?> {
            return arrayOfNulls(size)
        }
    }

    open fun onViewDetachedFromWindow() {}

    open fun isRecyclable() = true
}
