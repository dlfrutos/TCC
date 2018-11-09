package com.android.daniel.pontoaponto.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.daniel.pontoaponto.Modelos.Pontos
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import kotlinx.android.synthetic.main.modulo_lista_ponto.view.*

class PontosAdapter(val pontosFeed: PontosFeed) : RecyclerView.Adapter<PontoViewHolder>() {

    //val nomePontos = listOf<String>("Ponto 1", "Ponto 2", "Ponto 3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PontoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val celula = layoutInflater.inflate(R.layout.modulo_lista_ponto, parent, false)
        return PontoViewHolder(celula)
    }

    override fun getItemCount(): Int {
        return pontosFeed.pontos.count()
    }

    override fun onBindViewHolder(holder: PontoViewHolder, position: Int) {
//        val nomePontos = nomePontos.get(position)
        val nomePontos = pontosFeed.pontos.get(position)
        holder.view.txt_ponto_id.text = nomePontos.PontoID
        holder.view.txt_ponto_latitude.text = "Latitude: "+nomePontos.Latitude
        //holder.view.txt_ponto_longitude.text = "Longitude: "+nomePontos.Longitude
    }
}

class PontoViewHolder(val view: View, var ponto: Pontos? = null) : RecyclerView.ViewHolder(view) {
//    companion object {
//        val PONTO_ID = "PONTO_ID"
//        val PONTO_LATITUDE = "PONTO_LATITUDE"
//        val PONTO_LONGITUDE ="PONTO_LONGITUDE"
//    }
//
//    init {
//        view.setOnClickListener(){
//
//        }
//    }

}