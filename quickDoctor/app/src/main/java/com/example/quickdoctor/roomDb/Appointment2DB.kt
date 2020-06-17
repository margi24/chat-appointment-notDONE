package com.example.quickdoctor.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.quickdoctor.dao.AppointmentDao
import com.example.quickdoctor.data.Appointment2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val DATABASE = "apppointment2v2"

@Database(entities = [Appointment2::class], version = 2, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class Appointment2DB : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: Appointment2DB? = null

        //        @kotlinx.coroutines.InternalCoroutinesApi()
        fun getDatabase(context: Context, scope: CoroutineScope): Appointment2DB {
            val inst = INSTANCE
            if (inst != null) {
                return inst
            }
            val instance =
                Room.databaseBuilder(
                    context.applicationContext,
                    Appointment2DB::class.java,
                    "app_db"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
            INSTANCE = instance
            return instance
        }

        private class WordDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.appointmentDao())
                    }
                }
            }
            fun getInstance(context: Context): Appointment2DB {
                return INSTANCE ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(context).also { INSTANCE = it }
                }
            }

            private fun buildDatabase(context: Context): Appointment2DB {
                return Room.databaseBuilder(context, Appointment2DB::class.java, DATABASE)
                    .build()
            }
        }

        suspend fun populateDatabase(bookDao: AppointmentDao) {
            bookDao.deleteAll()
        }
    }

}