package com.example.daniel.jsontest2.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.daniel.jsontest2.Adapters.PontosProximosAdapter
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lista_ponto_mais_proximo.*
import okhttp3.*
import java.io.IOException
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class ListaPontoMaisProximoActivity : AppCompatActivity() {

    companion object {
        val PONTO_SELECIONADO = "Ponto_Selecionado"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ponto_mais_proximo)

        recyclerView_lista_pontos_proximos.layoutManager = LinearLayoutManager(this)
        fetchJsonPontos()

        //codigo anterior que trás os dados
        txt_buscapontos_latitude.setText("Latitude: " + intent.getStringExtra(MainActivity.LOC_LAT))
        txt_buscaponto_longitude.setText("Latitude: " + intent.getStringExtra(MainActivity.LOC_LON))
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

                //rotina para retirar \r\n
                body = body?.replace("\r\n", "")
                body = body?.replace("\t", "")

                //construir objeto a partir do JSON
                println(body)
                val gson = GsonBuilder().create()
                val pontosFeed = gson.fromJson(body, PontosFeed::class.java)

                calculaDistancia(pontosFeed)
                val pontosFeed2 = ordenaPorDistancia(pontosFeed)

                //mando informação para o adapter
                //que irá atualizar o recycled view
                runOnUiThread {
                    recyclerView_lista_pontos_proximos.adapter = PontosProximosAdapter(pontosFeed2!!)
                }
            }

            private fun calculaDistancia(pontosFeed: PontosFeed) {
                val LAT_Pessoa = intent.getStringExtra(MainActivity.LOC_LAT).toFloat()
                val LON_Pessoa = intent.getStringExtra(MainActivity.LOC_LON).toFloat()
                var lista_pontos = pontosFeed.pontos.get(1)

                val teste1 = 6371 * acos(cos(Math.toRadians((90 - LAT_Pessoa).toDouble())))
                val teste2 = cos(Math.toRadians((90 - (lista_pontos.Latitude).toFloat()).toDouble()))
                val teste3 = sin(Math.toRadians((90 - LAT_Pessoa).toDouble()))
                val teste4 = sin(Math.toRadians((90 - (lista_pontos.Latitude).toFloat()).toDouble()))
                val teste5 = cos(Math.toRadians((LON_Pessoa - lista_pontos.Longitude.toFloat()).toDouble()))


                for (i in 0..pontosFeed.pontos.count() - 1 step 1) {
                    lista_pontos = pontosFeed.pontos.get(i)
                    lista_pontos.Distancia = (6371 * acos(cos(Math.toRadians((90 - LAT_Pessoa).toDouble())) * cos(Math.toRadians((90 - (lista_pontos.Latitude).toFloat()).toDouble())) + sin(Math.toRadians((90 - LAT_Pessoa).toDouble())) * sin(Math.toRadians((90 - (lista_pontos.Latitude).toFloat()).toDouble())) * cos(Math.toRadians((LON_Pessoa - lista_pontos.Longitude.toFloat()).toDouble())))).toLong()
                }
            }

            private fun ordenaPorDistancia(pontosFeed: PontosFeed?): PontosFeed? {
                for (i in 0..pontosFeed?.pontos?.count()!! - 2 step 1) {
//                pontosFeed?.pontos?.
//                        take(3)?. //selecionamos os 10 primeiros pontos
//                        sortedBy { it.Distancia } //filtramos por distancia

                    for (j in i..pontosFeed?.pontos?.count() - 1 step 1) {
                        if (pontosFeed?.pontos?.get(i)?.Distancia!! > pontosFeed?.pontos?.get(j)?.Distancia) {

                            val temp_dist = pontosFeed.pontos.get(i).Distancia
                            val temp_lat = pontosFeed.pontos.get(i).Latitude
                            val temp_lon = pontosFeed.pontos.get(i).Longitude
                            val temp_id = pontosFeed.pontos.get(i).PontoID

                            pontosFeed.pontos.get(i).Distancia = pontosFeed.pontos.get(j).Distancia
                            pontosFeed.pontos.get(i).Latitude = pontosFeed.pontos.get(j).Latitude
                            pontosFeed.pontos.get(i).Longitude = pontosFeed.pontos.get(j).Longitude
                            pontosFeed.pontos.get(i).PontoID = pontosFeed.pontos.get(j).PontoID

                            pontosFeed.pontos.get(j).Distancia = temp_dist
                            pontosFeed.pontos.get(j).Latitude = temp_lat
                            pontosFeed.pontos.get(j).Longitude = temp_lon
                            pontosFeed.pontos.get(j).PontoID = temp_id

                        }
                    }
                }
                return pontosFeed
            }
        })
    }
}

