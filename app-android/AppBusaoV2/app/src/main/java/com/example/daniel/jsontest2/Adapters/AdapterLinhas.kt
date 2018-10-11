package com.example.daniel.jsontest2.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.Modelos.Linhas
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import kotlinx.android.synthetic.main.modulo_lista_linha.view.*

class AdapterLinhas(val pontosFeed: PontosFeed) : RecyclerView.Adapter<LinhaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val celula = layoutInflater.inflate(R.layout.modulo_lista_linha, parent, false)
        return LinhaViewHolder(celula)
    }

    override fun getItemCount(): Int {
       return pontosFeed.linhas.count()
    }

    override fun onBindViewHolder(holder: LinhaViewHolder, position: Int) {
        val nomeLinhas = pontosFeed.linhas.get(position)
        holder.view.txt_linha_id.text = nomeLinhas.LinhaID
        holder.view.txt_linha_roteiroInicio.text = nomeLinhas.RoteiroInicio
        holder.view.txt_linha_roteiroFim.text = nomeLinhas.RoteiroFim
    }
}

class LinhaViewHolder(val view: View, var linhas: Linhas? = null) : RecyclerView.ViewHolder(view) {
}