package com.example.mysportsapp.registration.domain

import com.example.mysportsapp.registration.data.UserDataModel
import com.google.firebase.auth.AuthResult

interface UserRepositoryInterface {
    suspend fun registerUser(userDataModel: UserDataModel): AuthResult
    suspend fun signInUser(userDataModel: UserDataModel): AuthResult
}