package com.example.prueba.app

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.prueba.data.database.AppDataBase

class RoomApp:Application() {
    companion object{
        lateinit var db:AppDataBase
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "recetaDB"
        ).fallbackToDestructiveMigration().build()

        val version = db.openHelper.readableDatabase.version
        Log.d("DB_VERSION", "Database version: $version")
    }

}