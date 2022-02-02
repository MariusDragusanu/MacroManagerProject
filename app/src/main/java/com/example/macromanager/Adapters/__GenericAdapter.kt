package com.example.macromanager.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class __GenericAdapter<T>  constructor( var m_List:MutableList<T>,private val m_LayoutId:Int):RecyclerView.Adapter<__GenericAdapter<T>.__GenericViewHolder>() {

    inner class __GenericViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): __GenericViewHolder {
     val view=LayoutInflater.from(parent.context).inflate(m_LayoutId,parent,false)
        return __GenericViewHolder(view)
    }

    override fun onBindViewHolder(holder: __GenericViewHolder, position: Int) {
       holder.itemView.apply {
           onElementConfig(this,m_List[position],position)
           setOnClickListener{
               onClick(this,m_List[position],position)
           }
           setOnLongClickListener {
               onLongClickListener(this,m_List[position],position)
           }
       }
    }

    abstract fun onLongClickListener(view: View, any: T, position: Int):Boolean

    abstract fun onClick(view: View, t: T, position: Int)

    abstract fun onElementConfig(view: View, t: T, position: Int)

    override fun getItemCount(): Int {
      return m_List.size
    }
    fun clearList() {
        notifyItemRangeRemoved(0,m_List.size)
        m_List.clear()
    }

    fun setListToBeDisplayed(newList: MutableList<T> ) {
        clearList()
         Log.d("Test","before newList ItemCount: ${newList.size}")
        m_List=newList
        Log.d("Test","after newList ItemCount: ${newList.size}")
        notifyItemRangeInserted(0,newList.size)
    }
    fun removeElement(where:Int){
        m_List.removeAt(where)
        notifyItemRemoved(where)
    }

    fun addItem(item: T) {
        m_List.add(item)
        //notifyItemInserted(m_List.size-1)

    }
}