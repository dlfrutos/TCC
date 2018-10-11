/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author Daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Semana s = new Semana();
        
        s.setSemanaID(9);
        s.setSemanaNome("Especial");

        s.setSemanaID(1);
        s.setSemanaNome("Segunda");
        
        System.out.println(SemanaController.getJSONSemana(s));
    }
    
}
