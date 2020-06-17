package com.example.quickdoctor.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickdoctor.R
import com.example.quickdoctor.extensions.findNavController
import com.example.quickdoctor.extensions.toast
import kotlinx.android.synthetic.main.home_page.*

class HomePage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    private fun setup() {
        startChat.setOnClickListener{
            val action = HomePageDirections.actionHomePageToDoctorList("")
            findNavController().navigate(action)
        }
        allAppointment.setOnClickListener{
            findNavController().navigate(R.id.allAppointmentFragment)
        }

        appointment.setOnClickListener {
            findNavController().navigate(R.id.hospitalList)
        }
    }
}
