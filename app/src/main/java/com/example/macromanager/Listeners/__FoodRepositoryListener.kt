package com.example.myapplication.Listeners

import com.example.macromanager.Entity.__Food


interface __FoodRepositoryListener {
    fun getRequestStatus(requestName:String,status:Boolean,error:String?=null)
    fun getFoodQuery(query: MutableList<__Food>)
}