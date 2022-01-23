package com.example.macromanager.Listeners

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface __FirebaseAccountManagerListener {


    fun getRequestStatus(requestName:String, requestStatus:Boolean, requesException:Exception?=null)
    fun getCurrentFirebaseAccount(currentUser: FirebaseUser?)


}