package com.registration.data

import com.google.firebase.auth.AuthResult
import com.registration.domain.models.UserDataModel
import com.registration.domain.repository.UserRepositoryInterface

class UserRepositoryImpl : UserRepositoryInterface {
    private val firebaseAuthService = FirebaseAuthStorage()

    override suspend fun registerUser(userDataModel: UserDataModel): AuthResult {
        val result = firebaseAuthService.registerUser(userDataModel)
        firebaseAuthService.saveUserInStorage(result.user!!.uid, userDataModel)
        return result
    }

    override suspend fun signInUser(userDataModel: UserDataModel): AuthResult {
        return firebaseAuthService.signIn(userDataModel)
    }

}