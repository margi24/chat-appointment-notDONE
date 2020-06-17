package com.example.quickdoctor.repository

import com.example.quickdoctor.core.Api
import com.example.quickdoctor.data.Appointment2
import com.example.quickdoctor.data.AppointmentRequest
import retrofit2.http.*

object AppointmentRemote {

    interface  Service {
        @GET("/appointment/{id}")
        suspend fun find(@Path ("id") pacientId: Int): List<Appointment2>

        @GET("/appointment")
        suspend fun findAll(): List<Appointment2>

        @POST("/appointment")
        suspend fun addAppointment(@Body app: AppointmentRequest)
    }
    val service: Service = Api.retrofit.create(Service::class.java)
}