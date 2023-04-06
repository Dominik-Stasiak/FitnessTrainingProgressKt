package pl.edu.zut.trackfitnesstraining.data.model

data class Exercise (
    var id: String = "",
    var name: String = "",
    var category: String = "",
) {
    override fun toString(): String {
        return name
    }
}