package com.example.myapplication.Repository

import android.content.Context
import android.util.Log
import com.example.macromanager.Entity.__User2
import com.example.macromanager.Entity.__User3

import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Object.__DatabaseAttributeTag
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class __UserRepository2 {
private lateinit var listener:__UserRepositoryListener
private var hasActiveListener=false

    private val usersCollection= Firebase.firestore.collection(__DatabaseAttributeTag.USERS_COLLECTION_NAME)
companion object{
   private val instance:__UserRepository2= __UserRepository2()
    fun getInstance():__UserRepository2?{
        if(instance.hasActiveListener){
            return instance
        }
    return null
    }
    fun setListener(newListener: __UserRepositoryListener){
       instance.listener=newListener
       instance.hasActiveListener=true
    }

    }

    fun createUser(user: __User3)= CoroutineScope(Dispatchers.IO).launch {
        try{
            usersCollection.document(user.UID!!).set(user).addOnCompleteListener {
                if(it.isSuccessful){
                    listener.getRequestStatus("CREATE_USER",true)
                }
                if(it.isCanceled){
                    listener.getRequestStatus("CREATE_USER",false,"__User_Repository:An error has occurred while creating the account\n Error:${it.exception?.message}, line number:${it.exception!!.stackTrace[0].lineNumber},file:${it.exception!!.stackTrace[0].className} ")
                }
            }.await()
        }catch (e:Exception){
            listener.getRequestStatus("CREATE_USER",false,"__User_Repository:An error has occurred while creating the account\n Error:${e.message}, line number:${e.stackTrace[0].lineNumber},file:${e.stackTrace[0].fileName}")
        }
    }
fun retrieveUser(uid:String)= CoroutineScope(Dispatchers.IO).launch {
    try {
      /*  val usersQuery=usersCollection.whereEqualTo(__DatabaseAttributeTag.USER_UID,uid).get().addOnFailureListener {
            Log.d("Repo","First call")
            listener.getRequestStatus("RETRIEVE_USER",false,"__User_Repository:An error has occurred while retrieving the account\n Error:${it.message}")
        }.await()
        if(usersQuery.isEmpty){
            Log.d("Repo","Second call")
            listener.getRequestStatus("RETRIEVE_USER",false,"__User_Repository:There is no account in the database that matches the email entered\n Current Email:$uid")

        }
        for(document in usersQuery.documents){
           val user=document.toObject<__User2>()
                 listener.getUser(user!!)
                 listener.getRequestStatus("RETRIEVE_USER",true)
            if(user==null){
                Log.d("Repo","third call")
                listener.getRequestStatus("RETRIEVE_USER",false,"__User_Repository:An error has occurred when retrieving the information ")
            }
        }*/
       /* val userDocument=usersCollection.document(uid).get().addOnCompleteListener {
            if(it.isSuccessful){
                val user=it.result.getField<String>(uid)
                Log.d("Repo",user!!)
               /* if(user!=null){
                    listener.getUser(user)
                }
                else{
                    listener.getRequestStatus("RETRIEVE_USER",false,it.exception?.message)
                }*/
            }
        }*/
        val userSnapshot=usersCollection.get().await()
        if(!userSnapshot.isEmpty)
            for(documents in userSnapshot.documents) {
                if (documents.id.equals(uid)) {
                    val user = documents.toObject(__User3::class.java)
                    if (user != null) {
                      listener.getUser(user)
                        listener.getRequestStatus("RETRIEVE_USER",true)
                    }
                }
            }
    }
    catch (e:Exception){
        listener.getRequestStatus("RETRIEVE_USER",false,"__User_Repository:An error has occurred while retrieving the account\n Error:${e.message}")
        Log.d("Test",e.message.toString())
    }
}
fun updateUser(currentUser:__User3)= CoroutineScope(Dispatchers.IO).launch {
    try {
        usersCollection.document(currentUser.UID!!).get().addOnSuccessListener {
            if (it.exists()) {
                val userAsMap = userToHashMap(currentUser)
                usersCollection.document(it.id).set(userAsMap)
                listener.getRequestStatus("UPDATE_USER",true)
            }
        }

    }catch (e:Exception){
        listener.getRequestStatus("UPDATE_USER",false,"__User_Repository:An error has occurred while updating the account\n Error:${e.message}")
    }
}
    fun deleteUser(oldUser: __User3)= CoroutineScope(Dispatchers.IO).launch {
        try {
            usersCollection.whereEqualTo(__DatabaseAttributeTag.USER_UID, oldUser.UID).get().addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    for (userDocument in it.documents) {
                        usersCollection.document(userDocument.id).delete()
                    }
                }
            }.await()

        }catch (e:Exception){
            listener.getRequestStatus("DELETE_USER",false,"__User_Repository:An error has occurred while deleting the account\n Error:${e.message}")
        }
    }
    fun userToHashMap(user: __User3):Map<String,Any> {
        val map = mutableMapOf<String, Any>()
        map[__DatabaseAttributeTag.USER_UID]=user.UID!!
        map[__DatabaseAttributeTag.USER_ACCOUNT_INFORMATION]=user.userAccountInformation!!
        map[__DatabaseAttributeTag.USER_MEAL_LIST_ATTRIBUTE] = user.mealCurrentList
        map[__DatabaseAttributeTag.USER_FAV_FOOD_LIST_ATTRIBUTE] = user.foodFavoriteList
        map[__DatabaseAttributeTag.USER_RECENT_FOOD_LIST_ATTRIBUTE] = user.foodRecentList
        map[__DatabaseAttributeTag.USER_BIOMETRICS_ATTRIBUTE] = user.userBiometrics!!
        map[__DatabaseAttributeTag.USER_DIET_PLAN_ATTRIBUTE] = user.userDietPlan!!
        map[__DatabaseAttributeTag.USER_SAVED_MEAL_ATTRIBUTE]=user.mealSavedList
        return map
    }
}