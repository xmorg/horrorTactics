/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.SlickException;

/**
 *
 * @author tcooper Right now we need to activate something when the player steps
 * on a tile. when map.player (moving) check the actors layer for not null if
 * its not null, get the custom properties (all out of trigger)
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
    public void updateTrigger(String trigger_type, String trigger_name) {
        this.type = trigger_type;
        this.name = trigger_name;
    }

    public void onSetXY(int x, int y) {
        this.tilex = x;
        this.tiley = y;
    }

    public void onSteppedOnTrigger(MyTiledMap m, int x, int y) {
        int actors_layer = m.getLayerIndex("actors_layer");
        int gid = m.getTileId(x, y, actors_layer);
        //long list of triggers!
        if (m.getTileProperty(gid, "audio_trigger", "none").equals("trapped_girl")) {
            m.active_trigger.updateTrigger("audio_trigger", "trapped girl");
        } else if (m.getTileProperty(gid, "activate_trigger", "none").equals("release yukari")) {
            m.active_trigger.updateTrigger("activate_trigger", "release yukari");
        } else {
            m.trigger_check = "none";
            m.active_trigger.name = "none";
        }
        m.trigger_check = m.active_trigger.name;
        m.active_trigger.runTrigger(m);
    }
    public void runTrigger(MyTiledMap m) {
        if (this.name.equals("trapped_girl")) {
            this.onSetSoundToBePlayed("trappedgirl.ogg");
        } else if (this.name.equals("release yukari")) {
            m.trigger_check = m.active_trigger.name;
            for (int i = 0; i < m.follower_max; i++) {
                if (m.follower[i].tilex == 9 && m.follower[i].tiley == 2) {
                    if (m.follower[i].visible == false) {
                        m.follower[i].visible = true;
                    }
                }
            }
        }
        else {
            //do nothing.
        }
    }

    //public void onActivateTrigger(int x, int y, )
    public void onSetSoundToBePlayed(String s) {

    }

}
