package com.zinc0214.ssgapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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

        Log.e("ayhan", "currentDate :$selectDate")
        checkView()
    }

    private fun checkView() {
        with(binding) {
            nickNameEdit.onTextChanged { checkConfirmEnable() }
            addrEdit.onTextChanged { checkConfirmEnable() }
            ageEdit.onTextChanged { checkConfirmEnable() }
            isEnabled = confirmEnabled
            confirmClickListener = View.OnClickListener { addNewMember() }
            calendar.setOnDateChangeListener { _, p1, p2, p3 ->
                val month = if (p2 == 9) "10" else "${p2 + 1}"
                val day = if (p3 == 10) "10" else p3.setDate()
                selectDate = "$p1/$month/$day"
                Log.e("ayhan", "selectDate : $selectDate")
            }

        }
    }

    private fun checkConfirmEnable() {
        with(binding) {
            confirmEnabled =
                nickNameEdit.text?.isNotBlank()!! && addrEdit.text?.isNotBlank()!! && ageEdit.text?.isNotBlank()!!
            confirmButton.isEnabled = confirmEnabled
            notifyChange()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addNewMember() {
        with(binding) {
            val newMember = NewMember(
                nickNameEdit.text.toString(),
                ageEdit.text.toString().toInt(),
                addrEdit.text.toString(),
                selectDate,
                if (genderButton.isChecked) "남" else "여",
                realNameEdit.text.toString()
            )

            viewMode.addNewMember(newMember, object : FirebaseViewModel.SendResult {
                override fun success(string: String) {
                    Toast.makeText(this@AddNewMemberActivity, string, Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun fail(string: String) {
                    Toast.makeText(this@AddNewMemberActivity, string, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}