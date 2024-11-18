package com.example.prueba.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prueba.ui.login.UI.LoginScreen
import com.example.prueba.ui.login.UI.RegisterScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController=navController, startDestination = Login ){
        composable<Login> {
            LoginScreen(viewModel(), {navController.navigate(Registro)})
        }
        composable<Registro> {
            RegisterScreen(viewModel())
        }
    }
}