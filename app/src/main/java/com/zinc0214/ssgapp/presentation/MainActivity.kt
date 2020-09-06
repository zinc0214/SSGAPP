package com.zinc0214.ssgapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zinc0214.ssgapp.*
import com.zinc0214.ssgapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FirebaseViewModel
    private var membersInfoDTOS = ArrayList<MemberInfoDTO>()
    private var memberInfos = ArrayList<MemberInfo>()
    private lateinit var resultCallBack: FirebaseViewModel.SendResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        viewModel = FirebaseViewModel()

        resultCallBack = object : FirebaseViewModel.SendResult {
            override fun success(string: String) {
                Toast.makeText(this@MainActivity, string, Toast.LENGTH_SHORT).show()
            }

            override fun fail(string: String) {
                Toast.makeText(this@MainActivity, string, Toast.LENGTH_SHORT).show()
            }
        }

        binding.apply { activity = this@MainActivity }

        viewModel.membesInfoDTO.observe(this, membersInfoObserver)
        viewModel.loading.observe(this, loadingObserver)

        viewModel.loadMembersInfo(resultCallBack)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMembersInfo(resultCallBack)
    }

    private val membersInfoObserver = Observer<List<MemberInfoDTO>> {
        membersInfoDTOS = it as ArrayList<MemberInfoDTO>
        memberInfos = changeDTO(membersInfoDTOS)
    }

    private val loadingObserver = Observer<Boolean> {
        binding.isLoading = it
    }


    fun goToAddMember() {
        val intent = Intent(this, AddNewMemberActivity::class.java)
        startActivity(intent)
    }

    fun goToAddMoim() {
        val intent = Intent(this, AddMoimActivity::class.java)
        intent.putExtra("memberInfo", membersInfoDTOS)
        startActivity(intent)
    }

    fun goToManageMember() {
        val intent = Intent(this, MemberManageActivity::class.java)
        intent.putExtra("memberInfo", membersInfoDTOS)
        startActivity(intent)
    }
}