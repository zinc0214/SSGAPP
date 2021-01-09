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
import com.zinc0214.ssgapp.MoimInfo
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.FragmentDeleteMoimDialogBinding

class DeleteMoimDialogFragment(val info: MoimInfo, val confirm: () -> Unit) : DialogFragment() {

    private lateinit var binding: FragmentDeleteMoimDialogBinding

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
            R.layout.fragment_delete_moim_dialog,
            container,
            false
        )
        binding.titleDesc = "${info.creator} 이(가) 생성한\n모임을 삭제하시겠습니까?"
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