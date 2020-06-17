package com.example.quickdoctor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.quickdoctor.adapters.HospitalAdapter
import com.example.quickdoctor.extensions.TAG

class MainActivity2 : AppCompatActivity() {

    var idUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreateSecondActivity")
        setContentView(R.layout.activity_main2)
        idUser = intent.getStringExtra("userId")

    }

}
