package pl.edu.zut.trackfitnesstraining.ui.options.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsViewModel() : ViewModel() {
    private val auth = Firebase.auth
    private val user = auth.currentUser

    val userEmail = MutableLiveData<String>()
    val userName = MutableLiveData<String>()
    val userID = MutableLiveData<String>()

    fun setData() {
        if (auth.currentUser != null) {
            user?.let {
                val name = it.displayName
                val email = it.email
                val uid = it.uid

                userEmail.value = email.toString()
                userName.value = name.toString()
                userID.value = uid
            }

        } else {
            userEmail.value = ""
            userName.value = ""
            userID.value = ""
        }
    }
}