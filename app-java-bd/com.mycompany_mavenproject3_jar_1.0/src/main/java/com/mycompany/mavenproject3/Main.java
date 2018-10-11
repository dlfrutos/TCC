/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //ALTERA UMA CLASSE
        List<Semana> listaSemana = new ArrayList<Semana>();
        Semana s1 = new Semana();
        Semana s2 = new Semana();
        s1.setDiaID(1);
        s1.setDiaNome("Segunda");
        listaSemana.add(s1);

        String link = "https://github.com/dlfrutos/TCC/blob/master/app-java-bd/mavenproject2/JsonFile.json";
        File out = new File("JsonFileTeste.json");

        
        new Thread(new Downloader(link, out)).start();
        
        
        s2.setDiaID(2);
        s2.setDiaNome("Terça");
        listaSemana.add(s2);

        //TRANSFORMA O A CLASSE PARA UM JSON
        Type type = new TypeToken<List<Semana>>() {
        }.getType();

        Gson gson = new Gson();
        //Imprime o JSON gerado a título de controle
        //System.out.println(gson.toJson(listaSemana, type));
        //String retornoSemana = (gson.toJson(listaSemana, type));

        //************************************************
        //AQUI
        //INSERIR A LEITURA DO ARQUIVO JSON QUE TEMOS
        //************************************************
        //System.out.println(retornoSemana);
        BufferedReader br = null;
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();
        String retornoSemana = null;

        try {

            fr = new FileReader("JsonFile.json");
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
                sb.append(System.lineSeparator());
                //System.out.println(sCurrentLine);
            }

            retornoSemana = sb.toString();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        //System.out.println(retornoSemana);
        //LÊ UM JSON E CARREGA NA LISTA DE UMA CLASE
        List<Semana> listaRetornoSemana = gson.fromJson(retornoSemana, type);

        for (Semana semanaRetorno : listaRetornoSemana) {
            System.out.print(" " + semanaRetorno.getDiaID());
            System.out.println(" " + semanaRetorno.getDiaNome());
        }
    }
}
