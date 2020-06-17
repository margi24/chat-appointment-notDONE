package com.example.quickdoctor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickdoctor.core.Result
import com.example.quickdoctor.data.Hospital
import com.example.quickdoctor.data.result.LoginResult
import com.example.quickdoctor.repository.HospitalRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HospitalViewModel: ViewModel() {

    private val hospitalRepo = HospitalRepository()
    var hospitalsList: MutableLiveData<List<Hospital>> = MutableLiveData(emptyList())

    fun getHospitals(){
        viewModelScope.launch(IO) {
            hospitalsList.postValue(hospitalRepo.getHospitals())
        }
    }

    fun addHospital(
        name: String,
        country: String,
        city: String,
        street: String,
        idUser: String,
        speciality: String
    ) {
        viewModelScope.launch(IO) {
            hospitalRepo.addHospital(name, country, city, street, idUser, speciality)
        }
    }
}