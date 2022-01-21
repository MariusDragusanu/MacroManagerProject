package com.example.myapplication.Entity


import com.example.myapplication.Object.__Utility
import java.util.*

class __Date() {
     var year_number: Int
     var day_number: Int
     var month_name: String
       var month_number:Int
     var day_name:String
     lateinit var date:Date

    init{
        date=Date()
        day_name=date.toString().subSequence(0,3).toString()
        month_name=date.toString().subSequence(3,7).toString()
        day_number=date.toString().subSequence(8,10).toString().toInt()
        year_number=date.toString().subSequence(date.toString().length-4,date.toString().length).toString().toInt()
      when(month_name){
          "Jan"->month_number=1
              "Feb"->month_number=2
          "Mar"->month_number=3
          "Apr"->month_number=4
          "May"->month_number=5
          "Jun"->month_number=6
          "Jul"->month_number=7
          "Aug"->month_number=8
          "Sep"->month_number=9
          "Oct"->month_number=10
          "Nov"->month_number=11
          "Dec"->month_number=12
          else->month_number=12
      }
    }
    fun getDateObject()=date
    fun getDateAsString():String{
      return "$day_name $day_number $month_name $year_number"
    }
    fun setDateFromCalendar(day:Int, month:Int, year:Int ) {

        when (month) {
            1 -> { month_name="Jan"}
            2 -> { month_name="Feb"}
            3 -> { month_name="Mar"}
            4 -> { month_name="Apr"}
            5 -> { month_name="May"}
            6 -> { month_name="Jun"}
            7 -> { month_name="Jul"}
            8 -> { month_name="Aug"}
            9 -> { month_name="Sep"}
            10 ->{ month_name="Oct"}
            11 ->{ month_name="Nov"}
            12 ->{ month_name="Dec"}
        }
        month_number=month
        year_number=year
        day_number=day
        day_name=""
    }
      operator fun minus (other:__Date):Int{
          return __Utility.computeTime(this,other)
      }
}