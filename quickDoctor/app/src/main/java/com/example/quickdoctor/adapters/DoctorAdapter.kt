package com.example.quickdoctor.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdoctor.R
import com.example.quickdoctor.data.DoctorCredentials
import com.example.quickdoctor.fragment.DoctorListDirections
import kotlinx.android.synthetic.main.doctor_item.view.*

class DoctorAdapter(
    val fragment: Fragment,
    idHospital: String
): RecyclerView.Adapter<DoctorAdapter.ViewHolderDoctor>(), Filterable{

    private var idH = ""

    private var doctorList = emptyList<DoctorCredentials>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var filterlist = emptyList<DoctorCredentials>()
        set(value) {
            field = value
            fragment.requireActivity().runOnUiThread(Runnable {
            notifyDataSetChanged()
            })
        }

    init {
        this.idH = idHospital
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDoctor {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_item, parent, false)
        return ViewHolderDoctor(view)    }

    override fun onBindViewHolder(holder: ViewHolderDoctor, position: Int) {
        val doctor = filterlist[position]
        holder.apply {
            idDoctor.text = doctor.id.toString()
            firstName.text = doctor.firstName
            lastName.text = doctor.lastName
            speciality.text = doctor.speciality
        }

        holder.itemView.setOnClickListener { view ->
            if (idH != "") {
                val action = DoctorListDirections.actionDoctorListToAddAppointment(
                    idH,
                    holder.idDoctor.text.toString()
                )
                view.findNavController().navigate(action)
            } else {
                view.findNavController().navigate(R.id.room)
            }
        }
    }

    override fun getItemCount(): Int { return filterlist.size }

    fun submitList(doctors: List<DoctorCredentials>) {
        this.doctorList = doctors
        this.filterlist = doctors
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filterlist = if (charString.isEmpty()) {
                    doctorList
                } else {
                    val listFilterd = mutableListOf<DoctorCredentials>()
                    for (d in doctorList) {
                        if (d.speciality.toLowerCase().contains(charString.toLowerCase())) {
                            listFilterd.add(d)
                        }
                    }
                    listFilterd
                }
                val filterResults = FilterResults()
                filterResults.values = filterlist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterlist = results?.values as List<DoctorCredentials>? ?: doctorList
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolderDoctor constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val idDoctor = itemView.idDoctor
        val firstName = itemView.firstName
        val lastName = itemView.lastName
        val speciality = itemView.speciality
    }

}