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
    public static final int DE_PIE = 0x1;
    public static final int INCADO = 0x2;//TODO implementar
    public static final int ESCALANDO = 0x4;//TODO implementar
    public static final int COLGADO = 0x8;//TODO implementar
    public static final int CORRIENDO = 0x10;
    public static final int IZQUIERDA = 0x20;
    public static final int DERECHA = 0x40;
    public static final int SALTANDO = 0x80;
    public static final int ATAQUE_DE_ESPADA = 0x100;//TODO implementar
    public static final int ATAQUE_DE_ESPADA_INCADO = 0x200;//TODO implementar
    public static final int ATAQUE_DE_ESPADA_SALTANDO = 0x400;//TODO implementar
    public static final int ATAQUE_ESPECIAL = 0x800;//TODO implementar
    public static final int ATAQUE_ESPECIAL_ESCALANDO = 0x1000;//TODO implementar
    public static final int ATAQUE_ESPECIAL_SALTANDO = 0x2000;//TODO implementar
    //Limite 0x80000000

    /**
     * Estado del Heroe. variable de 32 bits (4 Bytes) para almacenar 32 estados al mismo tiempo
     */
    public int estadoHeroe;
    
    boolean isDe_Pie() {
        return (estadoHeroe & DE_PIE) == DE_PIE;
    }
    
    boolean isCorriendo() {
        return (estadoHeroe & CORRIENDO) == CORRIENDO;
    }
    
    boolean isSaltando() {
        return (estadoHeroe & SALTANDO) == SALTANDO;
    }

    boolean isIzquierda() {
        return (estadoHeroe & IZQUIERDA) == IZQUIERDA;
    }
    
    boolean isDerecha() {
        return (estadoHeroe & DERECHA) == DERECHA;
    }
    
    boolean isAtaqueDeEspada() {
        return (estadoHeroe & ATAQUE_DE_ESPADA) == ATAQUE_DE_ESPADA;
    }

    //Activa el bit correspondiente al estado que llega, Quieto pone todos los estados en 0
    public void setEstadoHeroe(int estadoHeroe) {
        //Al utilizar esta funcion revisar si el estado nuevo tiene conflicto con otro estado viejo, no olvidar quitar el viejo
        //por ejemplo si esta INCADO y ponemos ESCALANDO, debemos quitar el estado INCADO
        if((estadoHeroe & DE_PIE) == DE_PIE)//DE_PIE no debe borrar IZQUIERDA y DERECHA
        {
            this.estadoHeroe &= IZQUIERDA + DERECHA;
            this.estadoHeroe |= DE_PIE;
        }
        else if((estadoHeroe & SALTANDO) == SALTANDO)//SALTANDO no debe borrar IZQUIERDA y DERECHA
        {
            this.estadoHeroe &= IZQUIERDA + DERECHA;
            this.estadoHeroe |= SALTANDO;
        }
        else if((estadoHeroe & CORRIENDO) == CORRIENDO)//CORRIENDO no debe borrar IZQUIERDA y DERECHA ni SALTANDO
        {
            this.estadoHeroe &= IZQUIERDA + DERECHA + SALTANDO;
            this.estadoHeroe |= CORRIENDO;
        }
        else if(estadoHeroe== IZQUIERDA || estadoHeroe == DERECHA){
            this.estadoHeroe &= DE_PIE + INCADO + ESCALANDO + COLGADO + CORRIENDO + SALTANDO;
            this.estadoHeroe |= estadoHeroe;
        }
        else if((estadoHeroe & ATAQUE_DE_ESPADA) == ATAQUE_DE_ESPADA)//ATAQUE_DE_ESPADA no debe borrar IZQUIERDA y DERECHA ni CORRIENDO
        {
            System.out.println("Esr: " + estadoHeroe);
            this.estadoHeroe &= IZQUIERDA + DERECHA + DE_PIE + INCADO + ESCALANDO + COLGADO + CORRIENDO + SALTANDO;
            this.estadoHeroe |= ATAQUE_DE_ESPADA;
            System.out.println("Esto r: " + estadoHeroe);
        }
        else{
            this.estadoHeroe |= estadoHeroe;
            System.out.println("Esto no debe ocurrir: " + estadoHeroe);
        }
    }
    
    public int getEstadoHeroe() {
        return estadoHeroe;
    }
}
