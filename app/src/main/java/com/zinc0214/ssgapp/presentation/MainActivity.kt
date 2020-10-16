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
        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMembersInfo(resultCallBack)
    }

    private fun setUpViews() {
        binding.memberLayout.apply {
            attendTextView.setTextIsSelectable(true)
            createTextView.setTextIsSelectable(true)
            redMember.setTextIsSelectable(true)
        }
    }

    private val membersInfoObserver = Observer<List<MemberInfoDTO>> {
        membersInfoDTOS = it as ArrayList<MemberInfoDTO>
        memberInfos = changeDTO(membersInfoDTOS)
        binding.memberState = memberState()
        binding.memberLayout.redTextView.setOnClickListener {
            DdayFilteDialogFragment(memberInfos).show(
                this.supportFragmentManager,
                "filter"
            )
        }
    }

    private val loadingObserver = Observer<Boolean> {
        binding.isLoading = it
    }


    private fun memberState(): MemberState {
        val count = memberInfos.count()
        val manCount = memberInfos.filter { it.gender == "남" }.count()
        val womanCount = memberInfos.filter { it.gender == "여" }.count()

        return MemberState(
            count,
            manCount,
            womanCount,
            memberInfos.attendString(),
            memberInfos.createString(),
            memberInfos.getRedMember()
        )
    }

    private fun List<MemberInfo>.attendString(): String {
        var attendString = ""
        this.sortedByDescending { it.attendeCount }.take(5)
            .forEachIndexed { index, memberInfo ->
                attendString += "${index + 1}등 : ${memberInfo.nickname}(${memberInfo.attendeCount}번)\n"
            }

        return attendString
    }

    private fun List<MemberInfo>.createString(): String {
        var attendString = ""
        this.sortedByDescending { it.createCount }.take(5).forEachIndexed { index, memberInfo ->
            attendString += "${index + 1}등 : ${memberInfo.nickname}(${memberInfo.createCount}번)\n"
        }
        return attendString
    }

    private fun List<MemberInfo>.getRedMember(): String {
        var redMemberString = ""
        this.filter { it.getDday() > 30 }.forEach { memberInfo ->
            redMemberString += "${memberInfo.nickname} "
        }
        return redMemberString
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

    fun goToManagerMoim() {
        val intent = Intent(this, MoimManageActivity::class.java)
        startActivity(intent)
    }

}