package com.example.daniel.jsontest2.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.example.daniel.jsontest2.R
import com.example.daniel.jsontest2.referencia.CourseActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

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



        //checando permissão
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
            val intent3 = Intent(this, ListaLinhaActivity::class.java)
            startActivity(intent3)
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
                btn_BuscaPontoMaisProximo.isEnabled =true
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
