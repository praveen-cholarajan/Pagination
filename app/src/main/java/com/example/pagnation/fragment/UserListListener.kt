package com.example.pagnation.fragment

import com.example.pagnation.response.PaginationResponse

interface UserListListener {

    fun userData(data:PaginationResponse?,comments:String?)

}