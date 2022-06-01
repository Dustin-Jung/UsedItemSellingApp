package com.android.aop.part2.useditemsellingapp.di

import com.android.aop.part2.useditemsellingapp.data.repo.FirebaseRepository
import com.android.aop.part2.useditemsellingapp.data.source.FirebaseRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(firebaseRepository: FirebaseRepository): FirebaseRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseRemoteDataSource(firebaseRemoteDataSource: FirebaseRemoteDataSource): FirebaseRemoteDataSource

}