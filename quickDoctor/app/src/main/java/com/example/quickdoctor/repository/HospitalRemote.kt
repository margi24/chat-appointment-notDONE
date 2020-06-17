package com.example.quickdoctor.repository

import com.example.quickdoctor.core.Api
import com.example.quickdoctor.data.Hospital
import com.example.quickdoctor.data.request.HospitalRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object HospitalRemote {

    interface  Service {
        @GET("/hospital/{name}")
        suspend fun find(@Path("name") name: String): Call<List<Hospital>>
        @GET("/hospital")
        suspend fun findAll(): List<Hospital>
        @POST("/hospital")
        suspend fun add(@Body hospital: HospitalRequest)

    }
    val service: Service = Api.retrofit.create(Service::class.java)
}