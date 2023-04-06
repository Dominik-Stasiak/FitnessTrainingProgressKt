package pl.edu.zut.trackfitnesstraining.ui.options.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.edu.zut.trackfitnesstraining.data.model.Measurment
import pl.edu.zut.trackfitnesstraining.data.model.User
import pl.edu.zut.trackfitnesstraining.data.model.Weight
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepository
import pl.edu.zut.trackfitnesstraining.data.repository.UserRepository
import pl.edu.zut.trackfitnesstraining.util.UiResState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (
    val repository: FitnessRepository,
    val userRepository: UserRepository
) : ViewModel() {

    private val _userInfo = MutableLiveData<UiResState<User?>>()
    val userInfo: LiveData<UiResState<User?>>
        get() = _userInfo

    private val _addUserInfo = MutableLiveData<UiResState<String>>()
    val addUserInfo: LiveData<UiResState<String>>
        get() = _addUserInfo

    private val _userWeight = MutableLiveData<UiResState<List<Weight?>>>()
    val userWeight: LiveData<UiResState<List<Weight?>>>
        get() = _userWeight

    private val _addUserWeight = MutableLiveData<UiResState<String>>()
    val addUserWeight: LiveData<UiResState<String>>
        get() = _addUserWeight

    private val _userMeasurment = MutableLiveData<UiResState<List<Measurment?>>>()
    val userMeasurment: LiveData<UiResState<List<Measurment?>>>
        get() = _userMeasurment

    private val _addUserMeasurment = MutableLiveData<UiResState<String>>()
    val addUserMeasurment: LiveData<UiResState<String>>
        get() = _addUserMeasurment

    fun addUserInfo(user: User) {
        _addUserInfo.value = UiResState.Loading
        userRepository.addBasicInfo(user) {
            _addUserInfo.value = it
        }
        getUserInfo()
    }

    fun getUserInfo() {
        _userInfo.value = UiResState.Loading
        userRepository.getBasicInfo {
            _userInfo.value = it
        }
    }

    fun addUserWeight(weight: Weight) {
        _addUserWeight.value = UiResState.Loading
        userRepository.addUserWeight(weight) {
            _addUserWeight.value = it
        }
        getUserWeight()
    }

    fun getUserWeight() {
        _userWeight.value = UiResState.Loading
        userRepository.getUserWeight {
            _userWeight.value = it
        }
    }

    fun addUserMeasurment(measurment: Measurment) {
        _addUserMeasurment.value = UiResState.Loading
        userRepository.addUserMeasurment(measurment) {
            _addUserMeasurment.value = it
        }
        getUserMeasurment()
    }

    fun getUserMeasurment() {
        _userMeasurment.value = UiResState.Loading
        userRepository.getUserMeasurment {
            _userMeasurment.value = it
        }
    }

    fun getLatestWeight(): Weight? {
        var res: Weight? = null
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        if (_userWeight.value is UiResState.Succes) {
            val weights = (_userWeight.value as UiResState.Succes).data
            for (weight in weights) {
                if (weight != null) {
                    val weightDate = dateFormat.parse(weight.date)
                    if (res == null || weightDate.after(dateFormat.parse(res.date))) {
                        res = weight
                    }
                }
            }
        }
        return res
    }

    fun getLatestMeasurment(): Measurment? {
        var res: Measurment? = null
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        if (_userMeasurment.value is UiResState.Succes) {
            val measurments = (_userMeasurment.value as UiResState.Succes).data
            for (measurment in measurments) {
                if (measurment != null) {
                    val measurmentDate = dateFormat.parse(measurment.date)
                    if (res == null || measurmentDate.after(dateFormat.parse(res.date))) {
                        res = measurment
                    }
                }
            }
        }
        return res
    }
}
