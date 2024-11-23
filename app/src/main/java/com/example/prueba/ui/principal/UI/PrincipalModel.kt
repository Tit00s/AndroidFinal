package com.example.prueba.ui.principal.UI

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

class PrincipalModel {

    val uri:String = ""


    fun saveImageToStorage(context: Context, bitmap: Bitmap): String {
        val fileName = "${System.currentTimeMillis()}.png" // Nombre único
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
        return file.absolutePath // Ruta que se guarda en la base de datos
    }
}