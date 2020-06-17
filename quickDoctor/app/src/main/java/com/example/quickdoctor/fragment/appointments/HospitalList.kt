package com.example.quickdoctor.fragment.appointments


import com.example.quickdoctor.activitiy.LoadingDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickdoctor.R
import com.example.quickdoctor.adapters.HospitalAdapter
import com.example.quickdoctor.viewModel.HospitalViewModel
import kotlinx.android.synthetic.main.hospital_list.*

class HospitalList : Fragment() {

    lateinit var hospitalAdapter: HospitalAdapter
    private lateinit var hospitalViewModel: HospitalViewModel
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.hospital_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hospitalViewModel = ViewModelProvider(this).get(HospitalViewModel::class.java)
        loadingDialog = LoadingDialog(requireActivity())
        setupAppointments()
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
                    hospitalAdapter.filter.filter(newText)
                    return true
                }
            })
        }
    }

    private fun setupAppointments() {
        hospitalAdapter = HospitalAdapter(loadingDialog, this)
        hospital_list.adapter = hospitalAdapter
        hospitalViewModel.hospitalsList.observe( viewLifecycleOwner, Observer { h ->
            hospitalAdapter.submitList(h)
            }
        )

        hospitalViewModel.getHospitals()
    }
}
