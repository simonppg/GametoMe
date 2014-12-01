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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author simonppg
 */
public class Heroe {
    
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
    private int heroeImgAncho;
    /**
     * Alto del heroe.
     */
    private int heroeImgAlto;
    /**
     * Determina si el heroe esta saltando
     */
    private boolean saltando;

    public Heroe() {
        Initialize();
        LoadContent();
        
        x = 400;
        y = (int) (FlujoDelJuego.frameHeight * 0.88) - heroeImgAlto;
    }

    private void Initialize() {
        //TODO Inicializar variables.
        saltando = false;
        velocidadAceleracionY = 5;
        velocidadDetenerY = 10;
        //Deacuerdo a la logica de desplazamiento estas variables deben ser iguales
        velocidadAceleracionX = velocidadDetenerX = 10;
    }

    private void LoadContent() {
        //TODO Cargar imagenes
        try
        {
            URL heroeImgUrl = this.getClass().getResource("/resources/images/iroman.png");
            heroeImg = ImageIO.read(heroeImgUrl);
            heroeImgAncho = heroeImg.getWidth();
            heroeImgAlto = heroeImg.getHeight();
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
                saltando = true;
        }
        
        if(saltando){
            //TODO duracion de la animacion de salto
            if(cont < 7) {
                velocidadY -= velocidadAceleracionY;
                cont++;
            }
            else {
                if(y + velocidadY +velocidadDetenerY > (int) (FlujoDelJuego.frameHeight * 0.88) - heroeImgAlto) {
                    velocidadY = 0;
                    y = (int) (FlujoDelJuego.frameHeight * 0.88) - heroeImgAlto;
                    saltando = false;
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
            velocidadX -= velocidadAceleracionX;
        else if(velocidadX < 0)
            velocidadX += velocidadDetenerX;
        
        // Calculating speed for moving or stopping to the right.
        if(Panel.keyboardKeyState(KeyEvent.VK_D))
            velocidadX += velocidadAceleracionX;
        else if(velocidadX > 0)
            velocidadX -= velocidadDetenerX;
        
        // Moves the rocket.
        x += velocidadX;
        y += velocidadY;
    }
    
    public void Draw(Graphics2D g2d)
    {
        //TODO Dibujar al personaje
        g2d.drawImage(heroeImg, x, y, null);
        g2d.setColor(Color.white);
        g2d.drawString("Heroe coordinates: " + x + " : " + y, 5, 15);
        g2d.drawString("Heroe cont: " + cont+" salto: "+ saltando, 5, 30);
    }
}
