package com.zinc0214.ssgapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zinc0214.ssgapp.*
import com.zinc0214.ssgapp.databinding.ActivityAddNewMemberBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNewMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewMemberBinding
    private lateinit var viewMode: FirebaseViewModel
    private var confirmEnabled = false
    private var selectDate = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_add_new_member
        )
        viewMode = FirebaseViewModel()
        checkView()
    }

    private fun checkView() {
        with(binding) {
            nickNameEdit.onTextChanged { checkConfirmEnable() }
            addrEdit.onTextChanged { checkConfirmEnable() }
            isEnabled = confirmEnabled
            confirmClickListener = View.OnClickListener { addNewMember() }
            calendar.setOnDateChangeListener { _, p1, p2, p3 ->
                selectDate = "$p1/${(p2 + 1).setDate()}/${p3.setDate()}"
            }
        }
    }

    private fun checkConfirmEnable() {
        with(binding) {
            confirmEnabled = nickNameEdit.text?.isNotBlank()!! && addrEdit.text?.isNotBlank()!!
            confirmButton.isEnabled = confirmEnabled
            notifyChange()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addNewMember() {
        with(binding) {
            val newMember = NewMember(
                nickNameEdit.text.toString(),
                slider.value.toInt(),
                addrEdit.text.toString(),
                selectDate,
                if (man.isChecked) "남자" else "여자"
            )

            viewMode.addNewMember(newMember) { isSuccess, content ->
                Toast.makeText(this@AddNewMemberActivity, content, Toast.LENGTH_SHORT).show()
                if (isSuccess) finish()
            }
        }
    }
}