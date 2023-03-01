package com.example.passlive.data.remote

import com.example.passlive.data.util.FirebaseConstants.USERS_COLLECTION
import com.example.passlive.domain.model.User
import com.example.passlive.domain.reposity.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//Creamos nuestra base de datos remota
class FirestoreUserRepositoryImpl @Inject constructor(

): UserRepository {

    //Creamos usuario
    override suspend fun createUser(user: User): Boolean {
        return try {
            var isSuccessful = false
            FirebaseFirestore.getInstance().collection(USERS_COLLECTION)
                .document(user.uid)
                .set(user, SetOptions.merge())
                .addOnCompleteListener { isSuccessful = it.isSuccessful }
                .await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    //Obtenemos usuario
    override suspend fun getUser(uid: String): User {
        return try {
            //Firestore-colección de usuarios, al domumento uid, obtenemos usuario, lo mapeamos
            //            a la clase usuario
            var loggedUser = User()
            FirebaseFirestore.getInstance().collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener {
                    loggedUser = it.toObject(User::class.java)!!
                }
                .await()
            loggedUser
        } catch (e: Exception) {
            User()
        }
    }
}