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
import java.util.*

class LinhaPontoHoraActivity : AppCompatActivity() {
    lateinit var pontoSelecionado: String
    lateinit var linhaSelecionada: String

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
                var i = 0
                val calendario = Calendar.getInstance()
                val horaCalculada = calendario
                val intervaloMinutos = 10

                //
                horaCalculada.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE), 0, 0, 0)

//                val diaSemana = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) //dia da semana int
//                val diaMes = Calendar.getInstance().get(Calendar.DATE)  //dia do mes int
//                val horaDia = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) //hora atual int
//                val minutoDia = Calendar.getInstance().get(Calendar.MINUTE) //hora atual int
//                val teste2 = calendario
//                val teste3 = calendario

                //gera uma data
//                teste3.set(calendario.get(Calendar.YEAR),calendario.get(Calendar.MONTH),calendario.get(Calendar.DATE), 0,0,0)
//                println("teste3: "+teste3.time)

                //ADIÇÃO DE INTERVALO
//                println("teste: "+calendario.time)
//                teste2.add(Calendar.MINUTE, 5)

                //              println("teste2: "+teste2)

                //deleta todas as linhas != daquela selecionada
                while (i < pontosFeed!!.horaLinha.count() - 1) {
                    //filtra linha selecionada
                    if (pontosFeed.horaLinha.get(i).LinhaID != linhaSelecionada) {
                        pontosFeed.horaLinha.removeAt(i)
                        i--
                    }
                    //filtra dia semana
                    else if (pontosFeed.horaLinha.get(i).DiaSemana.contains((calendario.get(Calendar.DAY_OF_WEEK)).toString())) {
                        //faz nada
                    } else {
                        //remove o ponto pelo dia da semana
                        pontosFeed.horaLinha.removeAt(i)
                        i--
                    }

                    //aumenta index i
                    i++
                }

                //deleta todos os pontos que não competem a linha selecionada
                i = 0
                while (i < pontosFeed.horaLinhaPontos.count() - 1) {
                    if (pontosFeed.horaLinhaPontos.get(i).LinhaID != linhaSelecionada) {
                        println("Removido:" + i + " (" + pontosFeed.horaLinhaPontos.get(i).LinhaID + ")")
                        pontosFeed.horaLinhaPontos.removeAt(i)
                        i--
                    }
                    i++
                }


                //populando com horarios os ponstos da linha selecionada
                // i= hora inicial da linha
                // j= ponto inicial da linha
                i = 0
                while (i < pontosFeed.horaLinha.count() - 1) {
                    var j = 0
                    var minuto: Int
                    var hora: Int
                    //varredura dos pontos da linha
                    while (j < pontosFeed.horaLinhaPontos.count()) {

                        val minuto = pontosFeed.horaLinha.get(i).Hora.substring(0, 2)
                        val hora = pontosFeed.horaLinha.get(i).Hora.substring(pontosFeed.horaLinha.get(i).Hora.length - 2, pontosFeed.horaLinha.get(i).Hora.length)
                        //seta a hora do primeiro ponto (programada)
                        if (j == 0) {
                        }

                        //caso nao seja o primeiro calcula pelo intervalo
                        else {
                            pontosFeed.horaLinhaPontos.get(j).Hora += intervaloMinutos
                        }

                        if (pontosFeed.horaLinhaPontos.get(j).PontoID == pontoSelecionado) {
                        }
                        j++
                    }
                    i++
                }

                return pontosFeed
            }
        })
    }
}