package com.example.twittersample.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class NetworkInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {


        Request req = chain.request();
        Request.Builder builder = req.newBuilder()
//                .addHeader("User-Agent","twitterApp")
                .addHeader("Authorization",NetworkUtility.getAuthorization(req.method(),req.url().toString()));
//                .addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
//                .addHeader("Content-Length","29")
//                .addHeader("Accept-Encoding","gzip")
//                .addHeader("grant_type","client_credentials");


        Request newRequest = builder.build();

        RequestBody rb = newRequest.body();

        Buffer buffer = new Buffer();
        if (rb != null) {
            rb.writeTo(buffer);
        }

        /*Logging modified and actual request which will hit server*/
        Log.d(getClass().getSimpleName(), "network payload" + " : " + newRequest.method() + ", " + newRequest.url());
        Log.d(getClass().getSimpleName(), "network payload body" + buffer.readUtf8() + "\n Headers:::" + newRequest.headers().toString());

        Response response = chain.proceed(newRequest);
        final String responseString = new String(response.body().bytes());


        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), responseString))
                .build();
    }
}
