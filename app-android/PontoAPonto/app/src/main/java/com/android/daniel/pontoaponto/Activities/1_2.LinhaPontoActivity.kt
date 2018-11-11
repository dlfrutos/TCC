package com.android.daniel.pontoaponto.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.daniel.pontoaponto.Adapters.AdapterLinhaPonto
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_linha_ponto.*

class LinhaPontoActivity() : AppCompatActivity() {

    lateinit var JSON_ATUAL: PontosFeed
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
        textView25.text = "►  " + PONTO_VALOR

        // INSERIR PONTO SELECIONADO
        // txt_linha_ponto_selecionado.text = "Ponto Selecionado: " + intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)
        recyclerView_linha_ponto.adapter = AdapterLinhaPonto(pontosFeed!!)
    }

    private fun encontraLinhas(): PontosFeed? {
        var pontosFeed2 = JSON_ATUAL
        var i = 0
        while (i < JSON_ATUAL!!.linhas.count()) {
            val pontoSelecionado = intent.getStringExtra(ListaPontoMaisProximoActivity.PONTO_SELECIONADO)

            if (JSON_ATUAL!!.linhas.get(i).RoteiroPontos.contains(pontoSelecionado, false)) {
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


}

