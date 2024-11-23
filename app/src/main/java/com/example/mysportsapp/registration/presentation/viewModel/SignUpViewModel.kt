package com.example.mysportsapp.registration.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.SignUpUserUseCase

class SignUpViewModel(private val signUpUserUseCase: SignUpUserUseCase) : ViewModel() {

    suspend fun registerUser(email: String, username: String, password: String) {
        val userDataModel = UserDataModel(email, password, username)
        signUpUserUseCase.execute(userDataModel)
    }
}