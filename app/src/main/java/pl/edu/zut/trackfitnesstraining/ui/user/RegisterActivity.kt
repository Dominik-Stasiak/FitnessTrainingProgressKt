package pl.edu.zut.trackfitnesstraining.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import pl.edu.zut.trackfitnesstraining.MainActivity
import pl.edu.zut.trackfitnesstraining.data.model.UserLog
import pl.edu.zut.trackfitnesstraining.databinding.ActivityRegisterBinding
import pl.edu.zut.trackfitnesstraining.util.FireStoreTables

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: UserLog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textHelp.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth
        user = UserLog()

        binding.buttonRegister.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val cPassword = binding.editConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && cPassword.isNotEmpty()) {
                if (isPasswordsEqual(password, cPassword)) {
                    if (isValidPassword(password)) {
                        if (isValidEmail(email)) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        user.email = email
                                        user.id = task.result.user?.uid ?: ""
                                        updateUser(user)
                                        Toast.makeText(
                                            this,
                                            "Zarejestrowano",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent =
                                            Intent(applicationContext, MainActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Wystąpił problem, lub dany użytkownik już istnieje",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                .addOnFailureListener {
                                    Log.d(
                                        "RegisterActivity",
                                        it.message.toString()
                                    )
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "Niepoprawny adres e-mail",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Hasło musi posiadać conajmniej 8 znaków ( w tym dużą literę," +
                                    "cyfrę oraz znak specjalny )",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Podane hasła nie są identyczne",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Uzupełnij wszystkie pola",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun updateUser(user: UserLog) {
        val database = FirebaseFirestore.getInstance()
        val document = database.collection(user.id).document(FireStoreTables.LOGININFO)
        document
            .set(user)
            .addOnSuccessListener {
                Log.d(
                    "RegisterActivity", "User updated"
                )
            }
            .addOnFailureListener {
                Log.d(
                    "RegisterActivity",
                    it.message.toString()
                )
            }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(
            pattern =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
        )
        return emailRegex.matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        val passwordRegex = Regex(
            pattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,}\$"
        )
        return passwordRegex.matches(password)
    }

    fun isPasswordsEqual(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}