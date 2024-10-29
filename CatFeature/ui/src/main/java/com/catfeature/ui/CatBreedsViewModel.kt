package com.catfeature.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catfeature.data.ApiResponse
import com.catfeature.data.CatBreedsRepo
import com.catfeature.data.ListViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatBreedsViewModel @Inject constructor(val catBreedsRepo: CatBreedsRepo): ViewModel() {

    companion object {
        private const val COOL_DOWN = 2000L
    }

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _error = MutableLiveData(false)
    val error: LiveData<Boolean> get() = _error

    private var _catBreeds = MutableLiveData<List<ListViewData>>()
    val catBreeds: LiveData<List<ListViewData>> get() = _catBreeds

    private var _selectedbreed = MutableLiveData<ListViewData>()
    val selectedbreed: LiveData<ListViewData> get() = _selectedbreed

    private var lastFetchedTime = 0L

    private var page = -1


    init {
        loadData(System.currentTimeMillis())
    }

    fun loadData(currentTimeMillis: Long) {
        lastFetchedTime =  currentTimeMillis
        viewModelScope.launch {
            catBreedsRepo.getCatBreeds(++page).collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _loading.postValue(false)
                        _error.postValue(true)
                    }

                    ApiResponse.Loading -> {if(page==0) _loading.postValue(true)}
                    is ApiResponse.Success -> {
                        _catBreeds.postValue(_catBreeds.value?.toList()?.plus(response.data) ?: response.data)
                        _loading.postValue(false)
                        _error.postValue(false)
                    }
                }
            }

        }
    }

    fun canLoadMore(currentTimeMillis: Long): Boolean {
        return loading.value == false && currentTimeMillis - lastFetchedTime > COOL_DOWN
    }

    fun setSelectedBreed(selectedBreed: ListViewData) {
        _selectedbreed.value = selectedBreed
    }
}