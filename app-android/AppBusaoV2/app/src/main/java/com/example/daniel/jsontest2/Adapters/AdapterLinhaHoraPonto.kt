package com.example.daniel.jsontest2.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.Modelos.Pontos
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import kotlinx.android.synthetic.main.modulo_linha_hora_ponto.view.*

class AdapterLinhaHoraPonto(val pontosFeed: PontosFeed) : RecyclerView.Adapter<LHPViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LHPViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val celula = layoutInflater.inflate(R.layout.modulo_linha_hora_ponto, parent, false)
        return LHPViewHolder(celula)
    }

    override fun getItemCount(): Int {
        return pontosFeed.horaLinhaPontos.count()
    }

    override fun onBindViewHolder(holder: LHPViewHolder, position: Int) {
        val hlp = pontosFeed.horaLinhaPontos.get(position)

        //implementar rotina para pegar informação do ponto

        //**

        holder.itemView.txt_mlhp_pontoID.text = hlp.PontoID
        holder.itemView.txt_mlhp_ponto_end.text = "Endereço ponto, numero"
        holder.itemView.txt_mlhp_ponto_prevChegada.text = "5 minutos"
    }

}

class LHPViewHolder(val view: View, var ponto: Pontos? = null) : RecyclerView.ViewHolder(view) {

}