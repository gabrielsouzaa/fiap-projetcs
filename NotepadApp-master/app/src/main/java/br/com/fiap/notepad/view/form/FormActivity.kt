package br.com.fiap.notepad.view.form

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.notepad.R
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        button.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

    }
}
