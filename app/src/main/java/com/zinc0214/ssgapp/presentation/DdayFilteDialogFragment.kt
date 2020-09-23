package com.zinc0214.ssgapp.presentation

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.FragmentDdayFilteDialogBinding


class DdayFilteDialogFragment(private val infos: ArrayList<MemberInfo>) : DialogFragment() {

    private lateinit var binding: FragmentDdayFilteDialogBinding

    override fun onResume() {
        super.onResume()

        val dialogWidth = resources.getDimensionPixelSize(R.dimen.width)
        val dialogHeight = ActionBar.LayoutParams.WRAP_CONTENT
        dialog?.window!!.setLayout(dialogWidth, dialogHeight)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dday_filte_dialog, container, false)


        binding.ddayEdit.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setMemberText()
                return@OnEditorActionListener true
            }
            false
        })

        binding.ddayToogle.setOnCheckedChangeListener { _, _ ->
            setMemberText()
        }

        binding.ddayMember.setTextIsSelectable(true)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }


    private fun setMemberText() {
        binding.ddayMember.text = infos.getDdayMember(binding.ddayEdit.safeText())
    }

    private fun EditText.safeText(): Int {
        return if (this.text.isNotBlank()) this.text.toString().toInt() else 0
    }

    private fun List<MemberInfo>.getDdayMember(dday: Int): String {
        var ddayMemberString = ""

        this.filter { it.getDday() > dday }.forEach { memberInfo ->
            ddayMemberString += if (binding.ddayToogle.isChecked) "${memberInfo.nickname}(D+${memberInfo.getDday()}) " else "${memberInfo.nickname} "
        }
        return ddayMemberString
    }


}