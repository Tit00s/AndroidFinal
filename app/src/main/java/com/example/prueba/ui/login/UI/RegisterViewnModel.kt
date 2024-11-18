package com.example.prueba.ui.login.UI

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class RegisterViewnModel: ViewModel() {

    private val _email= MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _pas= MutableLiveData<String>()
    val pas: LiveData<String> = _pas

    private val _regEnb= MutableLiveData<Boolean>()
    val regEnb: LiveData<Boolean> = _regEnb

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun onRegisChanged(email: String, pas: String){
        _email.value = email
        _pas.value = pas
        _regEnb.value = isValidEmail(email) && isValidPas(pas)

    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun  isValidPas(pas :String):Boolean = pas.length >6


    suspend fun onRegisSelected(){
        _isLoading.value = true
        delay(4000)
        _isLoading.value =false
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.value.toString(),pas.value.toString()).addOnCompleteListener(){
            if (it.isSuccessful){
                _email.value = "Enhorabuena"
                _pas.value = "Enhorabuena"
            }else{
                _email.value = "Algo ha fallado"
                _pas.value = "Algo ha fallado"
            }
        }


    }
}