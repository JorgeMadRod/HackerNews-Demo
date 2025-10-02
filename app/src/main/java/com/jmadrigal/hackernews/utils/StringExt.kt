package com.jmadrigal.hackernews.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun String.formatDate(): String {
    if (this.isEmpty()) return ""
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val fechaDada = format.parse(this)

    val calendarActual = Calendar.getInstance()
    val fechaActual = calendarActual.time

    val diferenciaMilis = fechaActual.time - fechaDada.time
    val diferenciaMinutos = diferenciaMilis / (60 * 1000)
    val diferenciaHoras = diferenciaMilis / (60 * 60 * 1000)

    return when {
        diferenciaMinutos < 60 && diferenciaMinutos >= 0 -> {
         "${diferenciaMinutos}m"
        }
        diferenciaHoras < 12 && diferenciaHoras >= 0 -> {
            if(diferenciaHoras == 0L) "1h"
            else "${diferenciaHoras}h"
        }
        else -> {
            val calendarDada = Calendar.getInstance().apply { time = fechaDada }
            calendarDada.set(Calendar.HOUR_OF_DAY, 0)
            calendarDada.set(Calendar.MINUTE, 0)
            calendarDada.set(Calendar.SECOND, 0)
            calendarDada.set(Calendar.MILLISECOND, 0)

            val calendarAyer = Calendar.getInstance().apply { add(Calendar.DATE, -1) }
            calendarAyer.set(Calendar.HOUR_OF_DAY, 0)
            calendarAyer.set(Calendar.MINUTE, 0)
            calendarAyer.set(Calendar.SECOND, 0)
            calendarAyer.set(Calendar.MILLISECOND, 0)

            if (calendarDada.timeInMillis == calendarAyer.timeInMillis) {
                "ayer"
            } else {
                val formatoFecha = SimpleDateFormat("d'/'MM'/'yyyy", Locale("es", "MX"))
                formatoFecha.format(fechaDada)
            }
        }
    }
}