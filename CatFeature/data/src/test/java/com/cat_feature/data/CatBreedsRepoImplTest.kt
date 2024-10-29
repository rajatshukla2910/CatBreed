package com.cat_feature.data


import com.catfeature.data.ApiResponse
import com.catfeature.data.CatApiData
import com.catfeature.data.CatBreedService
import com.catfeature.data.CatBreedsRepoImpl
import com.catfeature.data.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any

import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response


class CatBreedsRepoImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private val service: CatBreedService = mock()

   // private lateinit var catBreedsRepo: CatBreedsRepo
    private val mockData = listOf(
        CatApiData(
            id = "1",
            name = "Persian",
            origin = "India",
            life_span = "12-15",
            description = "some description",
            image = ImageData("some url")
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        //catBreedsRepo = CatBreedsRepoImpl(service)
    }

    @Test
    fun `test loading state`() = runTest(testDispatcher) {
        val catBreedsRepo = CatBreedsRepoImpl(service)
        val response = Response.error<List<CatApiData>>(500, mock())
        whenever(service.getCatBreeds(0, 0, "fake key")).thenReturn(response)

        val result = catBreedsRepo.getCatBreeds(any()).toList()

        Assert.assertTrue(result.contains(ApiResponse.Loading))
    }

    @Test
    fun `test success state`() = runTest(testDispatcher) {
        val catBreedsRepo = CatBreedsRepoImpl(service)
        val response = Response.success<List<CatApiData>>(mockData)
        whenever(service.getCatBreeds(any(), any(), any())).thenReturn(response)

        val result = catBreedsRepo.getCatBreeds(any()).toList()

        Assert.assertTrue(result.contains(ApiResponse.Success(mockData)))
    }

    @Test
    fun `test error state`() = runTest(testDispatcher) {
        val catBreedsRepo = CatBreedsRepoImpl(service)
        val response = Response.error<List<CatApiData>>(500, mock())
        whenever(service.getCatBreeds(any(), any(), any())).thenReturn(response)

        val result = catBreedsRepo.getCatBreeds(any()).toList()

        Assert.assertTrue(result.contains(ApiResponse.Error("Server Error500")))
    }

    @Test
    fun `test exception case`() = runTest(testDispatcher) {
        val catBreedsRepo = CatBreedsRepoImpl(service)
        whenever(service.getCatBreeds(anyInt(), anyInt(), anyString())).thenAnswer { Exception() }

        val result = catBreedsRepo.getCatBreeds(any()).toList()

        Assert.assertTrue(result.contains(ApiResponse.Error("Something went wrong")))
    }

}
