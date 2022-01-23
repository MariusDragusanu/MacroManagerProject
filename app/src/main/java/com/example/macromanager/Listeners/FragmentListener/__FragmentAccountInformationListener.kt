package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__UserAccountInformation

import kotlin.Exception

interface __FragmentAccountInformationListener {
    fun onRetrieveAccountInformation(userAccountInformation: __UserAccountInformation)
    fun onRequestFailed(exception: Exception)
}