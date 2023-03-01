package com.example.passlive.domain.reposity.usecase

import com.example.passlive.domain.model.User
import com.example.passlive.domain.reposity.AuthRepository
import com.example.passlive.domain.reposity.UserRepository
import com.example.passlive.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Registro de caso de uso
class FirebaseSignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email:String, password:String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        val userUID: String = authRepository.signup(email, password)
        //En caso de no ir vacío, realizamos un Succes
        if (userUID.isNotEmpty()){
            //creamos usuario
            userRepository.createUser(User(
            email= email,
            uid = userUID
            ))

            emit(Resource.Success(true))
            //En caso de ir vacío enviamos el mensaje de error
        }else{
            emit(Resource.Error("Ha ocurrido un error en el registro"))
        }
    }
    }
