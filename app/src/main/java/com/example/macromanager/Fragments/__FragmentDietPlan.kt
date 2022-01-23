package com.example.macromanager.Fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.macromanager.Listeners.__FragmentDietPlanListener
import com.example.macromanager.R
import com.example.myapplication.Entity.__Date
import com.example.myapplication.Entity.__UserBiometrics
import com.example.myapplication.Entity.__UserDietPlan
import com.example.myapplication.Object.__Utility


class __FragmentDietPlan(private val currentUser:__UserBiometrics) : Fragment() {
    private lateinit var listener:__FragmentDietPlanListener
private lateinit var pbProtein:SeekBar
private lateinit var pbFat:SeekBar
private lateinit var pbCarb:SeekBar
private  var newDailyIntake=0
    private var resultProgress=0
private var proteinProgress=30f
private var fatProgress=20f
private var carbProgress=50f




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_diet_plan, container, false)
        val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
        val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
        val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
        var dateStart=__Date()
        var dateEnd=__Date()
        tvFat.text="Fat Percent: ${fatProgress.toInt()}"
        tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
        tvCarb.text="Carb Percent: ${carbProgress.toInt()}"
        pbProtein=view.findViewById(R.id.frg_diet_pbProtein)
        pbFat=view.findViewById(R.id.frg_diet_pbFat)
        pbCarb=view.findViewById(R.id.frg_diet_pbPCarb)
        pbProtein.setProgress(proteinProgress.toInt(),true)
        pbCarb.setProgress(carbProgress.toInt(),true)
        pbFat.setProgress(fatProgress.toInt(),true)
        val btnKeto=view.findViewById<Button>(R.id.frg_dietplan_btnKeto)
        val btnBalance=view.findViewById<Button>(R.id.frg_dietplan_btnBalanced)
        val btnHighProtein=view.findViewById<Button>(R.id.frg_dietplan_btnHighProtein)
        btnKeto.setOnClickListener {
            proteinProgress=25f
            fatProgress=70f
            carbProgress=5f
            pbProtein.setProgress(proteinProgress.toInt(),true)
            pbCarb.setProgress(carbProgress.toInt(),true)
            pbFat.setProgress(fatProgress.toInt(),true)
            val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
            val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
            val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
            tvFat.text="Fat Percent: ${fatProgress.toInt()}"
            tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
            tvCarb.text="Carb Percent: ${carbProgress.toInt()}"
        }
        btnHighProtein.setOnClickListener {
            proteinProgress=30f
            fatProgress=25f
            carbProgress=45f
            pbProtein.setProgress(proteinProgress.toInt(),true)
            pbCarb.setProgress(carbProgress.toInt(),true)
            pbFat.setProgress(fatProgress.toInt(),true)
            val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
            val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
            val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
            tvFat.text="Fat Percent: ${fatProgress.toInt()}"
            tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
            tvCarb.text="Carb Percent: ${carbProgress.toInt()}"
        }
        btnBalance.setOnClickListener {
            proteinProgress=25f
            fatProgress=20f
            carbProgress=55f
            pbProtein.setProgress(proteinProgress.toInt(),true)
            pbCarb.setProgress(carbProgress.toInt(),true)
            pbFat.setProgress(fatProgress.toInt(),true)
            val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
            val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
            val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
            tvFat.text="Fat Percent: ${fatProgress.toInt()}"
            tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
            tvCarb.text="Carb Percent: ${carbProgress.toInt()}"
        }
        pbProtein.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, newProgress: Int, p2: Boolean) {

                val dx=newProgress-proteinProgress
                Log.d("test","ProteinDx:$dx")
                proteinProgress=newProgress.toFloat()
                fatProgress-=dx/2f
                carbProgress-=dx/2f

                pbCarb.setProgress(carbProgress.toInt(),true)
                pbFat.setProgress(fatProgress.toInt(),true)
                val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
                val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
                val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
                tvFat.text="Fat Percent: ${fatProgress.toInt()}"
                tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
                tvCarb.text="Carb Percent: ${carbProgress.toInt()}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        pbFat.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, newProgress: Int, p2: Boolean) {
                val dx=newProgress-fatProgress
                Log.d("test","FatDx:$dx")
                fatProgress=newProgress.toFloat()
                proteinProgress-=dx/2f
                carbProgress-=dx/2f


                pbCarb.setProgress(carbProgress.toInt(),true)
                pbProtein.setProgress(proteinProgress.toInt(),true)

                val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
                val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
                val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
                tvFat.text="Fat Percent: ${fatProgress.toInt()}"
                tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
                tvCarb.text="Carb Percent: ${carbProgress.toInt()}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        pbCarb.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, newProgress: Int, p2: Boolean) {
                val dx:Float= newProgress-carbProgress
                carbProgress=newProgress.toFloat()
                proteinProgress-=dx/2f
                fatProgress-=dx/2f
                Log.d("test","CarbDx:$dx")
                pbProtein.setProgress(proteinProgress.toInt(),true)
                pbFat.setProgress(fatProgress.toInt(),true)
                val tvFat=view.findViewById<TextView>(R.id.frg_diet_tvFat)
                val tvProtein=view.findViewById<TextView>(R.id.frg_diet_tvProtein)
                val tvCarb=view.findViewById<TextView>(R.id.frg_diet_tvCarb)
                tvFat.text="Fat Percent: ${fatProgress.toInt()}"
                tvProtein.text="Protein Percent: ${proteinProgress.toInt()}"
                tvCarb.text="carb Percent: ${carbProgress.toInt()}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        val btnStartDate=view.findViewById<Button>(R.id.frg_dietplan_btnStartDate)
        val btnEndDate=view.findViewById<Button>(R.id.frg_dietplan_btnEndDate)
        btnStartDate.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_calendar)
            dialog.show()
            val calendarView = dialog.findViewById<CalendarView>(R.id.calendarView)
            val date = __Date()
            calendarView.setOnDateChangeListener { calendarView, year, month, day ->
                date.setDateFromCalendar(day, month + 1, year)
            }
            val btnYes = dialog.findViewById<Button>(R.id.dialog_calendar_btnConfirm)
            btnYes.setOnClickListener {
                btnStartDate.text = date.getDateAsString()
                dateStart = date
                dialog.dismiss()
            }
            val btnNo = dialog.findViewById<Button>(R.id.dialog_calendar_btnCancel)
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        btnEndDate.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_calendar)
            dialog.show()
            val calendarView = dialog.findViewById<CalendarView>(R.id.calendarView)
            val date = __Date()
            calendarView.setOnDateChangeListener { calendarView, year, month, day ->
                date.setDateFromCalendar(day, month + 1, year)
            }
            val btnYes = dialog.findViewById<Button>(R.id.dialog_calendar_btnConfirm)
            btnYes.setOnClickListener {
                btnEndDate.text = date.getDateAsString()
                dateEnd = date
                dialog.dismiss()
            }
            val btnNo = dialog.findViewById<Button>(R.id.dialog_calendar_btnCancel)
            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        val pbGoal=view.findViewById<SeekBar>(R.id.frg_dietplan_pbGoal)
        pbGoal.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                computeGoal(dateStart-dateEnd,p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        val switch1=view.findViewById<Switch>(R.id.frg_dietplan_switchPercent)
        switch1.setOnClickListener {
            val constraint=view.findViewById<ConstraintLayout>(R.id.frg_dietplan_clPercentage)
            if(switch1.isChecked){

                constraint.visibility=VISIBLE
            }
            else{
                constraint.visibility= INVISIBLE
            }
        }
        val switch2=view.findViewById<Switch>(R.id.frg_dietplan_switchGoalEditor)
        switch2.setOnClickListener {
            val constraint=view.findViewById<ConstraintLayout>(R.id.frg_dietpln_clGoal)
            if(switch2.isChecked){

                constraint.visibility=VISIBLE
            }
            else{
                constraint.visibility= INVISIBLE
            }
        }
        val btnSave=view.findViewById<Button>(R.id.frg_dietplan_btnSave).setOnClickListener {
              val dietPlan=__UserDietPlan(proteinProgress/100f,fatProgress/100f,carbProgress/100f)
            dietPlan.baseCalories=newDailyIntake
            dietPlan.weeklyProgress=resultProgress
            dietPlan.setNewGoalTimeframe(dateStart,dateEnd)
            listener.onRetrieveDietPlan(dietPlan)

        }
        return view
    }

    private fun computeGoal(dayCount: Int, progress: Int) {
        val Ax=0f
        val Ay=-0.5f
        val Bx=100f
        val By=0.5f
         val result=__Utility.interpolateValue(Ax,Ay,progress.toFloat(),Bx,By)
        resultProgress=progress
         newDailyIntake=currentUser.computeBaseIntake()
        newDailyIntake+=((result*8800f)/7f).toInt()
        val tvWeeksAvailable=requireView().findViewById<TextView>(R.id.frg_dietplan_tvWeeks)
        val tvExpectedWeight=requireView().findViewById<TextView>(R.id.frg_dietplan_tvExpected)
        val btnDailyIntake=requireView().findViewById<Button>(R.id.frg_dietplan_btnDailyIntake)
        tvWeeksAvailable.text="${String.format("%.1f",dayCount/7f)} weeks"
        tvExpectedWeight.text="${String.format("%.1f",currentUser.weightRecord!![0].value!!+((result*dayCount)/7f))} Kgs"
        btnDailyIntake.text="$newDailyIntake kcal"
    }

    fun setListener(listener: __FragmentDietPlanListener) {
        this.listener=listener

    }


}