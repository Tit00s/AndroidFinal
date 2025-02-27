package com.example.prueba.ui.login.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.app.RoomApp
import com.example.prueba.data.entites.receta
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class LoginViewModel: ViewModel(){

    private val _email=MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _pas=MutableLiveData<String>()
    val pas: LiveData<String> = _pas

    private val _loginEnb=MutableLiveData<Boolean>()
    val loginEnb: LiveData<Boolean> = _loginEnb

    private val _isLoading=MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _Correcto = MutableLiveData<Boolean>()
    val correcto:LiveData<Boolean> =_Correcto


    fun onLoginChanged(email: String, pas: String) {
        _email.value = email
        _pas.value = pas
        _loginEnb.value = isValidEmail(email) && isValidPas(pas)

    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun  isValidPas(pas :String):Boolean = pas.length >6


    suspend fun onLoginSelected(){
        _isLoading.value = true
        delay(5000)
        _isLoading.value =false
         FirebaseAuth.getInstance().signInWithEmailAndPassword(email.value.toString(), pas.value.toString()).addOnCompleteListener(){
             if (it.isSuccessful){
                 val u = FirebaseAuth.getInstance().currentUser
                 if (u?.isEmailVerified == true){
                     _Correcto.postValue(true)
                 }else{
                     _email.value = "Verificate"
                     _pas.value = "Verificate"
                     u?.sendEmailVerification()
                 }

             }else{
                 _email.value = "Algo ha fallado"
                 _pas.value = "Algo ha fallado"
             }
         }


    }




}