package com.android.aop.part2.useditemsellingapp.di

import com.android.aop.part2.useditemsellingapp.data.repo.FirebaseRepository
import com.android.aop.part2.useditemsellingapp.data.repo.FirebaseRepositoryImpl
import com.android.aop.part2.useditemsellingapp.data.source.FirebaseRemoteDataSource
import com.android.aop.part2.useditemsellingapp.data.source.FirebaseRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseRemoteDataSource(firebaseRemoteDataSourceImpl: FirebaseRemoteDataSourceImpl): FirebaseRemoteDataSource

}