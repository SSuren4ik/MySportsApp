package mysportsapp.registration.domain

import com.google.firebase.auth.AuthResult
import mysportsapp.registration.data.UserDataModel

interface UserRepositoryInterface {
    suspend fun registerUser(userDataModel: UserDataModel): AuthResult
    suspend fun signInUser(userDataModel: UserDataModel): AuthResult
}