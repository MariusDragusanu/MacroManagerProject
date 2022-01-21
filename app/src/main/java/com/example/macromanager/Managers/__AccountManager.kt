package com.example.macromanager.Managers

import android.content.Context
import com.example.macromanager.Listeners.__AccountManagerListener
import com.google.firebase.auth.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class __AccountManager private constructor(){
    private var firebaseAuth=FirebaseAuth.getInstance()
    private lateinit var listener: __AccountManagerListener
    private  var currentUser:FirebaseUser?=null

    companion object{
        private val instance=__AccountManager()
        fun getInstance()= instance
        fun setListener(newListener: __AccountManagerListener){
            instance.listener=newListener
        }
    }
    fun createFirebaseAccount(email:String,password:String)=CoroutineScope(Dispatchers.IO).launch {
           try {
               firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

                   listener.getRegisterRequestStatus(true)
                   currentUser = firebaseAuth.currentUser!!

               }.addOnFailureListener {
                   listener.getErrorMessage(it.message.toString())
                   listener.getRegisterRequestStatus(false)
               }.await()
           }catch (e:Exception){
               listener.getErrorMessage(e.message!!)
               listener.getRegisterRequestStatus(false)
           }

        }
    fun signInAsGuest()= CoroutineScope(Dispatchers.IO).launch {
        try {
            firebaseAuth.signInAnonymously().addOnCompleteListener {
                if(it.isSuccessful){
                    currentUser=firebaseAuth.currentUser
                    listener.getGuestRegisterRequestStatus(true)
                    listener.getAccount(currentUser)
                }
                if(it.isCanceled){
                    listener.getGuestRegisterRequestStatus(false)
                listener.getErrorMessage(it.exception?.message.toString())
                }
            }.await()
        }
        catch (e:Exception){
            listener.getErrorMessage(e.message.toString())
        }

    }
    fun deleteFirebaseAccount(email: String,password: String)= CoroutineScope(Dispatchers.IO).launch {

        val credentials = EmailAuthProvider.getCredential(email, password)
        currentUser?.reauthenticate(credentials)?.addOnSuccessListener {
            currentUser?.delete()

        }?.addOnFailureListener {
          listener.getErrorMessage(it.message.toString())
        }?.await()
    }
    fun signOutAccount(){
        if(currentUser!=null){
            firebaseAuth.signOut()
        }
    }
    fun signInWithGoogleCredentials(credential: AuthCredential)= CoroutineScope(Dispatchers.IO).launch {
        try {
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                if(it.isSuccessful){
                    listener.getRegisterRequestStatus(true)
                    this@__AccountManager.currentUser=firebaseAuth.currentUser
                }
                else{
                    listener.getRegisterRequestStatus(false)
                }
            }.await()

        }catch (e:Exception){
            listener.getErrorMessage(e.message.toString())
        }
    }
    fun logInFirebaseAccount(email: String,password: String)=CoroutineScope(Dispatchers.IO).launch {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                currentUser = firebaseAuth.currentUser!!
                listener.getLogInRequestStatus(true)

            }.addOnFailureListener {
                listener.getErrorMessage(it.message.toString())
                listener.getLogInRequestStatus(false)
            }.await()
        }catch (e:Exception){
            listener.getErrorMessage(e.message!!)
            listener.getLogInRequestStatus(false)
        }

    }
  fun getSavedAccount()=currentUser

}