package com.zinc0214.ssgapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.zinc0214.ssgapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            activity = this@MainActivity
        }
    }

    fun goToAddMember() {
        val intent = Intent(this, AddNewMemberActivity::class.java)
        startActivity(intent)
    }
}