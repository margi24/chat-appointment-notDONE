package com.example.quickdoctor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdoctor.R
import com.example.quickdoctor.data.Appointment2
import kotlinx.android.synthetic.main.appointment_item_view.view.*

class AppointmentAdapter(private val fragment: Fragment): RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {


    var appointmentList = emptyList<Appointment2>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_item_view, parent, false)
        return ViewHolder(view)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointmentList[position]
        holder.doctorName.text = appointment.idDoctor.toString()
        holder.hospitalName.text = appointment.idHospital.toString()
        holder.startTime.text = appointment.date.toString()
        holder.endTime.text = appointment.hour
        holder.title.text = appointment.title
        holder.info.text = appointment.info
    }

    fun submitList(appointment: List<Appointment2>) {
        this.appointmentList = appointment
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return appointmentList.size
    }

    inner class ViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val doctorName = itemView.doctorName
        val hospitalName = itemView.hospitalName
        val startTime = itemView.startDate
        val endTime = itemView.endDate
        val title = itemView.title
        val info = itemView.info

    }
}