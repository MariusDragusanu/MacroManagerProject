package com.example.macromanager.CustomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.macromanager.Object.__ColorPallete

class __GraphView(context: Context,
                  attributes: AttributeSet?=null): View(context,attributes) {
    private  var relativeOrigin_X=0f
    private  var relativeOrigin_Y=0f
    private  var relativeMax_X=0f
    private  var relativeMax_Y=0f
    private  var  textSize=25f
    var  paddingVector: PointF = PointF(100f,80f)
    private var dx=0f
    private var dy=0f
    private var maxY=0f
    private var minY=999f
    private var dataArray= mutableListOf<Pair<Any,Float>>()
    private var graphTitle:String=""
    private var oyUnitOfMeasure:String=""
    private var m_backgroundColor:Int= __ColorPallete.backgroundStart
    private var m_foregroundColor:Int=__ColorPallete.backgroundStart
    private var m_textColor:Int=__ColorPallete.textColor
    private var m_gridColor:Int=__ColorPallete.primaryColor
    private var m_dataColor:Int=__ColorPallete.accentColor1
    fun setTitle(newText:String){
        graphTitle=newText
    }
    fun setUnitOfMeasure(newText: String){
        oyUnitOfMeasure=newText
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        relativeOrigin_X=0f
        relativeOrigin_Y=0f
        relativeMax_X=super.getWidth().toFloat()
        relativeMax_Y=super.getHeight().toFloat()
        dx=(relativeMax_X-paddingVector.x*2f)/dataArray.size.toFloat()
        dy=(relativeMax_Y-paddingVector.y*2f)/dataArray.size.toFloat()
        computeLimits()
        drawBackground(canvas)
        drawGrid(canvas)
        drawLinesBetweenPoints(canvas)
        drawPoints(canvas)
        drawText(canvas)
    }

    private fun drawBackground(canvas: Canvas?) {
        val pointBrush= Paint()
        pointBrush.color=this.m_backgroundColor
        pointBrush.strokeWidth=2f
        pointBrush.style=Paint.Style.FILL_AND_STROKE
        val bigRect=RectF(this.relativeOrigin_X,this.relativeOrigin_Y,this.relativeMax_X,this.relativeMax_Y)
        val graphRect= RectF(this.relativeOrigin_X+paddingVector.x,this.relativeOrigin_Y+paddingVector.y,this.relativeMax_X-paddingVector.x,this.relativeMax_Y-paddingVector.y)
        canvas!!.drawRoundRect(bigRect,20f,20f,pointBrush)
        pointBrush.color=m_foregroundColor
        canvas!!.drawRoundRect(graphRect,20f,20f,pointBrush)
    }


    private fun computeLimits(){
        if(dataArray.isNotEmpty()) {
            for (point in dataArray) {
                if (point.second > maxY) {
                    maxY = (point.second+1f)


                }
                if (point.second < minY) {
                    minY = (point.second-1f)

                }
            }


        }
    }
    private fun computeYcoordinate(y:Float):Float{
        var result=0f
        val pxAlpha=relativeMax_Y-2f*paddingVector.y
        val umAlpha=maxY-minY
        val umDelta=maxY-y
        val pxDelta=pxAlpha*(umDelta/umAlpha)+paddingVector.y
        result=pxDelta
        return result
    }
    fun drawPoints(canvas: Canvas?) {
        if (dataArray.isNotEmpty()) {
            val pointBrush = Paint()
            pointBrush.color = this.m_dataColor
            pointBrush.strokeWidth = 2f
            pointBrush.style = Paint.Style.FILL_AND_STROKE
            for ((index, point) in dataArray.withIndex()) {
                canvas!!.drawCircle(
                    paddingVector.x + index * dx,
                    computeYcoordinate(point.second),
                    10f,
                    pointBrush
                )

            }
        }
    }
    fun drawLinesBetweenPoints(canvas: Canvas?){
        if(dataArray.isNotEmpty()) {
            val pointBrush = Paint()
            pointBrush.color = this.m_dataColor
            pointBrush.strokeWidth =5f
            pointBrush.style = Paint.Style.FILL_AND_STROKE
            for (index in 0..dataArray.size - 2) {
                canvas!!.drawLine(
                    paddingVector.x + dx * index,
                    computeYcoordinate(dataArray[index].second),
                    paddingVector.x + dx * (index + 1),
                    computeYcoordinate(dataArray[index + 1].second),
                    pointBrush
                )
            }
        }
    }
    fun drawGrid(canvas: Canvas?){
        val pointBrush=Paint()
        pointBrush.color=this.m_gridColor
        pointBrush.strokeWidth=5f
        pointBrush.style=Paint.Style.FILL_AND_STROKE
        val pxAlpha=relativeMax_Y-2f*paddingVector.y
        canvas!!.drawLine(paddingVector.x,paddingVector.y+pxAlpha*0.33f,relativeMax_X-paddingVector.x ,paddingVector.y+pxAlpha*0.33f,pointBrush)
        canvas!!.drawLine(paddingVector.x,paddingVector.y+pxAlpha*0.66f,relativeMax_X-paddingVector.x ,paddingVector.y+pxAlpha*0.66f,pointBrush)
        canvas!!.drawLine(paddingVector.x,paddingVector.y,paddingVector.x,relativeMax_Y-paddingVector.y,pointBrush)
        canvas!!.drawLine(paddingVector.x,relativeMax_Y-paddingVector.y,relativeMax_X-paddingVector.x,relativeMax_Y-paddingVector.y,pointBrush)
    }
    fun drawText(canvas: Canvas?){
        if(dataArray.isNotEmpty()) {
            val pointBrush = Paint()
            pointBrush.color = this.m_textColor
            pointBrush.strokeWidth =2f
            pointBrush.textSize = textSize
            pointBrush.style = Paint.Style.FILL_AND_STROKE

            val pxAlpha = relativeMax_Y - 3f * paddingVector.y
            val umDelta = maxY - minY
            val minString = "${String.format("%.1f", minY)} $oyUnitOfMeasure"
            val dateString = dataArray[0].first.toString()
            val maxString = "${String.format("%.1f", maxY)} $oyUnitOfMeasure"
            val minPointF = PointF(relativeOrigin_X, paddingVector.y + textSize)
            val maxPointF = PointF(relativeOrigin_X, relativeMax_Y - paddingVector.y-textSize)
            val firstThirdString =
                "${String.format("%.1f", minY + umDelta * 0.33f)} $oyUnitOfMeasure"
            val secondThirdString =
                "${String.format("%.1f", minY + umDelta * 0.66f)} $oyUnitOfMeasure"
            canvas!!.drawText(
                firstThirdString,
                0,
                firstThirdString.length  ,
                relativeOrigin_X,
                paddingVector.y + pxAlpha * 0.66f+textSize,
                pointBrush
            )
            canvas!!.drawText(
                secondThirdString,
                0,
                secondThirdString.length ,
                relativeOrigin_X,
                paddingVector.y + pxAlpha * 0.33f+textSize,
                pointBrush
            )
            canvas!!.drawText(
                maxString,
                0,
                maxString.length ,
                minPointF.x,
                minPointF.y,
                pointBrush
            )
            canvas!!.drawText(
                minString,
                0,
                minString.length ,
                maxPointF.x,
                maxPointF.y,
                pointBrush
            )
            canvas.drawText(
                dateString,
                0,
                dateString.length ,
                paddingVector.x,
                relativeMax_Y - paddingVector.y / 2.5f,
                pointBrush
            )
            if (dataArray.size > 1) {
                canvas.drawText(
                    dataArray.last().first.toString(),
                    0,
                    dataArray.last().first.toString().length ,
                    (dataArray.size - 1) * dx,
                    relativeMax_Y - paddingVector.y / 2.5f,
                    pointBrush
                )
            }
        }
    }

    fun addEntryValue(pair: Pair<String, Float>) {
        dataArray.add(pair)
        dx=(relativeMax_X-paddingVector.x*2f)/dataArray.size.toFloat()
        dy=(relativeMax_Y-paddingVector.y*2f)/dataArray.size.toFloat()
        computeLimits()

    }
}