package com.example.prueba.data.entites

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class receta(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    @ColumnInfo("nombre") val nombre:String,
    @ColumnInfo("pasos")val pasos:String,
    @ColumnInfo("ingredientes") val ingredientes:String,
    @ColumnInfo("vegano") val vegano:Boolean,
    @ColumnInfo("gluten") val gluten:Boolean,
    @ColumnInfo("imagen") val imagen:String
) {
}