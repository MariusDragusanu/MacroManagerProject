package com.example.macromanager.Listeners

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface __AccountManagerListener {

    fun getErrorMessage(message:String)

    fun getLogInRequestStatus(canProceed:Boolean)
    fun getRegisterRequestStatus(canProceed:Boolean)
    fun getGuestRegisterRequestStatus(canProceed: Boolean)
    fun getAccount(account:FirebaseUser?)
}