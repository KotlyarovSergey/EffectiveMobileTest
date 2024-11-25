package com.ksv.effectivemobiletest.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateConverter {
    fun convert(dateToConvert: String): String {
        return if (Build.VERSION.SDK_INT >= 26) {
            dateConverterApi26(dateToConvert)
        } else {
            dateConverterUnderApi26(dateToConvert)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateConverterApi26(dateToConvert: String): String {
        return try {
            // в Date
            val originFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val dateTime = LocalDateTime.parse(dateToConvert, originFormat)

            // в новый формат
            val newFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            val dateDimeText = dateTime.format(newFormat)

            dateDimeText
        } catch (exception: Exception) {
            Log.d(
                "ksvlog",
                "dateConverterApi26 error conversion.\n inputDate: $dateToConvert\n ${exception.message}"
            )
            dateToConvert
        }
    }

    private fun dateConverterUnderApi26(dateToConvert: String): String {
        return try {
            dateToConvert.substring(0..9).replace('-', '.')
        } catch (exception: Exception) {
            Log.d(
                "ksvlog",
                "dateConverterUnderApi26 error conversion.\n inputDate: $dateToConvert\n ${exception.message}"
            )
            dateToConvert
        }
    }
}