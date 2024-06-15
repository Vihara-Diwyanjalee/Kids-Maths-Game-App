package com.example.kidsmathsgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PlayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val addtion = findViewById<ImageView>(R.id.addtion)
        addtion.setOnClickListener {
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","+")
            startActivity(calInt)
        }

        val sub = findViewById<ImageView>(R.id.sub)
        sub.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","-")
            startActivity(calInt)
        }

        val multi = findViewById<ImageView>(R.id.multi)
        multi.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","*")
            startActivity(calInt)
        }

        val divie = findViewById<ImageView>(R.id.divie)
        divie.setOnClickListener{
            val calInt = Intent(this@PlayActivity,
                MainActivity::class.java)

            calInt.putExtra("cals","/")
            startActivity(calInt)
        }
    }
}