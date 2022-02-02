package com.example.myapplication.Entity

import __Record

 class __UserBiometrics( var currentHeightCm:Float=160f,
                          currentWeight:Float=75f,
                             var ageYears:Int=25,
                             var isMale:Boolean?=true,
                             var activityLevelCoefficient:Float=1.45f,
                        )
{
     var weightRecord:MutableList<__Record> = mutableListOf(__Record(value = currentWeight!!))
     var baseMetabolicRate:Float=0f
     var baseMassIndex:Float=0f

fun setNewHeight(newVal:Float){
    currentHeightCm=newVal
}
fun addNewWeightEntry(newEntry:__Record){
    weightRecord!!.add(newEntry)
}
   fun onUpdate(){
        baseMassIndex=(weightRecord!!.last().value/(currentHeightCm!!*currentHeightCm!!))*10000
        if(isMale!!){
            baseMetabolicRate=10f*weightRecord!!.last().value+6.25f*currentHeightCm!!-5f*ageYears!!+5f
        }
        else{
            baseMetabolicRate=9.247f*weightRecord!!.last().value+3.098f*currentHeightCm!!-5f*ageYears!!-161f
        }
    }
  fun computeBaseIntake():Int{
      return (baseMetabolicRate!!*activityLevelCoefficient!!).toInt()
  }

}