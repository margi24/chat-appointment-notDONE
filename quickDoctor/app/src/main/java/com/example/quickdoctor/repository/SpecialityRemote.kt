package com.example.quickdoctor.repository

import com.example.quickdoctor.core.Api
import com.example.quickdoctor.data.DoctorCredentials
import com.example.quickdoctor.data.Speciality
import retrofit2.http.GET
import retrofit2.http.Path

object SpecialityRemote {
    interface  Service {
        @GET("/speciality")
        suspend fun findAll(): List<Speciality>

        @GET("/speciality/{idHospital}")
        suspend fun findForHospital(@Path("idHospital") idHospital: Int): List<Speciality>

        @GET("/speciality/doctor/{idHospital}")
        suspend fun getDoctors(@Path("idHospital")idHospital: Int): List<DoctorCredentials>
    }

    val service: Service = Api.retrofit.create(Service::class.java)

}