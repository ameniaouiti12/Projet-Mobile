package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val getStartedButton: Button = findViewById(R.id.get_started_button)
        getStartedButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
