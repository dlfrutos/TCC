package com.example.daniel.jsontest2.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.Modelos.Pontos
import com.example.daniel.jsontest2.Modelos.PontosFeed

class AdapterLinhaHoraPonto(val pontosFeed: PontosFeed) : RecyclerView.Adapter<LHPViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LHPViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: LHPViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class LHPViewHolder (val view: View, var ponto: Pontos? = null) : RecyclerView.ViewHolder(view){

}