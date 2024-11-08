package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.projectgainup.databinding.ActivityChangePasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.projectgainup.dtos.ChangePasswordRequest
import tn.esprit.projectgainup.dtos.GenericResponse
import tn.esprit.projectgainup.remote.RetrofitClient

class ChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Email de l'utilisateur (à adapter selon l'endroit où il est stocké)
        val email = intent.getStringExtra("email") ?: ""

        binding.changePasswordButton.setOnClickListener {
            val newPassword = binding.newPassword.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (validatePassword(newPassword, confirmPassword)) {
                changePassword(email, newPassword)
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

    private fun changePassword(email: String, newPassword: String) {
        val changePasswordRequest = ChangePasswordRequest(email, newPassword)

        val call = RetrofitClient.apiService.changePassword(changePasswordRequest)

        call.enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@ChangePassword, "Password changed successfully", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(this@ChangePassword, "Failed to change password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Toast.makeText(this@ChangePassword, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
