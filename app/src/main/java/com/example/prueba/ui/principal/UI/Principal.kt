package com.example.prueba.ui.principal.UI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.prueba.R


@Composable
fun principalScreen(){

    Box(Modifier.fillMaxWidth().background(color = colorResource(R.color.Fondo))){
        principal(Modifier)
    }

}

@Composable
private fun principal(modifier: Modifier){

    Column(modifier){ }

}