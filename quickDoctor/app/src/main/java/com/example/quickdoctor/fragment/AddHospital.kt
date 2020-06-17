package com.example.quickdoctor.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.quickdoctor.R
import com.example.quickdoctor.extensions.TAG
import com.example.quickdoctor.extensions.findNavController
import com.example.quickdoctor.extensions.toast
import com.example.quickdoctor.viewModel.HospitalViewModel
import kotlinx.android.synthetic.main.add_hospital.*

class AddHospital : BaseActivity() {

    private var idUser = ""
    lateinit var hospitalViewModel: HospitalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_hospital, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        idUser = ACTIVITY.idUser
        if(idUser == "") findNavController().navigate(R.id.homePage)
        hospitalViewModel = ViewModelProvider(this).get(HospitalViewModel::class.java)
        setup()
    }

    private fun setup() {
        addHospital.setOnClickListener {
            val name = nameTxt.text.toString()
            val country = countryTxt.text.toString()
            val city = cityTxt.text.toString()
            val street = streetTxt.text.toString()
            val speciality = specialityTxt.text.toString()
            when {
                name == "" -> toast("You have to enter hospital name")
                country == "" -> toast("You have to enter hospital country")
                city == "" -> toast("You have to enter hospital city")
                street == "" -> toast("You have to enter hospital street")
                speciality == "" -> toast("You have to enter your speciality")
                else -> {
                    hospitalViewModel.addHospital(name, country, city,street, idUser, speciality)
                    findNavController().navigate(R.id.homePage)
                }
            }
        }
    }
}