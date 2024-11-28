package com.example.prueba.ui.principal.UI

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.prueba.R
import com.example.prueba.data.entites.receta
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun PrincipalScreen(viewModel: PrincipalViewModel, navController: () -> Unit) {
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
        Boton(navController)
    }
}

@Composable
private fun PrincipalContent(
    recetas: List<receta>,
    viewModel: PrincipalViewModel,
) {
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
            // Nombre de la receta
            Text(
                text = receta.nombre,
                style = MaterialTheme.typography.bodyLarge

            )

            // Imagen de la receta
            RecipeImage(imagenPath = receta.imagen, viewModel,receta.id)

            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}

@Composable
fun RecipeImage(imagenPath: String, viewModel: PrincipalViewModel,recetaId:Int) {
    val delete = SwipeAction(
        onSwipe = {viewModel.borrarReceta(recetaId)},
        icon = { Icon(Icons.Default.Delete, contentDescription = "Eliminar") },
        background = Color.Red,
    )
    SwipeableActionsBox(endActions = listOf(delete), modifier = Modifier.height(200.dp)){
        Card {
            AsyncImage(
                model = imagenPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                )
        }
    }

}
@Composable
fun Boton(navController: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {  // Usar un Box como contenedor para el alineamiento
        FloatingActionButton(
            onClick = { navController() },
            modifier = Modifier
                .align(Alignment.BottomEnd)  // Alinea el FAB en la esquina inferior derecha
                .padding(16.dp)  // Añadir espacio alrededor del FAB
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir")
        }
    }
}





