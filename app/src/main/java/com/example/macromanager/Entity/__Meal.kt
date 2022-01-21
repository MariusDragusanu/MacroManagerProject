package com.example.macromanager.Entity



class __Meal(private var m_Title:String="Default Meal")

{  private var m_FoodList:MutableList<__Food>
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
}