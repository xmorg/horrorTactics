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
 */
public class GameEvent {
    int tile_x, tile_y; //tile of the event.  player must stand here to trigger.
    int prox_ambient; //how far away do you here a cry for help, scream, etc.
    String event_name; //name of the event (for lookup)
    String pre_trigger_text; //text appears off to the side of the event tile
    String trigger_text; //what happens when the event is triggered.
    String player_selection; // If you are forced to make a d, ?
    String e_option1;
    String e_option2;
    String e_option3; //only 3 options availible.
    
    public GameEvent(String s, int x, int y) throws SlickException {
        this.tile_x = x;
        this.tile_y = y;
        event_name = s;
        //pre_trigger_text = s;
    }
    
}