package com.example.macromanager.Fragments

import __Record
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.macromanager.Listeners.__Register2Listener
import com.example.macromanager.R
import com.example.myapplication.Entity.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class __FragmentRegister2:Fragment(R.layout.fragment_register_2) {
    private lateinit var listener:__Register2Listener
    fun setListener(newListener: __Register2Listener){
        listener=newListener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_register_2,container,false)
        val etHeight=view.findViewById<TextInputEditText>(R.id.frg_reg2_etHeight)
        val etWeight=view.findViewById<TextInputEditText>(R.id.frg_reg2_etWeight)
        val ilHeight=view.findViewById<TextInputLayout>(R.id.frg_reg2_ilHeight)
        val ilWeight=view.findViewById<TextInputLayout>(R.id.frg_reg2_ilWeight)
        val spSex=view.findViewById<Spinner>(R.id.frg_reg2_spSex)
        val spActLevel=view.findViewById<Spinner>(R.id.frg_reg2_spActLevel)
        val ilAge=view.findViewById<TextInputLayout>(R.id.frg_reg2_ilAge)
        val etAge=view.findViewById<TextInputEditText>(R.id.frg_reg2_etAge)
        val btnContinue=view.findViewById<Button>(R.id.frg_reg2_btnContinue)
        var condHeight=false
        var condWeight=false
        var condAge=false
        var isMan=true
        var coeff=1.25f

        spSex.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

            }
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                isMan = position == 0

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        spActLevel.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {

            override fun onItemClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                when (position) {
                    0 -> coeff = 1.25f
                    1 -> coeff = 1.375f
                    2 -> coeff = 1.55f
                    3 -> coeff = 1.725f
                    4 -> coeff = 1.9f

                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        btnContinue.setOnClickListener {
            condAge = etAge.text.toString().isNotEmpty()
            condHeight = etHeight.text.toString().isNotEmpty()
            condWeight = etWeight.text.toString().isNotEmpty()
            if (!condAge) {
                ilAge.setHelperTextColor(resources.getColorStateList(R.color.red))
            } else {
                ilAge.setHelperTextColor(resources.getColorStateList(R.color.colorPrimary2))
            }
            if (!condWeight) {
                ilWeight.setHelperTextColor(resources.getColorStateList(R.color.red))
            } else {
                ilWeight.setHelperTextColor(resources.getColorStateList(R.color.colorPrimary2))
            }
            if (!condHeight) {
                ilHeight.setHelperTextColor(resources.getColorStateList(R.color.red))
            } else {
                ilHeight.setHelperTextColor(resources.getColorStateList(R.color.colorPrimary2))
            }
            if (condAge and condHeight and condWeight) {

                val userBiometrics = __UserBiometrics(
                    etHeight.text.toString().toFloat(),

                    etAge.text.toString().toInt(),
                    isMan,
                    coeff
                )
                userBiometrics.addNewWeightEntry(__Record(__Date(),etWeight.text.toString().toFloat()))
                 userBiometrics.onUpdate()
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_decision)
                dialog.show()
                val tvTitle = dialog.findViewById<TextView>(R.id.dialog_decision_tvTitle)
                val btnYes = dialog.findViewById<Button>(R.id.dialog_decision_btnYes)
                val btnNo = dialog.findViewById<Button>(R.id.dialog_decision_btnNo)
                btnNo.text = "Later"
                btnNo.setTextColor(resources.getColorStateList(R.color.red))
                tvTitle.text = "Do you want to set a custom diet plan?"
                btnYes.setOnClickListener {
                    listener.getBiometrics(userBiometrics)
                    listener.decidedForMealPlan()
                    dialog.dismiss()
                }
                btnNo.setOnClickListener {
                    listener.getBiometrics(userBiometrics)
                    listener.hasFinishedRegistration()
                    dialog.dismiss()
                }
            }

        }
        return view
    }
}