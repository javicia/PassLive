package com.example.passlive.domain.reposity.usecase

import com.example.passlive.domain.reposity.AuthRepository
import com.example.passlive.domain.reposity.UserRepository
import com.example.passlive.util.Resource
import com.example.passlive.domain.model.User
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    //Regresamos usuario para que se muestre en pantalla en el home
    suspend operator fun invoke(email:String, password:String): Flow <Resource<User>> = flow {
        emit(Resource.Loading)
        /*Realizamos el login, en vez de devolver un true o un false, nos devuelve un String que en caso
            de estar vacío, no realizamos el login correctemente y en caso contrario, realizamos
            el login correctemente y el String tiene el Id del usuario que inició sesión. Con ese
            Id vamos a nuestra base de datos remota FirestoreUserRepository con la función getUser,
            iremos a Firestore-colección de usuarios, al domumento uid, obtenemos usuario, lo mapeamos
            a la clase usuario

 */
            val userUID = authRepository.login(email,password)
            if (userUID.isNotEmpty()) {

                val user = userRepository.getUser(uid = userUID)

                emit(Resource.Success(user))
                emit(Resource.Finished)
            } else {
                emit(Resource.Error("Se ha producido un error en el login"))
                emit(Resource.Finished)
            }
        }
    }