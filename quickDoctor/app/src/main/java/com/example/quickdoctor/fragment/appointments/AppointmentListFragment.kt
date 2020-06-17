package com.example.quickdoctor.fragment.appointments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.quickdoctor.R
import com.example.quickdoctor.adapters.AppointmentAdapter
import com.example.quickdoctor.viewModel.AppointmentViewModel
import kotlinx.android.synthetic.main.appointment.*


class AppointmentListFragment : Fragment() {

    private lateinit var appointmentAdapter: AppointmentAdapter
    private lateinit var appoinmentViewModel: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.appointment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appoinmentViewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)

        setupAppointments()

    }

    private fun setupAppointments() {

        appointmentAdapter = AppointmentAdapter(this)
        appointment_list.adapter = appointmentAdapter
        appoinmentViewModel.appointmentsForUser.observe(viewLifecycleOwner, Observer { appointments ->
            appointmentAdapter.submitList(appointments)
        })

        appoinmentViewModel.setUpList()
    }
}