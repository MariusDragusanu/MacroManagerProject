package com.example.macromanager.Entity

import com.example.myapplication.Entity.__Date
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

 class __UserAccountInformation(firebaseUser: FirebaseUser?=FirebaseAuth.getInstance().currentUser){
     private var email=firebaseUser?.email
      var isAnonymous=firebaseUser?.isAnonymous
      var emailVerified=firebaseUser?.isEmailVerified
      var providerId=firebaseUser?.providerId
      var rememberMe:Boolean=false
      var entryDate:__Date= __Date()
      var exitDate:__Date=__Date()
     fun getEmail()=email
     fun setEmail(newEmail:String){
         email=newEmail
     }


}
