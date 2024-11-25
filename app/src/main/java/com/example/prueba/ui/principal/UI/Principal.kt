package com.example.prueba.ui.principal.UI

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.prueba.R
import com.example.prueba.data.entites.receta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


@Composable
fun principalScreen(viewModel: PrincipalModel){





    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.Fondo))){
        principal(Modifier,viewModel)
    }

}

@Composable
private fun principal(modifier: Modifier, viewModel: PrincipalModel){


    Column(modifier){

        val receta = viewModel.getAllRecetas().observeAsState(emptyList())

        LazyColumn(modifier= Modifier.fillMaxSize()) {
            items(receta.value){
                receta -> RecetaCard(receta,viewModel)
            }
        }

    }


}
@Composable
fun RecetaCard(receta: receta, viewModel: PrincipalModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Usamos el componente de imagen
            RecipeImage(imagenPath = receta.imagen,viewModel)

            Spacer(modifier = Modifier.height(8.dp))

            // Texto de la receta
            Text(text = receta.nombre)
        }
    }
}
@Composable
fun RecipeImage(imagenPath: String,viewModel: PrincipalModel) {
    val coroutineScope = rememberCoroutineScope()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Realiza la tarea de redimensionamiento en un hilo de fondo
    LaunchedEffect(imagenPath) {
        coroutineScope.launch {
            val resizedBitmap = withContext(Dispatchers.Default) {

                viewModel.getResizedBitmap(imagenPath, 200, 200)
            }
            imageBitmap = resizedBitmap
        }
    }

    // Muestra la imagen cuando esté disponible
    imageBitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "Imagen de la receta",
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}



