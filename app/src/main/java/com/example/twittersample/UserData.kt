package com.example.twittersample

import twitter4j.DirectMessage
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

object UserData {

    var accessToken: String? = null
    var userId: Long? = null
    var message: DirectMessage? = null

    var twitterFactoryInstance: Twitter? = null

    fun getTwitterInstance(): Twitter {
        val cb = ConfigurationBuilder()
        if (twitterFactoryInstance == null) {
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("PyzVh7CpHFgm7X4Oi49VIFtI3")
                    .setOAuthConsumerSecret("R9aQ3UDmoqKfzbYMPAyiHsIXpi3HrGdFR8f6Pl92p9lcKKD6LT")
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret("AygZd3scD4FSW7GOPJ9OI5mygbSjqbQc7Nee2YmPCUqAS")
            twitterFactoryInstance = TwitterFactory(cb.build()).instance
        }
        return twitterFactoryInstance!!
    }
}