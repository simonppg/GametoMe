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
    public static final int QUIETO = 0x0;
    public static final int INCADO = 0x1;
    public static final int ESCALANDO = 0x2;
    public static final int COLGADO = 0x4;
    public static final int CORRIENDO = 0x8;
    public static final int SALTANDO = 0x10;
    public static final int ATAQUE_DE_ESPADA = 0x20;
    public static final int ATAQUE_DE_ESPADA_INCADO = 0x40;
    public static final int ATAQUE_DE_ESPADA_SALTANDO = 0x80;
    public static final int ATAQUE_ESPECIAL = 0x100;
    public static final int ATAQUE_ESPECIAL_ESCALANDO = 0x200;
    public static final int ATAQUE_ESPECIAL_SALTANDO = 0x400;
    public static final int IZQUIERDA = 0x800;
    public static final int DERECHA = 0x1000;
    /**
     * Estado del Heroe. variable de 32 bits (4 Bytes) para almacenar 32 estados al mismo tiempo
     */
    public int estadoHeroe;
    boolean isIzquierda;

    /*boolean isSaltando() {
        if((estadoHeroe & SALTANDO) == SALTANDO)
            return true;
        else
            return false;
    }

    boolean isCorriendo() {
        if((estadoHeroe & CORRIENDO) == CORRIENDO)
            return true;
        else
            return false;
    }
    
    boolean isIzquierda() {
        if((estadoHeroe & IZQUIERDA) == IZQUIERDA)
            return true;
        else
            return false;
    }
    
    boolean isDerecha() {
        if((estadoHeroe & DERECHA) == DERECHA)
            return true;
        else
            return false;
    }*/
    
    boolean isSaltando() {
        return (estadoHeroe & SALTANDO) == SALTANDO;
    }

    boolean isCorriendo() {
        return (estadoHeroe & CORRIENDO) == CORRIENDO;
    }
    
    boolean isIzquierda() {
        return (estadoHeroe & IZQUIERDA) == IZQUIERDA;
    }
    
    boolean isDerecha() {
        return (estadoHeroe & DERECHA) == DERECHA;
    }

    //Activa el bit correspondiente al estado que llega, Quieto pone todos los estados en 0
    public void setEstadoHeroe(int estadoHeroe) {
        //Al utilizar esta funcion revisar si el estado nuevo tiene conflicto con otro estado viejo, no olvidar quitar el viejo
        //por ejemplo si esta INCADO y ponemos ESCALANDO, debemos quitar el estado INCADO
        if(estadoHeroe == QUIETO)
        {
             this.estadoHeroe = 0;
        }
        else if(estadoHeroe == CORRIENDO && this.estadoHeroe == SALTANDO){
            this.estadoHeroe |= estadoHeroe | SALTANDO;
        }
        else
            this.estadoHeroe |= estadoHeroe;
    }
    
    public int getEstadoHeroe() {
        return estadoHeroe;
    }
    
}
