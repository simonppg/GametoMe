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

import gametome.FlujoDelJuego;
import gametome.Panel;
import gametome.personajes.Estados.Ataque;
import gametome.personajes.Estados.Direccion;
import gametome.personajes.Estados.Estado;
import gametome.personajes.Estados.Otro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Heroe controla los movimientos y el dibujado del personaje.
 * @author simonppg
 */
public class Heroe {
    //TODO faltan los topes de velocidad.
    
    /**
     * coordenada X del heroe.
     */
    public static int x;
    /**
     * coordenada Y del heroe.
     */
    public static int y;
    /**
     * Velocidad en X.
     */
    private int velocidadX;
    /**
     * Velocidad en Y.
     */
    private int velocidadY;
    /**
     * Velocidad con la que acelera el salto.
     */
    private int velocidadAceleracionY;
    /**
     * Velocidad con la que se detiene el salto.
     */
    private int velocidadDetenerY;
    /**
     * Velocidad con la que acelera en x.
     */
    private int velocidadAceleracionX;
    /**
     * Velocidad con la que se detiene en x.
     */
    private int velocidadDetenerX;
    /**
     * Tope de velocidad.
     */
    private int velocidadMax;
    /**
     * Imagen del heroe.
     */
    private BufferedImage heroeImg;
    /**
     * Estado del Heroe. actual.
     */
    public Estado estadoHeroe;
    public Direccion direccionHeroe;
    public Otro saltoHeroe;
    public Ataque ataqueHeroe;
    /**
     * Lista de imagenes. acciones.
     */
    private ArrayList<Imagen> img;
    private int tiempoAnimacion = 300;
    private long startTime;
    /**
     * Tiempo inicial para la animacion de salto
     */
    private long startTimeSalto;
    
    /**
     * alarma. mide el tiempo.
     */

    public Heroe() {
        Initialize();
        LoadContent();
        
        x = 400;
        y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
    }

    private void Initialize() {
        //TODO Inicializar variables.
        img = new ArrayList<Imagen>();
        estadoHeroe = Estado.dePie;
        direccionHeroe = Direccion.Derecha;
        saltoHeroe = Otro.nada;
        ataqueHeroe = Ataque.nada;
        velocidadAceleracionY = 5;
        velocidadDetenerY = 10;
        //Deacuerdo a la logica de desplazamiento estas variables deben ser iguales
        velocidadAceleracionX = velocidadDetenerX = 1;
        velocidadMax = 12;
    }

