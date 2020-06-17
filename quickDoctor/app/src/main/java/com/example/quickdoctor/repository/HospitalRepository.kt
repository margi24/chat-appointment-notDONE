package com.example.quickdoctor.repository

import com.example.quickdoctor.data.Hospital
import com.example.quickdoctor.data.request.HospitalRequest

class HospitalRepository {


    suspend fun getHospitals(): List<Hospital> {
        var result = emptyList<Hospital>()
        try {
            result = HospitalRemote.service.findAll()
        } catch (e: Exception) {
            print(e.message)
        } finally {
            return result
        }
    }
    suspend fun addHospital(name: String, country: String, city: String, street: String, idUser: String, speciality: String) {
        val hospital = HospitalRequest(
            name,
            country,
            city,
            street,
            idUser,
            speciality
        )
        try {
            HospitalRemote.service.add(hospital)
        } catch (e: java.lang.Exception) {
            print(e.message)
        }
    }
}