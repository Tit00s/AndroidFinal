package com.example.prueba.ui.recetaNueva.UI

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prueba.R

@Composable
fun RecetaNuevaScreen(navPrincipal: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = colorResource(R.color.Fondo))) {
        recetaContent(viewModel = viewModel(),navPrincipal)

    }
}
@Composable
private fun recetaContent(viewModel: recetaNuevaViewModel, navPrincipal: () -> Unit, ) {
    val titulo: String by viewModel.titulo.observeAsState("")
    val ingredientes: String by viewModel.ingredientes.observeAsState("")
    val pasos: String by viewModel.pasos.observeAsState("")
    var gluten by remember { mutableStateOf(false) }
    var vegano by remember { mutableStateOf(false) }

    val correcto:Boolean by viewModel.correcto.observeAsState(false)

    LaunchedEffect(correcto) {
        if (correcto){
            navPrincipal()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp)) //
        }

        item {
            PickMediaFromGallery(viewModel)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Titulo(titulo) { viewModel.ontextChange(it, ingredientes, pasos) }
        }

        item {
            Ingredientes(ingredientes) { viewModel.ontextChange(titulo, it, pasos) }
        }

        item {
            Pasos(pasos) { viewModel.ontextChange(titulo, ingredientes, it) }
        }

        item {
            gluten(gluten) { gluten = it }
        }

        item {
            vegano(vegano) { vegano = it }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            BotonAdd {
                viewModel.insertarReceta(
                    nombre = titulo,
                    ingredientes = ingredientes,
                    pasos = pasos,
                    vegano = vegano,
                    gluten = gluten
                )
            } // Botón para añadir receta
        }

        item {
            Spacer(modifier = Modifier.height(16.dp)) // Espaciado final
        }
    }
}



@Composable
fun BotonAdd(insertarReceta: () -> Unit) {
    Button(onClick = { insertarReceta() }) {
        Text(text = "Añadir receta")
    }
}
@Composable
fun gluten(gluten:Boolean,onCheckedChange: (Boolean) -> Unit) {
    Text(text = "Lleva gluten?")
        Checkbox(
            checked = gluten,
            onCheckedChange = onCheckedChange
        )
}
@Composable
fun vegano(vegano:Boolean,onCheckedChange: (Boolean) -> Unit) {
    Text(text = "Es vegano?")
    Checkbox(
        checked = vegano,
        onCheckedChange = onCheckedChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pasos(pasos: String,  onTextFieldChanged:(String) ->Unit) {
    Text(text = "Pasos")
    TextField(
        value = pasos,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.textBoxes),  // Color de fondo del TextField
            focusedIndicatorColor = colorResource(R.color.darker_shade), // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        ))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ingredientes(ingredientes: String,  onTextFieldChanged:(String) ->Unit) {
    Text(text = "Ingredientes")
    TextField(
        value = ingredientes,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.textBoxes),  // Color de fondo del TextField
            focusedIndicatorColor = colorResource(R.color.darker_shade), // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        ))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Titulo(titulo: String,  onTextFieldChanged:(String) ->Unit) {
    Text(text = "Titulo")
    TextField(
        value = titulo,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.textBoxes),  // Color de fondo del TextField
            focusedIndicatorColor = colorResource(R.color.darker_shade), // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        ))
}


@Composable
fun PickMediaFromGallery(viewModel: recetaNuevaViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // Convierte el URI en un Bitmap y guarda la imagen
            if (uri != null) {
                val bitmap = viewModel.uriToBitmap(context, uri)
                if (bitmap != null) {
                    viewModel.saveImage(context, bitmap)
                }
            }
        }
    )

    Button(onClick = { launcher.launch("image/*") }) {
        Text("Seleccionar Imagen")
    }
}