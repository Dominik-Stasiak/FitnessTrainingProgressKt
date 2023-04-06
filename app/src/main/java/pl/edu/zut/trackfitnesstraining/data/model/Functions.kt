package pl.edu.zut.trackfitnesstraining.data.model

import java.text.SimpleDateFormat
import java.util.*

val simpleDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
val simpleDateSave = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
val simpleTime = SimpleDateFormat("HH:mm", Locale.getDefault())

fun date(): String {
    return simpleDate.format(Date())
}
fun dateSave(): String {
    return simpleDateSave.format(Date())
}
fun actualTime(plus: Int): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar.add(Calendar.HOUR, plus)


    return simpleTime.format(calendar.time)
}
fun randomStr(): String {
    return (Random().nextInt(1000)*Random().nextInt(1000)*Random().nextInt(1000)).toString()
}
fun checkDate(date: String): Boolean {
    val currentDate = Calendar.getInstance()
    val givenDate = Calendar.getInstance()
    givenDate.time = simpleDate.parse(date) as Date
    return currentDate.get(Calendar.MONTH) == givenDate.get(Calendar.MONTH)
}
