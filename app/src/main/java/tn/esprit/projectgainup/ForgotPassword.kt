package tn.esprit.projectgainup

import tn.esprit.projectgainup.remote.RetrofitClient
import tn.esprit.projectgainup.dtos.ForgotPasswordRequest
import tn.esprit.projectgainup.dtos.GenericResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.projectgainup.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Événement du clic sur le bouton pour envoyer l'email
        binding.nextButtonContainer.setOnClickListener {
            val email = binding.signupEmailEditText.text.toString().trim() // Trimming whitespace
            if (email.isNotEmpty()) {
                // Appeler la méthode pour envoyer l'email de réinitialisation
                forgotPassword(email)
            } else {
                Toast.makeText(this, "Veuillez entrer un email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Méthode pour envoyer la demande de réinitialisation
    private fun forgotPassword(email: String) {
        val forgotPasswordRequest = ForgotPasswordRequest(email)

        // Vérifier d'abord si l'email existe déjà
        RetrofitClient.apiService.checkEmailExists(email).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                Log.d("ForgotPassword", "Response code: ${response.code()}, Body: ${response.body()}")
                if (response.isSuccessful && response.body()?.success == true) {
                    // Si l'email existe, envoyer le code de réinitialisation
                    sendResetCode(forgotPasswordRequest)
                } else {
                    // Si l'email n'existe pas, afficher un message d'invitation à l'inscription
                    Toast.makeText(this@ForgotPassword, "Email non trouvé. Veuillez vous inscrire.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("ForgotPassword", "Network error: ${t.message}")
                Toast.makeText(this@ForgotPassword, "Erreur de réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendResetCode(forgotPasswordRequest: ForgotPasswordRequest) {
        RetrofitClient.apiService.forgotPassword(forgotPasswordRequest).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                Log.d("ForgotPassword", "Send reset code response: ${response.code()}, Body: ${response.body()}")
                if (response.isSuccessful) {
                    Toast.makeText(this@ForgotPassword, "Code envoyé à votre email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ForgotPassword, ChangePassword::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ForgotPassword, "Échec de l'envoi du code", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("ForgotPassword", "Network error: ${t.message}")
                Toast.makeText(this@ForgotPassword, "Erreur de réseau", Toast.LENGTH_SHORT).show()
            }
        })
    }
}