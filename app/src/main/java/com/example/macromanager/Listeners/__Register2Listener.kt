package com.example.macromanager.Listeners

import com.example.myapplication.Entity.__UserBiometrics

interface __Register2Listener {
    fun getBiometrics(userBiometrics: __UserBiometrics)
    fun hasFinishedRegistration()
    fun decidedForMealPlan()
}