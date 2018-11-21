package com.android.daniel.pontoaponto.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.daniel.pontoaponto.R
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_pdfreader.*
import kotlinx.android.synthetic.main.activity_pdfreader.view.*

class PDFReader : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfreader)

        val pdfView = pdfView
        pdfView.fromAsset("Tutorial.pdf").load()

    }
}
