package pl.edu.zut.trackfitnesstraining.data.model

data class Plan(
    var id: String = "",
    var name: String = "",
    var exercises: List<Exercise> = listOf()
)
