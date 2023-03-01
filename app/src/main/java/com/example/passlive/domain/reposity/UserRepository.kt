package com.example.passlive.domain.reposity

import com.example.passlive.domain.model.User


//Creamos el repositorio de usuario
//Creamos usuario
//Devolvemos usuario
interface UserRepository {
    suspend fun createUser(user:User): Boolean
    suspend fun getUser(uid:String): User
}