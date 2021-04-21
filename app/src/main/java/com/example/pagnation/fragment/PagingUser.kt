package com.example.pagnation.fragment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagnation.network.ApiService
import com.example.pagnation.response.PaginationResponse


class PagingUser (private val service: ApiService) :
    PagingSource<Int, PaginationResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PaginationResponse> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getShopsDataList(10,pageNumber)
            val pagedResponse = response.body()

            var nextPageNumber: Int? =null
            if (pagedResponse != null) {
                nextPageNumber = if (pagedResponse.isEmpty()) {
                    null
                }else{
                    pageNumber +1
                }
            }

            LoadResult.Page(
                data = pagedResponse.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PaginationResponse>): Int = 1
}