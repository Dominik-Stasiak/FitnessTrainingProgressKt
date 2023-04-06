package pl.edu.zut.trackfitnesstraining.ui.options.timer

import android.graphics.Color
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    val flag = MutableLiveData(false)
    val timer = MutableLiveData(0L)
    val color = MutableLiveData(Color.BLACK)
    private lateinit var timeObject: CountDownTimer

    fun eraseTime() {
        if (timer.value!! > 0) {
            timer.value = 0
        }
    }

    fun increaseTime() {
        timer.value = timer.value!! + 1000
    }

    fun changeColor() {
        if (flag.value == true) {
            color.value = (Color.BLACK)
        } else {
            color.value = (Color.GREEN)
        }
    }

    fun startTimer() {
        flag.value = true
        timeObject = object : CountDownTimer(3600000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                increaseTime()
            }

            override fun onFinish() {
                timer.value = 0L
            }
        }.start()
    }

    fun pauseTimer() {
        flag.value = false
        timeObject.cancel()
    }
}