package com.vintesete.androidiot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (usuarioLogado()) {
            proximaTelaLogada()
        }

        botaoEntrar.setOnClickListener {
            validarUsuario()
        }
    }

    private fun validarUsuario() {

        val email = inputEmail.editText?.text.toString()
        val senha =  inputSenha.editText?.text.toString()

        if (email.isEmpty()){
            inputEmail.isErrorEnabled = true
            inputEmail.error = "Favor preencher o e-mail"
            return
        } else {
            inputEmail.isErrorEnabled = false
        }

        if (senha.isEmpty()){
            inputSenha.isErrorEnabled = true
            inputSenha.error = "Favor preencher a senha"
            return
        } else {
            inputSenha.isErrorEnabled = false
        }

        mAuth.signInWithEmailAndPassword(
            email,
            senha
           )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    proximaTelaLogada()
                } else {
                    Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun proximaTelaLogada() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun usuarioLogado(): Boolean {
       return mAuth.currentUser != null
    }
}
