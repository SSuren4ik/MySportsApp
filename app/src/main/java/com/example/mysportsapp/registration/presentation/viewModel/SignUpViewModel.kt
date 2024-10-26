package com.example.mysportsapp.registration.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.SignUpUserUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUserUseCase: SignUpUserUseCase) : ViewModel() {

    suspend fun registerUser(email: String, username: String, password: String) {
        val userDataModel = UserDataModel(email, password, username)
        signUpUserUseCase.execute(userDataModel)
    }
}
