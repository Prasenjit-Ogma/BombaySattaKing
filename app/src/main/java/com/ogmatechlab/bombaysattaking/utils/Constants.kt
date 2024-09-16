package com.ogmatechlab.bombaysattaking.utils

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Constants {
    const val BASE_URL = "https://www.ogmatechlab.com"
    const val TIME_API_URL = "https://worldtimeapi.org"
    const val DATE_INPUT_FORMAT = "yyyy/MM/dd HH:mm:ss"
    const val DATE_OUTPUT_FORMAT = "MM/dd/yyyy HH:mm:ss"
    const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss"
    const val TIME_FORMAT = "HH:mm:ss"
    const val TIME_IN_MIN = "mm"
    const val TIME_IN_SEC = "ss"

    fun getDesiredDateTimeFormat(
        dateTimeString: String,
        inputFormatDateTime: String,
        outputFormatDateTime: String
    ): String {
        val inputFormat = DateTimeFormatter.ofPattern(inputFormatDateTime)
        val dateTime = LocalDateTime.parse(dateTimeString, inputFormat)
        val outputFormat = DateTimeFormatter.ofPattern(outputFormatDateTime)
        return dateTime.format(outputFormat)
    }

    fun getMillisFromDate(
        dateTimeString: String,
        inputFormatDateTime: String
    ): Long? {
        val formatter = SimpleDateFormat(inputFormatDateTime, Locale.getDefault())
        val parsedDateTime = formatter.parse(dateTimeString)?.time
        return parsedDateTime
    }

    fun getDateTimeFromMillis(timeMillis: Long?, dateTimeFormat: String): String? {
        val date = timeMillis?.let { Date(it) }
        val dateTimeFormatter = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
        return date?.let { dateTimeFormatter.format(it) }
    }

}