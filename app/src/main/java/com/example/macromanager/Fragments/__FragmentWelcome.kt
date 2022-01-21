package com.example.macromanager.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.macromanager.Listeners.__AccountManagerListener
import com.example.macromanager.Listeners.__FragmentWelcomeListener
import com.example.macromanager.Managers.__AccountManager
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class __FragmentWelcome:Fragment(),__AccountManagerListener {
private lateinit var listener:__FragmentWelcomeListener
fun setListener(newListener: __FragmentWelcomeListener){
    this.listener=newListener
}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_welcome, container, false)


        __AccountManager.setListener(this)
        val btnRegister = view.findViewById<Button>(R.id.frg_welcome_btnRegister)
        val btnLogIn = view.findViewById<Button>(R.id.frg_welcome_btnLogIn)
        val etEmail = view.findViewById<TextInputEditText>(R.id.fragment_welcome_etEmail)
        val etPassword = view.findViewById<TextInputEditText>(R.id.dialog_credentials_etPasswor)
        val ilEmail=view.findViewById<TextInputLayout>(R.id.fragment_welcome_ilEmail)
        btnRegister.setOnClickListener {
            listener.onRegister()

            it.scaleX=1.1f
            it.scaleY=1.1f
        }
        btnLogIn.setOnClickListener {
           __AccountManager.getInstance().logInFirebaseAccount(etEmail.text.toString(),etPassword.text.toString())
        }

        etEmail.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                btnLogIn.visibility=VISIBLE
                if(!__Utility.checkEmailFormat(text.toString())){

                    ilEmail.error="Email format is invalid"
                }
                else if(__Utility.checkEmailFormat(text.toString())){

                    ilEmail.error==null
                }
            }
        }
        etPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                btnLogIn.visibility=VISIBLE

            }
        }

        return view
    }

    override fun getErrorMessage(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun getLogInRequestStatus(canProceed: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (canProceed) {
                Toast.makeText(requireContext(), "You are logged in", Toast.LENGTH_SHORT).show()
              //  val switch=view?.findViewById<SwitchCompat>(R.id.switch1)!!
                listener.onLogIn(true)
                //Handler(Looper.getMainLooper()).postDelayed({findNavController().navigate(R.id.action___FragmentWelcome_to_mainActivity)}, 1000)
            }
            
        }
    }

    override fun getRegisterRequestStatus(canProceed: Boolean) {

    }

    override fun getGuestRegisterRequestStatus(canProceed: Boolean) {

    }

    override fun getAccount(account: FirebaseUser?) {

    }


}