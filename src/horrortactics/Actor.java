/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet; //lets bring in their spritesheet
import org.newdawn.slick.SlickException;

/**
 * Actor class is to encap the actor methods and data
 * @author tcooper
 */
public class Actor {
    private int tilex, tiley; //the tile we are at (aprox)
    private int tiledestx, tiledesty; //where we are going
    private int animate_frame;
    private int direction;
    private int draw_x, draw_y; //where we are drawing them at X,Y
    private Image spriteImage;
    private SpriteSheet sprites;
    
    public Actor(String s, int sx,int sy) throws SlickException
    {
        spriteImage = new Image(s);
        sprites = new SpriteSheet(spriteImage, sx,sy );
        tilex = 0;
        tiley = 0;
        draw_x =0;
        draw_y = 0; 
        tiledestx = 0;
        tiledesty = 0;
        animate_frame = 0;
        direction = 0;
    }
    public Image getSpriteframe() //throws SlickException
    {
        Image i = sprites.getSubImage(animate_frame, direction);
        return i;
    }
    public int getIsoXToScreen(int x, int y) 
    {
        int posX = ( x - y) * 250;
        //int posY = ( x + y) * 129 /2;
        return posX;
    }
    public int getIsoYToScreen(int x, int y)
    {
        //int posX = ( x - y) * 250;
        int posY = ( x + y) * 129 /2;
        return posY;
    }
    public void updateDrawXY(int x, int y)
    {
        draw_x = x;
        draw_y = y;
    }
    public void setTileX(int x)
    {
        tilex = x;
    }
    public void setTileY(int y)
    {
        tiley = y;
    }
    public int getTileX()
    {
        return tilex;
    }
    public int getTileY()
    {
        return tiley;
    }
}
