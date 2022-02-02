package com.example.macromanager.Fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.example.macromanager.CustomViews.__GraphView
import com.example.macromanager.Entity.__User3
import com.example.macromanager.Listeners.FragmentListener.__FragmentSettingsBiometricsListener
import com.example.macromanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class __FragmentSettingsBiometrics(val currentUser:__User3):Fragment() {
    private lateinit var listener:__FragmentSettingsBiometricsListener
    fun setListener(newListener: __FragmentSettingsBiometricsListener){
        this.listener=newListener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_settings_biometrics,container,false)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner) {
            listener.detachFragmentSettingsBiometrics(this@__FragmentSettingsBiometrics,currentUser)
        }
        val fab=view.findViewById<FloatingActionButton>(R.id.frg_settings_biometrics_fabAddWeight)
        fab.setOnClickListener {
            val dialog=Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_modify_value_layout)
            dialog.show()
            val btnAdd = dialog.findViewById<Button>(R.id.dialog_value_btnAdd)
            val btnCancel = dialog.findViewById<Button>(R.id.dialog_value_btnCancel)
            val btnPlusDialog = dialog.findViewById<AppCompatImageButton>(R.id.dialog_value_btnPlus)
            val btnMinus = dialog.findViewById<AppCompatImageButton>(R.id.dialog_value_btnMinus)

            val display = dialog.findViewById<TextView>(R.id.dialog_value_tvOutput)
            var result=currentUser.userBiometrics?.weightRecord?.last()?.value!!
            display.text = String.format("%.1f", result)
            btnPlusDialog.setOnClickListener {
                result += 0.1f
                display.text =" ${String.format("%.1f", result)} Kgs"
                btnAdd.visibility = View.VISIBLE
            }
            btnMinus.setOnClickListener {
                btnAdd.visibility = View.VISIBLE
                display.text = " ${String.format("%.1f", result)} Kgs"
                result -= 0.1f
            }
            btnAdd.setOnClickListener {
                val stringOutput=display.text.toString()
                val newWeight=stringOutput.removeRange( stringOutput.length-4,stringOutput.length).toFloat()
                currentUser.addNewWeight(newWeight)
                listener.detachFragmentSettingsBiometrics(this,currentUser)
                dialog.dismiss()

            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        val gvWeight=view.findViewById<__GraphView>(R.id.frg_settings_biometrics_gvWeight)
        for(weightEntry in currentUser.userBiometrics.weightRecord)
          gvWeight.addEntryValue(Pair(weightEntry.date.getDateAsString(),weightEntry.value))
        return view
    }
}