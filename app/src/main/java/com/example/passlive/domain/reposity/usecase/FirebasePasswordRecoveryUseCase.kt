package com.example.passlive.domain.reposity.usecase

import com.example.passlive.util.Resource
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class FirebasePasswordRecoveryUseCase
@Inject constructor() {

    suspend operator fun invoke(email: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading)
            var isSuccessful = false
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { isSuccessful = it.isSuccessful }
                .await()
            emit(Resource.Success(isSuccessful))
            emit(Resource.Finished)
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            emit(Resource.Finished)
        }
    }

}