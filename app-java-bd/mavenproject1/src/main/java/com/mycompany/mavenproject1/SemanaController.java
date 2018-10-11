/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.google.gson.Gson;

/**
 *
 * @author Daniel
 */
public class SemanaController {

    private static Gson SERIALIZADOR;

    public static final String getJSONSemana(Semana s) {

        SERIALIZADOR = new Gson();

        return SERIALIZADOR.toJson(s);
    }

}
