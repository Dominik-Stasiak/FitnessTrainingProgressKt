package pl.edu.zut.trackfitnesstraining.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.zut.trackfitnesstraining.data.model.*
import pl.edu.zut.trackfitnesstraining.util.FireStoreTables
import pl.edu.zut.trackfitnesstraining.util.UiResState

class UserRepositoryImpl(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth
) : UserRepository {
    override fun getBasicInfo(result: (UiResState<User?>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.BASICINFO)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                result.invoke(
                    UiResState.Succes(user)
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

    override fun addBasicInfo(user: User, result: (UiResState<String>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.BASICINFO)
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiResState.Succes("Success")
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

    override fun getUserWeight(result: (UiResState<List<Weight?>>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.WEIGHT)
            .collection(FireStoreTables.WEIGHT)
            .get()
            .addOnSuccessListener {
                val weights = arrayListOf<Weight>()
                for (document in it) {
                    val weight = document.toObject(Weight::class.java)
                    weights.add(weight)
                }
                result.invoke(
                    UiResState.Succes(weights)
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

    override fun addUserWeight(weight: Weight, result: (UiResState<String>) -> Unit) {
        val wDate = simpleDate.parse(weight.date)
        val nDate = simpleDateSave.format(wDate!!)
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.WEIGHT)
            .collection(FireStoreTables.WEIGHT)
            .document(nDate)
            .set(weight)
            .addOnSuccessListener {
                result.invoke(
                    UiResState.Succes("Weight added")
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

    override fun getUserMeasurment(result: (UiResState<List<Measurment?>>) -> Unit) {
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.MEASUREMENT)
            .collection(FireStoreTables.MEASUREMENT)
            .get()
            .addOnSuccessListener {
                val measurments = arrayListOf<Measurment>()
                for (document in it) {
                    val measurment = document.toObject(Measurment::class.java)
                    measurments.add(measurment)
                }
                result.invoke(
                    UiResState.Succes(measurments)
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

    override fun addUserMeasurment(measurment: Measurment, result: (UiResState<String>) -> Unit) {
        val mDate = simpleDate.parse(measurment.date)
        val nDate = simpleDateSave.format(mDate!!)
        database.collection(auth.currentUser?.uid.toString())
            .document(FireStoreTables.MEASUREMENT)
            .collection(FireStoreTables.MEASUREMENT)
            .document(nDate)
            .set(measurment)
            .addOnSuccessListener {
                result.invoke(
                    UiResState.Succes("Measurment added")
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


    override fun userLogout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }
}