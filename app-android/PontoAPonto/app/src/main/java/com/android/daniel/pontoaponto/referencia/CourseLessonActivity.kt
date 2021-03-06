package com.android.daniel.pontoaponto.referencia

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.daniel.pontoaponto.Activities.LinhaPontoHoraActivity
import com.android.daniel.pontoaponto.Activities.MainActivity
import com.android.daniel.pontoaponto.R
import kotlinx.android.synthetic.main.activity_course_lesson.*
import java.util.*

class CourseLessonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course_lesson)
//        webview_course_lesson.setBackgroundColor(Color.RED)

        //val courseLink = "https://drive.google.com/open?id=1PP1Z8g7GrI0CdSwEP0ftbt777YJLUHX0&usp=sharing"

        val courseLink = intent.getStringExtra("site")


        webview_course_lesson.settings.javaScriptEnabled = true
        webview_course_lesson.settings.loadWithOverviewMode = true
        webview_course_lesson.settings.useWideViewPort = true
        webview_course_lesson.loadUrl(courseLink)
    }


    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}