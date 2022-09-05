package br.edu.infnet.simplerecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.infnet.simplerecipe.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    // FAz logout e volta pra tela de login
    private fun logout() {
        Firebase.auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}