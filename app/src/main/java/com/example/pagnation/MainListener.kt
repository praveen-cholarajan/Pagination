package com.example.pagnation

import com.example.pagnation.response.PaginationResponse

interface MainListener {

    fun userDetailFetch(user:PaginationResponse)
    fun getUserDetailFetch():PaginationResponse?
}