package com.registration.domain.repository

import com.google.firebase.auth.AuthResult
import com.registration.domain.models.UserDataModel

interface UserRepositoryInterface {
    suspend fun registerUser(userDataModel: UserDataModel): AuthResult
    suspend fun signInUser(userDataModel: UserDataModel): AuthResult
}