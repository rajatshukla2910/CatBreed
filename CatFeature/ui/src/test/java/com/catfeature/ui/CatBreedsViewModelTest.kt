package com.catfeature.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.catfeature.data.ApiResponse
import com.catfeature.data.CatApiData
import com.catfeature.data.CatBreedsRepo
import com.catfeature.data.ImageData
import com.catfeature.data.ListViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class CatBreedsViewModelTest {

    private val catBreedsRepo: CatBreedsRepo = mock()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loading should be set when called intially`() = runTest(testDispatcher) {
        val loadingFlow = flowOf(ApiResponse.Loading)
        whenever(catBreedsRepo.getCatBreeds(any())).thenReturn(loadingFlow)
        val viewModel = CatBreedsViewModel(catBreedsRepo)
        val loadingObserver = mock<Observer<Boolean>>()
        viewModel.loading.observeForever(loadingObserver)
        advanceUntilIdle()

        verify(loadingObserver).onChanged(true)
        viewModel.loading.removeObserver(loadingObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `error should be set when repo gives error`() = runTest {
        val loadingFlow = flowOf(ApiResponse.Error("some error"))
        whenever(catBreedsRepo.getCatBreeds(any())).thenReturn(loadingFlow)
        val viewModel = CatBreedsViewModel(catBreedsRepo)
        val errorObserver = mock<Observer<Boolean>>()
        viewModel.error.observeForever(errorObserver)
        advanceUntilIdle()
        verify(errorObserver).onChanged(true)
        viewModel.loading.removeObserver(errorObserver)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `success test for cat breed repo`() = runTest {

        val mockData = listOf(
            CatApiData(
                id = "1",
                name = "Persian",
                origin = "India",
                life_span = "12-15",
                description = "some description",
                image = ImageData("some url")
            )
        )
        val dataFlow = flow { emit(ApiResponse.Success(mockData)) }
        whenever(catBreedsRepo.getCatBreeds(any())).thenReturn(dataFlow)
        val viewModel = CatBreedsViewModel(catBreedsRepo)
        val dataObserver = mock<Observer<List<ListViewData>>>()
        viewModel.catBreeds.observeForever(dataObserver)
        advanceUntilIdle()
        verify(dataObserver).onChanged(mockData)
        viewModel.catBreeds.removeObserver(dataObserver)
    }

    @Test
    fun `setSelectedBreed test`() = runTest {

        val mockData =
            CatApiData(
                id = "1",
                name = "Persian",
                origin = "India",
                life_span = "12-15",
                description = "some description",
                image = ImageData("some url")
            )
        val loadingFlow = flowOf(ApiResponse.Error("some error"))
        whenever(catBreedsRepo.getCatBreeds(any())).thenReturn(loadingFlow)
        val viewModel = CatBreedsViewModel(catBreedsRepo)
        viewModel.setSelectedBreed(mockData)

        val selectedBreedObserver = mock<Observer<ListViewData>>()
        viewModel.selectedbreed.observeForever(selectedBreedObserver)
        advanceUntilIdle()
        verify(selectedBreedObserver).onChanged(mockData)
        viewModel.selectedbreed.removeObserver(selectedBreedObserver)
    }

}