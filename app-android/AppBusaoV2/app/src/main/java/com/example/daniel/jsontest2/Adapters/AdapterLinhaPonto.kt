package com.example.daniel.jsontest2.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.Activities.LinhaPontoActivity
import com.example.daniel.jsontest2.Activities.LinhaPontoHoraActivity
import com.example.daniel.jsontest2.Modelos.Linhas
import com.example.daniel.jsontest2.Modelos.Pontos
import com.example.daniel.jsontest2.Modelos.PontosFeed
import com.example.daniel.jsontest2.R
import kotlinx.android.synthetic.main.modulo_linha_ponto.view.*

class AdapterLinhaPonto(val pontosFeed: PontosFeed) : RecyclerView.Adapter<LinhaPontoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaPontoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val celula = layoutInflater.inflate(R.layout.modulo_linha_ponto, parent, false)
        return LinhaPontoViewHolder(celula)
    }

    override fun getItemCount(): Int {
        return pontosFeed.linhas.count()
    }


    override fun onBindViewHolder(holder: LinhaPontoViewHolder, position: Int) {
        val nomeLinhas = pontosFeed.linhas.get(position)
        holder.view.txt_linha_ponto_id.text = nomeLinhas.LinhaID
    }


}

class LinhaPontoViewHolder(val view: View, var linhas: Linhas? = null) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener() {
            val intent = Intent(view.context, LinhaPontoHoraActivity::class.java)
            intent.putExtra(LinhaPontoActivity.LINHA_SELECIONADA, linhas?.LinhaID)
            view.context.startActivity(intent)
        }
    }


}
