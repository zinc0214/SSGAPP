package com.zinc0214.ssgapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zinc0214.ssgapp.FirebaseViewModel
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.MemberInfoDTO
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FirebaseViewModel
    private var membersInfo = ArrayList<MemberInfoDTO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        viewModel = FirebaseViewModel()

        binding.apply { activity = this@MainActivity }
        viewModel.membesInfoDTO.observe(this, membersInfoObserver)

        viewModel.loadMembersInfo()
    }

    private val membersInfoObserver = Observer<List<MemberInfoDTO>> {
        membersInfo = it as ArrayList<MemberInfoDTO>
    }

    fun goToAddMember() {
        val intent = Intent(this, AddNewMemberActivity::class.java)
        startActivity(intent)
    }

    fun goToAddMoim() {
        val intent = Intent(this, AddMoimActivity::class.java)
        intent.putExtra("memberInfo", membersInfo)
        startActivity(intent)
    }
}