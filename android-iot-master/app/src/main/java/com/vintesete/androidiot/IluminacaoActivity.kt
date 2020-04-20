package com.vintesete.androidiot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_iluminacao.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.ValueEventListener


class IluminacaoActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var ledRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iluminacao)

        database = FirebaseDatabase.getInstance()
        ledRef = database.getReference("led")

        botaoLed.setOnClickListener {
            if (botaoLed.displayedChild == 0) {
                ledRef.setValue(1)
                botaoLed.showNext()
            } else {
                ledRef.setValue(0)
                botaoLed.showPrevious()
            }
        }

        ledRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Int::class.java)
                if (value == 1) {
                    if (botaoLed.displayedChild != 1) {
                        botaoLed.showNext()
                    }
                } else {
                    if (botaoLed.displayedChild != 0) {
                        botaoLed.showPrevious()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }


}
