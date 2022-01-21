package com.example.macromanager.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.macromanager.Entity.__User2
import com.example.macromanager.ViewModel.__UserViewModel

class __UserViewModelFactory (private val user: __User2) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return __UserViewModel(user = user) as T
    }
}