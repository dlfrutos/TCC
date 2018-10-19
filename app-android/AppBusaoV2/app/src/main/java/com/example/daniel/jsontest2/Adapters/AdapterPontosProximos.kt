package com.example.daniel.jsontest2.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.Activities.LinhaPontoActivity
import com.example.daniel.jsontest2.Activities.ListaPontoMaisProximoActivity.Companion.PONTO_SELECIONADO
import com.example.daniel.jsontest2.Activities.MainActivity
import com.example.daniel.jsontest2.Modelos.Pontos
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import kotlinx.android.synthetic.main.modulo_lista_ponto.view.*

class PontosProximosAdapter(val pontosFeed: PontosFeed) : RecyclerView.Adapter<PontoProxViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PontoProxViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val celula = layoutInflater.inflate(R.layout.modulo_lista_ponto, parent, false)
        return PontoProxViewHolder(celula)
    }

    override fun getItemCount(): Int {
        return pontosFeed.pontos.count()
    }

    override fun onBindViewHolder(holder: PontoProxViewHolder, position: Int) {
        val nomePontos = pontosFeed.pontos.get(position)
        holder.view.txt_ponto_id.text = nomePontos.PontoID
        //nomePontos.Distancia = 5
        holder.view.txt_ponto_latitude.text = "Latitude: " + nomePontos.Latitude
        holder.view.txt_ponto_longitude.text = "Longitude: " + nomePontos.Longitude
        holder.view.txt_ponto_distancia.text = "Distancia :" + nomePontos.Distancia

        holder.ponto = nomePontos
    }
}

class PontoProxViewHolder(val view: View, var ponto: Pontos? = null) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener() {
            val intentAPP = Intent(view.context, LinhaPontoActivity::class.java)
            intentAPP.putExtra(PONTO_SELECIONADO, ponto?.PontoID)
            view.context.startActivity(intentAPP)
        }
    }
}