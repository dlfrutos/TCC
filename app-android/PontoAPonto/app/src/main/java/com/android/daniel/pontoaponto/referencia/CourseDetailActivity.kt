package com.android.daniel.pontoaponto.referencia

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.daniel.pontoaponto.R
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_course.*
import kotlinx.android.synthetic.main.course_list.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        //we'll change the title bar
        val navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle.toString()

        //gambiarra para acertar a URL e ter detalhes do video
        fetchJSON()
    }


    private fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId
        println(courseDetailUrl)

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)
                //necessário senão da erro pq somente thread UI pode mudar interface
                runOnUiThread {
                    recyclerView_main.adapter = CourseDetailAdapter(courseLessons)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("falhou ao puxar os detalhes")
            }
        })
    }

    private class CourseDetailAdapter(val courseLessons: Array<CourseLesson>) : RecyclerView.Adapter<CourseLessonViewHolder>() {

        //como ele vai aparecer
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.course_list, parent, false)
//            val blueView = View(parent.context)
//            blueView.setBackgroundColor(Color.BLUE)
//            blueView.minimumHeight = 50
            return CourseLessonViewHolder(customView)
        }

        //numero de arquivos a ser gerado
        override fun getItemCount(): Int {
            return courseLessons.size
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            val courseLesson = courseLessons.get(position)
            holder.customView.textView_course_lesson.text = courseLesson.name
            holder.customView.textView_duration.text = courseLesson.duration

            val imageView = holder.customView.imageView_course_lesson
            Picasso.get().load(courseLesson.imageUrl).into(imageView)
//            Picasso.get().load(video.imageUrl).into(thumbnailImageView)

            holder.courseLesson = courseLesson
        }

    }

    class CourseLessonViewHolder(val customView: View, var courseLesson: CourseLesson? = null) : RecyclerView.ViewHolder(customView) {
        companion object {
            val COURSE_LESSON_LINK = "COURSE_LESSON_LINK"
        }

        init {
            customView.setOnClickListener {
                //                println("Attemp to load")
                val intent = Intent(customView.context, CourseLessonActivity::class.java)
                //intent.putExtra(COURSE_LESSON_LINK, courseLesson?.link)
                intent.putExtra(COURSE_LESSON_LINK, "https://drive.google.com/open?id=1PP1Z8g7GrI0CdSwEP0ftbt777YJLUHX0&usp=sharing")
                customView.context.startActivity(intent)
            }
        }
    }

}