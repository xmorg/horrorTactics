/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;


import org.newdawn.slick.SlickException;

/**
 *
 * @author tcooper
 * Right now we need to activate something when the player steps on a tile.
 * when map.player (moving) check the actors layer for not null
 * if its not null, get the custom properties (all out of trigger)
 */
public class Trigger {
    int tilex;
    int tiley; //the tile we are at (aprox)
    String type, name, action;
    
    public Trigger(String trigger_type, String trigger_name) throws SlickException {
        this.type = trigger_type;
        this.name = trigger_name;
        action = "none"; //prompt/dialogue/play sound/visible player;
    }
    //public void onActivateTrigger(int x, int y, )
    public void onSetSoundToBePlayed(String s) {
        
    }
    
}
