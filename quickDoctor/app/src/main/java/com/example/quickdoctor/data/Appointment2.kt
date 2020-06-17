package com.example.quickdoctor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "appointment2"
)
data class Appointment2(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "idDoctor")
    val idDoctor: Int,
    @ColumnInfo(name = "idHospital")
    val idHospital: Int,
    @ColumnInfo(name = "idPacient")
    val idPacient: Int,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "hour")
    val hour: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "info")
    val info: String)