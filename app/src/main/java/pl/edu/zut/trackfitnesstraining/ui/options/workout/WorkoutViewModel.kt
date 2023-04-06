package pl.edu.zut.trackfitnesstraining.ui.options.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.data.model.Workout
import pl.edu.zut.trackfitnesstraining.data.model.checkDate
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepository
import pl.edu.zut.trackfitnesstraining.data.repository.UserRepository
import pl.edu.zut.trackfitnesstraining.util.UiResState
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor
    (
    val repository: FitnessRepository,
    val userRepository: UserRepository
) : ViewModel() {

    private val _workouts = MutableLiveData<UiResState<List<Workout?>>>()
    val workout: LiveData<UiResState<List<Workout?>>>
        get() = _workouts

    private val _addWorkout = MutableLiveData<UiResState<Pair<Workout, String>>>()
    val addWorkout: LiveData<UiResState<Pair<Workout, String>>>
        get() = _addWorkout

    private val _exercises = MutableLiveData<MutableList<Exercise>>().apply {
        value = mutableListOf()
    }
    val exercises: LiveData<MutableList<Exercise>>
        get() = _exercises


    fun addWorkout(workout: Workout) {
        _addWorkout.value = UiResState.Loading
        repository.addWorkout(workout) {
            _addWorkout.value = it
        }
    }

    fun getWorkout() {
        _workouts.value = UiResState.Loading
        repository.getWorkouts {
            _workouts.value = it
        }
    }

    fun getKcal(): Int {
        var kcalSum = 0
        if (_workouts.value is UiResState.Succes) {
            val workouts = (_workouts.value as UiResState.Succes).data
            for (workout in workouts) {
                if (workout != null) {
                    if (checkDate(workout.date))
                        kcalSum += workout.kcal
                }
            }
        }
        return kcalSum
    }

    fun getNumberOfWorkouts(): Int {
        var Sum = 0
        if (_workouts.value is UiResState.Succes) {
            val workouts = (_workouts.value as UiResState.Succes).data
            for (workout in workouts) {
                if (workout != null) {
                    if (checkDate(workout.date))
                        Sum++
                }
            }
        }
        return Sum
    }

    fun addExercise(ex: Exercise) {
        exercises.value?.add(ex)
    }

    fun getExercises(): List<Exercise> {
        return exercises.value?.toList() ?: emptyList()
    }

    fun clearExercises() {
        exercises.value?.clear()
    }
}