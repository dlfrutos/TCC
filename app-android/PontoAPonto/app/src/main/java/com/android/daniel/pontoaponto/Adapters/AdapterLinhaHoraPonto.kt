package com.android.daniel.pontoaponto.Adapters

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.daniel.pontoaponto.Activities.LinhaPontoActivity
import com.android.daniel.pontoaponto.Activities.LinhaPontoHoraActivity
import com.android.daniel.pontoaponto.Modelos.Pontos
import com.android.daniel.pontoaponto.Modelos.PontosFeed
import com.android.daniel.pontoaponto.R
import com.android.daniel.pontoaponto.referencia.CourseLessonActivity
import kotlinx.android.synthetic.main.modulo_linha_hora_ponto.view.*
import java.util.*

class AdapterLinhaHoraPonto(val pontosFeed: PontosFeed) : RecyclerView.Adapter<LHPViewHolder>() {

    var token = true
    var item = 0

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
        //holder.itemView.txt_mlhp_ponto_end.text = "Endereço ponto, numero"
        holder.itemView.txt_mlhp_ponto_prevChegada.text = hlp.horaCalc
        holder.itemView.textView28.text = "Sentido >> " + hlp.SentidoLinha

        val horaAtual = Calendar.getInstance()

        val pontoHora = hlp.horaCalc.substring(6, 8).toInt()
        val pontoMinuto = hlp.horaCalc.substring(9, 11).toInt()
        val agoraHora = horaAtual.get(Calendar.HOUR_OF_DAY)
        val agoraMinuto = horaAtual.get(Calendar.MINUTE)

        //lógica para pintar cartas de pontas já passados
        if (pontoHora == agoraHora) {
            //SIM
            if (pontoMinuto > agoraMinuto) {
                //SIM
                holder.itemView.textView29.setBackgroundColor(Color.parseColor("#00000000"))
            } else {
                holder.itemView.textView29.setBackgroundColor(Color.parseColor("#78000000"))
//                if (token) {
//                    item = position
//                    token = false
//                }
            }
        } else if (pontoHora > agoraHora) {
            holder.itemView.textView29.setBackgroundColor(Color.parseColor("#00000000"))
        } else {
            holder.itemView.textView29.setBackgroundColor(Color.parseColor("#78000000"))
//            if (token) {
//                item = position
//                token = false
//            }
        }


        if (LinhaPontoActivity.PONTO_VALOR.equals(hlp.PontoID)) {
            LinhaPontoHoraActivity.pos = position
            //holder.itemView.cardView10.setBackgroundColor(Color.parseColor("#84FFC107"))
            //holder.itemView.cardView10.setBackgroundColor(Color.YELLOW)
        }
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