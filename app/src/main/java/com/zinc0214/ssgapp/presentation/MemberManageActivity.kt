package com.zinc0214.ssgapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zinc0214.ssgapp.*
import com.zinc0214.ssgapp.databinding.ActivityManageMemberBinding

class MemberManageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageMemberBinding
    private lateinit var viewModel: FirebaseViewModel
    private lateinit var adapter: MemberInfosAdapter
    private var membersInfo = ArrayList<MemberInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memberInfoDTO = intent.getSerializableExtra("memberInfo") as ArrayList<MemberInfoDTO>
        membersInfo = changeDTO(memberInfoDTO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_member)
        viewModel = FirebaseViewModel()

        setUpView()
    }

    private fun setUpView() {

        adapter = MemberInfosAdapter(membersInfo, object : ItemOptionClickInterface {
            override fun edit(string: String) {
                showEditDialog(string)
            }

            override fun delete(string: String) {
                showDeleteDialog(string)
            }

        })

        binding.apply {
            recyclerView.adapter = adapter

            memberSearchEdit.onAfterTextChanged {
                val name = memberSearchEdit.text.toString()
                adapter.filter?.filter(name)
            }
        }
    }

    private fun showEditDialog(nickname: String) {
        val originInfo = membersInfo.filter { it.nickname == nickname }[0]
        EditMemberDialogFragment(originInfo) { editInfo ->
            viewModel.eidtMember(editInfo, object : FirebaseViewModel.SendResult {
                override fun success(string: String) {
                    Toast.makeText(this@MemberManageActivity, string, Toast.LENGTH_SHORT).show()
                    adapter.editMember(editInfo)
                }

                override fun fail(string: String) {
                    Toast.makeText(this@MemberManageActivity, string, Toast.LENGTH_SHORT).show()
                }

            })
        }.show(this.supportFragmentManager, "tag")
    }

    private fun showDeleteDialog(nickname: String) {
        DeleteMemberDialogFragment(nickname) {
            viewModel.deleteMember(nickname, object : FirebaseViewModel.SendResult {
                override fun success(string: String) {
                    Toast.makeText(this@MemberManageActivity, string, Toast.LENGTH_SHORT).show()
                    adapter.deleteMember(nickname)
                }

                override fun fail(string: String) {
                    Toast.makeText(this@MemberManageActivity, string, Toast.LENGTH_SHORT).show()
                }
            })
        }.show(this.supportFragmentManager, "tag")
    }


}