package com.android.daniel.pontoaponto.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.daniel.pontoaponto.R
import com.android.daniel.pontoaponto.referencia.CourseLessonActivity
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        cardView8.setOnClickListener {
            val intent = Intent(this, PDFReader::class.java)
            intent.putExtra("PDF", "Tutorial_PontoMaisProximo.pdf")
            this.startActivity(intent)
        }
        cardView9.setOnClickListener {
            val intent = Intent(this, PDFReader::class.java)
            intent.putExtra("PDF", "Tutorial_QuandoUsar.pdf")
            this.startActivity(intent)
        }

        cardView6.setOnClickListener {
            val intent = Intent(this, PDFReader::class.java)
            intent.putExtra("PDF", "Tutorial_SemInternet.pdf")
            this.startActivity(intent)
        }

        cardView11.setOnClickListener {
            val intent = Intent(this, PDFReader::class.java)
            intent.putExtra("PDF", "Tutorial_SolicitarAlgo.pdf")
            this.startActivity(intent)
        }



        textView58.setOnClickListener {
            val intent = Intent(this, CourseLessonActivity::class.java)
            intent.putExtra("site", "https://www.youtube.com/watch?v=UNzu4wQ3FKs")
            this.startActivity(intent)
        }


    }


}
