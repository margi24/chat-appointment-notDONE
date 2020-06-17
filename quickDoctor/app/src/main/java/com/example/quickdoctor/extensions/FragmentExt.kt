package com.example.quickdoctor.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: CharSequence) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

