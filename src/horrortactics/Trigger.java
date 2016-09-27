/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

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

    public void onSteppedOnTrigger(MyTiledMap m, int x, int y) throws SlickException {
        int actors_layer = m.getLayerIndex("actors_layer");
        int gid = m.getTileId(x, y, actors_layer);
        //long list of triggers!
        if (m.getTileProperty(gid, "audio_trigger", "none").equals("trapped_girl")) {
            m.active_trigger.updateTrigger("audio_trigger", "trapped girl");
        } else if (m.getTileProperty(gid, "activate_trigger", "none").equals("release yukari")) {
            m.active_trigger.updateTrigger("activate_trigger", "release yukari");
        } else if (!m.getTileProperty(gid, "event_goal", "none").equals("none")) {
            //you stepped on teh event goal, run charbust?
            m.active_trigger.updateTrigger("event_goal",m.getTileProperty(gid, "event_goal", "none") );
            if (!m.active_trigger.name.equalsIgnoreCase("none")) { //still blank.
                //System.out.println("reached event goal");
                //assuming this exists?
                if(m.EventGoal_ran == false) {
                    m.EventGoal_p = new Image("data/" + m.getTileProperty(gid,"event_goal_p", "prt_player_00.png"));
                    m.EventGoal_m = m.getTileProperty(gid,"event_goal_m", "none");
                    m.EventGoal_ran = true;
                    m.old_turn_order = m.turn_order;
                    m.turn_order = "goal reached";
                }
            }
        }  else if (!m.getTileProperty(gid, "event_exit", "none").equals("none")) {
            //you stepped on teh event goal, run charbust?
            m.active_trigger.updateTrigger("event_exit",m.getTileProperty(gid, "event_exit", "none") );
            if (m.active_trigger.name.equalsIgnoreCase("exit")) { //still blank.
                //System.out.println("reached event goal");
                //assuming this exists?
                if(m.EventExit_ran == false) {
                    //event goal equals none, or (event goal != none and ran = true)
                    
                    if(m.RequiresGoal.equalsIgnoreCase("yes") && m.EventGoal_ran == true ) {
                        m.EventExit_p = new Image("data/" + m.getTileProperty(gid,"event_goal_p", "prt_player_00.png"));
                        m.EventExit_m = m.getTileProperty(gid,"event_goal_m", "none");
                        m.EventExit_ran = true;
                        m.old_turn_order = m.turn_order;
                        //System.out.println("if(m.RequiresGoal.equalsIgnoreCase(\"yes\") && m");
                        m.turn_order = "exit reached";
                    } else
                    if(m.RequiresGoal.equalsIgnoreCase("no")) {//EventGoal.equalsIgnoreCase("none") //requires goal?
                        m.EventExit_p = new Image("data/" + m.getTileProperty(gid,"event_goal_p", "prt_player_00.png"));
                        m.EventExit_m = m.getTileProperty(gid,"event_exit_m", "none");
                        m.EventExit_ran = true;
                        m.old_turn_order = m.turn_order;
                        //System.out.println("m.RequiresGoal.equalsIgnoreCase(\"no\")");
                        m.turn_order = "exit reached";
                    } 
                    //m.RequiresGoal.equalsIgnoreCase("yes") && 
                }
            }
        }
        
        else {
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
