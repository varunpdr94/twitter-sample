package com.example.twittersample.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RestClient {

    val BASE_URL = "https://api.twitter.com/"
    private var retrofit: Retrofit? = null
    private var apiInterface: ApiInterface? = null


    fun createClient(): Retrofit? {
        if (retrofit == null) {

            val builder = OkHttpClient().newBuilder()
                    .addInterceptor(NetworkInterceptor())

            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }

    fun getClient(): ApiInterface {
        if (apiInterface == null) {
            apiInterface = createClient()?.create(ApiInterface::class.java)
            return apiInterface!!
        } else {
            return apiInterface!!
        }
    }

}