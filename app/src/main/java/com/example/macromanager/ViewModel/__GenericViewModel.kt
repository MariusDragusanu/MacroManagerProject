package com.example.macromanager.ViewModel

import androidx.lifecycle.ViewModel

abstract class __GenericViewModel<T>(private var m_Data: T): ViewModel() {
    fun getProperty()=m_Data
    fun setProperty(newData:T){
        this.m_Data=newData
    }

}