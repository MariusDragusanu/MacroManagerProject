package com.example.macromanager.ViewModel

import com.example.macromanager.Entity.__Food
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Entity.__User2
import com.example.myapplication.Repository.__FoodRepository2

class __UserViewModel(user: __User2):__GenericViewModel<__User2>(user) {

    fun getRecentFoodList():List<__Food> {
        return getProperty().getRecentFoodList()
    }
    fun getFavFoodList():List<__Food>{
        return getProperty().getFavoriteFoodList()
    }
    fun addRecentFood(newFood: __Food){
        getProperty().addRecentFood(newFood)
    }
    fun addFavFood(newFood: __Food){
        getProperty().addFavoriteFood(newFood)
    }
    /*  fun saveUser(){
          __UserRepository.getInstance().updateUser(getProperty())
      }*/
    fun addFoodToDatabase(newFood: __Food){
        __FoodRepository2.getInstance()?.createFood(newFood)
    }

    fun addMeal(meal: __Meal) {this.getProperty().addMeal(meal)

    }
}
