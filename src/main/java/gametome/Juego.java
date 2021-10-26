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

import gametome.personajes.Enemigo;
import gametome.personajes.Heroe;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 * @author simonppg
 */

public class Juego {
    
    /**
     * Landing area on which rocket will have to land.
     */
    private LandingArea landingArea;
    
    /**
     * Game background image.
     */
    private BufferedImage backgroundImg;
    
    /**
     * Red border of the frame. It is used when player crash the rocket.
     */
    private BufferedImage redBorderImg;
    
    /**
     * Heroe del juego. Clase de nuestro heroe.
     */
    private Heroe heroe;
    /**
     * Enemigo.
     */
    private Enemigo enemigo;
    

    public Juego()
    {
        FlujoDelJuego.estadoJuego = FlujoDelJuego.ESTADO_DEL_JUEGO.CARGANDO_CONTENIDO_DEL_JUEGO;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                FlujoDelJuego.estadoJuego = FlujoDelJuego.ESTADO_DEL_JUEGO.JUGANDO;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void Initialize()
    {
        landingArea  = new LandingArea();
        heroe = new Heroe();
        
        enemigo = new Enemigo();
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void LoadContent()
    {   
        try
        {
            URL backgroundImgUrl = this.getClass().getResource("/resources/images/background.jpg");
            backgroundImg = ImageIO.read(backgroundImgUrl);
            
            URL redBorderImgUrl = this.getClass().getResource("/resources/images/red_border.png");
            redBorderImg = ImageIO.read(redBorderImgUrl);
        }
        catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Restart game - reset some variables.
     */
    public void RestartGame()
    {
        //heroe.ResetPlayer();
    }
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void UpdateGame(long gameTime, Point mousePosition)
    {
        //TODO update de los objetos en la escena
        heroe.Update();
        
        enemigo.Update();
        
        //TODO fisica del juego
        
        //TODO verifical si debe cambiar el estado del juego (pause, fin, gana, nueva escena, etc..)
        
        /*if(pause)
            FlujoDelJuego.gameState = FlujoDelJuego.GameState.GAMEOVER;
        if(fin)
            FlujoDelJuego.gameState = FlujoDelJuego.GameState.GAMEOVER;*/
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d, Point mousePosition)
    {        
        landingArea.Draw(g2d);
        
        heroe.Draw(g2d);
        
        enemigo.Draw(g2d);
    }
    
    
    /**
     * Draw the game over screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition Current mouse position.
     * @param gameTime Game time in nanoseconds.
     */
    public void DrawGameOver(Graphics2D g2d, Point mousePosition, long gameTime)
    {
        Draw(g2d, mousePosition);
        
        g2d.drawString("Press space or enter to restart.", FlujoDelJuego.frameWidth / 2 - 100, FlujoDelJuego.frameHeight / 3 + 70);
    }
}
