package com.zinc0214.ssgapp

import java.io.Serializable
import java.util.*


data class NewMember(
    val nickname: String,
    val age: Int,
    val addr: String,
    val signDate: String,
    val gender: String,
    val realName: String?,
    val phone: String?,
    val birthDay: String?
)


data class MemberState(
    val allNum: Int,
    val manNum: Int,
    val womanNum: Int,
    val attendScore: String,
    val createScore: String,
    val redMember: String
)

class MemberInfoDTO : Serializable {
    val nickname: String = ""
    val age: Long = 0.toLong()
    val addr: String = ""
    val signDate: String = ""
    val lastDate: String = ""
    var attendeCount: Long = 0.toLong()
    var createCount: Long = 0.toLong()
    val gender: String = ""
    val realName: String = ""
    val phone: String = ""
    val birthDay: String = ""
}

data class MemberInfo(
    var nickname: String = "",
    var age: Long = 0.toLong(),
    var addr: String = "",
    var signDate: String = "",
    var lastDate: String = "",
    var attendeCount: Long = 0.toLong(),
    var createCount: Long = 0.toLong(),
    var gender: String = "",
    var realName: String? = "",
    var phone: String? = "",
    val birthDay: String? = "",
    var isChecked: Boolean = false
) {
    fun getDday(): Int {
        val signDday = if (signDate.isBlank()) -1 else signDate.parseDate().toDate().dDay()
        val lastDday = if (lastDate.isBlank()) -1 else lastDate.parseDate().toDate().dDay()

        return if (signDday == -1 && lastDday > 0) lastDday
        else if (lastDday == -1 && signDday > 0) signDday
        else if (signDday.compareTo(lastDday) == 1) lastDday else signDday
    }

    fun getDdayString(): String {
        getDday().apply {
            return if (this > 0) "+${this}"
            else "$this"
        }
    }

    fun dDayState(dday: Int): DDayType {
        return when {
            dday > 30 -> DDayType.RED
            dday > 21 -> DDayType.YELLOW
            else -> DDayType.GREEN
        }
    }

    fun getAgeString(): String {
        return age.toString()
    }

    fun getBirthDayMonth(): String {
        return if (birthDay.isNullOrBlank()) ""
        else birthDay.substring(0, 2)
    }

}


private fun Date.dDay(): Int {
    val currentTime = Date().time
    val minusForDay = 1000 * 60 * 60 * 24
    return ((currentTime - this.time) / minusForDay).toInt()
}

enum class DDayType {
    RED, YELLOW, GREEN
}

class MoimInfo : Serializable {
    var id: String = ""
    var date: String = ""
    var creator: String = ""
    var attendee: String = ""
    var addr: String = ""
    var kind: String = ""
}