package com.example.mysportsapp.registration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.LoginUserUseCase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginInUserUseCase: LoginUserUseCase) : ViewModel() {

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    private val _onLoginSuccess = MutableSharedFlow<Unit>()
    val onLoginSuccess: SharedFlow<Unit> = _onLoginSuccess

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            if (!areFieldsEmpty(email, password)) {
                if (isEmailValid(email)) {
                    val userDataModel = UserDataModel(email, password, "")
                    try {
                        loginInUserUseCase.execute(userDataModel)
                        _onLoginSuccess.emit(Unit)
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        _errorMessage.emit("Такого пользователя не существует")
                    } catch (e: FirebaseNetworkException) {
                        _errorMessage.emit("Проблемы с соединением")
                    } catch (e: FirebaseTooManyRequestsException) {
                        _errorMessage.emit("Слишком много запросов, пожалуста, подождите")
                    }
                } else {
                    _errorMessage.emit("Неверный формат почты")
                }
            } else {
                _errorMessage.emit("Одно из полей пустое")
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