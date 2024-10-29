package com.catfeature.data.di

import com.catfeature.data.CatBreedService
import com.catfeature.data.CatBreedsRepo
import com.catfeature.data.CatBreedsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(SingletonComponent:: class)
@Module
object DataLayerModule {

    @Provides
    fun provideCatBreedRepo(service: CatBreedService): CatBreedsRepo {
        return  CatBreedsRepoImpl(service)
    }

    @Provides
    fun provideCatBreedService(): CatBreedService {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CatBreedService::class.java)
    }
}