package com.example.macromanager.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Listeners.FragmentListener.__FragmentSettingsBiometricsListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentSettingsListener
import com.example.macromanager.R

class __FragmentMainSettings(var currentUser3: __User3):Fragment(R.layout.fragment_main_account),__FragmentSettingsBiometricsListener{
    fun setListener(newListener: __FragmentSettingsListener){
        this.listener=newListener
    }
    private lateinit var listener: __FragmentSettingsListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_account, container, false)
        val btnBiometrics=view.findViewById<ImageButton>(R.id.frg_settings_btnBiometrics)
        btnBiometrics.setOnClickListener {
            listener.attachFragmentSettingsBiometrics()
        }
        return view
    }

    override fun detachFragmentSettingsBiometrics(
        fragment: __FragmentSettingsBiometrics,
        currentUser3: __User3?
    ) {
        listener.detachFragmentSettingsBiometrics(fragment,currentUser3)
    }

    fun updateUser(user3: __User3) {
        this.currentUser3=user3
    }

}