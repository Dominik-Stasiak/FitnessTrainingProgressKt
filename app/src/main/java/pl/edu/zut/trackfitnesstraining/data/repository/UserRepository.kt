package pl.edu.zut.trackfitnesstraining.data.repository

import pl.edu.zut.trackfitnesstraining.data.model.Measurment
import pl.edu.zut.trackfitnesstraining.data.model.User
import pl.edu.zut.trackfitnesstraining.data.model.Weight
import pl.edu.zut.trackfitnesstraining.util.UiResState


interface UserRepository {
    fun getBasicInfo(result: (UiResState<User?>) -> Unit)
    fun addBasicInfo(user: User, result: (UiResState<String>) -> Unit)
    fun getUserWeight(result: (UiResState<List<Weight?>>) -> Unit)
    fun addUserWeight(weight: Weight, result: (UiResState<String>) -> Unit)
    fun getUserMeasurment(result: (UiResState<List<Measurment?>>) -> Unit)
    fun addUserMeasurment(measurment: Measurment, result: (UiResState<String>) -> Unit)
    fun userLogout(result: () -> Unit)
}