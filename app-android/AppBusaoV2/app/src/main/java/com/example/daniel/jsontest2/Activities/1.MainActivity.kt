package com.example.daniel.jsontest2.Activities

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
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import com.example.daniel.jsontest2.referencia.CourseActivity
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.Format
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    var LATITUDE = ""
    var LONGITUDE = ""
    val REQUEST_CODE = 1000

    companion object {
        const val LOC_LAT = "loc_lat"
        const val LOC_LON = "loc_lon"
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ROTINA VERIFICAÇÃO BANCO DE DADOS E ATUALIZAÇÃO
        verificaBD()

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
                //trocado if por while
                if (ActivityCompat.checkSelfPermission(
                                this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
                    return@OnClickListener
                }

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                //mudando stado do botão
                btn_start_updates.isEnabled = !btn_start_updates.isEnabled
                btn_stop_updates.isEnabled = !btn_stop_updates.isEnabled

            })
            btn_stop_updates.setOnClickListener(View.OnClickListener {
                if (
                        ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
                    return@OnClickListener
                }

                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                //mudando stado do botão
                btn_start_updates.isEnabled = !btn_start_updates.isEnabled
                btn_stop_updates.isEnabled = !btn_stop_updates.isEnabled
            })

            //testando a função main
            //textView2.setBackgroundColor(Color.RED)
        }

        //CARREGA ACTIVITY BUSCAR PONTOS COM OS DADOS DE LATITUDE E LONGITUDE
        btn_BuscaPontoMaisProximo.setOnClickListener() {
            val intent1 = Intent(this, ListaPontoMaisProximoActivity::class.java)
            intent1.putExtra(LOC_LAT, LATITUDE)
            intent1.putExtra(LOC_LON, LONGITUDE)
            startActivity(intent1)
        }
        btn_TesteCourses.setOnClickListener() {
            val intent2 = Intent(this, CourseActivity::class.java)
            startActivity(intent2)
        }
        btn_ListaPontos.setOnClickListener() {
            val intent3 = Intent(this, ListaPontoActivity::class.java)
            startActivity(intent3)
        }
        btn_ListaLinhas.setOnClickListener() {
            val intent4 = Intent(this, ListaLinhaActivity::class.java)
            startActivity(intent4)
        }

    }

    fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            //control + O
            override fun onLocationResult(p0: LocationResult?) {
                var location = p0?.locations?.get(p0.locations.size - 1) //ultima posição
                location = p0?.locations?.get(p0.locations.size - 1) //ultima posição
                txt_Location_Latitude.text = "Latitude:  " + location?.latitude.toString()
                txt_Location_Longitude.text = "Longitude:  " + location?.longitude.toString()
                LATITUDE = location?.latitude.toString()
                LONGITUDE = location?.longitude.toString()
                btn_BuscaPontoMaisProximo.isEnabled = true
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

    private fun verificaBD() {
        //INICIALIAZAÇÃO DAS VARIÁVEIS
        var BD_ORI = ""
        var BD_NET = ""
        var BD_BKP = ""
        var BD = ""

        var JSON_ORI: PontosFeed? = null
        var JSON_NET: PontosFeed? = null
        var JSON_BKP: PontosFeed? = null
        var JSON_ATUAL: PontosFeed? = null

        val gson = GsonBuilder().create()

        /**
         * 1° PASSO
         * *********
         * Lê arquivo do ASSETS, cria uma String, transforma em JSON.
         * Dados são aqueles usando na compilação do programa.
         */
        try {
            BD_ORI = assets.open("ListaPontosLinhas.json").bufferedReader().readText()
            BD_ORI = BD_ORI.replace("\r", "")
            BD_ORI = BD_ORI.replace("\t", "")
            BD_ORI = BD_ORI.replace("\n", "")

            JSON_ORI = gson.fromJson(BD_ORI, PontosFeed::class.java)

        } catch (e: Exception) {
        }

        /**
         * 2° PASSO
         * *********
         * Busca arquivo no site GITHUB,
         * salva numa String,
         * cria JSON.
         */
        try {
            println("Attemp to fetch JSON PONTOS")
            val url = "https://raw.githubusercontent.com/dlfrutos/TCC/master/Repositorio/BD/BD.json"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Falha na requisição")
                }

                override fun onResponse(call: Call, response: Response) {
                    BD_NET = response.body()?.string().toString()

                    //rotina para retirar \r\n
                    BD_NET = BD_NET?.replace("\r", "")
                    BD_NET = BD_NET?.replace("\n", "")
                    BD_NET = BD_NET?.replace("\t", "")

                    //construir objeto a partir do JSON
                    println(BD_NET)
                    JSON_NET = gson.fromJson(BD_NET, PontosFeed::class.java)
                }
            })
        } catch (ex: Exception) {
        }

        /**
         * 3° PASSO
         * *********
         * Verifica os arquivos que existem e suas versões.
         */
        try {
//            var fin = FileReader("JSON_ATUAL.json")
//            var c: Int?
//            do {
//                c = fin.read()
//                BD = BD + c.toChar()
//            } while (c != -1)

//            var file = File("JSON_ATUAL.json")
//            var a = file.isFile
//            var b = file.exists()

            if (JSON_NET == null){
                //IMPLEMENTAÇÃO DA ROTINA PARA CONVERTER HORA
            }

            print("")

        } catch (ex: java.lang.Exception) {
        }


        /**
         * 4° PASSO
         * *********
         * Rotina de leituras e tualizações.
         */
        try {
            val FILENAME = "hello_file.txt"
            val string = "hello world!"
            val fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)
            fos.write(string.toByteArray())
            fos.close()

            //checando arquivos dentro da pasta
            var fo = FileWriter("teste.txt", true)
            fo.write("FALA BRO" + "\n")
            fo.close()
            Toast.makeText(this, "Arquivo salvo.", Toast.LENGTH_SHORT)
        } catch (ex: Exception) {
        }

    }
}
