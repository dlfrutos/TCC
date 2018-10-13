package com.example.daniel.jsontest2.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.daniel.jsontest2.Adapters.AdapterLinhaHoraPonto
import com.example.daniel.jsontest2.Adapters.LinhaPontoViewHolder.Companion.LINHA_SELECIONADA
import com.example.daniel.jsontest2.Adapters.LinhaPontoViewHolder.Companion.PONTO_SELECIONADO2
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lhp.*
import okhttp3.*
import java.io.IOException

class LinhaPontoHoraActivity : AppCompatActivity() {
    lateinit var pontoSelecionado: String
    lateinit var linhaSelecionada: String
    //lateinit var pontosFeed: PontosFeed


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lhp)
        recyclerView_lhp.layoutManager = LinearLayoutManager(this)

        pontoSelecionado = intent.getStringExtra(PONTO_SELECIONADO2)
        linhaSelecionada = intent.getStringExtra(LINHA_SELECIONADA)

        //atualiza os campos de texto
        txt_lhp_linhaID.text = "Linha: " + linhaSelecionada
        txt_lhp_pontoID.text = "Ponto: " + pontoSelecionado

        //busca json
        fetchJsonPontos()
    }

    private fun fetchJsonPontos() {
        val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/ModelagemDB/ListaPontosLinhas.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()


        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Falha na requisição")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = response.body()?.string()

                //rotina para retirar \r\n
                //e deixar o código limpo
                body = body?.replace("\r\n", "")
                body = body?.replace("\t", "")

                //construir objeto a partir do JSON
                //pontosFeed foi criado na activity para que não
                //seja necessário passar por parâmetro na função de busca
                //que será implementada, diferente das activities anteriores
                println(body)
                val gson = GsonBuilder().create()
                val pontosFeed = gson.fromJson(body, PontosFeed::class.java)

                //filtro linha, hora e ponto
                // e rotina de calculo da previsão de chegada
                val pontosFeed2 = encontraHora(pontosFeed)


                runOnUiThread {
                    recyclerView_lhp.adapter = AdapterLinhaHoraPonto(pontosFeed2!!)
                }
            }

            private fun encontraHora(pontosFeed: PontosFeed?): PontosFeed? {
                var i =0
                //deleta todas as linhas != da selecionada
                while (i<pontosFeed!!.horaLinha.count()){
                    if(pontosFeed.horaLinha.get(i).LinhaID != linhaSelecionada){
                        pontosFeed.horaLinha.removeAt(i)
                        i--
                    }
                    i++
                }
                return pontosFeed
            }
        })
    }
}