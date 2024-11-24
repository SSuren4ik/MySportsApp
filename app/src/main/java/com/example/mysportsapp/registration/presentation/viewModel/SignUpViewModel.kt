package com.example.mysportsapp.registration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.SignUpUserUseCase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUserUseCase: SignUpUserUseCase) : ViewModel() {

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    private val _onSignUpSuccess = MutableSharedFlow<Unit>()
    val onSignUpSuccess: SharedFlow<Unit> = _onSignUpSuccess

    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            if (!areFieldsEmpty(email, password, username)) {
                if (isEmailValid(email)) {
                    val dataModel = UserDataModel(email, password, username)
                    try {
                        signUpUserUseCase.execute(dataModel)
                        _onSignUpSuccess.emit(Unit)
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        _errorMessage.emit("Недостаточно хороший пароль")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        _errorMessage.emit("Неправильно указана почта")
                    } catch (e: FirebaseAuthUserCollisionException) {
                        _errorMessage.emit("Учетная запись с данной почтой уже существует")
                    } catch (e: FirebaseException) {
                        _errorMessage.emit(e.message.toString())
                    }
                } else {
                    _errorMessage.emit("Неверный формат почты")
                }
            } else {
                _errorMessage.emit("Одно из полей пустое")
            }
        }
    }

    private fun areFieldsEmpty(email: String, password: String, username: String): Boolean {
        return email.isEmpty() || password.isEmpty() || username.isEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}