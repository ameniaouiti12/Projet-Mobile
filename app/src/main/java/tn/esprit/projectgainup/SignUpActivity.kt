package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.projectgainup.dtos.AuthResponse
import tn.esprit.projectgainup.dtos.SignupRequest
import tn.esprit.projectgainup.remote.RetrofitClient
class SignUpActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var termsCheckbox: CheckBox
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialisation des composants UI
        nameEditText = findViewById(R.id.signup_name_edit_text)  // Initialisez le champ de texte pour le nom
        emailEditText = findViewById(R.id.signup_email_edit_text)
        passwordEditText = findViewById(R.id.signup_password_edit_text)
        confirmPasswordEditText = findViewById(R.id.password_verification_edit_text)
        signUpButton = findViewById(R.id.sign_up_button)
        termsCheckbox = findViewById(R.id.terms_checkbox)
        loginLink = findViewById(R.id.login_link)

        // Gestion du clic sur le bouton d'inscription
        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()  // Récupérez le nom
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Validation des champs
            if (validateInputs(name, email, password, confirmPassword)) {
                if (termsCheckbox.isChecked) {
                    // Appeler la méthode d'inscription
                    performSignUp(name, email, password, confirmPassword)
                } else {
                    Toast.makeText(this, "Please accept the terms and conditions.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Gestion du clic sur le lien vers la page de connexion
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Fonction pour effectuer l'inscription via Retrofit
    private fun performSignUp(name: String, email: String, password: String, confirmPassword: String) {
        val signUpRequest = SignupRequest(name, email, password, confirmPassword)  // Passez le nom à l'objet de requête

        RetrofitClient.apiService.signUp(signUpRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@SignUpActivity, "Sign-up successful!", Toast.LENGTH_SHORT).show()

                    // Rediriger vers la page de connexion
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()  // Fermer l'activité SignUp
                } else {
                    Toast.makeText(this@SignUpActivity, response.body()?.message ?: "Sign-up failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Fonction pour valider les champs du formulaire
    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        // Validation du nom
        if (name.isEmpty()) {
            nameEditText.error = "Name is required"
            isValid = false
        }

        // Validation de l'email
        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            isValid = false
        }

        // Validation du mot de passe
        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            isValid = false
        }

        // Validation de la confirmation du mot de passe
        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            isValid = false
        }

        return isValid
    }
}
