package com.zinc0214.ssgapp

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*


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

fun Int.setDate(): String {
    return if (this > 10) "$this" else "0$this"
}

@SuppressLint("SimpleDateFormat")
fun String.toDate() : Date = SimpleDateFormat("yyyy/MM/dd").parse(this);