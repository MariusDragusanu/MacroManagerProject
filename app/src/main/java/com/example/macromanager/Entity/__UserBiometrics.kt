package com.example.myapplication.Entity

import __Record

 class __UserBiometrics(private var currentHeightCm:Float?=null,
                            private var ageYears:Int?=null,
                            private var isMale:Boolean?=null,
                            private var activityLevelCoefficient:Float?=null,
                        )
{
    private var weightRecord:MutableList<__Record>? = mutableListOf()
    private var baseMetabolicRate:Float?=0f
    private var baseMassIndex:Float?=0f
fun getCurrentHeight()=currentHeightCm
fun getWeightRecord()=weightRecord
fun getBaseMetabolicRate()=baseMetabolicRate
fun getBaseMassIndex()=baseMassIndex
fun isMan()=isMale
fun getActivityLevelCoefficient()=activityLevelCoefficient
fun getAge()=ageYears
fun setNewHeight(newVal:Float){
    currentHeightCm=newVal
}
fun addNewWeightEntry(newEntry:__Record){
    weightRecord!!.add(newEntry)
}
    fun setSex(newVal:Boolean){
        isMale=newVal
    }
fun setActivityLevel(newVal: Float){
    activityLevelCoefficient=newVal
}
fun setNewAge(newVal: Int){
    ageYears=newVal
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