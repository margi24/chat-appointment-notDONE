package com.example.quickdoctor.activitiy

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.quickdoctor.R

class LoadingDialog(val activity: FragmentActivity) {

    lateinit var alertDialog: AlertDialog


    fun startLoadingAlertDialog() {
        val builder = AlertDialog.Builder(this.activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissDialog() {
        alertDialog.dismiss()
    }
}