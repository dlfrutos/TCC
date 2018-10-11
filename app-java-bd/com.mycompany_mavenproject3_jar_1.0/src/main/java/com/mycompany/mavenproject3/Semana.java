/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject3;

import java.util.Objects;

/**
 *
 * @author Daniel
 */
public class Semana {
    private int DiaID;
    private String DiaNome;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.DiaID;
        hash = 89 * hash + Objects.hashCode(this.DiaNome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Semana other = (Semana) obj;
        if (this.DiaID != other.DiaID) {
            return false;
        }
        if (!Objects.equals(this.DiaNome, other.DiaNome)) {
            return false;
        }
        return true;
    }

    public int getDiaID() {
        return DiaID;
    }

    public void setDiaID(int DiaID) {
        this.DiaID = DiaID;
    }

    public String getDiaNome() {
        return DiaNome;
    }

    public void setDiaNome(String DiaNome) {
        this.DiaNome = DiaNome;
    }

}
