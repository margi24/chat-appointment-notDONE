package com.example.quickdoctor.data

import java.util.*

data class AppointmentRequest(val idDoctor: String, val idHospital: String, val idPacient: Int,
                              val date: String?, val hour: String, val title: String, val info: String)