package com.example.daniel.jsontest2.referencia

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.daniel.jsontest2.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_course.*
import okhttp3.*
import java.io.IOException

class CourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
//       recyclerView_main.adapter = CourseAdapter()
        fetchJson()
    }

    private fun fetchJson() {
        println("Attemp to fetch JSON")
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Falha na requisição")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread {
                    recyclerView_main.adapter = CourseAdapter(homeFeed)
                }
            }
        })
    }
}

