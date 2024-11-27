package com.example.prueba.ui.principal.UI

import android.graphics.Bitmap
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.prueba.R
import com.example.prueba.data.entites.receta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@Composable
fun principalScreen(viewModel: PrincipalViewModel) {
    // Obtenemos las recetas desde el ViewModel


    val recetas by viewModel.recetasLiveData.observeAsState(emptyList())

    // UI principal
    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.Fondo))
    ) {
        LaunchedEffect(Unit) {
            viewModel.fetchRecetas()
        }
        PrincipalContent(recetas, viewModel)
    }
}

@Composable
private fun PrincipalContent(recetas: List<receta>, viewModel: PrincipalViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(recetas) { receta ->
                RecetaCard(receta = receta, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RecetaCard(receta: receta, viewModel: PrincipalViewModel) {
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
            // Imagen de la receta
            RecipeImage(imagenPath = receta.imagen, viewModel)

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre de la receta
            Text(
                text = receta.nombre,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun RecipeImage(imagenPath: String, viewModel: PrincipalViewModel) {
    Card {
        AsyncImage(
            model = imagenPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,

        )

    }
}



