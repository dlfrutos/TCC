package com.example.daniel.jsontest2.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.daniel.jsontest2.Adapters.PontosAdapter
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lista_ponto.*
import okhttp3.*
import java.io.IOException

class ListaPontoActivity : AppCompatActivity() {
    lateinit var JSON_ATUAL: PontosFeed
    val sb = MainActivity.sb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ponto)

        recyclerView_lista_pontos.layoutManager = LinearLayoutManager(this)

        //fetchJsonPontos()
        val pontosFeed = GsonBuilder().create().fromJson(sb, PontosFeed::class.java)
        recyclerView_lista_pontos.adapter = PontosAdapter(pontosFeed)


    }


//    private fun fetchJsonPontos() {
//        println("Attemp to fetch JSON PONTOS")
//        val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/BD/BD.json"
//        val request = Request.Builder().url(url).build()
//        val client = OkHttpClient()
//
//        client.newCall(request).enqueue(object : Callback {
//
//            override fun onFailure(call: Call, e: IOException) {
//                println("Falha na requisição")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                var body = response.body()?.string()
//
//                //rotina para retirar \r\n
//                body = body?.replace("\r\n", "")
//                body = body?.replace("\t", "")
//
//                //construir objeto a partir do JSON
//                println(body)
//                val gson = GsonBuilder().create()
//                val pontosFeed = gson.fromJson(body, PontosFeed::class.java)
//
//                runOnUiThread {
//                    recyclerView_lista_pontos.adapter = PontosAdapter(pontosFeed)
//                }
//            }
//        })
//    }
}