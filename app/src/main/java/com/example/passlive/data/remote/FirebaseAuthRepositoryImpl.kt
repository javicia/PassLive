package com.example.passlive.data.remote

import com.example.passlive.domain.reposity.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun login(email: String, password: String): String {
        return try {
            var userUID = ""
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                     userUID = it.user?.uid?: ""
                }
                .await()
            userUID
        }catch (e: Exception){
            ""
        }
    }
    override suspend fun signup(email: String, password: String): String {
        return try {
            var userUID = ""
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userUID = it.user?.uid?: ""
            }
            .await()
             userUID
        }catch (e:Exception){
            ""
        }
    }
}