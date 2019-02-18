package com.example.twittersample.directMessage

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.twittersample.R
import com.example.twittersample.databinding.MessageActivityBinding

class MessageActivity : AppCompatActivity(), MessageListFragment.FragmentInteraction {


    override fun openConversationDetail(messageListModel: MessageListModel) {
        val fragment = ConversationFragment.getInstance(messageListModel)
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragment).addToBackStack(null).commit()
    }


    var binding: MessageActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MessageActivity, R.layout.message_activity)
        initializeConversationList()
    }

    private fun initializeConversationList() {
        val fragment = MessageListFragment.getInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }

}