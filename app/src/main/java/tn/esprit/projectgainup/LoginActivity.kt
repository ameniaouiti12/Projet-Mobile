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

class LoginActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "CutPasteId")
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
            if (emailEditText.text.toString().isEmpty()) {
                emailContainer.backgroundTintList = ColorStateList.valueOf(Color.RED)
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            // Vérification du champ mot de passe
            if (passwordEditText.text.toString().isEmpty()) {
                passwordContainer.backgroundTintList = ColorStateList.valueOf(Color.RED)
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            // Vérification générale
            if (isValid) {
                // Simuler la connexion réussie
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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
}
