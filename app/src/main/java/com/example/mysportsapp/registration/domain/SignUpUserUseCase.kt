package com.example.mysportsapp.registration.domain

import com.example.mysportsapp.registration.data.UserDataModel
import com.google.firebase.auth.AuthResult

class SignUpUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(userDataModel: UserDataModel): AuthResult {
        return repository.registerUser(userDataModel)
    }
}
