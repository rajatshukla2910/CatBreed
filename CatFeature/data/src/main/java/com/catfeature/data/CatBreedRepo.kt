package com.catfeature.data

import kotlinx.coroutines.flow.Flow

interface CatBreedsRepo {
    suspend fun getCatBreeds(page: Int): Flow<ApiResponse<List<CatApiData>, String>>
}