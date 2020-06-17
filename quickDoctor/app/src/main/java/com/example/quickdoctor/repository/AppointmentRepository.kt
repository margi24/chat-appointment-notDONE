package com.example.quickdoctor.repository

import androidx.lifecycle.LiveData
import com.example.quickdoctor.dao.AppointmentDao
import com.example.quickdoctor.data.Appointment2
import com.example.quickdoctor.data.AppointmentRequest

class AppointmentRepository(private val appointmentDao: AppointmentDao) {

    val appointmentsForUser: LiveData<List<Appointment2>> = appointmentDao.getAppointments(3)


    suspend fun getList(id: Int): List<Appointment2> {
        return AppointmentRemote.service.find(id)
    }

    suspend fun setUpList() {
        try {
            val appFromService = AppointmentRemote.service.find(3)
            for (app in appFromService) {
                appointmentDao.insert(app)
            }
        } catch (e: Exception) {
            print(e.message)
        }
    }

    suspend fun getAllAppointments(): List<Appointment2> {
        return AppointmentRemote.service.findAll()
    }

    suspend fun addAppointment(idDoctor: String, idHospital: String, idPacient: Int, date: String, hour: String, title: String, info: String) {
        val app = AppointmentRequest(idDoctor,idHospital,3,date, hour,title,info)
        AppointmentRemote.service.addAppointment(app)
    }
}