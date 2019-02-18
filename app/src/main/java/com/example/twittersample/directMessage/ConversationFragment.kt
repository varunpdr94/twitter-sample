package com.example.twittersample.directMessage

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.twittersample.BR
import com.example.twittersample.R
import com.paytmmoney.common.BaseTModel


class ConversationFragment : Fragment(), BaseHandler<BaseTModel> {


    private var binding: com.example.twittersample.databinding.FragmentConversationDetailBinding? = null
    private var messageModel: MessageListModel? = null
    private var viewModel: ConversationFragmentViewModel? = null
    private var baseAdapter: BaseAdapter<BaseTModel>? = null


    companion object {

        var argKey = "message_arg_key"
        fun getInstance(messageListModel: MessageListModel): ConversationFragment {
            val bundle = Bundle()
            bundle.putParcelable(argKey, messageListModel)
            val fragment = ConversationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            messageModel = it.getParcelable(argKey)
            viewModel = getViewModel()
        }
    }

    private fun getViewModel(): ConversationFragmentViewModel {
        return ViewModelProviders.of(this).get(ConversationFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversation_detail, container, false)
        binding?.setVariable(BR.obj, messageModel)
        binding?.setVariable(BR.handlers, this)
        startObservingApi()
        return binding?.root!!
    }

    private fun startObservingApi() {
        viewModel?.liveData?.observe(this, Observer {
            handleResponse(it)
        })

        viewModel?.messageSentLiveData?.observe(this, Observer {
            if (it == true) {
                Toast.makeText(activity, "Message Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Message Sending Failed", Toast.LENGTH_SHORT).show()

            }
        })
        viewModel?.callGetConversatioinDetail(messageModel?.id!!)
    }

    private fun handleResponse(it: List<MessageItemModel>?) {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd=true
        binding?.recyclerView?.layoutManager = layoutManager
        baseAdapter = BaseAdapter(0, viewModel, this@ConversationFragment)
        binding?.recyclerView?.adapter = baseAdapter
        baseAdapter?.updateList(it)
        binding?.recyclerView?.post{
            binding?.recyclerView?.scrollToPosition(0)
        }
        binding?.executePendingBindings()
    }

    override fun onClick(view: View?, data: BaseTModel?) {
        when (view?.id) {
            R.id.write_message_et -> {
                if ((view as TextView).text.isNotEmpty()) {
                    viewModel?.sendDirectMessage((view as TextView).text.toString(), (data as MessageListModel).id!!)
                    baseAdapter?.insertIntoTopDatum(MessageItemModel((view as TextView).text.toString(), R.layout.sender_layout_item))
                    binding?.writeMessageEt?.setText("")
                    binding?.recyclerView?.scrollToPosition(0)

                }
            }
        }
    }


}