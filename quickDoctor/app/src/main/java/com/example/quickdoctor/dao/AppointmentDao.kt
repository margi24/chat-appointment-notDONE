package com.example.quickdoctor.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quickdoctor.data.Appointment2

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointment2 WHERE idPacient =:idP order by date desc")
    fun getAppointments(idP: Int): LiveData<List<Appointment2>>

    @Query("DELETE FROM appointment2")
    suspend fun deleteAll()

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(app: Appointment2)
}