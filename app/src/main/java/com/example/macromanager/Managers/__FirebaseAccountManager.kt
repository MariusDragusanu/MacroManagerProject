package com.example.macromanager.Managers

import com.example.macromanager.Listeners.__FirebaseAccountManagerListener
import com.google.firebase.auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class __FirebaseAccountManager private constructor(){
    private var firebaseAuth=FirebaseAuth.getInstance()
    private lateinit var listener: __FirebaseAccountManagerListener
    private  var currentUser:FirebaseUser?=null

    companion object{
        private val instance=__FirebaseAccountManager()
        fun getInstance()= instance
        fun setListener(newListener: __FirebaseAccountManagerListener){
            instance.listener=newListener
        }
    }
    fun createFirebaseAccount(email:String,password:String)=CoroutineScope(Dispatchers.IO).launch {
           try {
               firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

                   listener.getRequestStatus("CREATE_FIREBASE_ACCOUNT",true)
                   currentUser = firebaseAuth.currentUser!!
                   listener.getCurrentFirebaseAccount(currentUser)

               }.addOnFailureListener {
                   listener.getRequestStatus("CREATE_FIREBASE_ACCOUNT",false,it)
               }.await()
           }catch (e:Exception){
               listener.getRequestStatus("CREATE_FIREBASE_ACCOUNT",false,e)
           }

        }
    fun createFirebaseGuestAccount()= CoroutineScope(Dispatchers.IO).launch {
        try {
            firebaseAuth.signInAnonymously().addOnCompleteListener {
                if(it.isSuccessful){
                    listener.getRequestStatus("CREATE_FIREBASE_GUEST_ACCOUNT",true)
                    currentUser=firebaseAuth.currentUser
                    listener.getCurrentFirebaseAccount(currentUser)
                }
                if(it.isCanceled){
                    listener.getRequestStatus("CREATE_FIREBASE_GUEST_ACCOUNT",false,it.exception)
                listener.getRequestStatus("CREATE_FIREBASE_GUEST_ACCOUNT",false,it.exception)
                }
            }.await()
        }
        catch (e:Exception){
            listener.getRequestStatus("CREATE_FIREBASE_GUEST_ACCOUNT",false,e)
        }

    }
    fun deleteFirebaseAccount(email: String,password: String)= CoroutineScope(Dispatchers.IO).launch {

        val credentials = EmailAuthProvider.getCredential(email, password)
        currentUser?.reauthenticate(credentials)?.addOnSuccessListener {
            listener.getRequestStatus("DELETE_FIREBASE_ACCOUNT",true)
            currentUser?.delete()

        }?.addOnFailureListener {
          listener.getRequestStatus("DELETE_FIREBASE_ACCOUNT",false,it)
        }?.await()
    }
    fun signOutAccount(){
        if(currentUser!=null){
            firebaseAuth.signOut()
        }
    }
    fun createFirebaseAccountFromGoogleCredentials(credential: AuthCredential)= CoroutineScope(Dispatchers.IO).launch {
        try {
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                if(it.isSuccessful){
                    listener.getRequestStatus("CREATE_ACCOUNT_FROM_GOOGLE",true)
                    this@__FirebaseAccountManager.currentUser=firebaseAuth.currentUser
                }
                else{
                      listener.getRequestStatus("CREATE_ACCOUNT_FROM_GOOGLE",false,it.exception)
                }
            }.await()

        }catch (e:Exception){
            listener.getRequestStatus("CREATE_ACCOUNT_FROM_GOOGLE",false,e)
        }
    }
    fun logInFirebaseAccount(email: String,password: String)=CoroutineScope(Dispatchers.IO).launch {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                currentUser = firebaseAuth.currentUser!!
                listener.getRequestStatus("LOG_IN_FIREBASE_ACCOUNT",true)
                listener.getCurrentFirebaseAccount(currentUser)

            }.addOnFailureListener {
                listener.getRequestStatus("LOG_IN_FIREBASE_ACCOUNT",false,it)

            }.await()
        }catch (e:Exception){
            listener.getRequestStatus("LOG_IN_FIREBASE_ACCOUNT",false,e)

        }

    }


}