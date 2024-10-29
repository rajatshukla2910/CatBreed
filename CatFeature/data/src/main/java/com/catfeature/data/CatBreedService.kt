package com.catfeature.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatBreedService {
    @GET("v1/breeds")
    suspend fun getCatBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("api_key") apiKey: String,
    ): Response<List<CatApiData>>
}