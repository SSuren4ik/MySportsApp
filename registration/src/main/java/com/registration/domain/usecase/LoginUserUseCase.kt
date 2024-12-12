package com.registration.domain.usecase

import com.google.firebase.auth.AuthResult
import com.registration.domain.models.UserDataModel
import com.registration.domain.repository.UserRepositoryInterface

class LoginUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(dataModel: UserDataModel): AuthResult {
        return repository.signInUser(dataModel)
    }
}