package com.example.pagnation.fragment

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagnation.repository.PaginationRepository
import com.example.pagnation.response.PaginationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: PaginationRepository) : ViewModel() {

    val progressBarVisibility = ObservableField(View.GONE)

    private lateinit var _charactersFlow: Flow<PagingData<PaginationResponse>>
    val charactersFlow: Flow<PagingData<PaginationResponse>>
        get() = _charactersFlow




    fun getUserDataList() = launchPagingAsync({
        repository.getUserList().cachedIn(viewModelScope)
    }, {
        progressBarVisibility.set(View.GONE)
        _charactersFlow = it
    })



    private inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {

        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                progressBarVisibility.set(View.GONE)
              Timber.d("Exception : ${ex.message}")
            }
        }
    }

}