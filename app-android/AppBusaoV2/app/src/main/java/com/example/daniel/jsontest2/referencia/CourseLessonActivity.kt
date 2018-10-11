package com.example.daniel.jsontest2.referencia

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.daniel.jsontest2.R
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course_lesson)
//        webview_course_lesson.setBackgroundColor(Color.RED)

        val courseLink = intent.getStringExtra(CourseDetailActivity.CourseLessonViewHolder.COURSE_LESSON_LINK)

        webview_course_lesson.settings.javaScriptEnabled = true
        webview_course_lesson.settings.loadWithOverviewMode = true
        webview_course_lesson.settings.useWideViewPort = true

        webview_course_lesson.loadUrl(courseLink)



    }
}