package mysportsapp.registration.domain

import com.google.firebase.auth.AuthResult
import mysportsapp.registration.data.UserDataModel

class SignUpUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(userDataModel: UserDataModel): AuthResult {
        return repository.registerUser(userDataModel)
    }
}