package com.example.prueba.ui.principal.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorSpace
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.app.RoomApp
import com.example.prueba.data.dao.recetaDAO
import com.example.prueba.data.entites.receta
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class PrincipalModel{

    private val recetaDao = RoomApp.db.recetaDao()

    suspend fun getAllRecetas(): List<receta> {
        return recetaDao.getAll()
    }
    suspend fun deleteReceta(id:Int): Int {
        return recetaDao.deltebyId(id)
    }



}