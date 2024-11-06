package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplachScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)

        // Appliquer le padding pour les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Délai pour afficher le splash screen pendant 2 secondes
        Handler(Looper.getMainLooper()).postDelayed({
            // Naviguer vers WelcomeActivity après le délai
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()  // Terminer SplachScreen pour ne pas revenir en arrière
        }, 2000)  // Temps en millisecondes (2 secondes ici)
    }
}
