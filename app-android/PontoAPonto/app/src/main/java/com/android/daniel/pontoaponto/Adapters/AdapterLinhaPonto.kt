package com.android.daniel.pontoaponto.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.daniel.pontoaponto.Activities.LinhaPontoActivity.Companion.PONTO_VALOR
import com.android.daniel.pontoaponto.Activities.LinhaPontoHoraActivity
import com.android.daniel.pontoaponto.Modelos.Linhas
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
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
        val linhas = pontosFeed.linhas.get(position)
        holder.itemView.txt_linha_ponto_id.text = linhas.LinhaID
        holder.itemView.txt_linha_ponto_roteiroInicio.text = "Partida: "+linhas.RoteiroInicio
        holder.itemView.txt_linha_ponto_roteiroFim.text = "Bairros: "+linhas.RoteiroFim

        holder.linhas=linhas

    }
}

class LinhaPontoViewHolder(val view: View, var linhas: Linhas? = null) : RecyclerView.ViewHolder(view) {
    companion object {
        val LINHA_SELECIONADA = "Linha_Selecionada"
        val PONTO_SELECIONADO2 = "Ponto_Selecionado2"
    }

    init {
        view.setOnClickListener() {
            val intent = Intent(view.context, LinhaPontoHoraActivity::class.java)
            intent.putExtra(LINHA_SELECIONADA, linhas?.LinhaID)
            intent.putExtra(PONTO_SELECIONADO2,  PONTO_VALOR)
            view.context.startActivity(intent)
        }
    }


}
