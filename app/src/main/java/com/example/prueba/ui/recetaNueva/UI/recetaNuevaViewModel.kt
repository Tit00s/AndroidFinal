package com.example.prueba.ui.recetaNueva.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prueba.data.entites.receta
import com.example.prueba.ui.principal.UI.PrincipalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class recetaNuevaViewModel : ViewModel() {

    private val Model = recetaNuevaModel()

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo

    var vegano: Boolean = false

    private val _ingredientes = MutableLiveData<String>()
    val ingredientes: LiveData<String> = _ingredientes

    private val _pasos = MutableLiveData<String>()
    val pasos: LiveData<String> = _pasos

    private val _Correcto = MutableLiveData<Boolean>()
    val correcto:LiveData<Boolean> =_Correcto



    fun ontextChange(titulo: String, ingredientes: String, pasos: String) {
        _titulo.value = titulo
        _ingredientes.value = ingredientes
        _pasos.value = pasos

    }

    private val _imagePath = MutableLiveData<String>()
    val imagePath: LiveData<String> = _imagePath

    fun saveImage(context: Context, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val path = saveImageToStorage(context, bitmap)
            _imagePath.postValue(path)
        }
    }

    fun insertarReceta(nombre: String, pasos: String, ingredientes: String, vegano: Boolean, gluten: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val receta = receta(
                nombre = nombre,
                pasos = pasos,
                ingredientes = ingredientes,
                vegano = vegano,
                gluten = gluten,
                imagen = _imagePath.value ?: ""
            )
            Model.addReceta(receta)

            _Correcto.postValue(true)


        }
    }

    // Función para guardar la imagen en el almacenamiento interno
    private fun saveImageToStorage(context: Context, bitmap: Bitmap): String {
        val fileName = "imagen_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) // Abre el URI como InputStream
            BitmapFactory.decodeStream(inputStream) // Decodifica el InputStream en un Bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null // Devuelve null si ocurre un error
        }
    }
}






