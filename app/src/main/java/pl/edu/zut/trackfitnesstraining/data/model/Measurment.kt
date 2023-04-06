package pl.edu.zut.trackfitnesstraining.data.model


data class Measurment(
    var neck: Int = 0,
    var chest: Int = 0,
    var hip: Int = 0,
    var weist: Int = 0,
    var biceps: Int = 0,
    var tigh: Int = 0,
    var calv: Int = 0,
    var stomach: Int = 0,
    var date: String = date()
)


