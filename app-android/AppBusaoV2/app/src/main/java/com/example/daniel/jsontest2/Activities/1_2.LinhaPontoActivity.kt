package com.example.daniel.jsontest2.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.daniel.jsontest2.Adapters.AdapterLinhaPonto
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_linha_ponto.*

class LinhaPontoActivity() : AppCompatActivity() {

    lateinit var JSON_ATUAL:PontosFeed
    val sb = MainActivity.sb

    companion object {
        var PONTO_VALOR = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linha_ponto)
        PONTO_VALOR = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
        recyclerView_linha_ponto.layoutManager = LinearLayoutManager(this)

        //val sb = MainActivity.JSON_ATUAL.toString()
        JSON_ATUAL = GsonBuilder().create().fromJson(sb, PontosFeed::class.java)

        val pontosFeed = encontraLinhas()

        txt_linha_ponto_selecionado.text = "Ponto Selecionado: " + intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
        recyclerView_linha_ponto.adapter = AdapterLinhaPonto(pontosFeed!!)
    }

    private fun encontraLinhas(): PontosFeed? {

        var pontosFeed2 = JSON_ATUAL

        var i = 0
        while (i < JSON_ATUAL!!.linhas.count()) {
            val pontoSelecionado = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
            if (JSON_ATUAL!!.linhas.get(i).RoteiroPontos.contains(pontoSelecionado, true)) {
//                pontosFeed2.linhas.get(j).LinhaID = JSON_ATUAL.linhas.get(i).LinhaID
//                pontosFeed2.linhas.get(j).RoteiroFim = JSON_ATUAL.linhas.get(i).RoteiroFim
            } else {
                pontosFeed2.linhas.removeAt(i)
                i--
            }
            i++
        }

        return pontosFeed2
    }
//    private fun fetchJsonPontos() {
//        println("Attemp to fetch JSON PONTOS")
//        val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/ModelagemDB/ListaPontosLinhas.json"
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
//                val pontoSelecionado = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
//
//                //rotina para retirar \r\n
//                body = body?.replace("\r\n", "")
//                body = body?.replace("\t", "")
//
//                //construir objeto a partir do JSON
//                //println(body)
//                val gson = GsonBuilder().create()
//                val pontosFeed = gson.fromJson(body, PontosFeed::class.java)
//                val pontosFeed2 = encontraLinhas(pontosFeed)
//
//                //mando informação para o adapter
//                //que irá atualizar o recycled view
//                runOnUiThread {
//                    txt_linha_ponto_selecionado.text = "Ponto Selecionado: " + intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
//
//                    recyclerView_linha_ponto.adapter = AdapterLinhaPonto(pontosFeed2!!)
//                }
//            }
//
//            private fun encontraLinhas(pontosFeed: PontosFeed?): PontosFeed? {
//                //for( i in 0..pontosFeed!!.linhas.count()-1){
//
//                var i = 0
//                while (i < pontosFeed!!.linhas.count()) {
//                    val pontoSelecionado = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
//                    if (pontosFeed!!.linhas.get(i).RoteiroPontos.contains(pontoSelecionado, true)) {
//
//
//                    } else {
////                        println("ENCONTRADO PONTO: " + i)
////                        pontosFeed.linhas.removeAt(i)
////                        i--
//                    }
//                    i++
//                }
//
//                return pontosFeed
//            }
//        })
//    }


}

