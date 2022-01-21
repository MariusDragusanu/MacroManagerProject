package com.example.macromanager.Fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.macromanager.Listeners.__AccountManagerListener
import com.example.macromanager.Listeners.__Register1Listener
import com.example.macromanager.Managers.__AccountManager
import com.example.macromanager.R
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class __FragmentRegister1:Fragment(R.layout.fragment_register_1),__AccountManagerListener {
    private lateinit var btnContinue: Button
    private lateinit var listener:__Register1Listener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        __AccountManager.setListener(this)
        val view=inflater.inflate(R.layout.fragment_register_1,container,false)
        val btnEmail=view.findViewById<Button>(R.id.frg_reg1_btnEmail)
        val btnGoogle=view.findViewById<Button>(R.id.frg_reg1_btnGoogle)
        val btnAnonymously=view.findViewById<Button>(R.id.frg_reg1_btnGuest)
        btnContinue=view.findViewById<Button>(R.id.frg_reg1_btnContinue)
        btnGoogle.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id)).requestEmail().build()
            val signInClient= GoogleSignIn.getClient(this.requireContext(),options)
            signInClient.signInIntent.also{
                startActivityForResult(it,0 )

            }
        }
        btnAnonymously.setOnClickListener {

            __AccountManager.getInstance().signInAsGuest()
        }
        btnEmail.setOnClickListener {
            val dialog= Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_credentials_layout)
            dialog.show()
            val etEmail=dialog.findViewById<TextInputEditText>(R.id.dialog_credentials_etEmail)
            val etPassword=dialog.findViewById<TextInputEditText>(R.id.dialog_credentials_etPasswor)
            val btnNext=dialog.findViewById<Button>(R.id.dialog_credentials_btnContinue)
            val btnCancel=dialog.findViewById<Button>(R.id.dialog_credentials_btnCancel)
            etPassword.doAfterTextChanged {
                etPassword.text?.let {
                    if(it.length<8){
                        etPassword.error="Password too short"
                    }else{
                        btnNext.visibility=VISIBLE
                    }
                }
            }
            etEmail.doAfterTextChanged {
                etEmail.text?.let {
                        btnNext.visibility=VISIBLE
                }
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnNext.setOnClickListener {
                if(etEmail.text!!.isEmpty()){
                    etEmail.setError("Enter Email",resources.getDrawable(R.drawable.icon_warning))
                    btnNext.visibility= INVISIBLE
                }
                if(etPassword.text!!.isEmpty()){
                    etPassword.setError("Enter Password",resources.getDrawable(R.drawable.icon_warning))
                    btnNext.visibility= INVISIBLE
                }
                __AccountManager.getInstance().createFirebaseAccount(etEmail.text.toString(),etPassword.text.toString())

                dialog.dismiss()
            }
        }
        btnContinue.setOnClickListener {
            val viewPager=activity?.findViewById<ViewPager2>(R.id.frg_onboard_ViewPager)
            viewPager?.currentItem=1
        }
        return view
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        __AccountManager.getInstance().signInWithGoogleCredentials(credential = credentials)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener {
                if(it.isSuccessful){
                    googleAuthForFirebase(it.result!!)
                }
                if(it.isCanceled){
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }

            }.result
            account?.let {
                googleAuthForFirebase(it)
            }
        }
    }

    override fun getErrorMessage(message: String) {
       CoroutineScope(Dispatchers.Main).launch {
           Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
       }
    }

    override fun getLogInRequestStatus(canProceed: Boolean) {

    }

    override fun getRegisterRequestStatus(canProceed: Boolean) {
        if(canProceed){
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(requireContext(), "Account Created!", Toast.LENGTH_SHORT).show()
                listener.getNewAccount(FirebaseAuth.getInstance().currentUser)
                btnContinue.visibility= VISIBLE
            }
        }
    }

    override fun getGuestRegisterRequestStatus(canProceed: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (canProceed) {
                Toast.makeText(requireContext(), "Guest Account Created", Toast.LENGTH_SHORT).show()
                btnContinue.visibility = VISIBLE

            }
        }
    }

    override fun getAccount(account: FirebaseUser?) {

    }

    fun setListener(listener: __Register1Listener) {
        this.listener=listener

    }
}