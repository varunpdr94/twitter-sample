package com.example.twittersample.directMessage

import com.paytmmoney.common.BaseTModel

class MessageItemModel(var message: String,var resId: Int) : BaseTModel(resId){

    var retryRequired:Boolean=false
}
