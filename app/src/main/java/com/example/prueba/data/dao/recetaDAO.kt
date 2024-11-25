package com.example.prueba.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.prueba.data.entites.receta


@Dao
interface recetaDAO{

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun  insertar(receta:receta):Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun  insertarLista(recetas:List<receta>):List<Long>

    @Update
    suspend fun  update(receta:receta):Int

    @Query("SELECT imagen FROM RECETA WHERE ID = 2")
    suspend fun getimg():String


    @Delete
    suspend fun delete(receta: receta):Int

    @Query("SELECT * FROM RECETA")
    suspend fun getAll():List<receta>
}