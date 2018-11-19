package com.android.daniel.pontoaponto.Activities

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.android.daniel.pontoaponto.Adapters.PontosProximosAdapter
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lista_ponto_mais_proximo.*
import java.io.File
import java.io.FileReader
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class ListaPontoMaisProximoActivity : AppCompatActivity() {
    var JSON_ATUAL: PontosFeed? = MainActivity.JSON_ATUAL

    companion object {
        val PONTO_SELECIONADO = "Ponto_Selecionado"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ponto_mais_proximo)
        recyclerView_lista_pontos_proximos.layoutManager = LinearLayoutManager(this)

        val pref1 = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val primeiroAcesso1 = pref1.getBoolean("primeiroAcesso1", true)

        calculaDistancia()
        recyclerView_lista_pontos_proximos.adapter = PontosProximosAdapter(ordenaPorDistancia(JSON_ATUAL)!!)


        /**
         * MSGDIALOG que deve aparecer apenas 1 vez
         */
        if (primeiroAcesso1) {
            dialogTutorial()

            /**
             * Salva o "PRIMEIRO ACESSO"
             * para que o dialog não apareça novamente
             */
            pref1.edit().putBoolean("primeiroAcesso1", false).apply()
        }
    }

    private fun dialogTutorial() {
        val builder2 = AlertDialog.Builder(this)
        builder2.setTitle("*** PONTOS MAIS PRÓXIMOS ***")
        builder2.setMessage("Esta é a tela mostra os pontos mais próximos de sua localização. A distância é mostrada em Metros." +
                "\n\n Pressione um dos pontos para selecionar o mesmo. Para mudar, basta voltar a esta tela e selecionar outro ponto.")
        builder2.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int -> })
        builder2.show()
    }

    private fun leJson() {
        //LÊ JSON ATUAL se existir
        var f = File("JSON_ATUAL.json")
        var BD_ATUAL = ""
        if (f.exists()) {
            var fr = FileReader("JSON_ATUAL.json")
            var c: Int?
            do {
                c = fr.read()
                BD_ATUAL = BD_ATUAL + c.toChar()
            } while (c != -1)

            //rotina para retirar \r\n
            BD_ATUAL = BD_ATUAL?.replace("\r", "")
            BD_ATUAL = BD_ATUAL?.replace("\n", "")
            BD_ATUAL = BD_ATUAL?.replace("\t", "")

            //construir objeto a partir do JSON
            JSON_ATUAL = GsonBuilder().create().fromJson(BD_ATUAL, PontosFeed::class.java)
        }

        calculaDistancia()
        ordenaPorDistancia(JSON_ATUAL)
    }

    fun calculaDistancia() {
        val LAT_Pessoa = intent.getStringExtra(MainActivity.LOC_LAT).toFloat()
        val LON_Pessoa = intent.getStringExtra(MainActivity.LOC_LON).toFloat()
        val pontosFeed = JSON_ATUAL

        for (i in 0..pontosFeed!!.pontos.count() - 1 step 1) {

            var lista_pontos = pontosFeed?.pontos?.get(i)

            val valor = (6371 * acos(
                cos(Math.toRadians((90 - LAT_Pessoa).toDouble())) * cos(Math.toRadians((90 - (lista_pontos.Latitude).toFloat()).toDouble())) + sin(
                    Math.toRadians((90 - LAT_Pessoa).toDouble())
                ) * sin(Math.toRadians((90 - (lista_pontos.Latitude).toFloat()).toDouble())) * cos(Math.toRadians((LON_Pessoa - lista_pontos.Longitude.toFloat()).toDouble()))
            )) * 1000

            lista_pontos.Distancia = valor.roundToInt()
        }
    }

    fun ordenaPorDistancia(pontosFeed: PontosFeed?): PontosFeed? {
        for (i in 0..pontosFeed?.pontos?.count()!! - 2 step 1) {
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
}