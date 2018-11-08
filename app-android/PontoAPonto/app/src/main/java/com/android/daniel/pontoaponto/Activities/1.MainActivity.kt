package com.android.daniel.pontoaponto.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main2.*
import okhttp3.*
import java.io.FileReader
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    var LATITUDE = ""
    var LONGITUDE = ""
    val REQUEST_CODE = 1000

    var BD_NET = ""
    var JSON_NET: PontosFeed? = null

    companion object {
        const val LOC_LAT = "loc_lat"
        const val LOC_LON = "loc_lon"
        var JSON_ATUAL: PontosFeed? = null
        lateinit var sb: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        cardView3.visibility = View.GONE

        //ROTINA VERIFICAÇÃO BANCO DE DADOS E ATUALIZAÇÃO
        println("Attemp to fetch JSON PONTOS")
        val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/BD/BD.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Falha na requisição")
                //já inicializado a variável como null

                verificaBD()
            }

            override fun onResponse(call: Call, response: Response) {
                //lendo a página
                BD_NET = response.body()?.string().toString()

                //rotina para retirar \r\n
                BD_NET = BD_NET?.replace("\r", "")
                BD_NET = BD_NET?.replace("\n", "")
                BD_NET = BD_NET?.replace("\t", "")

                //construir objeto a partir do JSON
                JSON_NET = GsonBuilder().create().fromJson(BD_NET, PontosFeed::class.java)

                verificaBD()
            }
        })

        //VERIFICA PERMISSÃO DE UTILIZAÇÃO DO GPS
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
        } else {
            buildLocationRequest()
            buildLocationCallback()

            //criando FuserProviderClient
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            //setar evento
            btn_start_updates.setOnClickListener(View.OnClickListener {
                if (ActivityCompat.checkSelfPermission(
                                this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
                    return@OnClickListener
                }
                btn_start_updates.text = "Atualizando..."
                progressBar3.visibility= View.VISIBLE

                //mudando stado do botão
                btn_start_updates.isEnabled = !btn_start_updates.isEnabled

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                btn_start_updates.isEnabled = !btn_start_updates.isEnabled
                Toast.makeText(this, "Localização atualizada.", Toast.LENGTH_SHORT).show()
            })

//            btn_stop_updates.setOnClickListener(View.OnClickListener {
//                if (
//                        ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
//                    return@OnClickListener
//                }
//
//                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//                //mudando stado do botão
//                btn_start_updates.isEnabled = !btn_start_updates.isEnabled
//                btn_stop_updates.isEnabled = !btn_stop_updates.isEnabled
//            })

            //testando a função main
            //textView2.setBackgroundColor(Color.RED)
        }

        //CARREGA ACTIVITY BUSCAR PONTOS COM OS DADOS DE LATITUDE E LONGITUDE
//        btn_BuscaPontoMaisProximo.setOnClickListener() {
//            val intent1 = Intent(this, ListaPontoMaisProximoActivity::class.java)
//            intent1.putExtra(LOC_LAT, LATITUDE)
//            intent1.putExtra(LOC_LON, LONGITUDE)
//
//            startActivity(intent1)
//        }
//        btn_TesteCourses.setOnClickListener() {
//            val intent2 = Intent(this, CourseActivity::class.java)
//            startActivity(intent2)
//        }
//        btn_ListaPontos.setOnClickListener() {
//            val intent3 = Intent(this, ListaPontoActivity::class.java)
//            startActivity(intent3)
//        }
//        btn_ListaLinhas.setOnClickListener() {
//            val intent4 = Intent(this, ListaLinhaActivity::class.java)
//            startActivity(intent4)
//        }

        cardView3.setOnClickListener() {

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

            val intent1 = Intent(this, ListaPontoMaisProximoActivity::class.java)
            intent1.putExtra(LOC_LAT, LATITUDE)
            intent1.putExtra(LOC_LON, LONGITUDE)

            startActivity(intent1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this@MainActivity, "PERMISSION GRANTED", Toast.LENGTH_SHORT)
                    } else {
                        Toast.makeText(this@MainActivity, "PERMISSION DENIED", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }

    private fun verificaBD() {
        //INICIALIAZAÇÃO DAS VARIÁVEIS
        var BD_ORI = ""
        var BD_ATUAL = ""
        var JSON_ORI: PontosFeed? = null
        val gson = GsonBuilder().create()

        /**
         * 1° PASSO
         * *********
         * Lê arquivo do ASSETS, cria uma String, transforma em JSON.
         * Dados são aqueles usando na compilação do programa.
         */
        try {
            BD_ORI = assets.open("BD.json").bufferedReader().readText()
            BD_ORI = BD_ORI.replace("\r", "")
            BD_ORI = BD_ORI.replace("\t", "")
            BD_ORI = BD_ORI.replace("\n", "")
            JSON_ORI = gson.fromJson(BD_ORI, PontosFeed::class.java)
        } catch (e: Exception) {
        }

        /**
         * 2° PASSO
         * *********
         * Verifica os arquivos que existem e suas versões.
         * Mantem a versão atual na mais recente.
         */
        try {
            //LÊ JSON ATUAL se existir
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
            sb = BD_ATUAL

            //construir objeto a partir do JSON
            JSON_ATUAL = gson.fromJson(BD_ATUAL, PontosFeed::class.java)
        } catch (ex: Exception) {
        }

        //caso não tenha internet
        if (JSON_NET == null) {
            //criamos uma instância para trabalhar com data
            val c = Calendar.getInstance()

            //cria a versão data BKP
            println("test: " + (JSON_ORI?.versao?.DataHora)?.substring(14, 15))
            println("test: " + (JSON_ORI?.versao?.DataHora)?.substring(14, 16))

            var ano = (JSON_ORI?.versao?.DataHora)?.substring(0, 4)?.toInt()
            var mes = (JSON_ORI?.versao?.DataHora)?.substring(5, 7)?.toInt()
            var dia = (JSON_ORI?.versao?.DataHora)?.substring(8, 10)?.toInt()
            var hora = (JSON_ORI?.versao?.DataHora)?.substring(11, 13)?.toInt()
            var minuto = (JSON_ORI?.versao?.DataHora)?.substring(15, 16)?.toInt()
            c.set(ano!!, mes!!, dia!!, hora!!, 0, 0)
            val v_bkp = c

            //caso não tenhamos um JSON atual criado no aparelho
            if (JSON_ATUAL == null) {
                JSON_ATUAL = JSON_ORI
                sb = BD_ORI
            }

            //caso tenhamos o JSON atual
            else {
                //cria a versão data ATUAL
                ano = (JSON_ATUAL?.versao?.DataHora)?.substring(2, 5)?.toInt()
                mes = (JSON_ATUAL?.versao?.DataHora)?.substring(7, 8)?.toInt()
                dia = (JSON_ATUAL?.versao?.DataHora)?.substring(10, 11)?.toInt()
                hora = (JSON_ATUAL?.versao?.DataHora)?.substring(13, 14)?.toInt()
                minuto = (JSON_ATUAL?.versao?.DataHora)?.substring(14, 16)?.toInt()
                c.set(ano!!, mes!!, dia!!, hora!!, minuto!!, 0)
                val v_atual = c

                if (v_atual.timeInMillis > v_bkp.timeInMillis) {
                } else {
                    JSON_ATUAL = JSON_ORI
                    sb = BD_ORI
                }
            }
        }

        //se tivermos internet
        else if (JSON_ORI?.versao != null) {
            //cria a versão data BKP
            var ano = (JSON_ORI?.versao?.DataHora)?.substring(0, 4)?.toInt()
            var mes = (JSON_ORI?.versao?.DataHora)?.substring(5, 7)?.toInt()
            var dia = (JSON_ORI?.versao?.DataHora)?.substring(8, 10)?.toInt()
            var hora = (JSON_ORI?.versao?.DataHora)?.substring(11, 13)?.toInt()
            var minuto = (JSON_ORI?.versao?.DataHora)?.substring(14, 16)?.toInt()
            val v_bkp = Calendar.getInstance()
            v_bkp.set(ano!!, mes!!, dia!!, hora!!, minuto!!, 0)

            val v_atual = Calendar.getInstance()
            v_atual.set(0, 0, 0, 0, 0)

            if (JSON_ATUAL != null) {
                //cria a versão data ATUAL
                ano = (JSON_ATUAL?.versao?.DataHora)?.substring(0, 4)?.toInt()!!
                mes = (JSON_ATUAL?.versao?.DataHora)?.substring(5, 7)?.toInt()!!
                dia = (JSON_ATUAL?.versao?.DataHora)?.substring(8, 10)?.toInt()!!
                hora = (JSON_ATUAL?.versao?.DataHora)?.substring(11, 13)?.toInt()!!
                minuto = (JSON_ATUAL?.versao?.DataHora)?.substring(14, 16)?.toInt()!!
                v_atual.set(ano!!, mes!!, dia!!, hora!!, minuto!!, 0)

            }

            //cria a versão data ATUAL
            ano = (JSON_NET?.versao?.DataHora)?.substring(0, 4)?.toInt()!!
            mes = (JSON_NET?.versao?.DataHora)?.substring(5, 7)?.toInt()!!
            dia = (JSON_NET?.versao?.DataHora)?.substring(8, 10)?.toInt()!!
            hora = (JSON_NET?.versao?.DataHora)?.substring(11, 13)?.toInt()!!
            minuto = (JSON_NET?.versao?.DataHora)?.substring(14, 16)?.toInt()!!
            val v_net = Calendar.getInstance()
            v_net.set(ano!!, mes!!, dia!!, hora!!, minuto!!, 0)


            if (v_net.timeInMillis > v_atual.timeInMillis && v_net.timeInMillis > v_bkp.timeInMillis) {
                JSON_ATUAL = JSON_NET
                sb = BD_NET
            } else if (v_bkp.timeInMillis > v_atual.timeInMillis) {
                JSON_ATUAL = JSON_ORI
                sb = BD_ORI
            }
        }


        /**
         * 3° PASSO
         * *********
         * Escreve a versão final na memoria interna
         */
        try {
            val FILENAME = "JSON_ATUAL.json"
//            val string = JSON_ATUAL.toString()
            val fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)
            fos.write(JSON_ATUAL.toString().toByteArray())
            fos.close()

            //checando arquivos dentro da pasta
//            var fo = FileWriter(FILENAME, true)
//            fo.write(string)
//            fo.close()
            Toast.makeText(this, "Arquivo salvo.", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
        }
    }

    fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            //control + O
            override fun onLocationResult(p0: LocationResult?) {
                var location = p0?.locations?.get(p0.locations.size - 1) //ultima posição
                location = p0?.locations?.get(p0.locations.size - 1) //ultima posição
//                txt_Location_Latitude.text = "Latitude:  " + location?.latitude.toString()
//                txt_Location_Longitude.text = "Longitude:  " + location?.longitude.toString()
                LATITUDE = location?.latitude.toString()
                LONGITUDE = location?.longitude.toString()

                //btn_start_updates.visibility = View.GONE
                cardView3.visibility = View.VISIBLE
                btn_start_updates.text = "Localização OK!"

                progressBar3.visibility= View.GONE


                //btn_BuscaPontoMaisProximo.isEnabled = true
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3000
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 10F
    }
}