package com.example.macromanager.Entity

import android.os.Parcel
import android.os.Parcelable


class __Meal(private var m_Title:String="Default Meal"): Parcelable {  private var m_FoodList:MutableList<__Food>

    constructor(parcel: Parcel) : this(parcel.readString()!!) {
                    
    }

    init {
        m_FoodList= mutableListOf()
    }
    fun getTitle():String=m_Title
    fun getList()=m_FoodList.toList()

    fun setTitle(newTitle: String)
    {
        this.m_Title=newTitle
    }
    fun setList(newList: MutableList<__Food>)
    {
        this.m_FoodList=newList
    }
    fun addFood(newFood: __Food)
    {
        this.m_FoodList.add(newFood)
    }
    fun removeFood(where:Int)
    {

        this.m_FoodList.removeAt(where)
    }
    fun removeFood(oldFood: __Food)
    {
        this.m_FoodList.remove(oldFood)
    }
    fun updateFood(where: Int,newFood: __Food){
        m_FoodList[where]=newFood
    }
    fun updateFood(newFood: __Food){
        for(index in m_FoodList.indices){
            if((m_FoodList[index].Name==newFood.Name) and (m_FoodList[index].Brand==newFood.Brand)){
                m_FoodList[index]=newFood
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(m_Title)
        parcel.writeArray(this.m_FoodList.toTypedArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<__Meal> {
        override fun createFromParcel(parcel: Parcel): __Meal {
            return __Meal(parcel)
        }

        override fun newArray(size: Int): Array<__Meal?> {
            return arrayOfNulls(size)
        }
    }
}