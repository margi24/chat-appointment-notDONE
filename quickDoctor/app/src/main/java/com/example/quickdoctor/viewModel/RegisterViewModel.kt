package com.example.quickdoctor.viewModel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickdoctor.repository.auth.AuthRepository
import com.example.quickdoctor.core.Result
import com.example.quickdoctor.data.result.RegisterResult
import com.example.quickdoctor.extensions.TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val mutableRegisterResult = MutableLiveData<Result<RegisterResult>>()
    val registerResult: LiveData<Result<RegisterResult>> = mutableRegisterResult

    fun isEmailValid(username: String) = username.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(username).matches()
    fun isPasswordValid(password: String) = password.matches(Regex("^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{6,}\$\n"))
    fun isPasswordIdentical(password1: String, password2: String) = password1.equals(password2)
    fun isNameValid(name: String) = name.matches(Regex("^[A-Z][a-z]*[ ]([A-Z][a-z]*[ ])+"))
    fun doctorOrPacientPressed(option: String) = option != null
    fun register(email: String, firstName: String, lastName: String, password: String, type: String) {
        viewModelScope.launch(IO) {
            Log.v(TAG, "User Created")
            mutableRegisterResult.postValue(AuthRepository.register(email,firstName,lastName,password,type))
        }
    }
}