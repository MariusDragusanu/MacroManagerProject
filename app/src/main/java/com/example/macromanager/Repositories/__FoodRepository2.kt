package com.example.myapplication.Repository


import com.example.macromanager.Entity.__Food
import com.example.myapplication.Listeners.__FoodRepositoryListener
import com.example.myapplication.Object.__DatabaseAttributeTag
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_BRAND_ATTRIBUTE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class __FoodRepository2 {
    private lateinit var listener:__FoodRepositoryListener
    private var hasActiveListener=false
    private var foodCollection=Firebase.firestore.collection(__DatabaseAttributeTag.FOOD_COLLECTION_NAME)
    companion object{
        private val instance=__FoodRepository2()
        fun getInstance():__FoodRepository2?{
            if(instance.hasActiveListener) return instance
            return null
        }
        fun setListener(newListener: __FoodRepositoryListener){
            this.instance.listener=newListener
            instance.hasActiveListener=true
        }
    }
    fun createFood(newFood: __Food)=CoroutineScope(Dispatchers.IO).launch {
        try {
            foodCollection.add(newFood).addOnCompleteListener {
                if(it.isSuccessful){
                    listener.getRequestStatus("CREATE_FOOD",true)

                }
                if(it.isCanceled){
                    listener.getRequestStatus("CREATE_FOOD",false,"__OldFoodRepository:An error has occurred while adding the food in database\n Error:${it.exception?.message}")
                }
            }
        }
        catch (e:Exception){
            listener.getRequestStatus("CREATE_FOOD",false,"__OldFoodRepository:An error has occurred while adding the food in database\n Error:${e.message}")
        }
    }
    fun queryFood(enteredText:String)= CoroutineScope(Dispatchers.IO).launch {
        try {
            val resultQuery= mutableListOf<__Food>()
              val foodQuery=foodCollection.get().addOnCompleteListener{
                  if(it.isSuccessful){

                      for(foodDoc in it.result!!.documents ){
                          val foodNameString=foodDoc.get(__DatabaseAttributeTag.FOOD_NAME_ATTRIBUTE) as String
                          val brandNameString=foodDoc.get(FOOD_BRAND_ATTRIBUTE) as String
                          if(foodNameString.contains(enteredText,true) or (brandNameString.contains(enteredText))){
                              val food=foodDoc.toObject(__Food::class.java)
                              food?.let {
                                  resultQuery.add(it)
                              }
                          }
                      }
                      if(resultQuery.size==0){
                          listener.getRequestStatus("QUERY_FOOD",false,"There is no match!")
                      }
                      listener.getFoodQuery(resultQuery)
                      listener.getRequestStatus("QUERY_FOOD",true)
                  }
                  if(it.isCanceled){
                      listener.getRequestStatus("QUERY_FOOD",false,"__OldFoodRepository:An error has occurred while searching in the database\n Error:${it.exception?.message}")
                  }
              }.await()
        }
        catch (e:Exception){
            listener.getRequestStatus("QUERY_FOOD",false,"__OldFoodRepository:An error has occurred while searching in the database\n Error:${e.message}")
        }
    }
}