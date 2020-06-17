package com.example.quickdoctor.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quickdoctor.data.Appointment2
import com.example.quickdoctor.extensions.TAG
import com.example.quickdoctor.repository.AppointmentRepository
import com.example.quickdoctor.roomDb.Appointment2DB
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application): AndroidViewModel(application) {


    val appointmentsForUser: LiveData<List<Appointment2>>
    private val appointmentRepo: AppointmentRepository
    val appointments: MutableLiveData<List<Appointment2>> = MutableLiveData(emptyList())

    init {
        val appointmentDao = Appointment2DB.getDatabase(application, viewModelScope).appointmentDao()
        appointmentRepo = AppointmentRepository(appointmentDao)
        appointmentsForUser = appointmentRepo.appointmentsForUser
    }

    fun setUpList() {
        viewModelScope.launch(IO) {
            appointmentRepo.setUpList()
        }
    }

    fun getAllAppointents() {
        viewModelScope.launch(IO){
            appointments.postValue(appointmentRepo.getAllAppointments())
        }
    }

    fun addAppointment(
        idHospital: String,
        idDoctor: String,
        idPacient: Int,
        date: String,
        hour: String,
        title: String,
        info: String
    ) {
        viewModelScope.launch(IO){
            appointmentRepo.addAppointment(idDoctor, idHospital,idPacient, date, hour, title,info)
        }
    }
}