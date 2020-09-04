package com.zinc0214.ssgapp

import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database

class FirebaseViewModel : ViewModel() {

    fun addNewMember(newMember: NewMember, result : (Boolean, String) -> Unit) {

        val database = com.google.firebase.ktx.Firebase.database.reference
        database.child("user").child(newMember.nickname).setValue(newMember)
            .addOnSuccessListener {
                result(true, "데이터 전송에 성공!")
            }
            .addOnFailureListener {
                result(false, "데이터 전송에 실패ㅠㅠ : $it")
            }

    }
}