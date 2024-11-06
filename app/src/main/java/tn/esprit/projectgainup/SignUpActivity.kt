package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.projectgainup.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var termsCheckbox: CheckBox
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize UI components
        emailEditText = findViewById(R.id.signup_email_edit_text)
        passwordEditText = findViewById(R.id.signup_password_edit_text)
        confirmPasswordEditText = findViewById(R.id.password_verification_edit_text)
        signUpButton = findViewById(R.id.sign_up_button)
        termsCheckbox = findViewById(R.id.terms_checkbox)
        loginLink = findViewById(R.id.login_link)  // Initialize the login link TextView

        // Set up sign-up button click listener
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Validate input fields
            if (validateInputs(email, password, confirmPassword)) {
                if (termsCheckbox.isChecked) {
                    // Handle successful sign-up here
                    Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()

                    // Cocher automatiquement la case "I agree to Terms"
                    termsCheckbox.isChecked = true

                    // Vous pouvez procéder avec votre logique d'inscription (par exemple, enregistrer dans la base de données, naviguer vers un autre écran, etc.)
                } else {
                    // Show message to accept terms
                    Toast.makeText(this, "Please accept the terms and conditions.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set up the login link click listener
        loginLink.setOnClickListener {
            // Navigate to the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to validate inputs
    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        // Email validation
        if (email.isEmpty()) {
            emailEditText.error = "Email is required"
            emailEditText.setBackgroundColor(resources.getColor(R.color.red))
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.setBackgroundColor(resources.getColor(R.color.red))
            isValid = false
        } else {
            emailEditText.setBackgroundColor(resources.getColor(R.color.white))  // Restore color when valid
        }

        // Password validation
        if (password.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.setBackgroundColor(resources.getColor(R.color.red))
            isValid = false
        } else if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            passwordEditText.setBackgroundColor(resources.getColor(R.color.red))
            isValid = false
        } else {
            passwordEditText.setBackgroundColor(resources.getColor(R.color.white))  // Restore color when valid
        }

        // Confirm Password validation
        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            confirmPasswordEditText.setBackgroundColor(resources.getColor(R.color.red))
            isValid = false
        } else {
            confirmPasswordEditText.setBackgroundColor(resources.getColor(R.color.white))  // Restore color when valid
        }

        return isValid
    }
}
