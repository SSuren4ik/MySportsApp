package com.registration.domain.usecase

import com.google.firebase.auth.AuthResult
import com.registration.domain.models.UserDataModel
import com.registration.domain.repository.UserRepositoryInterface

class SignUpUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(userDataModel: UserDataModel): AuthResult {
        return repository.registerUser(userDataModel)
    }
}