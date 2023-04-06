package pl.edu.zut.trackfitnesstraining.data.model

data class Workout(
    var id: String = "",
    var date: String = date(),
    var kcal: Int = 0,
    var minutes: Int = 0,
    var exercises: List<Exercise> = listOf()
)