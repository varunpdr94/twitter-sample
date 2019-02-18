package com.example.twittersample.network

import com.example.twittersample.UserData
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.GeneralSecurityException
import java.util.*

object NetworkUtility {

    @JvmStatic
    fun getAuthorization(requestType: String, endPoint: String): String {

        val consumerKey = "mjTSElIaU2VYK38Fn6UoWXkKF"
        val nonce = getRandom32ByteString()
        val authSignatureMethod = "HMAC-SHA1"
        val consumerSecretKey="Dt3ow81LitcGYWkgqv6yCVclgmu9k0CvPZ8GjTUr14WbdWY0LJ"
        val authSecretKey="HEurHOIGAxGSNg5ekD5JdT8b6bHDhfxc3kv4sjA3v4KTl"
        val entity = true

        val tempcal = Calendar.getInstance()
        val ts = tempcal.timeInMillis// get current time in milliseconds
        val timeStamp = (ts / 1000).toString()


        val parameter_string = "include_entities=$entity&oauth_consumer_key=$consumerKey&oauth_nonce=$nonce&oauth_signature_method=$authSignatureMethod&oauth_timestamp=$timeStamp&oauth_version=1.0"

        val signature_base_string = requestType + "&" + percentEncode(endPoint) + "&" + percentEncode(parameter_string)

        var oauth_signature = ""
        try {
            oauth_signature = ComputeBase64.computeSignature(signature_base_string, percentEncode("Dt3ow81LitcGYWkgqv6yCVclgmu9k0CvPZ8GjTUr14WbdWY0LJ") + "&" + percentEncode("HEurHOIGAxGSNg5ekD5JdT8b6bHDhfxc3kv4sjA3v4KTl"))  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        val authorzation = "OAuth oauth_consumer_key=" + consumerKey + ",oauth_nonce=" + nonce + ",oauth_signature= " + percentEncode(oauth_signature) + ",oauth_signature_method=" + "HMAC-SHA1" +
                ",oauth_timestamp=" + timeStamp+",oauth_token="+UserData.accessToken!!+
        ",oauth_version=" + "1.0"


        return authorzation
    }

    fun getRandom32ByteString(): String {
        var uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "")
    }

//    @Throws(GeneralSecurityException::class, UnsupportedEncodingException::class)
//    private fun computeSignature(baseString: String, keyString: String): String {
//
//        var secretKey: SecretKey? = null
//
//        val keyBytes = keyString.toByteArray()
//        secretKey = SecretKeySpec(keyBytes, "HmacSHA1")
//
//        val mac = Mac.getInstance("HmacSHA1")
//
//        mac.init(secretKey)
//
//        val text = baseString.toByteArray()
//
//        return "".trim()
//    }

//    fun encode(value: String): String {
//        var encoded: String? = null
//        try {
//            encoded = URLEncoder.encode(value, "UTF-8")
//        } catch (ignore: UnsupportedEncodingException) {
//        }
//
//        val buf = StringBuilder(encoded!!.length)
//        var focus: Char
//        var i = 0
//        while (i < encoded.length) {
//            focus = encoded[i]
//            if (focus == '*') {
//                buf.append("%2A")
//            } else if (focus == '+') {
//                buf.append("%20")
//            } else if (focus == '%' && i + 1 < encoded.length
//                    && encoded[i + 1] == '7' && encoded[i + 2] == 'E') {
//                buf.append('~')
//                i += 2
//            } else {
//                buf.append(focus)
//            }
//            i++
//        }
//        return buf.toString()
//    }


    fun percentEncode(s: String?): String {
        if (s == null) {
            return ""
        }
        try {
            return URLEncoder.encode(s, "UTF-8")
                    // OAuth encodes some characters differently:
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~")
            // This could be done faster with more hand-crafted code.
        } catch (wow: UnsupportedEncodingException) {
            throw RuntimeException(wow.message, wow)
        }

    }


    @JvmStatic
    fun getBearerToken(): String {
        var bearTokenKey: String = ""
        bearTokenKey = percentEncode("HdWlyYcIiArjxw8S6XsHUsAWL") + ":" + percentEncode("K2U0NqmwcXUYJ09qsvgP42CbW7voLE9PB00cenw9PvQX1huUmP")
        var baseEncodedString = "SGRXbHlZY0lpQXJqeHc4UzZYc0hVc0FXTDpLMlUwTnFtd2NYVVlKMDlxc3ZnUDQyQ2JXN3ZvTEU5UEIwMGNlbnc5UHZRWDFodVVtUA=="
        return baseEncodedString.toString()
    }
}

