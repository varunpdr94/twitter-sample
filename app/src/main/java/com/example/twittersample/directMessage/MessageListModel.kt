package com.example.twittersample.directMessage

import android.annotation.SuppressLint
import com.example.twittersample.MainApplication
import com.example.twittersample.R
import com.paytmmoney.common.BaseTModel
import kotlinx.android.parcel.Parcelize
import twitter4j.DirectMessage
import twitter4j.User

@Parcelize
@SuppressLint("ParcelCreator")
class MessageListModel(
        var user: User? = null,
        var id: Long? = null,
        var message: DirectMessage? = null
) : BaseTModel(R.layout.message_list_item) {


    fun getHeaderMessageText(): String {
        return "Message To : " + user?.name
    }
}
