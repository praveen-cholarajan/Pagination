package com.example.pagnation.network


import com.example.pagnation.response.PaginationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Method to get all shops data
    @GET("users/geerlingguy/repos")
    suspend fun getShopsDataList(
        @Query("per_page") perPage:Int,
        @Query("page") page :Int
    ): Response<List<PaginationResponse>>


}