package com.example.pagnation.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.pagnation.response.PaginationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor() :ViewModel(){


    val fullName = ObservableField<String>()
    val name = ObservableField<String>()
    val login = ObservableField<String>()


    fun updateUI(user:PaginationResponse){
        fullName.set(user.fullName)
        name.set(user.name)
        login.set(user.owner?.login)
    }

}