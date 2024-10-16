package com.example.mysportsapp.registration.data

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

interface Storage {

    suspend fun signIn(userDataModel: UserDataModel): AuthResult

    suspend fun registerUser(userDataModel: UserDataModel): AuthResult

    suspend fun saveUserInStorage(uid: String, userDataModel: UserDataModel)
}