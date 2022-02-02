package com.example.macromanager.Adapters

import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.macromanager.Entity.__Meal
import com.example.macromanager.Listeners.__AdapterMealListener
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility


class __MealAdapter( mealList:MutableList<__Meal>, layoutId:Int):__GenericAdapter<__Meal>(mealList,layoutId) {
    private lateinit var listener:__AdapterMealListener
    fun setListener(newListener: __AdapterMealListener){
        this.listener=newListener
    }
    override fun onLongClickListener(view: View, any: __Meal, position: Int): Boolean {
        return true
    }

    override fun onClick(view: View, t: __Meal, position: Int) {
listener.onMealClicked(t)
    }

    override fun onElementConfig(view: View, t: __Meal, position: Int) {
        val tvTitle = view.findViewById<TextView>(R.id.entity_meal_tvTitle)
        tvTitle.text=t.getTitle()
view.animation=  AnimationUtils.loadAnimation(view.context, R.anim.fall_down)

        val btnClear=view.findViewById<ImageButton>(R.id.imageButton)
        btnClear.setOnClickListener {
           listener.clearCurrentMeal(t)

        }
        val icon=view.findViewById<ImageView>(R.id.entity_meal_ivCategory)
        setIcon(icon,t,view)

    }
    fun setIcon(imageView: ImageView,currentMeal:__Meal,view: View){
        val cardView=view.findViewById<CardView>(R.id.entity_meal_card)
        cardView.setCardBackgroundColor(view.context.getColorStateList(R.color.colorMainMeal))
        if(currentMeal.category=="Breakfast"){
            imageView.setImageDrawable(view.context.getDrawable( R.drawable.icon_breakfast))

        }
        if(currentMeal.category=="Lunch"){
            imageView.setImageDrawable(view.context.getDrawable( R.drawable.icon_lunch))
        }
        if(currentMeal.category=="Dinner"){
            imageView.setImageDrawable(view.context.getDrawable( R.drawable.icon_dinner))
        }
        if(currentMeal.category=="Snack"){
            imageView.setImageDrawable(view.context.getDrawable( R.drawable.icon_snack))
            cardView.setCardBackgroundColor(view.context.getColorStateList(R.color.colorSnack))
        }
        if(currentMeal.category=="Pre-Workout Meal"){
            imageView.setImageDrawable(view.context.getDrawable( R.drawable.icon_workout))
            cardView.setCardBackgroundColor(view.context.getColorStateList(R.color.colorWorkout))
        }
        if(currentMeal.category=="Post-Workout Meal"){
            imageView.setImageDrawable(view.context.getDrawable( R.drawable.icon_workout))
            cardView.setCardBackgroundColor(view.context.getColorStateList(R.color.colorWorkout))
        }
    }

    fun removeMeal(oldMeal: __Meal) {
        var where=0
        for(index in m_List.indices){
            if(m_List[index].getTitle()==oldMeal.getTitle()){
                where=index
            }
        }
        m_List.removeAt(where)
        notifyItemRemoved(where)

    }
    fun addMeal(newMeal: __Meal){
        m_List.add(newMeal)
       // notifyItemInserted(m_List.size-1)
    }
    fun setList(newList:MutableList<__Meal>){
        super.setListToBeDisplayed(newList)
    }
}

