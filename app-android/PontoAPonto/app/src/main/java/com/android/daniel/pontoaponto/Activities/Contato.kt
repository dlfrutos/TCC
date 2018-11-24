package com.android.daniel.pontoaponto.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.daniel.pontoaponto.R
import com.android.daniel.pontoaponto.referencia.CourseLessonActivity
import kotlinx.android.synthetic.main.activity_contato.*

class Contato : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)


            cardView12.setOnClickListener {
                val intent = Intent(this, CourseLessonActivity::class.java)
                intent.putExtra("site","https://play.google.com/store/apps/details?id=com.daniel.pontoaponto")
                this.startActivity(intent)

            }

            cardView15.setOnClickListener {
                val intent = Intent(this, CourseLessonActivity::class.java)
                intent.putExtra("site","https://github.com/dlfrutos/TCC")
                this.startActivity(intent)
            }
    }
}
