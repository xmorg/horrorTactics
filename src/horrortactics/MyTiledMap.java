/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet; //lets bring in their spritesheet
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Color;

/**
 *
 * @author tcooper
 */
public class MyTiledMap extends TiledMap {
    public Image tiles250x129,  walls250x512 = null;
    public Image tactics_in_distress00, tactics_in_distress01 = null;
    public SpriteSheet tilesheet, wallsheet = null;
    Actor player, follower1, follower2, follower3 = null;
    private int m_draw_x, m_draw_y;
    //private int player_x, player_y;
    
    public MyTiledMap(String ref, int draw_x, int draw_y) throws SlickException {
        super(ref);
        player = new Actor("data/tactics_in_distress00.png",218,313);
        m_draw_x = draw_x;
        m_draw_y = draw_y;
        player.setTileX(1);
        player.setTileY(5);
    }
    //how do we get player coords to test if the player exist in the 
    //renderred line
    @Override public void renderedLine(int visualX, int mapY, int layer)
    {
        if( mapY == player.getTileX())
            {
                player.getSpriteframe().draw(getIsoXToScreen(player.getTileX(),player.getTileY())+m_draw_x,//+110, 
                getIsoYToScreen(player.getTileX(),player.getTileY())+m_draw_y+160);
            }
            if(follower1 != null)
            {
                if(mapY == follower1.getTileX())
                {
                    follower1.getSpriteframe().draw(getIsoXToScreen(follower1.getTileX(),follower1.getTileY())+m_draw_x,//+110, 
                    getIsoYToScreen(follower1.getTileX(),follower1.getTileY())+m_draw_y+160);
                }
            }
            if(follower2 != null)
            {
                if(mapY == follower2.getTileX())
                {
                    follower2.getSpriteframe().draw(getIsoXToScreen(follower2.getTileX(),follower2.getTileY())+m_draw_x,//+110, 
                getIsoYToScreen(follower2.getTileX(),follower2.getTileY())+m_draw_y+160);
                }
            }
            if(follower3 != null)
            {
                if(mapY == follower3.getTileX())
                {
                    follower3.getSpriteframe().draw(getIsoXToScreen(follower3.getTileX(),follower3.getTileY())+m_draw_x,//+110, 
                    getIsoYToScreen(follower3.getTileX(),follower3.getTileY())+m_draw_y+160);
                }
            }
    }
    public void updateMapXY(int x, int y)
    {
        m_draw_x = x;
        m_draw_y = y;
        player.updateDrawXY(x, y);
        //player_x = px;
        //player_y = py;
        //sg1sheet.draw_x
    }
    public int getScreenToIsoX(int x, int y)
    {
        int isoX = ( (y*2 / 129) + (x / 250)) /2;
        return isoX;
    }
    public int getScreenToIsoY(int x, int y)
    {
        int isoX = ((y*2 / 129) + (x / 250)) /2;
        int isoY = (y *2 / 129) - isoX;            
        return isoY;
    }
    public int getIsoXToScreen(int x, int y)
    {
        int posX = ( x - y ) * (250/2);
        //int posY = ( x + y) * 129 /2;
        return posX;
    }
    public int getIsoYToScreen(int x, int y)
    {
        //int posX = ( x - y) * 250;
        int posY = ( x + y ) * 129 /2;
        return posY;
    }
    public int getPlayerX()
    {
        return player.getTileX();
    }
    public int getPlayerY()
    {
        return player.getTileY();
    }
}
