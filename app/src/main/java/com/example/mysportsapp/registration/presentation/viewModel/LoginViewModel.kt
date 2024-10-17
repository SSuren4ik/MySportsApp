package com.example.mysportsapp.registration.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.LoginUserUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginInUserUseCase: LoginUserUseCase) : ViewModel() {

    fun loginUser(userDataModel: UserDataModel, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                loginInUserUseCase.execute(userDataModel)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}