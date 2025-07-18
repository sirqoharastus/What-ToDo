package com.abdulqohar.whattodo.di

import com.abdulqohar.whattodo.data.remote.service.ApiService
import com.abdulqohar.whattodo.data.repository.ToDoRepositoryImpl
import com.abdulqohar.whattodo.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl(): String = "https://dummyjson.com/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideToDoRepository(apiService: ApiService): ToDoRepository =
        ToDoRepositoryImpl(apiService)
}