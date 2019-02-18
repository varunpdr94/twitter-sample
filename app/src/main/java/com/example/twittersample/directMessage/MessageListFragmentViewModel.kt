package com.example.twittersample.directMessage

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.example.twittersample.UserData
import com.paytmmoney.common.BaseTModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import twitter4j.DirectMessage
import twitter4j.DirectMessageList
import twitter4j.TwitterFactory
import twitter4j.User
import twitter4j.conf.ConfigurationBuilder


class MessageListFragmentViewModel : BaseViewModel() {

    var messageListLiveData: MutableLiveData<List<BaseTModel>> = MutableLiveData()


    private var messageList = ArrayList<MessageListModel>()
    private var userIdList = ArrayList<Long>()

    private var lastMessageSet = HashSet<Long>()

    var directMessageLiveData: List<DirectMessage>? = null

    init {

    }

    @SuppressLint("CheckResult")
    fun callConversationApi() {


        getDirectMessageList()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    directMessageLiveData = it
                    processMessageList(it)
                    //messageListLiveData.value = it
                }
    }

    @SuppressLint("CheckResult")
    private fun processMessageList(list: DirectMessageList?) {

        list?.let {
            it.forEach {
                var friendId = if (it.senderId != UserData.userId) it.senderId else it.recipientId
                if (!lastMessageSet.contains(friendId)) {
                    lastMessageSet.add(friendId)
                    userIdList.add(friendId)
                    messageList.add(MessageListModel(null,friendId, it))
                }
            }
        }

        getUserInfo(userIdList.toLongArray())?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    if (it != null) {
                        for (i in it.indices) {
                            messageList.get(i).user = it.get(i)
                        }
                    }
                    messageListLiveData.value = messageList
                }


    }

    private fun getUserInfo(varargs: LongArray): Observable<List<User>>? {
        return Observable.create { userEmitter
            ->
            userEmitter.onNext(UserData.getTwitterInstance().lookupUsers(*varargs))
        }

    }


    private fun getDirectMessageList(): Observable<DirectMessageList> {

        return Observable.create { emitter
            ->
            emitter.onNext(UserData.getTwitterInstance().getDirectMessages(30)!!)
        }

    }

    fun getConversationList(data: MessageListModel, id: Long?): List<DirectMessage> {
        var conversationList = ArrayList<DirectMessage>()
        for (item in directMessageLiveData!!) {
            if (item.senderId == id || item.recipientId == id) {
                conversationList.add(item)
            }

        }
        return conversationList
    }

}
