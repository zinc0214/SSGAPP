package com.zinc0214.ssgapp.presentation

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
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
    private var memberInfoDTOv = ArrayList<MemberInfoDTO>()
    private var moimInfo: MoimInfo? = null
    private var selectDate = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memberInfoDTO = intent.getSerializableExtra("memberInfo") as ArrayList<MemberInfoDTO>
        membersInfo = changeDTO(memberInfoDTO)
        memberInfoDTOv = memberInfoDTO
        val intentMoimInfo = intent.getSerializableExtra("moimInfo") as? MoimInfo
        moimInfo = intentMoimInfo

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_moim)
        viewModel = FirebaseViewModel()

        preSetUpView()
        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            attendeeEdit.imeOptions = EditorInfo.IME_ACTION_DONE
            attendeeEdit.setRawInputType(InputType.TYPE_CLASS_TEXT)

            attendeeEdit.onTextChanged {
                chipgroup.removeAllViews()
                val list = it.split(".", ",", "/", " ").distinct()
                list.forEach { name ->
                    if (name.isNotBlank()) {
                        val chip = layoutInflater.inflate(R.layout.chip_item, null, false) as Chip
                        chip.text = name
                        chipgroup.addView(chip)
                    }
                }
            }

            calendar.setOnDateChangeListener { _, p1, p2, p3 ->
                val month = if (p2 == 9) "10" else (p2 + 1).setDate()
                val day = if (p3 == 10) "10" else p3.setDate()
                selectDate = "$p1/$month/$day"
                Log.e("ayhan", "selectDate : $selectDate")
            }

            confirmClickListener =
                View.OnClickListener {
                    if (previousCheck() && checkCreator()) {
                        if (moimInfo != null) {
                            viewModel.deleteMoim(
                                moimInfo!!,
                                memberInfoDTOv,
                                object : FirebaseViewModel.SendResult {
                                    override fun success(string: String) {
                                        viewModel.loadMembersInfo(object :
                                            FirebaseViewModel.SendResult {
                                            override fun success(string: String) {
                                                addMoim()
                                                addMoimInfo()
                                            }

                                            override fun fail(string: String) {
                                                Toast.makeText(
                                                    this@AddMoimActivity,
                                                    string,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }

                                        })
                                    }

                                    override fun fail(string: String) {
                                        Toast.makeText(
                                            this@AddMoimActivity,
                                            string,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                })
                        } else {
                            addMoim()
                            addMoimInfo()
                        }

                    }
                }
        }

    }

    private fun preSetUpView() {
        moimInfo?.let {

            binding.apply {
                attendeeEdit.setText("${it.creator}, ${it.attendee}")
                addrEdit.setText(it.addr)
                kindEdit.setText(it.kind)
                calendar.date = it.date.toDate().time
            }

        }

    }


    private fun previousCheck(): Boolean {

        val notAddedMemberList = ArrayList<Chip>()
        var notAddedMembers = ""

        for (i in 0 until binding.chipgroup.childCount) {
            val chip = binding.chipgroup.getChildAt(i) as Chip
            val list = membersInfo.filter { it.nickname == chip.text }
            if (list.isEmpty()) {
                notAddedMemberList.add(chip)
                notAddedMembers += chip.text.toString() + ", "
            }
        }

        return if (notAddedMemberList.isEmpty()) true
        else {
            Toast.makeText(
                this@AddMoimActivity,
                "$notAddedMembers 는 없는 멤버입니다.\n멤버추가를 먼저 해주세요!",
                Toast.LENGTH_SHORT
            ).show()
            notAddedMemberList.forEach {
                binding.chipgroup.removeView(it)
            }
            false
        }
    }

    private fun checkCreator(): Boolean {
        for (i in 0 until binding.chipgroup.childCount) {
            val chip = binding.chipgroup.getChildAt(i) as Chip
            if (chip.isChecked) return true
        }
        Toast.makeText(
            this@AddMoimActivity,
            "벙 생성자를 선택해주세요.",
            Toast.LENGTH_SHORT
        ).show()
        return false
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

    private fun addMoimInfo() {
        var creator = ""
        var attendee = ""
        for (i in 0 until binding.chipgroup.childCount) {
            val chip = binding.chipgroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                creator = chip.text as String
            } else {
                attendee = if (attendee.isNotBlank()) {
                    attendee + ", " + chip.text
                } else {
                    chip.text.toString()
                }

            }

        }
        val moimInfo = MoimInfo().apply {
            this.id = selectDate.replace("/", "") + "_${creator}"
            this.date = selectDate
            this.creator = creator
            this.attendee = attendee
            this.addr = binding.addrEdit.text.toString()
            this.kind = binding.kindEdit.text.toString()
        }

        viewModel.addMoimInfo(moimInfo)
    }
}