package com.example.macromanager.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.macromanager.Listeners.__FragmentUserBiometricsListener
import com.example.macromanager.R
import com.example.myapplication.Entity.__UserBiometrics
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class __FragmentUserBiometrics:Fragment(R.layout.fragment_user_biometrics) {
private lateinit var listener:__FragmentUserBiometricsListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_user_biometrics,container,false)
        val ilHeight=view.findViewById<TextInputLayout>(R.id.frg_reg2_ilHeight)
        val etHeight=view.findViewById<TextInputEditText>(R.id.frg_reg2_etHeight)
        val ilWeight=view.findViewById<TextInputLayout>(R.id.frg_reg2_ilWeight)
        val etWeight=view.findViewById<TextInputEditText>(R.id.frg_reg2_etWeight)
        val ilAge=view.findViewById<TextInputLayout>(R.id.frg_reg2_ilAge)
        val etAge=view.findViewById<TextInputEditText>(R.id.frg_reg2_etAge)
        val spSex=view.findViewById<Spinner>(R.id.frg_reg2_spSex)
        val spActLevel=view.findViewById<Spinner>(R.id.frg_reg2_spActLevel)
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
            condAge=etAge.text.toString().isNotEmpty()
            condHeight=etHeight.text.toString().isNotEmpty()
            condWeight=etWeight.text.toString().isNotEmpty()
            if(condAge and condHeight and condWeight){
                val userBiometrics=__UserBiometrics(etHeight.text.toString().toFloat(),etWeight.text.toString().toFloat(),etAge.text.toString().toInt(),isMan,coeff)
                listener.onRetrieveUserBiometrics(userBiometrics)
            }
            if(!condAge){
                ilAge.setHelperTextColor(resources.getColorStateList(R.color.red))
            }
            else{
                ilAge.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
            }
            if(!condHeight){
                ilHeight.setHelperTextColor(resources.getColorStateList(R.color.red))
            }
            else{
                ilHeight.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
            }
            if(!condWeight){
                ilWeight.setHelperTextColor(resources.getColorStateList(R.color.red))
            }
            else{
                ilWeight.setHelperTextColor(resources.getColorStateList(R.color.colorHint))
            }
        }




                return view
    }

    fun setListener(newListener: __FragmentUserBiometricsListener) {
        listener=newListener

    }
}