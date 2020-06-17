package com.example.quickdoctor.data

data class DoctorCredentials(val id: Int,
                             val idUser: Int,
                             val idHospital:Int,
                             val speciality: String,
                             val firstName: String,
                             val lastName: String)