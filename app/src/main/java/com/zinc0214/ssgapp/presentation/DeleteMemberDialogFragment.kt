package com.zinc0214.ssgapp.presentation

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.FragmentDeleteMemberDialogBinding

class DeleteMemberDialogFragment(val name: String, val confirm: () -> Unit) : DialogFragment() {

    private lateinit var binding: FragmentDeleteMemberDialogBinding

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

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_delete_member_dialog,
            container,
            false
        )
        binding.nickName = name
        binding.cancelButtonClickListener = View.OnClickListener {
            dismiss()
        }
        binding.confirmButtonClickListener = View.OnClickListener {
            dismiss()
            confirm()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }


}