package com.example.mysportsapp.registration.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.LoginUserUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginInUserUseCase: LoginUserUseCase) : ViewModel() {

    suspend fun loginUser(email:String, password:String) {
        val userDataModel = UserDataModel(email, password, "")
        loginInUserUseCase.execute(userDataModel)
    }
}