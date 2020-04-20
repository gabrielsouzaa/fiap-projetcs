package com.vintesete.androidiot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_iluminacao.*
import kotlinx.android.synthetic.main.activity_temperatura.*
import com.google.firebase.database.DatabaseError



class TemperaturaActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var tempRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperatura)

        database = FirebaseDatabase.getInstance()
        tempRef = database.getReference("temperatura")

        tempRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Float::class.java)
                tvTemperatura.text = value.toString() + "°C"
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}
