package com.example.twittersample.directMessage

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Message
import com.example.twittersample.R
import com.example.twittersample.UserData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import twitter4j.DirectMessage
import twitter4j.DirectMessageList
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class ConversationFragmentViewModel : BaseViewModel() {

    var liveData: MutableLiveData<List<MessageItemModel>> = MutableLiveData()

    var messageSentLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun callGetConversatioinDetail(userId: Long) {
        getDirectMessageList(userId)?.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    processDirectMessages(userId, it)
                }

    }

    private fun processDirectMessages(userId: Long, messageList: DirectMessageList) {
        val messageItemModelList = ArrayList<MessageItemModel>()
        messageList?.let {
            for (item in it) {
                if (item.recipientId == userId || item.senderId == userId) {
                    if (item.senderId == UserData.userId) {
                        messageItemModelList.add(MessageItemModel(item.text, R.layout.sender_layout_item))
                    } else {
                        messageItemModelList.add(MessageItemModel(item.text, R.layout.reciever_layout_item))
                    }
                }
            }
            liveData.value = messageItemModelList
        }
    }

    private fun getDirectMessageList(userId: Long): Observable<DirectMessageList> {

        return Observable.create { emitter
            ->
            emitter.onNext(UserData.getTwitterInstance()?.getDirectMessages(30)!!)
        }

    }

    @SuppressLint("CheckResult")
    fun sendDirectMessage(text: String?, id: Long) {
        sendDirectMessageObservable(text, id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
                    messageSentLiveData.value = it != null
                }
    }

    fun sendDirectMessageObservable(text: String?, id: Long): Observable<DirectMessage> {
        return Observable.create { emitter
            ->
            emitter.onNext(UserData.getTwitterInstance().sendDirectMessage(id, text))
        }
    }

}