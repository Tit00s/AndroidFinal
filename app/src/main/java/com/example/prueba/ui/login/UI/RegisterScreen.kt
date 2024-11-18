package com.example.prueba.ui.login.UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prueba.R
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewnModel: RegisterViewnModel){

    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.Fondo))
    ){
        Regis(modifier = Modifier.align(Alignment.Center), viewModel())
    }


}

@Composable
fun Regis(modifier: Modifier, viewModel: RegisterViewnModel) {

    val email:String by viewModel.email.observeAsState("")
    val pas:String by viewModel.pas.observeAsState("")
    val regEnb:Boolean by viewModel.regEnb.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    val loading :Boolean by viewModel.isLoading.observeAsState(false)

    if (loading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = modifier) {
            HeaderImage()
            Spacer(modifier= Modifier.padding(16.dp))
            EmailField(email) {viewModel.onRegisChanged(it,pas)}
            Spacer(modifier= Modifier.padding(16.dp))
            PaswordField(pas) {viewModel.onRegisChanged(email,it) }
            RegBotn(regEnb) { coroutineScope.launch {
                viewModel.onRegisSelected()
            }}

        }
    }


}

@Composable
private fun RegBotn(RegisEnb: Boolean, onRegisSelected: () -> Unit) {
    Button(onClick = {onRegisSelected()}, modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.naranja),
            disabledContainerColor = colorResource(id= R.color.analogous_1),
            contentColor = colorResource(id = R.color.analogous_1),
            disabledContentColor = colorResource(id = R.color.naranja)
        ), enabled = RegisEnb
    ) {
        Text(text = "Registrarte")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailField(email:String, onRegisChanged:(String)->Unit ) {
    TextField(
        value = email,
        onValueChange = {onRegisChanged(it)},
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaswordField(pas: String, onRegisChanged:(String)->Unit) {
    TextField(
        value = pas,
        onValueChange = {onRegisChanged(it)},
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
@Composable
private fun HeaderImage(){
    Image(painter = painterResource(id= R.drawable.logoapp), contentDescription = "Header")
}