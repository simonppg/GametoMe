/*
 * Copyright (C) 2014 simonppg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gametome.personajes;

/**
 * EstadoHeroe contendra todos los estados del Heroe.
 * @author simonppg
 */
public class EstadoHeroe {
    /**
     * Lista de estados. acciones.
     */    
    private boolean dePie = true;
    private boolean incado = false;//TODO implementar
    private boolean ecalando = false;//TODO implementar
    private boolean colgado = false;//TODO implementar
    private boolean corriendo = false;
    private boolean frenando = false;//TODO implementar
    private boolean izquierda = false;
    private boolean derecha = false;
    private boolean saltando = false;
    private boolean ataqueDeEspada = false;//TODO implementar
    private boolean ataqueDeEspadaIncado = false;//TODO implementar
    private boolean ataqueDeEspadaSaltando = false;//TODO implementar
    private boolean ataqueEspecial = false;//TODO implementar
    private boolean ataqueEspecialEscalando = false;//TODO implementar
    private boolean ataqueEspecialSaltando = false;//TODO implementar
    
    public void setDePie(boolean dePie) {
        this.dePie = dePie;
    }

    public void setIncado(boolean incado) {
        this.incado = incado;
    }

    public void setEcalando(boolean ecalando) {
        this.ecalando = ecalando;
    }

    public void setColgado(boolean colgado) {
        this.colgado = colgado;
    }

    public void setCorriendo(boolean corriendo) {
        this.corriendo = corriendo;
    }

    public void setFrenando(boolean frenando) {
        this.frenando = frenando;
    }

    public void setIzquierda(boolean izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(boolean derecha) {
        this.derecha = derecha;
    }

    public void setSaltando(boolean saltando) {
        this.saltando = saltando;
    }

    public void setAtaqueDeEspada(boolean ataqueDeEspada) {
        this.ataqueDeEspada = ataqueDeEspada;
    }

    public void setAtaqueDeEspadaIncado(boolean ataqueDeEspadaIncado) {
        this.ataqueDeEspadaIncado = ataqueDeEspadaIncado;
    }

    public void setAtaqueDeEspadaSaltando(boolean ataqueDeEspadaSaltando) {
        this.ataqueDeEspadaSaltando = ataqueDeEspadaSaltando;
    }

    public void setAtaqueEspecial(boolean ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    public void setAtaqueEspecialEscalando(boolean ataqueEspecialEscalando) {
        this.ataqueEspecialEscalando = ataqueEspecialEscalando;
    }

    public void setAtaqueEspecialSaltando(boolean ataqueEspecialSaltando) {
        this.ataqueEspecialSaltando = ataqueEspecialSaltando;
    }    
    
    public boolean isDePie(){
        return this.dePie;
    }

    public boolean isIncado(){
        return this.incado;
    }

    public boolean isEcalando(){
        return this.ecalando;
    }

    public boolean isColgado(){
        return this.colgado;
    }

    public boolean isCorriendo(){
        return this.corriendo;
    }

    public boolean isFrenando(){
        return this.frenando;
    }

    public boolean isIzquierda(){
        return this.izquierda;
    }

    public boolean isDerecha(){
        return this.derecha;
    }

    public boolean isSaltando(){
        return this.saltando;
    }

    public boolean isAtaqueDeEspada(){
        return this.ataqueDeEspada;
    }

    public boolean isAtaqueDeEspadaIncado(){
        return this.ataqueDeEspadaIncado;
    }

    public boolean isAtaqueDeEspadaSaltando(){
        return this.ataqueDeEspadaSaltando;
    }

    public boolean isAtaqueEspecial(){
        return this.ataqueEspecial;
    }

    public boolean isAtaqueEspecialEscalando(){
        return this.ataqueEspecialEscalando;
    }

    public boolean isAtaqueEspecialSaltando(){
        return this.ataqueEspecialSaltando;
    }
}
