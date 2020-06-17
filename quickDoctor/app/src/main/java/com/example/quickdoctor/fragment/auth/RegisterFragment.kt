package com.example.quickdoctor.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quickdoctor.R
import com.example.quickdoctor.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.register.*
import com.example.quickdoctor.core.Result
import com.example.quickdoctor.extensions.toast

class RegisterFragment: Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        setup()
    }
    private fun setup() {

        email.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(!registerViewModel.isEmailValid(name.toString())) {
                    name.error = "invalid email"
                }
            }
        }
        name.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(!registerViewModel.isNameValid(email.toString())) {
                    email.error = "invalid name"
                }
            }
        }
        passwordRText.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(!registerViewModel.isPasswordValid(passwordRText.toString())) {
                    passwordRText.error = "Invalid password"
                }
            }
        }

        repeatPasswordText.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(!registerViewModel.isPasswordIdentical(passwordRText.toString(),
                                                          repeatPasswordText.toString())) {
                    repeatPasswordText.error = "Passwords are not identical"
                }
            }
        }
        register.setOnClickListener {
            val firstName = name.text.toString().split(" ")[0]
            val lastName = name.text.toString().split(" ")[1]
            if( doctorRadioBtn.isChecked)
                registerViewModel.register(email.text.toString(), firstName,lastName, passwordRText.text.toString(),"DoctorCredentials" )

            else
                registerViewModel.register(email.text.toString(), firstName,lastName, passwordRText.text.toString(),"Pacient" )
            }

        registerViewModel.registerResult.observe(this, Observer {
            val registerResult = it ?: return@Observer
            if (registerResult is Result.Success<*>) {
                toast("User created successfully")
            } else if (registerResult is Result.Error){
                if(registerResult.exception.message.toString() == "HTTP 400 Bad Request")
                    toast("User already exist")
                else {
                    toast(registerResult.exception.message.toString())
                }
            }
        })
    }
}