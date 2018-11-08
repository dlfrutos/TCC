package com.android.daniel.pontoaponto.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.daniel.pontoaponto.Adapters.AdapterLinhaHoraPonto
import com.android.daniel.pontoaponto.Adapters.LinhaPontoViewHolder.Companion.LINHA_SELECIONADA
import com.android.daniel.pontoaponto.Adapters.LinhaPontoViewHolder.Companion.PONTO_SELECIONADO2
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lhp.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class LinhaPontoHoraActivity : AppCompatActivity() {
    lateinit var pontoSelecionado: String
    lateinit var linhaSelecionada: String
    lateinit var JSON_ATUAL: PontosFeed
    val sb = MainActivity.sb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lhp)
        recyclerView_lhp.layoutManager = LinearLayoutManager(this)

        //recuperado os dados da intent (selecionador pelo usuário)
        pontoSelecionado = intent.getStringExtra(PONTO_SELECIONADO2)
        linhaSelecionada = intent.getStringExtra(LINHA_SELECIONADA)

        //atualiza os campos de texto
        txt_lhp_linhaID.text = "Linha: " + linhaSelecionada
        txt_lhp_pontoID.text = "Ponto: " + pontoSelecionado

        //trabalha com JSON
        JSON_ATUAL = GsonBuilder().create().fromJson(sb,PontosFeed::class.java)
        val pontosFeed2 = encontraHora(JSON_ATUAL)

        //envia resultado no adaptador
        recyclerView_lhp.adapter = AdapterLinhaHoraPonto(pontosFeed2!!)
    }
    fun encontraHora(pontosFeed: PontosFeed?): PontosFeed? {
        var i = 0
        val calendario = Calendar.getInstance()
        val horaCalculada = Calendar.getInstance()
        val intervaloMinutos = 10
        var encontrei = false

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
        var token = false
        while (i < pontosFeed.horaLinha.count() - 1 && !encontrei) {
            var hora = pontosFeed.horaLinha.get(i).Hora.substring(0, 2)
            var minuto = pontosFeed.horaLinha.get(i).Hora.substring(pontosFeed.horaLinha.get(i).Hora.length - 2, pontosFeed.horaLinha.get(i).Hora.length)
            var j = 1

            //varredura dos pontos da linha
            if (token == false) {

                //cria a DataHora em função da primeira hora registrada
                horaCalculada.set(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DATE), Integer.valueOf(hora), Integer.valueOf(minuto), 0)

                //seta a primeira hora
                pontosFeed.horaLinhaPontos.get(0).horaCalc = SimpleDateFormat("dd/MM HH:mm:ss").format(horaCalculada.time)

                while (j < pontosFeed.horaLinhaPontos.count()) {
                    horaCalculada.add(Calendar.MINUTE, pontosFeed.horaLinhaPontos.get(j).IntervaloMin)
                    horaCalculada.add(Calendar.SECOND, pontosFeed.horaLinhaPontos.get(j).IntervaloSeg)

                    pontosFeed.horaLinhaPontos.get(j).horaCalc = SimpleDateFormat("dd/MM HH:mm").format(horaCalculada.time)

                    //calcula pelo intervalo
                    if (pontosFeed.horaLinhaPontos.get(j).PontoID == pontoSelecionado) {
                        if (calendario.time < horaCalculada.time) {
                            println("HoraComp1" + calendario.time)
                            println("HoraComp2" + horaCalculada.time)
                            token = true
                        }
                    }
                    j++
                }
            }
            if (j == pontosFeed.horaLinhaPontos.count() && token) {
                encontrei = true
            }
            i++
        }
        return pontosFeed
    }

    private fun fetchJsonPontos() {
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


        })
    }
}