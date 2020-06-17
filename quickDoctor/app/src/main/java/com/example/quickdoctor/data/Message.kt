package com.example.quickdoctor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class Message(val userName : String, val messageContent : String, val roomName: String,var viewType : Int)


