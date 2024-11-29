package com.registration.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FirebaseAuthStorage : Storage {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://mysports-b52fe-default-rtdb.europe-west1.firebasedatabase.app").reference

    override suspend fun signIn(userDataModel: UserDataModel): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(userDataModel.email, userDataModel.password)
            .await()
    }

    override suspend fun registerUser(userDataModel: UserDataModel): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(userDataModel.email, userDataModel.password)
            .await()
    }

    override suspend fun saveUserInStorage(uid: String, userDataModel: UserDataModel) {
        val userInfo = mapOf("email" to userDataModel.email, "username" to userDataModel.userName)
        firebaseDatabase.child("Users").child(uid).setValue(userInfo).await()
    }
}