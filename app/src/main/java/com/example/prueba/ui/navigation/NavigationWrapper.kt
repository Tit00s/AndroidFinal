package com.example.prueba.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prueba.ui.login.UI.LoginScreen
import com.example.prueba.ui.principal.UI.PrincipalScreen
import com.example.prueba.ui.recetaNueva.UI.RecetaNuevaScreen
import com.example.prueba.ui.register.UI.RegisterScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController=navController, startDestination = Login ){
        composable<Login> {
            LoginScreen(viewModel(), {navController.navigate(Registro)},{navController.navigate(Principal)})
        }
        composable<Registro> {
            RegisterScreen(viewModel()) { navController.navigate(Login) }
        }
        composable<Principal> {
            PrincipalScreen(viewModel()) { navController.navigate(recetaNueva)}
        }
        composable<recetaNueva> {
            RecetaNuevaScreen() {navController.navigate(Principal)}
        }
    }
}