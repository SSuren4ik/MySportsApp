package mysportsapp.registration.data

import com.google.firebase.auth.AuthResult
import mysportsapp.registration.domain.UserRepositoryInterface

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