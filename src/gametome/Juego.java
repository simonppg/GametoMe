package gametome;

import java.awt.Color;
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
    

    public Juego()
    {
        FlujoDelJuego.gameState = FlujoDelJuego.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                
                FlujoDelJuego.gameState = FlujoDelJuego.GameState.PLAYING;
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
        
        //TODO fisica del juego
        
        //Gravedad
        /*if(heroe.y + heroe.heroeImgAlto > 500)
        {
            //Esto es para darle tiempo a que empice el salto, sin esto el salto no inicia
            if(heroe.cont==7)
                heroe.saltando = false;
        }*/
        
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
