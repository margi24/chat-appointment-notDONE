package com.example.quickdoctor.data

import org.json.JSONArray
import org.json.JSONObject

class AppointmentResponse(json: String) : JSONObject(json) {


    val book: JSONArray? = this.opt("results") as JSONArray
}