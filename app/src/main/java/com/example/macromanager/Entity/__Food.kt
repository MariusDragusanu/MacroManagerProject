package com.example.macromanager.Entity

import android.os.Parcel
import android.os.Parcelable
import com.example.macromanager.Adapters.__Serving
import java.util.ArrayList

class __Food (var Name:String="Oats",
              var UnitOfMeasurement:String="Grams",
              var servingList:ArrayList<__Serving> = ArrayList(),
              var Brand:String="Popular",
              var Category:String="Default",
              var Quantity:Double=100.0,
              var caloriesIn100UM:Double=304.0,
              var nProteins:Double=12.0,
              var nFats:Double=1.0,
              var nCarbs:Double=56.0,
              var nFiber: Double =1.0
){

    init {
        if(servingList.size==0){
            servingList.add(__Serving("",UnitOfMeasurement,1.0))
            servingList.add(__Serving("",UnitOfMeasurement,100.0))
        }
    }



}