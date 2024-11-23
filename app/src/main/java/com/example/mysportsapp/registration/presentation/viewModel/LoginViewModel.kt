package com.example.mysportsapp.registration.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.LoginUserUseCase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginInUserUseCase: LoginUserUseCase) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _onLoginSuccess = MutableSharedFlow<Unit>()
    val onLoginSuccess: SharedFlow<Unit> = _onLoginSuccess

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            if (!areFieldsEmpty(email, password)) {
                if (isEmailValid(email)) {
                    val userDataModel = UserDataModel(email, password, "")
                    try {
                        loginInUserUseCase.execute(userDataModel)
                        _errorMessage.value = null
                        _onLoginSuccess.emit(Unit)
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        Log.d("Log", "FirebaseAuthInvalidCredentialsException" + e.message.toString())
                        _errorMessage.value = "Такого пользователя не существует"
                    } catch (e: FirebaseNetworkException) {
                        Log.d("Log", "FirebaseNetworkException" + e.message.toString())
                        _errorMessage.value = "Проблемы с соединением"
                    } catch (e: FirebaseTooManyRequestsException) {
                        Log.d("Log", "FirebaseTooManyRequestsException" + e.message.toString())
                        _errorMessage.value = "Слишком много запросов, пожалуста, подождите"
                    } catch (e: Exception) {
                        Log.d("Log",  "Exception" +  e.message.toString())
                        _errorMessage.value = "Другая ошибка " + e.message
                    }
                } else {
                    _errorMessage.value = "Неверный формат почты"
                }
            } else {
                _errorMessage.value = "Одно из полей пустое"
            }
        }
    }

    private fun areFieldsEmpty(email: String, password: String): Boolean {
        return email.isEmpty() || password.isEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}