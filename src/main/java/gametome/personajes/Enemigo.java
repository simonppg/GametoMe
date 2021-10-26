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
public class Enemigo {
    //TODO faltan los topes de velocidad.
    
    /**
     * coordenada X del heroe.
     */
    public int x;
    /**
     * coordenada Y del heroe.
     */
    public int y;
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
    public Estado estadoEnemigo;
    public Direccion direccionEnemigo;
    public Otro saltoEnemigo;
    public Ataque ataqueEnemigo;
    /**
     * Lista de imagenes. acciones.
     */
    private ArrayList<Imagen> img;
    private int tiempoAnimacion = 300;
    private long startTime;
    
    /**
     * alarma. mide el tiempo.
     */

    public Enemigo() {
        Initialize();
        LoadContent();
        
        x = 400;
        y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
    }

    private void Initialize() {
        //TODO Inicializar variables.
        img = new ArrayList<Imagen>();
        estadoEnemigo = Estado.dePie;
        direccionEnemigo = Direccion.Derecha;
        saltoEnemigo = Otro.nada;
        ataqueEnemigo = Ataque.nada;
        velocidadAceleracionY = 5;
        velocidadDetenerY = 10;
        //Deacuerdo a la logica de desplazamiento estas variables deben ser iguales
        velocidadAceleracionX = velocidadDetenerX = 1;
        velocidadMax = 10;
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
        if(x > Heroe.x){
            estadoEnemigo = Estado.corriendo;
            direccionEnemigo = Direccion.Izquierda;
            velocidadX--;
            if(velocidadX < velocidadMax*-1)
                velocidadX = velocidadMax*-1;
        }
        else if(x< Heroe.x){
            estadoEnemigo = Estado.corriendo;
            direccionEnemigo = Direccion.Derecha;
            velocidadX++;
            if(velocidadX > velocidadMax)
                velocidadX = velocidadMax;
        }
        
        if((x > Heroe.x?x-Heroe.x:Heroe.x-x) < 100)
        {
            if(ataqueEnemigo != Ataque.espada)
            {
                ataqueEnemigo = Ataque.espada;
                startTime = System.currentTimeMillis();
                //System.out.println("Inicio animacion");
                Alarma a = new Alarma(tiempoAnimacion, new Callable() {
                    @Override
                    public Object call() throws Exception {
                        //System.out.println("Fin animacion");
                        ataqueEnemigo = Ataque.nada;
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
        if(saltoEnemigo == Otro.saltando){
            if(ataqueEnemigo == Ataque.espada)
            {
                if(direccionEnemigo == Direccion.Derecha){
                    long currenTime = System.currentTimeMillis() - startTime;
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(14).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(15).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(16).getImagen(), x, y, null);
                }
                if(direccionEnemigo == Direccion.Izquierda){
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
        if((ataqueEnemigo == Ataque.espada ||
                estadoEnemigo == Estado.corriendo ||
                estadoEnemigo == Estado.dePie) && saltoEnemigo != Otro.saltando && estadoEnemigo != Estado.incado)
        {
            //Ataque de espada
            if(ataqueEnemigo == Ataque.espada)
            {
                if(direccionEnemigo == Direccion.Derecha){
                    long currenTime = System.currentTimeMillis() - startTime;
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(1).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(2).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(3).getImagen(), x, y, null);
                }
                if(direccionEnemigo == Direccion.Izquierda){
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
            else if(estadoEnemigo == Estado.corriendo){
                if(direccionEnemigo == Direccion.Derecha)
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
                if(direccionEnemigo == Direccion.Izquierda)
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
            else if(estadoEnemigo == Estado.dePie)
            {
                if(direccionEnemigo  == Direccion.Derecha){
                    g2d.drawImage(img.get(0).getImagen(), x, y, null);
                }
                if(direccionEnemigo == Direccion.Izquierda){
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    g2d.drawImage(img.get(0).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
        }
        if(estadoEnemigo == Estado.incado){
            if(ataqueEnemigo == Ataque.espada){
                if(direccionEnemigo == Direccion.Derecha){
                    long currenTime = System.currentTimeMillis() - startTime;
                    if((currenTime * 100) / tiempoAnimacion <= 33.33)
                        g2d.drawImage(img.get(7).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 66.66)
                        g2d.drawImage(img.get(8).getImagen(), x, y, null);
                    else if((currenTime * 100) / tiempoAnimacion <= 100)
                        g2d.drawImage(img.get(9).getImagen(), x, y, null);
                }
                if(direccionEnemigo == Direccion.Izquierda){
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
                if(direccionEnemigo  == Direccion.Derecha){
                    g2d.drawImage(img.get(6).getImagen(), x, y, null);
                }
                if(direccionEnemigo == Direccion.Izquierda){
                    g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
                    g2d.drawImage(img.get(6).getImagen(), -x-img.get(0).getImgAncho(), y, null);
                }
            }
        }
        
        g2d.setTransform(AffineTransform.getScaleInstance(1, 1));
        g2d.setColor(Color.white);
        g2d.drawString("Enemigo coordinates: " + x + " : " + y, 400, 15);        
        g2d.drawString("Estado: " + estadoEnemigo, 400, 45);
        g2d.drawString("Direccion: " + direccionEnemigo, 400, 60);
        g2d.drawString("Salto: " + saltoEnemigo, 400, 75);
        g2d.drawString("Ataque: " + ataqueEnemigo, 400, 90);
    }
}
