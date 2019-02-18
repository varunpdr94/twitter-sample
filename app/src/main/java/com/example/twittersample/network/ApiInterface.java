package com.example.twittersample.network;

import com.example.twittersample.directMessage.ConversationOutputModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("https://api.twitter.com/1.1/direct_messages/events/list.json")
    Call<ConversationOutputModel> getUserConversation();

}