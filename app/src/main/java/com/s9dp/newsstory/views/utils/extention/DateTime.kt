package com.s9dp.newsstory.views.utils.extention

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val SIMPLE_DATE_FORMAT = "yyyy-MM-dd"

@SuppressLint("SimpleDateFormat")
fun getCurrentDate():String{
    val sdf = SimpleDateFormat(SIMPLE_DATE_FORMAT)
    return sdf.format(Date())
}