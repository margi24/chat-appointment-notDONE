package com.example.quickdoctor.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.quickdoctor.R
import com.example.quickdoctor.data.Appointment2
import com.example.quickdoctor.extensions.TAG
import com.example.quickdoctor.extensions.toast
import com.example.quickdoctor.viewModel.AppointmentViewModel
import kotlinx.android.synthetic.main.add_appointment.*
import java.text.SimpleDateFormat
import java.util.*

class AddAppointment : Fragment() {

    val args: AddAppointmentArgs by navArgs()
    lateinit var idHospital: String
    lateinit var idDoctor: String
    lateinit var appoinmentViewModel: AppointmentViewModel
    private val  DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")
    private var todayDate = SimpleDateFormat("yyyy:MM:dd:hh:mm").format(Date()).split(":")
    private val pmChecker = SimpleDateFormat("hh:mm a").format(Date()).split(" ")[1]
    lateinit var selectedDateC: String
    private var buttonsList = mutableListOf<Button>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        idHospital= args.idHospital
        idDoctor = args.idDoctor
        return inflater.inflate(R.layout.add_appointment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appoinmentViewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        setup()
    }

    private fun setup() {
        buttonsList.add(button1)
        buttonsList.add(button2)
        buttonsList.add(button3)
        buttonsList.add(button4)
        buttonsList.add(button5)
        buttonsList.add(button6)
        buttonsList.add(button7)
        buttonsList.add(button8)
        buttonsList.add(button9)
        buttonsList.add(button10)
        buttonsList.add(button11)
        buttonsList.add(button12)

        appoinmentViewModel.appointments.observe(viewLifecycleOwner, Observer {
            uncheckForToday(it)
            uncheckHours(it)
        })
        appoinmentViewModel.getAllAppointents()
        setupButtonClick()
    }

    private fun uncheckHours(l: List<Appointment2>) {

        calendarView.setOnDateChangeListener { view, year, m, dayOfMonth ->
            var carry = 0
            if (pmChecker === "PM") carry = 12
            val day = carry + dayOfMonth
            val mont = m + 1
            // selectedDateC = DATE_FORMAT.parse("$year-$mont-$day")
            selectedDateC = "$year-$mont-$day"
            val month = m + 1
            val filteredList = l.filter { app ->
                year == DATE_FORMAT.format(app.date).split("-")[0].toInt() &&
                        month == DATE_FORMAT.format(app.date)
                    .split("-")[1].toInt() && dayOfMonth == DATE_FORMAT.format(app.date)
                    .split("-")[2].toInt()
            }.sortedBy { appointment2 -> appointment2.hour }

            if (todayDate[0].toInt() > year || todayDate[0].toInt() == year && todayDate[1].toInt() > month ||
                todayDate[0].toInt() == year && todayDate[1].toInt() == month && todayDate[2].toInt() > dayOfMonth ||
                ((todayDate[3].toInt() + 12 > 14) && (pmChecker == "PM") && todayDate[3].toInt() != 12 && todayDate[0].toInt() == year && todayDate[1].toInt() == month && todayDate[2].toInt() == dayOfMonth)
            ) {
                for (button in buttonsList) {
                    button.isEnabled = false
                }
            } else {
                for (button in buttonsList) {
                    button.isEnabled = true
                }
                if (todayDate[0].toInt() == year && todayDate[1].toInt() == month && todayDate[2].toInt() == dayOfMonth)
                    uncheckForToday(filteredList)
                else {
                    uncheckHoursByAppointments(filteredList)
                }
            }
        }
    }

    private fun uncheckForToday(filteredList: List<Appointment2>) {

            var openingHour = 8
            var index = 0
            var carry = 0
            if(pmChecker == "PM" && todayDate[3].toInt()!=12 )  carry = 12
            while (todayDate[3].toInt() - openingHour + carry >= 0 && index <= 11) {
                val diff = todayDate[3].toInt() - openingHour
                if (diff != 0) {
                    buttonsList[index].isEnabled = false
                } else if (todayDate[4].toInt() <= 30) {
                    buttonsList[index].isEnabled = false
                } else if (todayDate[4].toInt() > 30 && index != 11) {
                    this.buttonsList[index].isEnabled = false
                    buttonsList[index + 1].isEnabled = false
                }
                if (index % 2 == 0) {
                    openingHour += 1
                }
                index++;
            }
            uncheckHoursByAppointments(filteredList)
    }



    private fun uncheckHoursByAppointments(filteredList: List<Appointment2>) {
        for(appointment in filteredList) {
            Log.v(TAG, "heeeeeeeeeeeeeei ${appointment.hour}" + appointment.hour.toString().compareTo("8.00-8.30"))
            when (appointment.hour) {
                "08.00-08.30" -> button1.isEnabled = false
                "08.30-09.00" -> button2.isEnabled = false
                "09.00-09.30" -> button3.isEnabled = false
                "09.30-10.00" -> button4.isEnabled = false
                "10.00-10.30" -> button5.isEnabled = false
                "10.30-11.00" -> button6.isEnabled = false
                "11.00-11.30" -> button7.isEnabled = false
                "11.30-12.00" -> button8.isEnabled = false
                "12.00-12.30" -> button9.isEnabled = false
                "12.30-13.00" -> button10.isEnabled = false
                "13.00-13.30" -> button11.isEnabled = false
                "13.30-14.00" -> button12.isEnabled = false
            }
        }
    }

    private fun setupButtonClick() {
        val idPacient = 0
        for (i in 0..11) {
            buttonsList[i].setOnClickListener {
                buttonsList[i].isSelected = !buttonsList[i].isSelected
                if (buttonsList[i].isSelected) {
                    deselectOtherButtonsExcept(i + 1)
                }
            }
        }

        button_addApp.setOnClickListener {
            when {
                titleApp.text.toString().isEmpty() -> toast("Please enter appointment title")
                infoApp.text.toString().isEmpty() -> toast("Please enter appointment information")
                else -> {
                    var isPressed = false
                    for (i in 0..11) {
                        if (buttonsList[i].isSelected) {
                            appoinmentViewModel.addAppointment(
                                idHospital,
                                idDoctor,
                                idPacient,
                                selectedDateC,
                                buttonsList[i].text.toString(),
                                titleApp.text.toString(),
                                infoApp.text.toString()
                            )
                            isPressed = true
                        }
                    }
                    if (!isPressed) {
                        toast("You have to select a hour")
                    }
                }
            }
        }
    }

    private fun deselectOtherButtonsExcept(i: Int) {
        for (index in 0..11) {
            if(i-1!=index) {
                buttonsList[i].isSelected = false
            }
        }
    }
}