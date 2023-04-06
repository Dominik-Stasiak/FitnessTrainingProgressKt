package pl.edu.zut.trackfitnesstraining.ui.options.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.data.model.Plan
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepository
import pl.edu.zut.trackfitnesstraining.util.UiResState
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor
    (val repository: FitnessRepository) : ViewModel() {

    private val _plans = MutableLiveData<UiResState<List<Plan?>>>()
    val plans: LiveData<UiResState<List<Plan?>>>
        get() = _plans

    private val _addPlan = MutableLiveData<UiResState<Pair<Plan, String>>>()
    val addPlan: LiveData<UiResState<Pair<Plan, String>>>
        get() = _addPlan

    fun getPlan() {
        _plans.value = UiResState.Loading
        repository.getPlan {
            _plans.value = it
        }
    }

    fun addPlan(plan: Plan) {
        _addPlan.value = UiResState.Loading
        repository.addPlan(plan) {
            _addPlan.value = it
        }
        getPlan()
    }

    private val _exercises = MutableLiveData<UiResState<List<Exercise?>>>()
    val exercise: LiveData<UiResState<List<Exercise?>>>
        get() = _exercises
}