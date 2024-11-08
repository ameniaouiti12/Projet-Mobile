package tn.esprit.projectgainup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.projectgainup.dtos.AuthResponse
import tn.esprit.projectgainup.dtos.LoginRequest
import tn.esprit.projectgainup.remote.RetrofitClient
import tn.esprit.projectgainup.utils.SharedPreferencesUtils

class LoginActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast", "CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText: EditText = findViewById(R.id.signin_email_edit_text)
        val passwordEditText: EditText = findViewById(R.id.sigin_password_edit_text)
        val emailContainer: LinearLayout = findViewById(R.id.email_container)
        val passwordContainer: LinearLayout = findViewById(R.id.password_container)
        val signInButton: Button = findViewById(R.id.connection_button)

        signInButton.setOnClickListener {
            var isValid = true

            // Réinitialiser les bordures des champs
            emailContainer.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F4F6F7"))
            passwordContainer.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F4F6F7"))

            // Vérification du champ email
            val email = emailEditText.text.toString()
            if (email.isEmpty()) {
                emailContainer.backgroundTintList = ColorStateList.valueOf(Color.RED)
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            // Vérification du champ mot de passe
            val password = passwordEditText.text.toString()
            if (password.isEmpty()) {
                passwordContainer.backgroundTintList = ColorStateList.valueOf(Color.RED)
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            // Si les champs sont valides, lancer la connexion
            if (isValid) {
                login(email, password)
            }
        }

        val forgotPasswordText: TextView = findViewById(R.id.change_password_link)
        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }

        val signUpLink: TextView = findViewById(R.id.Register_link)
        signUpLink.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    // Fonction de connexion
    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    authResponse?.token?.let { token ->
                        SharedPreferencesUtils.saveToken(this@LoginActivity, token)
                    }
                    Toast.makeText(this@LoginActivity, "Connexion réussie", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Échec de la connexion", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Erreur de réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
