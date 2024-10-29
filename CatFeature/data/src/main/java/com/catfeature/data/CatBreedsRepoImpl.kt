package com.catfeature.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CatBreedsRepoImpl @Inject constructor(private val service: CatBreedService): CatBreedsRepo {

    companion object {
        private const val API_KEY = "live_oRDBD6ENEac1B6yCEjNtP0KeyUwd1igejs77imtTpmURhcM4jHJalMNpqJJ4SS4a"
        private const val LIMIT = 10
    }

    override suspend fun getCatBreeds(page: Int): Flow<ApiResponse<List<CatApiData>, String>> {

        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.getCatBreeds(page,LIMIT, API_KEY)
                when {
                    response.isSuccessful -> {
                        val body = response.body()
                        if (body == null) emit(ApiResponse.Error("No Data available"))
                        else {
                            emit(ApiResponse.Success(body))
                        }
                    }

                    else ->
                        emit(ApiResponse.Error("Server Error" + response.code()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error("Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)

    }
}