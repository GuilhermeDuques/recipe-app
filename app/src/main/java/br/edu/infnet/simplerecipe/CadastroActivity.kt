package br.edu.infnet.simplerecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import br.edu.infnet.simplerecipe.databinding.ActivityCadastroBinding
import br.edu.infnet.simplerecipe.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CadastroActivity : AppCompatActivity() {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Configuração FireStore:  ////////////////////////////////////////////////////////////////////
    val db = Firebase.firestore

    fun cadastrarUsuarioComDados(user: Usuario, userId: String){
        db.collection("usuarios").document(userId).set(user)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////


    // Configuração Auth:
    private lateinit var auth: FirebaseAuth




    private lateinit var binding: ActivityCadastroBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()

    }

    private fun setup() {
        setupFirebase()
        setupClickListeners()
    }

    private fun setupFirebase() {
        // Initialize Firebase Auth
        auth = Firebase.auth
    }



    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            val senha = binding.inputSenha.text.toString()
            cadastrarUsuario(getUsuario(), senha)
        }
    }

    val TAG = "Firebase"
    private fun cadastrarUsuario(usuario: Usuario, senha: String) {
        val email = usuario.email.toString() ?: ""
        // Chamar firebase para cadastrar

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val userId = user?.uid.toString()
                    cadastrarUsuarioComDados(usuario, userId)

                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }



    }

    private fun getUsuario(): Usuario {
        val user = Usuario(
            nome = binding.inputNome.text.toString(),
            email = binding.inputEmail.text.toString()
        )
        return user
    }


}