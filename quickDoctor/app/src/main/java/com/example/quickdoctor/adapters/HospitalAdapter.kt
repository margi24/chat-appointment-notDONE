package com.example.quickdoctor.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdoctor.R
import com.example.quickdoctor.activitiy.LoadingDialog
import com.example.quickdoctor.data.Hospital
import com.example.quickdoctor.fragment.appointments.HospitalList
import com.example.quickdoctor.fragment.appointments.HospitalListDirections
import kotlinx.android.synthetic.main.appointment_item_view.view.hospitalName
import kotlinx.android.synthetic.main.hospital_item.view.*

class HospitalAdapter(
    val loadingDialog: LoadingDialog,
    val hospitalListFragment: HospitalList
) : RecyclerView.Adapter<HospitalAdapter.ViewHolderH>(), Filterable {

    private var hospitalList = emptyList<Hospital>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var filterlist = emptyList<Hospital>()
        set(value) {
            field = value
            hospitalListFragment.requireActivity().runOnUiThread (Runnable {
                notifyDataSetChanged()
            })
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hospital_item, parent, false)
        return ViewHolderH(view)
    }

    override fun onBindViewHolder(holder: ViewHolderH, position: Int) {
        val hospital = filterlist[position]
        holder.hospitalName.text = hospital.name
        holder.country.text = hospital.country
        holder.city.text = hospital.city
        holder.street.text = hospital.street
        holder.idHospital.text = hospital.id.toString()
        holder.itemView.setOnClickListener {view ->
            loadingDialog.startLoadingAlertDialog()
            val action = HospitalListDirections.actionAddAppointmentToDoctorList(holder.idHospital.text.toString())
            view.findNavController().navigate(action)
            loadingDialog.dismissDialog()
        }
    }

    fun submitList(hospital: List<Hospital>) {
        this.hospitalList = hospital
        this.filterlist = hospital
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return filterlist.size
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if(charString.isEmpty()) {
                    filterlist = hospitalList
                } else {
                    val listFilterd = mutableListOf<Hospital>()
                    for(h in hospitalList) {
                        if (h.name.toLowerCase().contains(charString.toLowerCase()) ||
                                h.city.toLowerCase().contains(charString.toLowerCase())) {
                            listFilterd.add(h)
                        }
                    }
                        filterlist = listFilterd
                }
                val filterResults = FilterResults()
                filterResults.values = filterlist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterlist = results?.values as List<Hospital>? ?: hospitalList
                notifyDataSetChanged()
            }

        }
    }

    inner class ViewHolderH constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val hospitalName = itemView.hospitalName
        val country = itemView.country
        val city = itemView.city
        val street = itemView.street
        val idHospital = itemView.idHospital
    }
}