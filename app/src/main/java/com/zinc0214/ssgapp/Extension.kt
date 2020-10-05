package com.zinc0214.ssgapp

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun EditText.onTextChanged(callBack: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            // Do Nothing
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Do Nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            callBack(p0.toString())
        }

    })
}

fun EditText.onAfterTextChanged(callBack: () -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            callBack()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Do Nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Do Nothing
        }

    })
}

fun Int.setDate(): String {
    return if (this > 10) "$this" else "0$this"
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date = SimpleDateFormat("yyyy/MM/dd").parse(this);


fun changeDTO(infos: ArrayList<MemberInfoDTO>): ArrayList<MemberInfo> {
    val membersInfo = ArrayList<MemberInfo>()

    infos.forEach { info ->
        membersInfo.add(
            MemberInfo(
                info.nickname,
                info.age,
                info.addr,
                info.signDate.parseDate(),
                info.lastDate.parseDate(),
                info.attendeCount,
                info.createCount,
                info.gender,
                info.realName
            )
        )
    }
    return membersInfo
}

fun String.parseDate(): String {
    return if (this.isNotBlank()) {
        Log.e("ayhan", "string : $this")
        this
    } else {
        ""
    }
}