package com.example.passlive.domain.reposity

interface AuthRepository {
    suspend fun login(email:String, password:String): String

    suspend fun signup (email:String, password:String): String
}