package com.example.mysportsapp.registration.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysportsapp.registration.data.UserDataModel
import com.example.mysportsapp.registration.domain.SignUpUserUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUserUseCase: SignUpUserUseCase) : ViewModel() {

    fun registerUser(userDataModel: UserDataModel, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                signUpUserUseCase.execute(userDataModel)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}
