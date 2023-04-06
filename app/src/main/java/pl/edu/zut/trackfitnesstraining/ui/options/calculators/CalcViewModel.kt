package pl.edu.zut.trackfitnesstraining.ui.options.calculators

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class CalcViewModel() : ViewModel() {

    val ormWeight = MutableLiveData(0.0)
    val ormRep = MutableLiveData(0)
    val ormRes = MutableLiveData(0.0)

    fun oneRepMax() {
        if (ormRep.value!! > 0 && ormWeight.value!! > 0)
            ormRes.value = ormWeight.value?.times((1 + 0.025 * ormRep.value!!))
        else ormRes.value = 0.0
    }

    val bmrWeight = MutableLiveData(0.0)
    val bmrHeight = MutableLiveData(0)
    val bmrSex = MutableLiveData(true)
    val bmrRes = MutableLiveData(0)
    val bmrAge = MutableLiveData(0)
    val bmrActivity = MutableLiveData(1.0)

    fun BMR() {
        if (bmrWeight.value!! > 0 && bmrHeight.value!! > 0 && bmrAge.value!! > 0) {
            if (bmrSex.value == true) {
                bmrRes.value =
                    ((10 * bmrWeight.value!! + 6.25 * bmrHeight.value!! - 5 * bmrAge.value!! + 5)
                            * bmrActivity.value!!).roundToInt()
            } else {
                bmrRes.value =
                    ((10 * bmrWeight.value!! + 6.25 * bmrHeight.value!! - 5 * bmrAge.value!! - 161)
                            * bmrActivity.value!!).roundToInt()
            }
        } else bmrRes.value = 0
    }

}