package com.example.quickdoctor.fragment.auth

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickdoctor.MainActivity2
import com.example.quickdoctor.R
import com.example.quickdoctor.activitiy.LoadingDialog
import com.example.quickdoctor.core.Result
import com.example.quickdoctor.data.auth.UserDTO
import com.example.quickdoctor.data.result.LoginResult
import com.example.quickdoctor.extensions.TAG
import com.example.quickdoctor.extensions.findNavController
import com.example.quickdoctor.extensions.toast
import com.example.quickdoctor.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.login.*


class LoginFragment: Fragment()  {

    val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
    val ARG_AUTH_TYPE = "AUTH_TYPE"
    val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
    val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"

    val KEY_ERROR_MESSAGE = "ERR_MSG"

    val PARAM_USER_PASS = "USER_PASS"

    private val REQ_SIGNUP = 1

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loadingDialog = LoadingDialog(requireActivity())
        setup()
    }

    private fun setup() {

        email.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(!loginViewModel.isUserNameValid(email.toString())) {
                    email.error = "invalid username"
                }
            }
        }
        password.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if(!loginViewModel.isPasswordValid(email.toString())) {
                    password.error = "Invalid password"
                }
            }
        }
        register.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer
            val user = UserDTO("","","","")
            if (loginResult is Result.Success<LoginResult>) {
                loadingDialog.dismissDialog()
                if(loginResult.data.userDTO.type == "Doctor")
                    moveToNewActivity(loginResult.data.userDTO)
                else {
                    moveToNewActivity(user)
                }
                toast("The app is not for life-threatening medical emergencies")
            } else if (loginResult is Result.Error){
                when (loginResult.exception.message.toString()) {
                    "HTTP 400 Bad Request" -> toast("User do not exist")
                    "HTTP 405 Method Not Allowed" -> toast("Invalid password")
                    else -> {toast(loginResult.exception.message.toString())
                    Log.v(TAG, loginResult.exception.message.toString())}
                }
            }
        })

        logInBtn.setOnClickListener {
            loginViewModel.login(email.text.toString(), password.text.toString())
            loadingDialog.startLoadingAlertDialog()
        }
    }

    private fun moveToNewActivity(userDTO: UserDTO) {
        val i = Intent(activity, MainActivity2::class.java)
        i.putExtra("userId", userDTO.id)
        startActivity(i)
        (activity as Activity).overridePendingTransition(0, 0)

    }
}