package com.android.daniel.pontoaponto.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.daniel.pontoaponto.Adapters.AdapterLinhas
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lista_linhas.*
import okhttp3.*
import java.io.IOException

class ListaLinhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_linhas)
        recyclerView_lista_linhas.layoutManager = LinearLayoutManager(this)
        fetchJSON()

    }

    private fun fetchJSON() {
        val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/BD/BD.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Falha na requisição")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body()?.string()

                //rotina para retirar \r\n
                body = body?.replace("\r\n", "")
                body = body?.replace("\t", "")

                //construir objeto a partir do JSON
                println(body)
                val gson = GsonBuilder().create()
                val pontosFeed = gson.fromJson(body, PontosFeed::class.java)

                runOnUiThread {
                    recyclerView_lista_linhas.adapter = AdapterLinhas(pontosFeed)
                }
            }
        })
    }
}