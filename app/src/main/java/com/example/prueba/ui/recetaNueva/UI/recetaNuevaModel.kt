package com.example.prueba.ui.recetaNueva.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.app.RoomApp
import com.example.prueba.data.entites.receta
import kotlinx.coroutines.launch

class recetaNuevaModel{

    private val recetaDao = RoomApp.db.recetaDao()

    suspend fun addReceta(receta: receta): Long {
        return recetaDao.insertar(receta)
    }
}