package com.example.passlive.di

import com.example.passlive.data.remote.FirebaseAuthRepositoryImpl
import com.example.passlive.data.remote.FirestoreUserRepositoryImpl
import com.example.passlive.domain.reposity.AuthRepository
import com.example.passlive.domain.reposity.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//Inyectamos repositorios
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepository: FirebaseAuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(userRepository: FirestoreUserRepositoryImpl): UserRepository
}