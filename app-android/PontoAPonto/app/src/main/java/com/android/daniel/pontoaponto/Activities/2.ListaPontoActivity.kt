package com.android.daniel.pontoaponto.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.daniel.pontoaponto.Adapters.PontosAdapter
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lista_ponto.*

class ListaPontoActivity : AppCompatActivity() {
    lateinit var JSON_ATUAL: PontosFeed
    val sb = MainActivity.sb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ponto)

        recyclerView_lista_pontos.layoutManager = LinearLayoutManager(this)

        val pontosFeed = GsonBuilder().create().fromJson(sb, PontosFeed::class.java)
        recyclerView_lista_pontos.adapter = PontosAdapter(pontosFeed)
    }
}