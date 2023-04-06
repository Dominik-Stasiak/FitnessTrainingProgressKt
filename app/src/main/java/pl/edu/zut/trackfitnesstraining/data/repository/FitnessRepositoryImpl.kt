package pl.edu.zut.trackfitnesstraining.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.zut.trackfitnesstraining.data.model.*
import pl.edu.zut.trackfitnesstraining.util.FireStoreTables
import pl.edu.zut.trackfitnesstraining.util.UiResState

class FitnessRepositoryImpl(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth
) : FitnessRepository {
    override fun getExercises(result: (UiResState<List<Exercise>>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.EXERCISES)
            .collection(FireStoreTables.EXERCISES)
            .get()
            .addOnSuccessListener {
                val exercises = arrayListOf<Exercise>()
                for (document in it) {
                    val exercise = document.toObject(Exercise::class.java)
                    exercises.add(exercise)
                }
                result.invoke(
                    UiResState.Succes(exercises)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiResState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }


    override fun addExercise(
        exercise: Exercise,
        result: (UiResState<Pair<Exercise, String>>) -> Unit
    ) {
        val document = database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.EXERCISES)
            .collection(FireStoreTables.EXERCISES)
            .document()
        exercise.id = document.id
        document
            .set(exercise)
            .addOnSuccessListener {
                result.invoke(
                    UiResState.Succes(Pair(exercise, "Exercise added"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiResState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getPlan(result: (UiResState<List<Plan>>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.PLANS)
            .collection(FireStoreTables.PLANS)
            .get()
            .addOnSuccessListener {
                val plans = arrayListOf<Plan>()
                for (document in it) {
                    val plan = document.toObject(Plan::class.java)
                    plans.add(plan)
                }
                result.invoke(
                    UiResState.Succes(plans)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiResState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }


    override fun addPlan(plan: Plan, result: (UiResState<Pair<Plan, String>>) -> Unit) {
        val document = database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.PLANS)
            .collection(FireStoreTables.PLANS)
            .document()
        plan.id = document.id
        document
            .set(plan)
            .addOnSuccessListener {
                result.invoke(
                    UiResState.Succes(Pair(plan, "Plan added"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiResState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun addWorkout(workout: Workout, result: (UiResState<Pair<Workout, String>>) -> Unit) {
        val wDate = simpleDate.parse(workout.date)
        val nDate = simpleDateSave.format(wDate!!)
        val document = database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.WORKOUT)
            .collection(FireStoreTables.WORKOUT)
            .document(nDate + randomStr())
        workout.id = document.id
        document
            .set(workout)
            .addOnSuccessListener {
                result.invoke(
                    UiResState.Succes(Pair(workout, "Workout added"))
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiResState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun getWorkouts(result: (UiResState<List<Workout>>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.WORKOUT)
            .collection(FireStoreTables.WORKOUT)
            .get()
            .addOnSuccessListener {
                val workouts = arrayListOf<Workout>()
                for (document in it) {
                    val workout = document.toObject(Workout::class.java)
                    workouts.add(workout)
                }
                result.invoke(
                    UiResState.Succes(workouts)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiResState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}