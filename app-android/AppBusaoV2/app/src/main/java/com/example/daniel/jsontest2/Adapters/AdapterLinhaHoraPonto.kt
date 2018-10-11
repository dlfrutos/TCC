package com.example.daniel.jsontest2.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.daniel.jsontest2.Modelos.Pontos
import com.example.daniel.jsontest2.Modelos.PontosFeed

class AdapterLinhaHoraPonto(val pontosFeed: PontosFeed) : RecyclerView.Adapter<LHPviewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LHPviewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: LHPviewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class LHPviewHolder (val view: View, var ponto: Pontos? = null) : RecyclerView.ViewHolder(view){

}