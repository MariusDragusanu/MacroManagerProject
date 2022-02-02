package com.example.macromanager.Listeners.FragmentListener

import com.example.macromanager.Entity.__User3
import com.example.macromanager.Fragments.__FragmentSettingsBiometrics

interface __FragmentSettingsListener {
    fun detachFragmentSettingsBiometrics(fragment: __FragmentSettingsBiometrics, currentUser3: __User3?=null)
    fun attachFragmentSettingsBiometrics()
}