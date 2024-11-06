package tn.esprit.projectgainup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.projectgainup.databinding.ActivityConfirmCodeBinding

class ConfirmCode : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val codeInputs = listOf(
            binding.codeInput1,
            binding.codeInput2,
            binding.codeInput3,
            binding.codeInput4
        )

        // TextWatcher pour activer le bouton de confirmation lorsque tous les champs sont remplis
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.confirmButton.isEnabled = codeInputs.all { it.text.isNotEmpty() }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        // Ajouter TextWatcher à chaque champ de code
        codeInputs.forEach { editText ->
            editText.addTextChangedListener(textWatcher)
        }

        // Action au clic du bouton de confirmation
        binding.confirmButton.setOnClickListener {
            val code = codeInputs.joinToString("") { it.text.toString() }
            if (code == "1234") { // Exemple de code de vérification
                navigateToChangePassword()
            } else {
                // Afficher une erreur si le code est incorrect
                Toast.makeText(this, "Code incorrect. Veuillez réessayer.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToChangePassword() {
        // Intent pour naviguer vers ChangePasswordActivity
        val intent = Intent(this, ChangePassword::class.java)
        startActivity(intent)
        finish() // Facultatif : termine ConfirmCodeActivity
    }
}
