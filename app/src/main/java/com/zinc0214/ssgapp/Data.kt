package com.zinc0214.ssgapp

import java.io.Serializable


data class NewMember(
    val nickname: String,
    val age: Int,
    val addr: String,
    val signdate: String,
    val gender: String,
    val realName: String?
)


data class EditMember(
    val nickname: String,
    val age: Int,
    val addr: String,
    val realName: String?
)


class MemberInfoDTO : Serializable {
    val id: Long = 0.toLong()
    val nickname: String = ""
    val age: Long = 0.toLong()
    val addr: String = ""
    val signDate: String = ""
    val lastDate: String = ""
    val attendeCount: Long = 0.toLong()
    val createCount: Long = 0.toLong()
    val gender: String = ""
    val realName: String? = ""
}

data class MemberInfo(
    var id: Long = 0.toLong(),
    var nickname: String = "",
    var age: Long = 0.toLong(),
    var addr: String = "",
    var signDate: String = "",
    var lastDate: String = "",
    var attendeCount: Long = 0.toLong(),
    var createCount: Long = 0.toLong(),
    var gender: String = "",
    var realName: String? = "",
    var isChecked: Boolean = false
)