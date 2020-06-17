package com.example.quickdoctor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickdoctor.data.DoctorCredentials
import com.example.quickdoctor.data.Speciality
import com.example.quickdoctor.repository.DoctorRemote
import com.example.quickdoctor.repository.SpecialityRemote
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DoctorListViewModel: ViewModel(){

    var doctorListCredentials:MutableLiveData<List<DoctorCredentials>> = MutableLiveData(emptyList())


    fun getDoctorsForHospital(id: String){
        viewModelScope.launch(IO){
                doctorListCredentials.postValue(DoctorRemote.service.findForHospital(id.toInt()))
        }
    }

    fun getDoctors(){
        viewModelScope.launch(IO){
            doctorListCredentials.postValue(DoctorRemote.service.findAll())
        }
    }
}