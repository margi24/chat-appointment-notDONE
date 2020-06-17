package com.example.quickdoctor.repository

import com.example.quickdoctor.core.Api
import com.example.quickdoctor.data.DoctorCredentials
import retrofit2.http.GET
import retrofit2.http.Path

object DoctorRemote {
    interface  Service {
        @GET("/doctor")
        suspend fun findAll(): List<DoctorCredentials>

        @GET("/doctor/{idHospital}")
        suspend fun findForHospital(@Path("idHospital") idHospital: Int): List<DoctorCredentials>
    }

    val service: Service = Api.retrofit.create(Service::class.java)

}