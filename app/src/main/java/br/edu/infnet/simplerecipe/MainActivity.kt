package br.edu.infnet.simplerecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.infnet.simplerecipe.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    val TAG = "Firebase"

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Configuração Auth:     //////////////////////////////////////////////////////////////////////
    private lateinit var auth: FirebaseAuth

    private fun setupFirebase() {
        // Initialize Firebase Auth
        auth = Firebase.auth
    }
    private fun logar(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Pega o usuario logado
                    val user = auth.currentUser
                    //Inicializa a home
                    startHome()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    // Verifica se existe usuário logado, se existir vai direto pra Home
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startHome();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        setupFirebase()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val senha = binding.inputSenha.text.toString()
            logar(email, senha)
        }
    }






}