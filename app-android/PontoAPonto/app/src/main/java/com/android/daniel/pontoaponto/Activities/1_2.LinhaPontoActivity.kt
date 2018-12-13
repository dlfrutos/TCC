package com.android.daniel.pontoaponto.Activities

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
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
        JSON_ATUAL = GsonBuilder().create().fromJson(sb, PontosFeed::class.java)

        val pontosFeed = encontraLinhas()
        val pref2 = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val primeiroAcesso2 = pref2.getBoolean("primeiroAcesso2", true)

        recyclerView_linha_ponto.layoutManager = LinearLayoutManager(this)
        textView25.text = "►  " + PONTO_VALOR
        recyclerView_linha_ponto.adapter = AdapterLinhaPonto(pontosFeed!!)

        if (primeiroAcesso2) {
            dialogTutorial()

            /**
             * Salva o "PRIMEIRO ACESSO"
             * para que o dialog não apareça novamente
             */
            pref2.edit().putBoolean("primeiroAcesso2", false).apply()

        }
    }

    private fun dialogTutorial() {
        val builder2 = AlertDialog.Builder(this)
        builder2.setTitle("LISTA LINHAS")
        builder2.setMessage("Esta é a tela que mostra as linhas que passam no ponto selecionado. " +
                "\n\nAo selecionar a linha, o programa calcula automaticamente o horário previsto para passar a LINHA selecionada no PONTO desejado.")
        builder2.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int -> })
        builder2.show()
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


}

