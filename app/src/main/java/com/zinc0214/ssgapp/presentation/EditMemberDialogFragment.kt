package com.zinc0214.ssgapp.presentation

import android.annotation.SuppressLint
import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.zinc0214.ssgapp.EditMember
import com.zinc0214.ssgapp.MemberInfo
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.FragmentEditMemberInfoBinding
import com.zinc0214.ssgapp.onTextChanged

class EditMemberDialogFragment(val info: MemberInfo, val confirm: (EditMember) -> Unit) :
    DialogFragment() {

    private lateinit var binding: FragmentEditMemberInfoBinding
    private var confirmEnabled = false

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
            DataBindingUtil.inflate(inflater, R.layout.fragment_edit_member_info, container, false)

        checkConfirmEnable()
        with(binding) {
            memberInfo = info
            addrEdit.onTextChanged { checkConfirmEnable() }
            isEnabled = confirmEnabled
            confirmClickListener = View.OnClickListener { addNewMember() }
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }

    private fun checkConfirmEnable() {
        with(binding) {
            confirmEnabled = addrEdit.text?.isNotBlank()!!
            confirmButton.isEnabled = confirmEnabled
            notifyChange()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addNewMember() {
        with(binding) {
            val editMember = EditMember(
                info.nickname,
                slider.value.toInt(),
                addrEdit.text.toString(),
                realNameEdit.text.toString()
            )
            confirm(editMember)
        }
    }
}