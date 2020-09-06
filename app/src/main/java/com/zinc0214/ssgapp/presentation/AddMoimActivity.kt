package com.zinc0214.ssgapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.zinc0214.ssgapp.*
import com.zinc0214.ssgapp.databinding.ActivityAddMoimBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddMoimActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMoimBinding
    private lateinit var viewModel: FirebaseViewModel
    private var membersInfo = ArrayList<MemberInfo>()
    private var selectDate = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memberInfoDTO = intent.getSerializableExtra("memberInfo") as ArrayList<MemberInfoDTO>
        membersInfo = changeDTO(memberInfoDTO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_moim)
        viewModel = FirebaseViewModel()
        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            attendeeEdit.onTextChanged {
                chipgroup.removeAllViews()
                val list = it.split(".", ",", "/", " ").distinct()
                list.forEach { name ->
                    val chip = layoutInflater.inflate(R.layout.chip_item, null, false) as Chip
                    chip.text = name
                    chipgroup.addView(chip)
                }
                calendar.setOnDateChangeListener { _, p1, p2, p3 ->
                    selectDate = "$p1/${(p2 + 1).setDate()}/${p3.setDate()}"
                }
            }

            confirmClickListener = View.OnClickListener { addMoim() }
        }
    }

    private fun addMoim() {
        val updateMembers = ArrayList<MemberInfo>()

        for (i in 0 until binding.chipgroup.childCount) {
            val chip = binding.chipgroup.getChildAt(i) as Chip
            membersInfo.forEach {
                if (it.nickname == chip.text) {
                    val member = it
                    if (chip.isChecked) member.createCount++ else member.attendeCount++
                    member.lastDate = selectDate
                    updateMembers.add(member)
                }
            }
        }

        viewModel.addMoim(updateMembers, object : FirebaseViewModel.SendResult {
            override fun success(string: String) {
                Toast.makeText(this@AddMoimActivity, string, Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun fail(string: String) {
                Toast.makeText(this@AddMoimActivity, string, Toast.LENGTH_SHORT).show()
            }
        })
    }
}