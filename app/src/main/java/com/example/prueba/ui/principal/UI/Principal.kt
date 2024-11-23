package com.example.prueba.ui.principal.UI

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.prueba.R
import java.io.IOException


@Composable
fun principalScreen(){





    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.Fondo))){
        principal(Modifier)
    }

}

@Composable
private fun principal(modifier: Modifier){


    Column(modifier){
        prueba()


}

}
@Composable
private fun prueba(){
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val c = LocalContext.current
    // Lanzador para abrir la galería de imágenes
    val getImage: ActivityResultLauncher<String> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
            uri?.let {
                // Convertir la URI a Bitmap
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(c.contentResolver, uri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    )
    Button(onClick = { getImage.launch("image/*") }) {
        Text("Seleccionar Imagen")
    }
    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Imagen seleccionada",
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
    }
}