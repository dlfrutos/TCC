package com.android.daniel.pontoaponto.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.daniel.pontoaponto.Activities.LinhaPontoActivity
import com.android.daniel.pontoaponto.Activities.ListaPontoMaisProximoActivity.Companion.PONTO_SELECIONADO
import com.android.daniel.pontoaponto.Modelos.Pontos
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
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
            holder.view.txt_ponto_latitude.text = "Rua: " + nomePontos.Rua
//        holder.view.txt_ponto_longitude.text = "Longitude: " + nomePontos.Longitude
//        holder.view.txt_ponto_distancia.text = "Distancia :" + nomePontos.Distancia
        holder.view.textView21.text = "" + nomePontos.Distancia+"m"

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