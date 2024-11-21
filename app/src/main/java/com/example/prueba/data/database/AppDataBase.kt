package com.example.prueba.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prueba.data.dao.recetaDAO
import com.example.prueba.data.entites.receta

@Database(entities =  [receta::class], version = 1,)
abstract class AppDataBase:RoomDatabase(){
    abstract fun recetaDao():recetaDAO

}