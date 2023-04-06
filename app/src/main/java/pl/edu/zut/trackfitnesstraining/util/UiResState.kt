package pl.edu.zut.trackfitnesstraining.util

sealed class UiResState<out T> {
    object Loading : UiResState<Nothing>()
    data class Succes<out T>(val data: T) : UiResState<T>()
    data class Failure(val error: String?) : UiResState<Nothing>()
}