    private void LoadContent() {
        //TODO Cargar imagenes
        try
        {
            for (int i = 1; i <= 29; i++) {
                URL heroeImgUrl = this.getClass().getResource("/resources/images/heroe/ryu"+i+".png");
                Imagen localImg = new Imagen();
                localImg.setImagen(ImageIO.read(heroeImgUrl));
                img.add(localImg);                
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Heroe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int cont=0;
    public void Update(){
        //TODO Logica del heroe
        //TODO verificar colicion con otros objetos
        //Calcula la velocidad de movimiento o frenado a la izquierda
        if(Panel.keyboardKeyState(KeyEvent.VK_A)){
            if(estadoHeroe != Estado.incado){
                velocidadX -= velocidadAceleracionX;
                if(velocidadX < velocidadMax*-1)
                    velocidadX = velocidadMax*-1;
                estadoHeroe = Estado.corriendo;
            }
            direccionHeroe = Direccion.Izquierda;
        }
        else
        {
            if(velocidadX < 0)
                velocidadX += velocidadDetenerX;
            else
                if(velocidadX == 0 && estadoHeroe != Estado.incado){
                    estadoHeroe  = Estado.dePie;
                }
        }
        //TODO verificar colicion con otros objetos
        //Calcula la velocidad de movimiento o frenado a la derecha
        if(Panel.keyboardKeyState(KeyEvent.VK_D)){
            if(estadoHeroe != Estado.incado){
                velocidadX += velocidadAceleracionX;
                if(velocidadX > velocidadMax)
                    velocidadX = velocidadMax;
                estadoHeroe = Estado.corriendo;
            }
            direccionHeroe = Direccion.Derecha;
        }
        else
        {
            if(velocidadX > 0)
                velocidadX -= velocidadDetenerX;
            else
                if(velocidadX == 0  && estadoHeroe != Estado.incado){
                    estadoHeroe  = Estado.dePie;
                }
        }
        
        if(Panel.keyboardKeyState(KeyEvent.VK_NUMPAD2))
        {
            if(estadoHeroe != Estado.incado && saltoHeroe != Otro.saltando){
                saltoHeroe = Otro.saltando;
                startTimeSalto = System.currentTimeMillis();
            }
        }
        
        if(saltoHeroe == Otro.saltando){
            //duracion de la animacion de salto
            long diff = (System.currentTimeMillis()-startTimeSalto)/100;//el 100 es para hacer la animacion visible, para ir mas lento
            velocidadY = (int) (-1*(30 -9.81 * diff));
            y += velocidadY;
            if(y > (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto()) {
                velocidadY = 0;
                y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
                saltoHeroe = Otro.nada;
            }
        }
        
        if(Panel.keyboardKeyState(KeyEvent.VK_S))
        {
            if(estadoHeroe == Estado.dePie && saltoHeroe != Otro.saltando){
                estadoHeroe = Estado.incado;
                //Hacer que toque el suelo
                y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(6).getImgAlto();
            }
        }
        else{
            if(estadoHeroe == Estado.incado){
                estadoHeroe = Estado.dePie;
                y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
            }
        }
        
        //Boton de Ataque
        if(Panel.keyboardKeyState(KeyEvent.VK_NUMPAD1))
        {
            if(ataqueHeroe != Ataque.espada)
            {
                ataqueHeroe = Ataque.espada;
                startTime = System.currentTimeMillis();
                //System.out.println("Inicio animacion");
                Alarma a = new Alarma(tiempoAnimacion, new Callable() {
                    @Override
                    public Object call() throws Exception {
                        //System.out.println("Fin animacion");
                        ataqueHeroe = Ataque.nada;
                        return null;
                    }
                });
            }
        }
        
        // Mueve al heroe
        x += velocidadX;
        y += velocidadY;
    }
    
    int salto = 0;
    int corre = 0;
    int ataque = 0;
    public void Draw(Graphics2D g2d)
    {
        //TODO Dibujar al personaje
        if(saltoHeroe == Otro.saltando){
            if(ataqueHeroe == Ataque.espada)
            {
                if(direccionHeroe == Direccion.Derecha){
                    long currenTime = System.currentTimeMillis() - startTime;
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(14).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(15).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(16).getImagen(), x, y, null);
                }
                if(direccionHeroe == Direccion.Izquierda){
                    long currenTime = System.currentTimeMillis() - startTime;
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(14).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(15).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(16).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
            else{
                switch(salto){
                    case 0:
                        g2d.drawImage(img.get(10).getImagen(), x, y, null);
                        salto++;
                        break;
                    case 1:
                        g2d.drawImage(img.get(11).getImagen(), x, y, null);
                        salto++;
                        break;
                    case 2:
                        g2d.drawImage(img.get(12).getImagen(), x, y, null);
                        salto++;
                        break;
                    case 3:
                        g2d.drawImage(img.get(13).getImagen(), x, y, null);
                        salto=0;
                        break;
                }
            }
            
        }
        //
        if((ataqueHeroe == Ataque.espada ||
                estadoHeroe == Estado.corriendo ||
                estadoHeroe == Estado.dePie) && saltoHeroe != Otro.saltando && estadoHeroe != Estado.incado)
        {
            //Ataque de espada
            if(ataqueHeroe == Ataque.espada)
            {
                if(direccionHeroe == Direccion.Derecha){
                    long currenTime = System.currentTimeMillis() - startTime;
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(1).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(2).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(3).getImagen(), x, y, null);
                }
                if(direccionHeroe == Direccion.Izquierda){
                    long currenTime = System.currentTimeMillis() - startTime;
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(1).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(2).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(3).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
            //Corriendo y Frenando derecha
            else if(estadoHeroe == Estado.corriendo){
                if(direccionHeroe == Direccion.Derecha)
                {
                    switch(corre){
                        case 0:
                            g2d.drawImage(img.get(26).getImagen(), x, y, null);
                            corre++;
                            break;
                        case 1:
                            g2d.drawImage(img.get(27).getImagen(), x, y, null);
                            corre++;
                            break;
                        case 2:
                            g2d.drawImage(img.get(28).getImagen(), x, y, null);
                            corre=0;
                            break;
                    }
                }
                if(direccionHeroe == Direccion.Izquierda)
                {
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    switch(corre){
                        case 0:
                            g2d.drawImage(img.get(26).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                            corre++;
                            break;
                        case 1:
                            g2d.drawImage(img.get(27).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                            corre++;
                            break;
                        case 2:
                            g2d.drawImage(img.get(28).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                            corre=0;
                            break;
                    }
                }
            }
            else if(estadoHeroe == Estado.dePie)
            {
                if(direccionHeroe  == Direccion.Derecha){
                    g2d.drawImage(img.get(0).getImagen(), x, y, null);
                }
                if(direccionHeroe == Direccion.Izquierda){
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    g2d.drawImage(img.get(0).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
        }
        if(estadoHeroe == Estado.incado){
            if(ataqueHeroe == Ataque.espada){
                if(direccionHeroe == Direccion.Derecha){
                    long currenTime = System.currentTimeMillis() - startTime;
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(7).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(8).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(9).getImagen(), x, y, null);
                }
                if(direccionHeroe == Direccion.Izquierda){
                    long currenTime = System.currentTimeMillis() - startTime;
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(7).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(8).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(9).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
            else{
                if(direccionHeroe  == Direccion.Derecha){
                    g2d.drawImage(img.get(6).getImagen(), x, y, null);
                }
                if(direccionHeroe == Direccion.Izquierda){
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    g2d.drawImage(img.get(6).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
        }
        
        g2d.setTransform(AffineTransform.getScaleInstance(1, 1));
        g2d.setColor(Color.white);
        g2d.drawString("Heroe coordinates: " + x + " : " + y, 5, 15);        
        g2d.drawString("Estado: " + estadoHeroe, 5, 45);
        g2d.drawString("Direccion: " + direccionHeroe, 5, 60);
        g2d.drawString("Salto: " + saltoHeroe, 5, 75);
        g2d.drawString("Ataque: " + ataqueHeroe, 5, 90);
        g2d.setColor(Color.red);
        g2d.drawOval(x-2, y-2, 4, 4);
    }
}
