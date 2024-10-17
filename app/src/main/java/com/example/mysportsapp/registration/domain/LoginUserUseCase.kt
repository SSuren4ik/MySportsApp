package com.example.mysportsapp.registration.domain

import com.example.mysportsapp.registration.data.UserDataModel
import com.google.firebase.auth.AuthResult

class LoginUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(dataModel: UserDataModel): AuthResult {
        return repository.signInUser(dataModel)
    }

}