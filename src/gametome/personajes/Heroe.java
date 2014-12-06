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
     * Imagen del heroe.
     */
    private BufferedImage heroeImg;
    /**
     * Estado del Heroe. actual.
     */
    public EstadoHeroe estadoHeroe;
    /**
     * Lista de imagenes. acciones.
     */
    private ArrayList<Imagen> img;
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
        estadoHeroe = new EstadoHeroe();
        estadoHeroe.setEstadoHeroe(EstadoHeroe.DE_PIE);
        estadoHeroe.setEstadoHeroe(EstadoHeroe.DERECHA);
        velocidadAceleracionY = 5;
        velocidadDetenerY = 10;
        //Deacuerdo a la logica de desplazamiento estas variables deben ser iguales
        velocidadAceleracionX = velocidadDetenerX = 1;
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
        
        if(Panel.keyboardKeyState(KeyEvent.VK_W))
        {
            //TODO cambiar cont por alguna medida de tiempo
            if(cont==0){
                estadoHeroe.setEstadoHeroe(EstadoHeroe.SALTANDO);
            }
        }
        
        if(estadoHeroe.isSaltando()){
            //duracion de la animacion de salto
            if(cont < 7) {
                velocidadY -= velocidadAceleracionY;
                cont++;
            }
            else {
                if(y + velocidadY +velocidadDetenerY > (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto()) {
                    velocidadY = 0;
                    y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
                    estadoHeroe.setEstadoHeroe(EstadoHeroe.DE_PIE);
                }
                else {
                    velocidadY += velocidadDetenerY;
                }
            }
        }
        else
        {
            velocidadY = 0;
            cont = 0;
        }
        
        // Calculating speed for moving or stopping to the left.
        if(Panel.keyboardKeyState(KeyEvent.VK_A))
        {
            velocidadX -= velocidadAceleracionX;
            estadoHeroe.setEstadoHeroe(EstadoHeroe.CORRIENDO);
            estadoHeroe.setEstadoHeroe(EstadoHeroe.IZQUIERDA);
        }
        else if(velocidadX < 0){
            velocidadX += velocidadDetenerX;
            estadoHeroe.setEstadoHeroe(EstadoHeroe.CORRIENDO);
            estadoHeroe.setEstadoHeroe(EstadoHeroe.IZQUIERDA);
        }
        
        // Calculating speed for moving or stopping to the right.
        if(Panel.keyboardKeyState(KeyEvent.VK_D)){
            velocidadX += velocidadAceleracionX;
            estadoHeroe.setEstadoHeroe(EstadoHeroe.CORRIENDO);
            estadoHeroe.setEstadoHeroe(EstadoHeroe.DERECHA);
        }
        else if(velocidadX > 0){
            velocidadX -= velocidadDetenerX;
            estadoHeroe.setEstadoHeroe(EstadoHeroe.CORRIENDO);
            estadoHeroe.setEstadoHeroe(EstadoHeroe.DERECHA);
        }
        
        // Calculating speed for moving or stopping to the left.
        if(Panel.keyboardKeyState(KeyEvent.VK_SPACE))
        {
            if(!estadoHeroe.isAtaqueDeEspada())
            {
                estadoHeroe.setEstadoHeroe(EstadoHeroe.ATAQUE_DE_ESPADA);
                //System.out.println("Inicio animacion");
                Alarma a = new Alarma(5, new Callable() {
                    @Override
                    public Object call() throws Exception {
                        //System.out.println("Fin animacion");
                        //estadoHeroe.setEstadoHeroe(estadoHeroe.getEstadoHeroe()&EstadoHeroe.ATAQUE_DE_ESPADA);
                        return null;
                    }
                });
            }
        }
        
        // Mueve al heroe
        x += velocidadX;
        y += velocidadY;
        if(velocidadX == 0 && velocidadY == 0){
            estadoHeroe.setEstadoHeroe(EstadoHeroe.DE_PIE);
        }
    }
    
    int salto = 0;
    int corre = 0;
    int ataque = 0;
    public void Draw(Graphics2D g2d)
    {
        //TODO Dibujar al personaje
        if((estadoHeroe.isDe_Pie() || estadoHeroe.isCorriendo()) && 
            estadoHeroe.isDerecha() &&
            estadoHeroe.isAtaqueDeEspada()){
            switch(ataque){
                case 0:
                    g2d.drawImage(img.get(1).getImagen(), x, y, null);
                    ataque++;
                    break;
                case 1:
                    g2d.drawImage(img.get(2).getImagen(), x, y, null);
                    ataque++;
                    break;
                case 2:
                    g2d.drawImage(img.get(3).getImagen(), x, y, null);
                    ataque=0;
                    break;
            }
        }
        else if((estadoHeroe.isDe_Pie() || estadoHeroe.isCorriendo()) &&
            estadoHeroe.isIzquierda() &&
            estadoHeroe.isAtaqueDeEspada()){
            g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
            switch(ataque){
                case 0:
                    g2d.drawImage(img.get(1).getImagen(), -x-img.get(26).getImgAncho(), y, null);
                    ataque++;
                    break;
                case 1:
                    g2d.drawImage(img.get(2).getImagen(), -x-img.get(27).getImgAncho(), y, null);
                    ataque++;
                    break;
                case 2:
                    g2d.drawImage(img.get(3).getImagen(), -x-img.get(28).getImgAncho(), y, null);
                    ataque=0;
                    break;
            }
        }
        else if(estadoHeroe.isSaltando() || (estadoHeroe.isSaltando() && estadoHeroe.isCorriendo())){
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
        else if(estadoHeroe.isCorriendo() && estadoHeroe.isDerecha()){
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
        else if( estadoHeroe.isCorriendo() && estadoHeroe.isIzquierda()){
            g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
            switch(corre){
                case 0:
                    g2d.drawImage(img.get(26).getImagen(), -x-img.get(26).getImgAncho(), y, null);
                    corre++;
                    break;
                case 1:
                    g2d.drawImage(img.get(27).getImagen(), -x-img.get(27).getImgAncho(), y, null);
                    corre++;
                    break;
                case 2:
                    g2d.drawImage(img.get(28).getImagen(), -x-img.get(28).getImgAncho(), y, null);
                    corre=0;
                    break;
            }
        }
        else if(estadoHeroe.isDe_Pie() && estadoHeroe.isDerecha()){
            g2d.drawImage(img.get(0).getImagen(), x, y, null);
        }
        else if(estadoHeroe.isDe_Pie() && estadoHeroe.isIzquierda()){
            g2d.setTransform(AffineTransform.getScaleInstance(-1, 1));
            g2d.drawImage(img.get(0).getImagen(), -x-img.get(0).getImgAncho(), y, null);
        }
        
        
            
        g2d.setColor(Color.white);
        g2d.drawString("Heroe coordinates: " + x + " : " + y, 5, 15);        
        g2d.drawString("Heroe estado: " + estadoHeroe.getEstadoHeroe(), 5, 45);
    }
}
