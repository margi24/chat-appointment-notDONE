package com.example.quickdoctor.core

import android.R
import com.example.quickdoctor.data.Message
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.security.cert.X509Certificate


object Api{

    private const val URL = "http://192.168.43.34:4000/"

    var internet=false

    val tokenInterceptor = TokenInterceptor()

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(tokenInterceptor)
    }.build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}