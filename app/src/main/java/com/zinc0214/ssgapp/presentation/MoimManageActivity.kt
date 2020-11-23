package com.zinc0214.ssgapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zinc0214.ssgapp.FirebaseViewModel
import com.zinc0214.ssgapp.MoimInfo
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.ActivityManageMoimBinding

class MoimManageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageMoimBinding
    private lateinit var viewModel: FirebaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_moim)
        viewModel = FirebaseViewModel()
        viewModel.loadMoimInfo(object : FirebaseViewModel.SendResult {
            override fun success(string: String) {

            }

            override fun fail(string: String) {

            }

        })

        viewModel.moimInfoDTO.observe(this, moimInfoObserver)
    }

    private fun setUpView(infos: List<MoimInfo>) {

        val realInfo = if (infos.size < 2) {
            infos
        } else {
            infos.reversed()
        }
        val adapter = MoimInfosAdapter(realInfo as ArrayList<MoimInfo>)

        binding.apply {
            recyclerView.adapter = adapter
        }
    }

    private val moimInfoObserver = Observer<List<MoimInfo>> {
        setUpView(it)
    }

}