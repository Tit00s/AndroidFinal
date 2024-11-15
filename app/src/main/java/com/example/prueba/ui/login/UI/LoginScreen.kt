package com.example.prueba.ui.login.UI

import androidx.compose.foundation.Image
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
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel){

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)){
        Login(Modifier.align(Alignment.Center),viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email : String by viewModel.email.observeAsState("")
    val pas : String by viewModel.pas.observeAsState("")
    val loginEnb:Boolean by viewModel.loginEnb.observeAsState(false)
    val loading :Boolean by viewModel.isLoading.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()


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
            ForgotPas(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LogBotn(loginEnb) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }

        }
    }



}

@Composable
fun LogBotn(loginEnb: Boolean, onLoginSelected: () -> Unit) {
    Button(onClick = {onLoginSelected()}, modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            disabledContainerColor = Color.Red,
            contentColor = Color(0xFFFB9600),
            disabledContentColor = Color.Black
        ), enabled = loginEnb
    ) {
        Text(text = "Iniciar sesion")
    }
}

@Composable
fun ForgotPas(align: Modifier) {
    Text(
        text = "Olvidaste la contraseña?",
        modifier = align.clickable {},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaswordField(pas: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = pas,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Contraseña")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.LightGray,  // Color de fondo del TextField
            focusedIndicatorColor = Color.Blue, // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        )

        )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onTextFieldChanged:(String) ->Unit) {
    TextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Email")
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.LightGray,  // Color de fondo del TextField
            focusedIndicatorColor = Color.Blue, // Color del borde cuando está enfocado
            unfocusedIndicatorColor = Color.Gray // Color del borde cuando no está enfocado
        )

    )
}

@Composable
fun HeaderImage(){
    Image(painter = painterResource(id= R.drawable.logoapp), contentDescription = "Header")
}


