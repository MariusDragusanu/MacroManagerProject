package com.example.macromanager.Fragments

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.macromanager.Entity.__UserAccountInformation
import com.example.macromanager.Listeners.__FirebaseAccountManagerListener
import com.example.macromanager.Listeners.FragmentListener.__FragmentAccountInformationListener
import com.example.macromanager.Managers.__FirebaseAccountManager
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class __FragmentAccountInformation:Fragment(R.layout.fragment_account_information),__FirebaseAccountManagerListener {
private lateinit var listener:__FragmentAccountInformationListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_account_information, container, false)
        __FirebaseAccountManager.setListener(this)
        val btnEmail = view.findViewById<Button>(R.id.frg_reg1_btnEmail)
        val btnGoogle = view.findViewById<Button>(R.id.frg_reg1_btnGoogle)
        val btnGuest = view.findViewById<Button>(R.id.frg_reg1_btnGuest)
        btnEmail.setOnClickListener {
            onEmailSelected()
        }
        btnGoogle.setOnClickListener {
            onGoogleSelected()
        }
        btnGuest.setOnClickListener {
            onGuestSelected()
        }
        return view
    }

    private fun onGuestSelected() {
        __FirebaseAccountManager.getInstance().createFirebaseGuestAccount()
    }

    private fun onGoogleSelected() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

           val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
           val signInIntent = googleSignInClient.signInIntent
           startActivityForResult(signInIntent, 0)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                __FirebaseAccountManager.getInstance().createFirebaseAccountFromGoogleCredentials(credential)

            } catch (e: ApiException) {
                listener.onRequestFailed(e)
            }
        }
    }
    override fun getRequestStatus(
        requestName: String,
        requestStatus: Boolean,
        requestException: Exception?
    ) {
        if (requestName == "CREATE_FIREBASE_ACCOUNT") {
            if (requestStatus) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Account registered!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
        if(requestName=="CREATE_FIREBASE_GUEST_ACCOUNT"){
            if(requestStatus){
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Guest Account created! You can register with the other methods at any time and not loose the account ", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        if(requestName=="CREATE_ACCOUNT_FROM_GOOGLE"){
            if(requestStatus){
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Account linked with Google successfully" , Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        requestException?.let {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(requireContext(),requestName+" :: "+it.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getCurrentFirebaseAccount(currentUser: FirebaseUser?) {
        listener.onRetrieveAccountInformation(__UserAccountInformation(currentUser))
    }
    fun onEmailSelected(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_credentials_layout)
        dialog.show()
        val ilEmail = dialog.findViewById<TextInputLayout>(R.id.dialog_credentials_ilEmail)
        val etEmail = dialog.findViewById<TextInputEditText>(R.id.dialog_credentials_etEmail)
        val ilPassword = dialog.findViewById<TextInputLayout>(R.id.dialog_credentials_ilPassword)
        val etPassword = dialog.findViewById<TextInputEditText>(R.id.dialog_credentials_etPasswor)
        val btnConfirm = dialog.findViewById<Button>(R.id.dialog_credentials_btnContinue)
        val btnCancel = dialog.findViewById<Button>(R.id.dialog_credentials_btnCancel)
        etEmail.doAfterTextChanged {
            if (__Utility.checkEmailFormat(etEmail.text.toString())) {
                ilEmail.setEndIconDrawable(android.R.drawable.checkbox_on_background)
            } else {
                ilEmail.setEndIconDrawable(android.R.drawable.ic_delete)
                ilEmail.errorContentDescription = "Email format is invalid"
            }
        }
        etPassword.doAfterTextChanged {
            if (etPassword.text.toString().length < 8) {
                ilPassword.errorContentDescription = "Password too short!"
                ilPassword.setEndIconDrawable(android.R.drawable.ic_delete)
            } else {
                ilPassword.setEndIconDrawable(android.R.drawable.checkbox_on_background)
            }
            btnConfirm.visibility=VISIBLE
        }
        btnConfirm.setOnClickListener {
            __FirebaseAccountManager.getInstance().createFirebaseAccount(etEmail.text.toString(),etPassword.text.toString())
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun setListener(newListener: __FragmentAccountInformationListener) {
        this.listener=newListener

    }
}


