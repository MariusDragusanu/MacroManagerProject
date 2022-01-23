package com.example.macromanager.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.macromanager.Listeners.__FirebaseAccountManagerListener
import com.example.macromanager.Listeners.__FragmentWelcomeListener
import com.example.macromanager.Managers.__FirebaseAccountManager
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class __FragmentWelcome:Fragment(),__FirebaseAccountManagerListener {
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


        __FirebaseAccountManager.setListener(this)
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
           __FirebaseAccountManager.getInstance().logInFirebaseAccount(etEmail.text.toString(),etPassword.text.toString())
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



    override fun getRequestStatus(
        requestName: String,
        requestStatus: Boolean,
        requesException: Exception?
    ) {

    }

    override fun getCurrentFirebaseAccount(currentUser: FirebaseUser?) {

    }


}