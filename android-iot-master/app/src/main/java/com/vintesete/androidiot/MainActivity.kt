package com.vintesete.androidiot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botaoIluminacao.setOnClickListener {
            startActivity(Intent(this, IluminacaoActivity::class.java))
        }

        botaoSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        botaoTemperatura.setOnClickListener {
            startActivity(Intent(this, TemperaturaActivity::class.java))
        }
    }
}
