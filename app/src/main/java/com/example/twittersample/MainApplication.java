package com.example.twittersample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.twittersample.utils.DataManager;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.jetbrains.annotations.NotNull;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.consumer_key), getString(R.string.consumer_secret)))
                .debug(true)
                .build();
        Twitter.initialize(config);


        new DataManager(getApplicationContext());
    }
}
