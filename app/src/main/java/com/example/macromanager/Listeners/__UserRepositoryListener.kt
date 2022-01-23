package com.example.myapplication.Listeners

import com.example.macromanager.Entity.__User2
import com.example.macromanager.Entity.__User3


interface __UserRepositoryListener {
    fun getRequestStatus(requestName:String,status:Boolean,error:String?=null)
    fun getUser(retrievedUser: __User3)
}