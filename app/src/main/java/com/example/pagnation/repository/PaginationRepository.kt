package com.example.pagnation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagnation.fragment.PagingUser
import com.example.pagnation.network.SafeApiRequest
import com.example.pagnation.network.ApiService
import com.example.pagnation.response.PaginationResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaginationRepository @Inject constructor(private val apiService: ApiService) : SafeApiRequest() {

    suspend fun getUserList(): Flow<PagingData<PaginationResponse>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { PagingUser(apiService) }
    ).flow

}