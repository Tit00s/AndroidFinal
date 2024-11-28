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

    @Update
    suspend fun  update(receta:receta):Int

    @Query("SELECT imagen FROM RECETA WHERE ID = 2")
    suspend fun getimg():String


    @Query("DELETE FROM receta where id = :recetaID")
    suspend fun deltebyId(recetaID:Int): Int

    @Query("SELECT * FROM RECETA")
    suspend fun getAll():List<receta>
}