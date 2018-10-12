package com.example.daniel.jsontest2.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.daniel.jsontest2.Adapters.AdapterLinhaPonto
import com.example.daniel.jsontest2.Modelos.Pontos
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_linha_ponto.*
import okhttp3.*
import java.io.IOException

class LinhaPontoActivity : AppCompatActivity() {

    companion object {
        var PONTO_VALOR = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linha_ponto)

        PONTO_VALOR = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
        recyclerView_linha_ponto.layoutManager = LinearLayoutManager(this)
        fetchJsonPontos()
    }

    private fun fetchJsonPontos() {
        println("Attemp to fetch JSON PONTOS")
        val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/ModelagemDB/ListaPontosLinhas.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Falha na requisição")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body()?.string()
                val pontoSelecionado = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)

                //rotina para retirar \r\n
                body = body?.replace("\r\n", "")
                body = body?.replace("\t", "")

                //construir objeto a partir do JSON
                //println(body)
                val gson = GsonBuilder().create()
                val pontosFeed = gson.fromJson(body, PontosFeed::class.java)
                val pontosFeed2 = encontraLinhas(pontosFeed)

                //mando informação para o adapter
                //que irá atualizar o recycled view
                runOnUiThread {
                    txt_linha_ponto_selecionado.text = "Ponto Selecionado: " + intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)

                    recyclerView_linha_ponto.adapter = AdapterLinhaPonto(pontosFeed2!!)
                }
            }

            private fun encontraLinhas(pontosFeed: PontosFeed?): PontosFeed? {
                //for( i in 0..pontosFeed!!.linhas.count()-1){

                var i = 0
                while (i < pontosFeed!!.linhas.count()) {

                    val pontoSelecionado = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)

                    //pontosFeed!!.linhas.forEach {
                    if (pontosFeed!!.linhas.get(i).RoteiroPontos.contains(pontoSelecionado, true)) {
                    } else {
                        println("ENCONTRADO PONTO: " + i)
                        pontosFeed.linhas.removeAt(i)
                        i--
                    }
                    i++
                }

                return pontosFeed
            }
        })
    }
}

