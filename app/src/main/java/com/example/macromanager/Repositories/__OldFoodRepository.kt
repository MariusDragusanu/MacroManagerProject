package com.example.myapplication.Repository

import android.util.Log
import com.example.macromanager.Entity.__Food

import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_BRAND_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_CALORIES_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_CARBS_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_CATEGORY_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_COLLECTION_NAME
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_FATS_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_FIBER_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_NAME_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_PROTEIN_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_QUANTITY_ATTRIBUTE
import com.example.myapplication.Object.__DatabaseAttributeTag.FOOD_UNIT_OF_MEASUREMENT_ATTRIBUTE
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class __OldFoodRepository private constructor() {

    private val tag="FoodRepository"

    private val foodCollection= Firebase.firestore.collection(FOOD_COLLECTION_NAME)

    private var m_foodsQueried= mutableListOf<__Food>()

    companion object{
        private var instance= __OldFoodRepository()
        fun getInstance()= instance
    }

    fun addFood(newFood: __Food)=CoroutineScope(Dispatchers.IO).launch {
        try{
            foodCollection.add(newFood).await()
        }
        catch (e:Exception)
        {
            Log.d(tag+"_Exception",e.message.toString())
        }
    }

    fun removeFood(oldFood: __Food)= CoroutineScope(Dispatchers.IO).launch {
        try{
            foodCollection.whereEqualTo(FOOD_NAME_ATTRIBUTE,oldFood.Name).get().addOnSuccessListener {
            if(it.documents.isNotEmpty() and (it.documents.size==1))
                for(foodDocuments in it.documents)
                {
                    foodCollection.document(foodDocuments.id).delete()

                }
            }.await()
        }
        catch (e:Exception)
        {
            Log.d(tag+"_Exception",e.message.toString())
        }
    }

    fun foodToHashMap(food: __Food):Map<String,Any> {
        val map= mutableMapOf<String,Any>()
        map[FOOD_NAME_ATTRIBUTE]=food.Name
        map[FOOD_BRAND_ATTRIBUTE]=food.Brand
        map[FOOD_UNIT_OF_MEASUREMENT_ATTRIBUTE]=food.UnitOfMeasurement
        map[FOOD_CATEGORY_ATTRIBUTE]=food.Category
        map[FOOD_CALORIES_ATTRIBUTE]=food.caloriesIn100UM
        map[FOOD_QUANTITY_ATTRIBUTE]=food.Quantity
        map[FOOD_PROTEIN_ATTRIBUTE]=food.nProteins
        map[FOOD_FATS_ATTRIBUTE]=food.nFats
        map[FOOD_CARBS_ATTRIBUTE]=food.nCarbs
        map[FOOD_FIBER_ATTRIBUTE]=food.nFiber
        return map
    }

    fun updateFood(food: __Food)= CoroutineScope(Dispatchers.IO).launch {
    try{
        foodCollection.whereEqualTo(FOOD_NAME_ATTRIBUTE,food.Name).get().addOnSuccessListener {
            if(it.documents.isNotEmpty())
                for(foodDocuments in it.documents)
                {
                    val foodMap=foodToHashMap(food)
                    foodCollection.document(foodDocuments.id).set(foodMap, SetOptions.merge())

                }
        }
    }
    catch (e:Exception)
    {
        Log.d(tag+"_Exception",e.message.toString())
    }
}


    fun searchFoodbyComparing(enteredText:String)= CoroutineScope(Dispatchers.IO).launch {
        this@__OldFoodRepository.m_foodsQueried.clear()
        foodCollection.get().addOnSuccessListener {
            if(it.documents.isNotEmpty())
            {
                for(document in it.documents)

                    if(document[FOOD_NAME_ATTRIBUTE].toString().contains(enteredText)){
                        val food=document.toObject(__Food::class.java)!!
                        m_foodsQueried.add(food)
                    }
            }
        }.await()
    }
fun getFoodCollection(quantity:Int)= CoroutineScope(Dispatchers.IO).launch {
    try {
        m_foodsQueried.clear()
        foodCollection.get().addOnSuccessListener {
            for (documents in it.documents) {
                var index = 1
                val food = documents.toObject(__Food::class.java)
                m_foodsQueried.add(food!!)

                if (index++ == quantity) {
                    break
                }
            }
        }.await()
    } catch (e: Exception) {
        Log.d(tag + "_Exception", e.message.toString())
    }
}

    fun getQueryResult()=this.m_foodsQueried
}