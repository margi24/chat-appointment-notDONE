package com.example.quickdoctor.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }
