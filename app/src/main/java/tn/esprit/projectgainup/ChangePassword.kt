package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.projectgainup.databinding.ActivityChangePasswordBinding

class ChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changePasswordButton.setOnClickListener {
            val newPassword = binding.newPassword.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (validatePassword(newPassword, confirmPassword)) {
                // Logic for password change (e.g., save to database or backend)
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()

                // Naviguer vers la page de connexion
                navigateToLogin()
            }
        }
    }

    private fun validatePassword(newPassword: String, confirmPassword: String): Boolean {
        if (newPassword.length < 8) {
            binding.newPassword.error = "Password must be at least 8 characters."
            return false
        }
        if (newPassword != confirmPassword) {
            binding.confirmPassword.error = "Passwords do not match."
            return false
        }
        return true
    }

    private fun navigateToLogin() {
        // Intent pour naviguer vers LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Terminer ChangePasswordActivity pour que l'utilisateur ne puisse pas revenir en arrière
    }
}
