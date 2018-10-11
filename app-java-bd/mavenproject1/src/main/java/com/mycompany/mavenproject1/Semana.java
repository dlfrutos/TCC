/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.util.Objects;

/**
 *
 * @author Daniel
 */
public class Semana {
    private int SemanaID;
    private String SemanaNome;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.SemanaID;
        hash = 89 * hash + Objects.hashCode(this.SemanaNome);
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
        if (this.SemanaID != other.SemanaID) {
            return false;
        }
        if (!Objects.equals(this.SemanaNome, other.SemanaNome)) {
            return false;
        }
        return true;
    }

    public int getSemanaID() {
        return SemanaID;
    }

    public void setSemanaID(int SemanaID) {
        this.SemanaID = SemanaID;
    }

    public String getSemanaNome() {
        return SemanaNome;
    }

    public void setSemanaNome(String SemanaNome) {
        this.SemanaNome = SemanaNome;
    }

}
