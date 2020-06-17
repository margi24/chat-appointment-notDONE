package com.example.quickdoctor.viewModel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickdoctor.repository.auth.AuthRepository
import com.example.quickdoctor.core.Result
import com.example.quickdoctor.data.auth.Token
import com.example.quickdoctor.data.result.LoginResult
import com.example.quickdoctor.extensions.TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val mutableLoginResult = MutableLiveData<Result<LoginResult>>()
    val loginResult: LiveData<Result<LoginResult>> = mutableLoginResult

    fun isUserNameValid(username: String) = username.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(username).matches()
    fun isPasswordValid(password: String) = password.matches(Regex("^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{6,}\$\n"))

    fun login(email: String, password: String) {
        viewModelScope.launch(IO) {
            Log.v(TAG, "User Created")
            mutableLoginResult.postValue(AuthRepository.login(email,password))
        }
    }
}