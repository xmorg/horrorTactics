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

/**
 *
 * @author tcooper
 */
public class MyTiledMap extends TiledMap {
    public Image tiles250x129,  walls250x512 = null;
    public Image tactics_in_distress00, tactics_in_distress01 = null;
    public SpriteSheet tilesheet, wallsheet = null;
    //public SpriteSheet sg1sheet = null; //218x313, sg2sheet = null;
    Actor sg1sheet = null;
    private int m_draw_x, m_draw_y;
    private int player_x, player_y;
    
    public MyTiledMap(String ref, int draw_x, int draw_y) throws SlickException {
        super(ref);
        sg1sheet = new Actor("data/tactics_in_distress00.png",218,313);
        m_draw_x = draw_x;
        m_draw_y = draw_y;
        player_x = 0; player_y = 0;
    }
    //how do we get player coords to test if the player exist in the 
    //renderred line
    @Override public void renderedLine(int visualX, int mapY, int layer)
    {
        if( getIsoXToScreen(1,3) == mapY)
        {
            //sg1sheet.getSpriteframe(0, 1).draw(
            //        getIsoXToScreen(player_x,player_y)+m_draw_x, 
            //        getIsoYToScreen(player_x,player_y)+m_draw_y);
            //sg1sheet.getSubImage(0, 1).draw(getIsoXToScreen(player_x,player_y)+m_draw_x, 
            //        getIsoYToScreen(1,3)+m_draw_y);
        }
    }
    public void updateMapXY(int x, int y)
    {
        m_draw_x = x;
        m_draw_y = y;
        sg1sheet.updateDrawXY(x, y);
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
            int posX = ( x - y) * 250;
            int posY = ( x + y) * 129 /2;
            return posX;
        }
        public int getIsoYToScreen(int x, int y) 
        {
            int posX = ( x - y) * 250;
            int posY = ( x + y) * 129 /2;
            return posY;
        }
        
}
