package com.example.macromanager.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.macromanager.Entity.__Meal
import com.example.macromanager.R

/*class __MealAdapter2(private val context: Context,private val mealList: MutableList<__Meal>):RecyclerView.Adapter<__MealAdapter2.ViewHolder>() {
    public inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.entity_meal_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.itemView.let {
         val tvTitle=it.findViewById<TextView>(R.id.entity_meal_tvTitle)
         val btnMore=it.
         tvTitle.text=mealList[position].getTitle()
     }
    }

    override fun getItemCount(): Int {
      return mealList.size
    }
}*/