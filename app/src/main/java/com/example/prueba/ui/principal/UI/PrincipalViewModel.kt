package com.example.prueba.ui.principal.UI

import android.graphics.Bitmap
import android.graphics.ColorSpace.Model
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prueba.app.RoomApp
import com.example.prueba.data.database.AppDataBase
import com.example.prueba.data.entites.receta
import kotlinx.coroutines.launch

class PrincipalViewModel: ViewModel(){



    private val Model = PrincipalModel()
    private val _recetasLiveData = MutableLiveData<List<receta>>()
    val recetasLiveData: LiveData<List<receta>> get() = _recetasLiveData

    fun fetchRecetas() {
        viewModelScope.launch {
            val recetas = Model.getAllRecetas() // Llamada al modelo
            _recetasLiveData.postValue(recetas)
        }
    }

    fun borrarReceta(id:Int){
        viewModelScope.launch {
            Model.deleteReceta(id)
        }
        fetchRecetas()
    }




}