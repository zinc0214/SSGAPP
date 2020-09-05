package com.zinc0214.ssgapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FirebaseViewModel : ViewModel() {

    private val _membersInfo = MutableLiveData<ArrayList<MemberInfoDTO>>()
    val membesInfoDTO: LiveData<ArrayList<MemberInfoDTO>> get() = _membersInfo

    fun loadMembersInfo() {
        val database = Firebase.database.reference
        val contentDB = database.child("user")

        contentDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("ayhan", "error : $error")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<MemberInfoDTO>()
                snapshot.children.forEach {
                    it.getValue<MemberInfoDTO>()?.let { result ->
                        Log.e("ayhan", "hahaha: ${result.nickname}")
                        list.add(result)
                    }
                }
                _membersInfo.value = list
            }
        })
    }

    fun addNewMember(newMember: NewMember, result: (Boolean, String) -> Unit) {

        val database = Firebase.database.reference
        database.child("user").child(newMember.nickname).setValue(newMember)
            .addOnSuccessListener {
                result(true, "데이터 전송에 성공!")
            }
            .addOnFailureListener {
                result(false, "데이터 전송에 실패ㅠㅠ : $it")
            }
    }

    fun addMoim(memberInfos: List<MemberInfo>, result: (Boolean, String) -> Unit) {
        val database = Firebase.database.reference
        var isSucess = true
        memberInfos.forEach {
            val childUpdates = hashMapOf<String, Any>(
                "/user/${it.nickname}" to it
            )
            database.updateChildren(childUpdates).addOnSuccessListener {
                Log.e("ayhan", "완료")
                isSucess = if (isSucess) isSucess else !isSucess
            }.addOnFailureListener { e ->
                Log.e("ayhan", "실패 : $e")
                isSucess = false
            }
        }
        if (isSucess) {
            result(true, "데이터 전송에 성공!")
        } else {
            result(false, "데이터 전송에 실패ㅠㅠ")
        }


    }
}