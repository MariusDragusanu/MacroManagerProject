package com.example.macromanager.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.macromanager.R

class __EntryActivity:AppCompatActivity() {
    override fun onBackPressed() {

        super.onBackPressed()
        super.finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

    }

}