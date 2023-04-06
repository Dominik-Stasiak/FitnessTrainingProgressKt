package pl.edu.zut.trackfitnesstraining.ui.options.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjoe64.graphview.series.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.edu.zut.trackfitnesstraining.data.model.Measurment
import pl.edu.zut.trackfitnesstraining.data.model.Weight
import pl.edu.zut.trackfitnesstraining.data.model.Workout
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepository
import pl.edu.zut.trackfitnesstraining.data.repository.UserRepository
import pl.edu.zut.trackfitnesstraining.util.UiResState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor
    (
    val repository: FitnessRepository,
    val userRepository: UserRepository
) : ViewModel() {

    private val _workouts = MutableLiveData<UiResState<List<Workout?>>>()
    val workout: LiveData<UiResState<List<Workout?>>>
        get() = _workouts

    private val _userWeight = MutableLiveData<UiResState<List<Weight?>>>()
    val userWeight: LiveData<UiResState<List<Weight?>>>
        get() = _userWeight

    private val _userMeasurment = MutableLiveData<UiResState<List<Measurment?>>>()
    val userMeasurment: LiveData<UiResState<List<Measurment?>>>
        get() = _userMeasurment

    fun getWorkout() {
        _workouts.value = UiResState.Loading
        repository.getWorkouts {
            _workouts.value = it
        }
    }

    fun getUserWeight() {
        _userWeight.value = UiResState.Loading
        userRepository.getUserWeight {
            _userWeight.value = it
        }
    }

    fun getUserMeasurment() {
        _userMeasurment.value = UiResState.Loading
        userRepository.getUserMeasurment {
            _userMeasurment.value = it
        }
    }

    fun graphDataWeight(selectedMonth: Int): Array<DataPoint> {
        val res = mutableListOf<DataPoint>()
        val calendar = Calendar.getInstance()
        val selectMonth: Int
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        if (selectedMonth == 0) selectMonth = 12 else selectMonth = selectedMonth
        val actualMonth = calendar.get(Calendar.MONTH) + 1
        val actualYear = calendar.get(Calendar.YEAR)
        val selectedYear = if (selectMonth > actualMonth) actualYear - 1 else actualYear

        val sDate = "00.$selectMonth.$selectedYear"
        val eDate = "32.$selectMonth.$selectedYear"

        if (_userWeight.value is UiResState.Succes) {
            val weights = (_userWeight.value as UiResState.Succes).data
            for (weight in weights) {
                if (weight != null) {
                    val weightDate = dateFormat.parse(weight.date)
                    if (weightDate.after(dateFormat.parse(sDate)) && weightDate.before(
                            dateFormat.parse(eDate)
                        )
                    ) {
                        calendar.time = dateFormat.parse(weight.date)!!
                        res.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                weight.weight
                            )
                        )
                    }
                }
            }
        }
        return res.toTypedArray()
    }

    fun graphDataWorkout(selectedMonth: Int): Array<DataPoint> {
        val res = mutableListOf<DataPoint>()
        val calendar = Calendar.getInstance()
        val selectMonth: Int
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        if (selectedMonth == 0) selectMonth = 12 else selectMonth = selectedMonth
        val actualMonth = calendar.get(Calendar.MONTH) + 1
        val actualYear = calendar.get(Calendar.YEAR)
        val selectedYear = if (selectMonth > actualMonth) actualYear - 1 else actualYear

        val sDate = "00.$selectMonth.$selectedYear"
        val eDate = "32.$selectMonth.$selectedYear"

        if (_workouts.value is UiResState.Succes) {
            val workouts = (_workouts.value as UiResState.Succes).data
            var dateOfWorkout = ""
            var sumOfWorkout = 0.0
            for (workout in workouts) {
                if (workout != null) {
                    if (dateOfWorkout == workout.date) {
                        dateOfWorkout = workout.date
                        sumOfWorkout += 1.0
                    } else {
                        dateOfWorkout = workout.date
                        sumOfWorkout = 1.0
                    }
                    val kcalDate = dateFormat.parse(workout.date)
                    if (kcalDate.after(dateFormat.parse(sDate)) && kcalDate.before(
                            dateFormat.parse(eDate)
                        )
                    ) {
                        calendar.time = dateFormat.parse(workout.date)!!
                        res.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                sumOfWorkout
                            )
                        )
                    }
                }
            }
        }
        return res.toTypedArray()
    }


    fun graphDataKcal(selectedMonth: Int): Array<DataPoint> {
        val res = mutableListOf<DataPoint>()
        val calendar = Calendar.getInstance()
        val selectMonth: Int
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        if (selectedMonth == 0) selectMonth = 12 else selectMonth = selectedMonth
        val actualMonth = calendar.get(Calendar.MONTH) + 1
        val actualYear = calendar.get(Calendar.YEAR)
        val selectedYear = if (selectMonth > actualMonth) actualYear - 1 else actualYear

        val sDate = "00.$selectMonth.$selectedYear"
        val eDate = "32.$selectMonth.$selectedYear"

        if (_workouts.value is UiResState.Succes) {
            val workouts = (_workouts.value as UiResState.Succes).data
            var dateOfKcal = ""
            var sumOfKcal = 0.0
            for (workout in workouts) {
                if (workout != null) {
                    if (dateOfKcal == workout.date) {
                        dateOfKcal = workout.date
                        sumOfKcal += workout.kcal
                    } else {
                        dateOfKcal = workout.date
                        sumOfKcal = workout.kcal.toDouble()
                    }
                    val kcalDate = dateFormat.parse(workout.date)
                    if (kcalDate.after(dateFormat.parse(sDate)) && kcalDate.before(
                            dateFormat.parse(eDate)
                        )
                    ) {
                        calendar.time = dateFormat.parse(workout.date)!!
                        res.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                sumOfKcal
                            )
                        )
                    }
                }
            }
        }
        return res.toTypedArray()
    }

    fun graphDataMeasurement(selectedMonth: Int): List<Array<DataPoint>> {
        val res = mutableListOf<Array<DataPoint>>()
        val neck = mutableListOf<DataPoint>()
        val chest = mutableListOf<DataPoint>()
        val biceps = mutableListOf<DataPoint>()
        val weist = mutableListOf<DataPoint>()
        val stomach = mutableListOf<DataPoint>()
        val hip = mutableListOf<DataPoint>()
        val tigh = mutableListOf<DataPoint>()
        val calv = mutableListOf<DataPoint>()
        val calendar = Calendar.getInstance()
        val selectMonth: Int
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        if (selectedMonth == 0) selectMonth = 12 else selectMonth = selectedMonth
        val actualMonth = calendar.get(Calendar.MONTH) + 1
        val actualYear = calendar.get(Calendar.YEAR)
        val selectedYear = if (selectMonth > actualMonth) actualYear - 1 else actualYear

        val sDate = "00.$selectMonth.$selectedYear"
        val eDate = "32.$selectMonth.$selectedYear"

        if (_userMeasurment.value is UiResState.Succes) {
            val measurments = (_userMeasurment.value as UiResState.Succes).data
            for (measurment in measurments) {
                if (measurment != null) {
                    val measurmentData = dateFormat.parse(measurment.date)
                    if (measurmentData.after(dateFormat.parse(sDate)) && measurmentData.before(
                            dateFormat.parse(eDate)
                        )
                    ) {
                        calendar.time = dateFormat.parse(measurment.date)!!
                        neck.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.neck.toDouble()
                            )
                        )
                        chest.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.chest.toDouble()
                            )
                        )
                        biceps.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.biceps.toDouble()
                            )
                        )
                        weist.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.weist.toDouble()
                            )
                        )
                        stomach.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.stomach.toDouble()
                            )
                        )
                        hip.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.hip.toDouble()
                            )
                        )
                        tigh.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.tigh.toDouble()
                            )
                        )
                        calv.add(
                            DataPoint(
                                calendar.get(Calendar.DAY_OF_MONTH).toDouble(),
                                measurment.calv.toDouble()
                            )
                        )
                    }
                }
            }
        }
        res.add(neck.toTypedArray())
        res.add(chest.toTypedArray())
        res.add(biceps.toTypedArray())
        res.add(weist.toTypedArray())
        res.add(stomach.toTypedArray())
        res.add(hip.toTypedArray())
        res.add(tigh.toTypedArray())
        res.add(calv.toTypedArray())
        return res
    }
}