package com.example.prueba.ui.login.UI

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prueba.R
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prueba.app.RoomApp
import com.example.prueba.data.entites.receta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun LoginScreen(viewModel: LoginViewModel, navRegistro: () -> Unit, navPrincipal:  () -> Unit){
    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.Fondo))
        ){
        Login(Modifier.align(Alignment.Center),viewModel,navRegistro,navPrincipal)
    }



}





@Composable
private  fun Login(
    modifier: Modifier,
    viewModel: LoginViewModel,
    navRegistro: () -> Unit,
    navPrincipal: () -> Unit
) {

    val email : String by viewModel.email.observeAsState("")
    val pas : String by viewModel.pas.observeAsState("")
    val loginEnb:Boolean by viewModel.loginEnb.observeAsState(false)
    val loading :Boolean by viewModel.isLoading.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    val registro = navRegistro
    val correcto:Boolean by viewModel.correcto.observeAsState(false)
    //saveRecipe(viewModel)

    LaunchedEffect(correcto) {
        if (correcto){

            navPrincipal()
        }
    }


    if (loading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier) {
            HeaderImage()
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onLoginChanged(it, pas) }
            Spacer(modifier = Modifier.padding(16.dp))
            PaswordField(pas) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            resgistrarse(Modifier.align(Alignment.End),registro)
            Spacer(modifier = Modifier.padding(16.dp))
            LogBotn(loginEnb) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }

        }
    }
}

/*@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun saveRecipe(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // Usar corutinas

    // Usa el estado para almacenar el estado de la imagen una vez que se haya guardado
    var imagePath by remember { mutableStateOf<String>("") }

    // Realiza la carga y guardado en segundo plano
    LaunchedEffect(true) {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                BitmapFactory.decodeResource(context.resources, R.drawable.prueba) // Carga en segundo plano
            }
            imagePath = saveImageToStorage(context, bitmap) // Guarda en segundo plano
            val recetaItem = receta(
                nombre = "PruebaF",
                pasos = "PruebaF",
                ingredientes = "PruebaF",
                vegano = false,
                gluten = true,
                imagen = imagePath
            )

            // Usamos el ViewModel para insertar la receta en la base de datos
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.insertarReceta(recetaItem)
            }
        }
    }
}

fun saveImageToStorage(context: Context, bitmap: Bitmap): String {
    // Crea un nombre único para la imagen
    val fileName = "imagen_${System.currentTimeMillis()}.jpg"

    // Crea un archivo en el directorio de almacenamiento interno
    val file = File(context.filesDir, fileName)

    try {
        // Usa un FileOutputStream para escribir el bitmap en el archivo
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Guarda la imagen como JPEG
        outputStream.flush()
        outputStream.close()

        // Devuelve la ruta del archivo guardado
        return file.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        return "" // Retorna una cadena vacía si ocurre un error
    }
}*/

@Composable
private fun LogBotn(loginEnb: Boolean, onLoginSelected: () -> Unit) {
    Button(onClick = {onLoginSelected()}, modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.naranja),
            disabledContainerColor = colorResource(id= R.color.analogous_1),
            contentColor = colorResource(id = R.color.analogous_1),
            disabledContentColor = colorResource(id = R.color.naranja)
        ), enabled = loginEnb
    ) {
        Text(text = "Iniciar sesion")
    }
}

@Composable
private fun resgistrarse(align: Modifier, registro: () -> Unit) {
    Text(
        text = "Quieres registarte??",
        modifier = align.clickable {registro()},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaswordField(pas: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = pas,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(text = "Contraseña")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.textBoxes),  // Color de fondo del TextField
            focusedIndicatorColor = colorResource(R.color.darker_shade), // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        )

        )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailField(email: String, onTextFieldChanged:(String) ->Unit) {
    TextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(text = "Email")
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.textBoxes),  // Color de fondo del TextField
            focusedIndicatorColor = colorResource(R.color.darker_shade), // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        )

    )
}

@Composable
private fun HeaderImage(){
    Image(painter = painterResource(id= R.drawable.logoapp), contentDescription = "Header")
}


