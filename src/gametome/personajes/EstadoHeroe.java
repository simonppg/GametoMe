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
    public static final int INCADO = 0x1;//TODO implementar
    public static final int ESCALANDO = 0x2;//TODO implementar
    public static final int COLGADO = 0x4;//TODO implementar
    public static final int CORRIENDO_IZQUIERDA = 0x8;
    public static final int CORRIENDO_DERECHA = 0x10;
    public static final int SALTANDO = 0x20;
    public static final int ATAQUE_DE_ESPADA = 0x40;//TODO implementar
    public static final int ATAQUE_DE_ESPADA_INCADO = 0x80;//TODO implementar
    public static final int ATAQUE_DE_ESPADA_SALTANDO = 0x100;//TODO implementar
    public static final int ATAQUE_ESPECIAL = 0x200;//TODO implementar
    public static final int ATAQUE_ESPECIAL_ESCALANDO = 0x400;//TODO implementar
    public static final int ATAQUE_ESPECIAL_SALTANDO = 0x800;//TODO implementar

    /**
     * Estado del Heroe. variable de 32 bits (4 Bytes) para almacenar 32 estados al mismo tiempo
     */
    public int estadoHeroe;
    
    boolean isCorriendo() {
        return (estadoHeroe & CORRIENDO_IZQUIERDA) == CORRIENDO_IZQUIERDA
                ||
                (estadoHeroe & CORRIENDO_DERECHA) == CORRIENDO_DERECHA;
    }
    
    boolean isSaltando() {
        return (estadoHeroe & SALTANDO) == SALTANDO;
    }

    boolean isCorriendoIzquierda() {
        return (estadoHeroe & CORRIENDO_IZQUIERDA) == CORRIENDO_IZQUIERDA;
    }
    
    boolean isCorriendoDerecha() {
        return (estadoHeroe & CORRIENDO_DERECHA) == CORRIENDO_DERECHA;
    }

    //Activa el bit correspondiente al estado que llega, Quieto pone todos los estados en 0
    public void setEstadoHeroe(int estadoHeroe) {
        //Al utilizar esta funcion revisar si el estado nuevo tiene conflicto con otro estado viejo, no olvidar quitar el viejo
        //por ejemplo si esta INCADO y ponemos ESCALANDO, debemos quitar el estado INCADO
        if(estadoHeroe == QUIETO)
        {
             this.estadoHeroe = 0;
        }
        else if((estadoHeroe== CORRIENDO_IZQUIERDA || estadoHeroe == CORRIENDO_DERECHA) && this.estadoHeroe == SALTANDO){
            this.estadoHeroe |= estadoHeroe | SALTANDO;
        }
        else
            this.estadoHeroe |= estadoHeroe;
    }
    
    public int getEstadoHeroe() {
        return estadoHeroe;
    }
    
}
