package mysportsapp.registration.domain

import com.google.firebase.auth.AuthResult
import mysportsapp.registration.data.UserDataModel

class LoginUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(dataModel: UserDataModel): AuthResult {
        return repository.signInUser(dataModel)
    }
}