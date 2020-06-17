package com.example.quickdoctor.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.quickdoctor.R
import com.example.quickdoctor.activitiy.LoadingDialog
import com.example.quickdoctor.adapters.DoctorAdapter
import com.example.quickdoctor.extensions.TAG
import com.example.quickdoctor.viewModel.DoctorListViewModel
import kotlinx.android.synthetic.main.doctor_list.*

class DoctorList : Fragment() {

    private lateinit var doctorViewModel: DoctorListViewModel
    lateinit var doctorAdapter: DoctorAdapter
    lateinit var hospitelIdRecipiend: String
    private val args: DoctorListArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myInflater = inflater.inflate(R.layout.doctor_list, container, false)
        hospitelIdRecipiend = args.hospitalIdRecipient

        return myInflater
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        doctorViewModel = ViewModelProvider(this).get(DoctorListViewModel::class.java)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.searchHospital)

        if( searchItem !=null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    doctorAdapter.filter.filter(newText)
                    return true
                }
            })
        }
    }

    private fun setup() {
        doctorAdapter = DoctorAdapter(this, hospitelIdRecipiend)
        doctor_list.adapter = doctorAdapter
        doctorViewModel.doctorListCredentials.observe(viewLifecycleOwner, Observer { d ->
            doctorAdapter.submitList(d)
        })
        if(hospitelIdRecipiend != "") {
            doctorViewModel.getDoctorsForHospital(hospitelIdRecipiend)
        } else {
            doctorViewModel.getDoctors()
        }
    }
}
