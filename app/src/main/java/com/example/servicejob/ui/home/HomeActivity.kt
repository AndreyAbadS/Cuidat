package com.example.servicejob.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.servicejob.R
import com.example.servicejob.databinding.ActivityHomeBinding
import com.example.servicejob.ui.activity.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttomNavigationBarHome.itemIconTintList = null
        chechCurrentUser()
    }

    private fun chechCurrentUser(){
        val user = Firebase.auth.currentUser
        if (user!=null){

        }else{
            binding.fragmentContainerBody.isVisible = false
            binding.buttomNavigationBarHome.isVisible = false
            Toast.makeText(applicationContext,getString(R.string.message_singin),Toast.LENGTH_SHORT)
            val intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}