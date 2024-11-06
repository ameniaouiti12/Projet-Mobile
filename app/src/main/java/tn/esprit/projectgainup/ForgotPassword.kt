package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.projectgainup.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Naviguer vers ConfirmCodeActivity au clic sur next_button
        binding.nextButtonContainer.setOnClickListener {
            val intent = Intent(this, ConfirmCode::class.java)
            startActivity(intent)
        }
    }
}
