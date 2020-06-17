package com.example.quickdoctor.data

import java.util.*

data class Appointment(val doctorName: String,
                       val hospitalName: String,
                       val startDate: Date,
                       val endDate: Date,
                       val title: String,
                       val info: String)