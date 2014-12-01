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
package gametome;

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
class Heroe {
    
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
    public int velocidadY;
    /**
     * Velocidad con la que aceleracion.
     */
    private int velocidadAceleracion;
    /**
     * Velocidad con la que se detiene.
     */
    private int velocidadDetener;
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

    public Heroe() {
        Initialize();
        LoadContent();
        
        x = 400;
        y = 300;
    }

    private void Initialize() {
        //TODO Inicializar variables.
        x = 400;
        y = 300;
        velocidadAceleracion = 2;
        velocidadDetener = 1;
    }

    private void LoadContent() {
        //TODO Cargar imagenes
        try
        {
            URL heroeImgUrl = this.getClass().getResource("/resources/images/ryu.png");
            heroeImg = ImageIO.read(heroeImgUrl);
            heroeImgAncho = heroeImg.getWidth();
            heroeImgAlto = heroeImg.getHeight();
        }
        catch (IOException ex) {
            Logger.getLogger(PlayerRocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Update(){
        //TODO Logica del heroe
        
        
        // Calculating speed for moving or stopping to the left.
        if(Panel.keyboardKeyState(KeyEvent.VK_A))
            velocidadX -= velocidadAceleracion;
        else if(velocidadX < 0)
            velocidadX += velocidadDetener;
        
        // Calculating speed for moving or stopping to the right.
        if(Panel.keyboardKeyState(KeyEvent.VK_D))
            velocidadX += velocidadAceleracion;
        else if(velocidadX > 0)
            velocidadX -= velocidadDetener;
        
        // Moves the rocket.
        x += velocidadX;
        y += velocidadY;
    }
    
    public void Draw(Graphics2D g2d)
    {
        //TODO Dibujar al personaje
        g2d.drawImage(heroeImg, x, y, null);
    }
}
