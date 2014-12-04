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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
     * Ancho del heroe.
     */
    //private int heroeImgAncho;
    /**
     * Alto del heroe.
     */
    //private int heroeImgAlto;
    /**
     * Saltando. Determina si el heroe esta saltando
     */
    //private boolean saltando;
    /**
     * Lista de estados. acciones.
     */
    public static enum ESTADO_DEL_HEROE {
        QUIETO, INCADO, ESCALANDO, COLGADO, CORRIENDO, SALTANDO,
        ATAQUE_DE_ESPADA, ATAQUE_DE_ESPADA_INCADO, ATAQUE_DE_ESPADA_SALTANDO, 
        ATAQUE_ESPECIAL, ATAQUE_ESPECIAL_ESCALANDO, ATAQUE_ESPECIAL_SALTANDO
    }
    /**
     * Estado del Heroe. actual.
     */
    public ESTADO_DEL_HEROE estadoHeroe;
    /**
     * Lista de imagenes. acciones.
     */
    private ArrayList<Imagen> img;

    public Heroe() {
        Initialize();
        LoadContent();
        
        x = 400;
        y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
    }

    private void Initialize() {
        //TODO Inicializar variables.
        img = new ArrayList<Imagen>();
        estadoHeroe = ESTADO_DEL_HEROE.QUIETO;
        //saltando = false;
        velocidadAceleracionY = 5;
        velocidadDetenerY = 10;
        //Deacuerdo a la logica de desplazamiento estas variables deben ser iguales
        velocidadAceleracionX = velocidadDetenerX = 1;
    }

    private void LoadContent() {
        //TODO Cargar imagenes
        try
        {
            /*URL heroeImgUrl = this.getClass().getResource("/resources/images/heroe/ryu1.png");
            heroeImg = ImageIO.read(heroeImgUrl);
            heroeImgAncho = heroeImg.getWidth();
            heroeImgAlto = heroeImg.getHeight();*/
            for (int i = 1; i <= 29; i++) {
                URL heroeImgUrl = this.getClass().getResource("/resources/images/heroe/ryu"+i+".png");
                Imagen localImg = new Imagen();
                localImg.setImagen(ImageIO.read(heroeImgUrl));
                img.add(localImg);
                //heroeImgAncho = img.get(0).getImgAncho();
                //heroeImgAlto = img.get(0).getImgAlto();
                
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
            if(cont==0)
                estadoHeroe = ESTADO_DEL_HEROE.SALTANDO;
                //saltando = true;
        }
        
        if(estadoHeroe == ESTADO_DEL_HEROE.SALTANDO){
            //TODO duracion de la animacion de salto
            if(cont < 7) {
                velocidadY -= velocidadAceleracionY;
                cont++;
            }
            else {
                if(y + velocidadY +velocidadDetenerY > (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto()) {
                    velocidadY = 0;
                    y = (int) (FlujoDelJuego.frameHeight * 0.88) - img.get(0).getImgAlto();
                    estadoHeroe = ESTADO_DEL_HEROE.QUIETO;
                    //saltando = false;
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
            estadoHeroe = ESTADO_DEL_HEROE.CORRIENDO;
        }
        else if(velocidadX < 0){
            velocidadX += velocidadDetenerX;
            estadoHeroe = ESTADO_DEL_HEROE.CORRIENDO;
        }
        
        // Calculating speed for moving or stopping to the right.
        if(Panel.keyboardKeyState(KeyEvent.VK_D)){
            velocidadX += velocidadAceleracionX;
            estadoHeroe = ESTADO_DEL_HEROE.CORRIENDO;
        }
        else if(velocidadX > 0){
            velocidadX -= velocidadDetenerX;
            estadoHeroe = ESTADO_DEL_HEROE.CORRIENDO;
        }
        
        // Moves the rocket.
        x += velocidadX;
        y += velocidadY;
        if(velocidadX == 0 && velocidadY == 0)
            estadoHeroe = ESTADO_DEL_HEROE.QUIETO;
    }
    
    int s = 0;
    int corre =0;
    public void Draw(Graphics2D g2d)
    {
        //TODO Dibujar al personaje
        if(estadoHeroe == ESTADO_DEL_HEROE.SALTANDO){
            switch(s){
                case 0:
                    g2d.drawImage(img.get(10).getImagen(), x, y, null);
                    s++;
                    break;
                case 1:
                    g2d.drawImage(img.get(11).getImagen(), x, y, null);
                    s++;
                    break;
                case 2:
                    g2d.drawImage(img.get(12).getImagen(), x, y, null);
                    s++;
                    break;
                case 3:
                    g2d.drawImage(img.get(13).getImagen(), x, y, null);
                    s=0;
                    break;
            }       
        }
        else if(estadoHeroe == ESTADO_DEL_HEROE.CORRIENDO){
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
        else
            g2d.drawImage(img.get(0).getImagen(), x, y, null);
        g2d.setColor(Color.white);
        g2d.drawString("Heroe coordinates: " + x + " : " + y, 5, 15);
        g2d.drawString("Heroe cont: " + cont+" salto: "+ estadoHeroe, 5, 30);
    }
}
