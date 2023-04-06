package pl.edu.zut.trackfitnesstraining.ui.options.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepository
import pl.edu.zut.trackfitnesstraining.util.UiResState
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    val repository: FitnessRepository
) : ViewModel() {

    private val _exercises = MutableLiveData<UiResState<List<Exercise?>>>()
    val exercise: LiveData<UiResState<List<Exercise?>>>
        get() = _exercises

    private val _addExercise = MutableLiveData<UiResState<Pair<Exercise, String>>>()
    val addExercise: LiveData<UiResState<Pair<Exercise, String>>>
        get() = _addExercise

    fun addExercise(exercise: Exercise) {
        _addExercise.value = UiResState.Loading
        repository.addExercise(exercise) {
            _addExercise.value = it
        }
        getExercise()
    }

    fun getExercise() {
        _exercises.value = UiResState.Loading
        repository.getExercises {
            _exercises.value = it
        }
    }
}