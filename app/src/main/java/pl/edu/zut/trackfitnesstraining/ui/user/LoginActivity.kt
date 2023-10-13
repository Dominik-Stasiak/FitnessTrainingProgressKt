package pl.edu.zut.trackfitnesstraining.ui.user

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import pl.edu.zut.trackfitnesstraining.MainActivity
import pl.edu.zut.trackfitnesstraining.data.model.UserLog
import pl.edu.zut.trackfitnesstraining.databinding.ActivityLoginBinding
import pl.edu.zut.trackfitnesstraining.util.FireStoreTables


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("280047339549-b4f7bdogouk7a46udj241j9jqtr0a255.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.textHelp.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogIn.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                logIn(email, password)
            } else {
                Toast.makeText(
                    this,
                    "Uzupełnij wszystkie pola",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.googleBtn.setOnClickListener {
            signIn()
        }
    }


    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Zalogowano",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "Wystąpił błąd lub niepoprawne dane logowania",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Log.d("LOG", "Authentication failed ${it.localizedMessage}")
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failer", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val nUser = UserLog(
                id = user.uid,
                email = user.email ?: ""
            )
            updateUser(nUser)
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(NAME, user.displayName)
            startActivity(intent)
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

    companion object {
        const val RC_SIGN_IN = 1001
        const val NAME = "NAME"
    }
}