package com.example.quickdoctor.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.quickdoctor.MainActivity2

abstract class BaseActivity: Fragment() {

    lateinit var ACTIVITY: MainActivity2

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as MainActivity2
    }
}