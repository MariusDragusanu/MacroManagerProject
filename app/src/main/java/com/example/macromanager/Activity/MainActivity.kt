package com.example.macromanager.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.macromanager.Entity.__User2
import com.example.macromanager.Fragments.__FragmentAccount
import com.example.macromanager.Fragments.__FragmentFoodLibrary
import com.example.macromanager.Fragments.__FragmentHome
import com.example.macromanager.R
import com.example.macromanager.ViewModel.__UserViewModel
import com.example.macromanager.ViewPagers.__GenericViewPagerAdapter
import com.example.myapplication.Listeners.__UserRepositoryListener
import com.example.myapplication.Repository.__UserRepository2
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),__UserRepositoryListener {
private lateinit var user2: __User2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bar=findViewById<ProgressBar>(R.id.main_pnBar)
        bar.visibility=INVISIBLE
        val fragmentArrayList= mutableListOf(__FragmentFoodLibrary(),__FragmentHome(__User2(firebaseUser = FirebaseAuth.getInstance().currentUser)),__FragmentAccount())
        val pager=findViewById<ViewPager2>(R.id.main_ViewPager)
        val adapter=__GenericViewPagerAdapter(fragmentArrayList,supportFragmentManager,lifecycle)
        pager.adapter=adapter
        __UserRepository2.setListener(this)
        __UserRepository2.getInstance()?.retrieveUser(FirebaseAuth.getInstance().currentUser?.uid!!)

    }

    override fun onPause() {
        super.onPause()
        __UserRepository2.getInstance()?.updateUser(user2)
    }
    override fun getRequestStatus(requestName: String, status: Boolean, error: String?) {
        if(requestName=="UPDATE_USER"){
            if(status){
                CoroutineScope(Dispatchers.Main).launch { Toast.makeText(this@MainActivity, "Changes saved", Toast.LENGTH_LONG).show()}
            }
            else{
                CoroutineScope(Dispatchers.Main).launch {   Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show() }
            }
        }
        if(requestName=="RETRIEVE_USER"){
            if(status){

                 CoroutineScope(Dispatchers.Main).launch {  Toast.makeText(this@MainActivity, "Retrieval completed!", Toast.LENGTH_SHORT).show()}
            }
            else{
                CoroutineScope(Dispatchers.Main).launch {   Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show() }

            }
        }
    }
    override fun getUser(retrievedUser: __User2) {
               CoroutineScope(Dispatchers.Main).launch {
                 user2=retrievedUser
                   user2.getAccountInfo()!!.setEmail(FirebaseAuth.getInstance().currentUser?.email!!)
                   Log.d("Test","from here")
                   Toast.makeText(this@MainActivity, retrievedUser.UID, Toast.LENGTH_SHORT).show()
                   val bar=findViewById<ProgressBar>(R.id.main_pnBar)
                   bar.visibility=INVISIBLE
               }
    }
}