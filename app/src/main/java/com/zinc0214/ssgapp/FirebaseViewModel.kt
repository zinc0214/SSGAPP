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

    interface SendResult {
        fun success(string: String)
        fun fail(string: String)
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _membersInfo = MutableLiveData<ArrayList<MemberInfoDTO>>()
    val membesInfoDTO: LiveData<ArrayList<MemberInfoDTO>> get() = _membersInfo

    private val _moimInfo = MutableLiveData<ArrayList<MoimInfo>>()
    val moimInfoDTO: LiveData<ArrayList<MoimInfo>> get() = _moimInfo

    fun loadMembersInfo(result: SendResult) {
        val database = Firebase.database.reference
        val contentDB = database.child("user")

        _loading.value = true
        contentDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("ayhan", "error : $error")
                _loading.value = false
                result.fail("데이터 불러오기 실패 ;ㅁ;")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<MemberInfoDTO>()
                snapshot.children.forEach {
                    it.getValue<MemberInfoDTO>()?.let { result ->
                        list.add(result)
                    }
                }
                result.success("데이터 불러오기 성공 > < ")
                _membersInfo.value = list
                _loading.value = false
            }
        })
    }

    fun addNewMember(newMember: NewMember, result: SendResult) {

        val database = Firebase.database.reference
        database.child("user").child(newMember.nickname).setValue(newMember)
            .addOnSuccessListener {
                result.success("데이터 전송에 성공!")
            }
            .addOnFailureListener {
                result.fail("데이터 전송에 실패ㅠㅠ : $it")
            }
    }

    fun addMoim(memberInfos: List<MemberInfo>, result: SendResult) {
        val database = Firebase.database.reference
        var isSucessCount = 0
        memberInfos.forEach {
            val childUpdates = hashMapOf<String, Any>(
                "/user/${it.nickname}" to it
            )
            database.updateChildren(childUpdates).addOnSuccessListener {
                isSucessCount++
                if (isSucessCount == memberInfos.size) result.success("데이터 전송에 성공!")
            }.addOnFailureListener { e ->
                result.fail("데이터 전송에 실패ㅠㅠ : $e")
            }
        }
    }

    fun editMember(editMember: MemberInfo, result: SendResult) {
        val database = Firebase.database.reference
        val childUpdates = hashMapOf<String, Any>(
            "/user/${editMember.nickname}" to editMember
        )
        database.updateChildren(childUpdates).addOnSuccessListener {
            result.success("데이터 전송에 성공!")
        }.addOnFailureListener {
            result.fail("데이터 전송에 실패ㅠㅠ : $it")
        }
    }


    fun deleteMember(nickName: String, result: SendResult) {
        val database = Firebase.database.reference.child("user").child(nickName)
        database.removeValue().addOnSuccessListener {
            result.success("$nickName 을(를) SSG멤버에서 삭제했습니다 ;ㅁ;")
        }.addOnFailureListener { e ->
            result.fail("$nickName 을(를) 의 삭제가 실패했습니다 ㅇㅁㅇ : $e")
        }
    }


    fun loadMoimInfo(result: SendResult) {
        val database = Firebase.database.reference
        val contentDB = database.child("moim")

        _loading.value = true
        contentDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.e("ayhan", "error : $error")
                _loading.value = false
                result.fail("데이터 불러오기 실패 ;ㅁ;")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<MoimInfo>()
                snapshot.children.forEach {
                    it.getValue<MoimInfo>()?.let { result ->
                        list.add(result)
                    }
                }
                result.success("데이터 불러오기 성공 > < ")
                _moimInfo.value = list
                _loading.value = false
            }
        })
    }

    fun addMoimInfo(moimInfo: MoimInfo) {
        val database = Firebase.database.reference
        val path = moimInfo.date.replace("/", "")
        database.child("moim").child(path).setValue(moimInfo)
    }
}