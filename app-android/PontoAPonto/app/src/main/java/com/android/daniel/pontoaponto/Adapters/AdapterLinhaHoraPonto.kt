package com.android.daniel.pontoaponto.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.daniel.pontoaponto.Modelos.Pontos
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.android.daniel.pontoaponto.referencia.CourseLessonActivity
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
        holder.itemView.txt_mlhp_ponto_prevChegada.text = hlp.horaCalc
    }

}

class LHPViewHolder(val view: View, var ponto: Pontos? = null) : RecyclerView.ViewHolder(view) {

    companion object {
        val COURSE_LESSON_LINK = "COURSE_LESSON_LINK"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, CourseLessonActivity::class.java)
            view.context.startActivity(intent)
        }

    }
}