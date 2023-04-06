package pl.edu.zut.trackfitnesstraining.ui.user

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.edu.zut.trackfitnesstraining.data.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {
    fun logout(result: () -> Unit) {
        userRepository.userLogout(result)
    }
}