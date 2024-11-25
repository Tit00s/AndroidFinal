package com.example.prueba.ui.principal.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

class PrincipalModel: ViewModel() {
    private val recetaDao = RoomApp.db.recetaDao()

    fun getAllRecetas(): LiveData<List<receta>> {
        val recetasLiveData = MutableLiveData<List<receta>>()

        // Lanzamos la corrutina para acceder a la base de datos de manera asíncrona
        viewModelScope.launch {
            // Ejecutamos la consulta en segundo plano
            val recetas = recetaDao.getAll()
            recetasLiveData.postValue(recetas) // Actualizamos el LiveData con los resultados
        }

        return recetasLiveData
    }
    fun getResizedBitmap(filePath: String, maxWidth: Int, maxHeight: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        // Calcular la escala
        val scaleFactor = Math.min(options.outWidth / maxWidth, options.outHeight / maxHeight)

        // Redimensionar la imagen si es necesario
        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor

        return BitmapFactory.decodeFile(filePath, options)
    }

}