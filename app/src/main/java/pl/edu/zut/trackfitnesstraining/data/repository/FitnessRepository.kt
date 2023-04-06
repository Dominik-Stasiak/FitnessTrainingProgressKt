package pl.edu.zut.trackfitnesstraining.data.repository

import pl.edu.zut.trackfitnesstraining.data.model.Exercise
import pl.edu.zut.trackfitnesstraining.data.model.Plan
import pl.edu.zut.trackfitnesstraining.data.model.Workout
import pl.edu.zut.trackfitnesstraining.util.UiResState

interface FitnessRepository {
    fun getExercises(result: (UiResState<List<Exercise>>) -> Unit)
    fun addExercise(exercise: Exercise, result: (UiResState<Pair<Exercise, String>>) -> Unit)
    fun getPlan(result: (UiResState<List<Plan>>) -> Unit)
    fun addPlan(plan: Plan, result: (UiResState<Pair<Plan, String>>) -> Unit)
    fun addWorkout(workout: Workout, result: (UiResState<Pair<Workout, String>>) -> Unit)
    fun getWorkouts(result: (UiResState<List<Workout>>) -> Unit)
}