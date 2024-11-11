package com.example.marvelapp.di

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.Date

class ApiKeyInterceptor(
    private val publicKey: String,
    private val privateKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Timestamp (can be current time in milliseconds or any string)
        val ts = Date().time.toString()

        // Generate hash using MD5
        val hash = md5("$ts$privateKey$publicKey")

        // Add required query parameters
        val urlWithParams = originalUrl.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .build()

        // Build the new request
        val requestWithParams = originalRequest.newBuilder()
            .header("User-Agent", "MarvelApp")
            .url(urlWithParams)
            .build()

        return chain.proceed(requestWithParams)
    }

    // Helper function to generate MD5 hash
    private fun md5(string: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(string.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
