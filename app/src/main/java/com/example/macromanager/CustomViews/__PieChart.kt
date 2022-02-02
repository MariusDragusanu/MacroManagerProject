package com.example.macromanager.CustomViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.toRectF
import com.example.macromanager.Object.__ColorPallete
import com.example.macromanager.R
import com.example.myapplication.Object.__Utility
import java.util.jar.Attributes
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class __PieChart(context:Context,viewAttributes: AttributeSet): View(context,viewAttributes) {
    var progress1=33.33f
    var progress2=33.33f
    var progress3=33.33f
    var progress1Color: Int=resources.getColor(R.color.progress1)
    var progress2Color:Int=resources.getColor(R.color.progress2)
    var progress3Color:Int= resources.getColor(R.color.progress3)
    var radius=125f
    private var sizeVector:Point= Point()
    private var paddingVector:Point= Point(50,50)
    var dataEntries= arrayListOf<Pair<String,Float>>(Pair("Protein",20f),Pair("Fat",40f),Pair("Carbs",40f))
    var colorEntries= arrayListOf<Int>(progress1Color, progress2Color, progress3Color)
    private var angleEntries= arrayListOf<Float>()
    private var total=0f
    private val angleDif=__Utility.PI/2
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        sizeVector.x=super.getMeasuredWidth()-150
        sizeVector.y=super.getMeasuredWidth()-100
        computeTotalValue()
        computeAngles()
        drawBackground(canvas)
        drawProgress(canvas)
    }
    private fun drawBackground(canvas: Canvas?){
        val brush=Paint()
        brush.color=__ColorPallete.primaryColor
        brush.style=Paint.Style.FILL_AND_STROKE
        brush.color=__ColorPallete.primaryColor
        canvas?.drawCircle(sizeVector.x/2f,sizeVector.y/2f,radius,brush)

    }
  private  fun drawProgress(canvas: Canvas?){

     for(index in 0 until angleEntries.size-1){


         drawSliceBetweenExtremities(canvas,angleEntries[index]-angleDif,angleEntries[index+1]-angleDif,colorEntries[index])
        if(dataEntries[index].second>0.9f)
         drawTextForProgress(canvas,getPointFromAngle(angleEntries[index+1]-angleDif), dataEntries[index],colorEntries[index]  )
     }



    }
    private fun drawSliceBetweenExtremities(canvas:Canvas?,angleA:Float,angleB:Float,color:Int) {
        val Dn = 0.001f
        val brush = Paint()
        brush.color = color
        brush.style = Paint.Style.FILL_AND_STROKE
        brush.strokeWidth = 1f
        var angle = angleA
        if (angleA <= angleB) {
            while (angle <= angleB) {
                angle += Dn
                val point = getPointFromAngle(angle)
                canvas?.drawLine(
                    sizeVector.x / 2f,
                    sizeVector.y / 2f,
                    sizeVector.x / 2f + point.x,
                    sizeVector.y / 2f + point.y,
                    brush
                )

            }
            val pointF = getPointFromAngle(angle)
            canvas?.drawCircle(
                sizeVector.x / 2f + pointF.x,
                sizeVector.y / 2 + pointF.y,
                10f,
                brush
            )
        }

    }
   private  fun getPointFromAngle(angle:Float):PointF{
        return PointF(cos(angle)*radius,sin(angle)*radius)
    }

    private fun drawTextForProgress(canvas: Canvas?,pointF: PointF,dataEntry:Pair<String,Float>,color:Int){
        val string="${String.format("%.1f",((dataEntry.second)/total)*100f)}% ${dataEntry.first}"
        val brush=Paint()
        brush.textSize=35f
        brush.strokeWidth=2f

        brush.color=color

        if(pointF.x+sizeVector.x>sizeVector.x) {
            brush.color=color
            canvas?.drawText(string, 0, string.length, sizeVector.x/2f+pointF.x+50f ,sizeVector.y/2f+pointF.y-30f , brush)
            brush.color=__ColorPallete.progress3

        }
        else{
            brush.color=color
            canvas?.drawText(string, 0, string.length,sizeVector.x/2f+pointF.x - brush.measureText(string)*1.33f, sizeVector.y/2f+pointF.y-30f , brush)
            brush.color=__ColorPallete.progress3

        }
    }
    fun computeTotalValue(){
        total=0f
        for(entry in dataEntries){
            total+=entry.second
        }
    }
    fun computeAngles(){
       angleEntries.clear()
        angleEntries.add(0f)

        angleEntries.add((dataEntries[0].second/total)*2*__Utility.PI)
        for(index in 2 until dataEntries.size){
            val angle=angleEntries[index-1]+(dataEntries[index-1].second/total)*2f*__Utility.PI
            angleEntries.add(angle)
        }
        angleEntries.add(__Utility.PI*2f)

    }
   fun setEntries(entries:ArrayList<Pair<String,Float>>){
       this.dataEntries=entries
       this.computeTotalValue()
       this.computeAngles()
       dataEntries.sortBy { it.second }

   }

}