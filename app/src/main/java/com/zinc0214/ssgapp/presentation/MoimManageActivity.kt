package com.zinc0214.ssgapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zinc0214.ssgapp.FirebaseViewModel
import com.zinc0214.ssgapp.MemberInfoDTO
import com.zinc0214.ssgapp.MoimInfo
import com.zinc0214.ssgapp.R
import com.zinc0214.ssgapp.databinding.ActivityManageMoimBinding

class MoimManageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageMoimBinding
    private lateinit var viewModel: FirebaseViewModel
    private lateinit var moimAdatper: MoimInfosAdapter
    private lateinit var memberInfos: ArrayList<MemberInfoDTO>

    override fun onResume() {
        super.onResume()
        viewModel.loadMoimInfo()
        viewModel.loadMembersInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val memberInfoDTO = intent.getSerializableExtra("memberInfo") as ArrayList<MemberInfoDTO>
        memberInfos = memberInfoDTO

        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_moim)
        viewModel = FirebaseViewModel()
        viewModel.loadMoimInfo()
        viewModel.loadMembersInfo()

        viewModel.moimInfoDTO.observe(this, moimInfoObserver)
        viewModel.loading.observe(this, loadingObserver)
    }

    private fun setUpView(infos: List<MoimInfo>) {

        val realInfo = if (infos.size < 2) {
            infos
        } else {
            infos.reversed()
        }
        moimAdatper = MoimInfosAdapter(
            realInfo as ArrayList<MoimInfo>,
            object : MoimItemOptionClickInterface {
                override fun edit(info: MoimInfo) {
                    showEditNoticeDialog(info)
                }

                override fun delete(info: MoimInfo) {
                    showDeleteDialog(info)
                }

            })

        binding.apply {
            recyclerView.adapter = moimAdatper
        }
    }

    private val moimInfoObserver = Observer<List<MoimInfo>> {
        setUpView(it)
    }

    private val loadingObserver = Observer<Boolean> {
        binding.isLoading = it
    }


    private fun showDeleteDialog(info: MoimInfo) {
        DeleteMoimDialogFragment(info) {
            viewModel.deleteMoim(info, memberInfos, object : FirebaseViewModel.SendResult {
                override fun success(string: String) {
                    Toast.makeText(this@MoimManageActivity, string, Toast.LENGTH_SHORT).show()
                    moimAdatper.deleteMoim(info.id)
                }

                override fun fail(string: String) {
                    Toast.makeText(this@MoimManageActivity, string, Toast.LENGTH_SHORT).show()
                }
            })
        }.show(this.supportFragmentManager, "tag")
    }

    private fun showEditNoticeDialog(info: MoimInfo) {
        EditMoimDialogFragment {
            goToMoimEdit(info)
        }.show(this.supportFragmentManager, "tag")
    }

    private fun goToMoimEdit(info: MoimInfo) {
        val intent = Intent(this, AddMoimActivity::class.java)
        intent.putExtra("memberInfo", memberInfos)
        intent.putExtra("moimInfo", info)
        startActivity(intent)
    }
}