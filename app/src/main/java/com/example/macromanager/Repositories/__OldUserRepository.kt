package com.example.myapplication.Repository

import android.util.Log



import com.example.myapplication.Object.__DatabaseAttributeTag.USER_FAV_FOOD_LIST_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.USER_MEAL_LIST_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.USER_RECENT_FOOD_LIST_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.USERS_COLLECTION_NAME
import com.example.myapplication.Object.__DatabaseAttributeTag.USER_BIOMETRICS_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.USER_DIET_PLAN_ATTRIBUTE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

/*class __OldUserRepository private constructor() {

    private val usersCollection= Firebase.firestore.collection(USERS_COLLECTION_NAME)

    lateinit var currentUser: __User

    companion object{
        private var instance= __OldUserRepository()
        fun getInstance()= instance
    }


    fun addUser(newUser: __User) = CoroutineScope(Dispatchers.IO).launch {

        Log.d("UserRepository", newUser.getEmail())
        usersCollection.document(newUser.getEmail()).set(newUser).addOnFailureListener {
            Log.d("UserRepository", "Exception thrown:" + it.message)
        }.await()


    }
     fun removeUser(oldUser:String)= CoroutineScope(Dispatchers.IO).launch {
        try {
            usersCollection.whereEqualTo(USER_EMAIL_ATTRIBUTE, oldUser).get().addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    for (userDocument in it.documents) {
                        usersCollection.document(userDocument.id).delete()
                    }
                }
            }.await()
        }
        catch (e:Exception) {
        Log.d("Exception",e.message.toString())
        }
    }

     fun updateUser(user: __User)= CoroutineScope(Dispatchers.IO).launch {
        try {
            usersCollection.document(user.getEmail()).get().addOnSuccessListener {
                Log.d("Test","User Updated:"+it.id)
                if (it.exists()) {
                        val userAsMap = userToHashMap(user)
                        usersCollection.document(it.id).set(userAsMap)
            }
        }.await()
            }
            catch (e:Exception) {
                Log.d("Test","User Updated:"+e.message)
            }
    }

     fun getUser(userEmail:String)= CoroutineScope(Dispatchers.IO).launch {
           try {
               val userSnapshot=usersCollection.get().await()
               if(!userSnapshot.isEmpty)
                   for(documents in userSnapshot.documents) {
                       if (documents.id.equals(userEmail)) {
                           val user = documents.toObject(__User::class.java)
                           if (user != null) {
                               currentUser = user
                           }
                       }
                   }
     }
     catch (e: Exception) {
         Log.d("EXCEPTIONS", e.message.toString())
     }
     }

     fun userToHashMap(user: __User):Map<String,Any> {
         val map = mutableMapOf<String, Any>()

         map[USER_EMAIL_ATTRIBUTE] = user.getEmail()
         map[USER_MEAL_LIST_ATTRIBUTE] = user.getMealList()!!
         map[USER_FAV_FOOD_LIST_ATTRIBUTE] = user.getFavoriteFoodList()!!
         map[USER_RECENT_FOOD_LIST_ATTRIBUTE] = user.getRecentFoodList()!!
         map[USER_EXIT_DATE_ATTRIBUTE] = user.getExitDate()!!
         map[USER_ENTRY_DATE_ATTRIBUTE] = user.getEntryDate()!!
         map[USER_BIOMETRICS_ATTRIBUTE] = user.getUserBiometrics()!!
         map[USER_DIET_PLAN_ATTRIBUTE] = user.getDietPlan()!!

         return map
     }
}
*/