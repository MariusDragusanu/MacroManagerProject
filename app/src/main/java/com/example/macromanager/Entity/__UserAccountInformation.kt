package com.example.macromanager.Entity

import com.example.myapplication.Entity.__Date
import com.google.firebase.auth.FirebaseUser

 class __UserAccountInformation(firebaseUser: FirebaseUser?){
     private var email=firebaseUser?.email
     private var isAnonymous=firebaseUser?.isAnonymous
     private var emailVerified=firebaseUser?.isEmailVerified
     private var providerId=firebaseUser?.providerId
     private var rememberMe:Boolean?=false
     private var entryDate:__Date?= __Date()
     private var exitDate:__Date?=__Date()
     fun getEmail()=email
     fun setEmail(newEmail:String){
         email=newEmail
     }
     fun setRememberMe(boolean: Boolean){
         rememberMe=boolean
     }
     fun getRememberMe()=rememberMe
     fun setEntryDate(newEntry:__Date= __Date()){
         entryDate=newEntry
     }
     fun setExitDate(newEntry: __Date){
         exitDate=newEntry
     }
     fun getExitDate()=exitDate
     fun getEntryDate()=entryDate

}
