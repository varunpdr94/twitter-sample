package com.example.twittersample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.twittersample.directMessage.MessageActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton

class MainActivity : AppCompatActivity() {

    private var loginButton: TwitterLoginButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById<TwitterLoginButton>(R.id.login_button)

            loginButton!!.callback = object : Callback<TwitterSession>() {

                override fun success(result: Result<TwitterSession>) {
                    if (result.data != null) {
                        UserData.accessToken = result.data.authToken.token
                        UserData.userId = result.data.userId
                        val intent = Intent(this@MainActivity, MessageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun failure(exception: TwitterException) {
                    Toast.makeText(this@MainActivity, getString(R.string.login_failed), Toast.LENGTH_LONG).show()
                }
            }
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton!!.onActivityResult(requestCode, resultCode, data)
    }
}
