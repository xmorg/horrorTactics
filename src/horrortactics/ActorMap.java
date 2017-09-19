/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * all this stuff got moved out of "MyTiledMap"
 */
package horrortactics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Tim Cooper - ActorMap is the database of who actors are from tiled.
 */
public class ActorMap {

    //int[ ][ ] tiles = new int[][];
    int[][] tiles;

    public ActorMap() {
        //nothing
    }

    //publicActorMap
    public void getActorLocationFromTMX(MyTiledMap m) throws SlickException {
        //We do not get stats, only locations.
        //note, how do we carry followers from map to map? (or will we ever?)
        int monster_loop = 0;
        int follower_loop = 0;
        int actor_layer = m.getLayerIndex("actors_layer");
        for (int i = 0; i < m.monster_max; i++) {
            try {
                m.monster[i] = new Actor("data/monster00", 218, 313);
                m.monster[i].visible = false; //default

            } catch (SlickException e) {
            }
        }
        for (int i = 0; i < m.follower_max; i++) {
            try {
                m.follower[i] = new Actor("data/girl01", 218, 313);
                m.follower[i].visible = false;
                m.follower[i].direction = m.follower[i].getEast();
            } catch (SlickException e) {
            }
        }
        for (int y = 0; y < m.getHeight(); y++) {
            for (int x = 0; x < m.getWidth(); x++) {
                int gid = m.getTileId(x, y, actor_layer);
                if (gid > 0) {
                    //System.out.println(this.getTileId(x, y, actor_layer));
                    String pname = m.getTileProperty(gid, "actor_name", "none");
                    String weapon = m.getTileProperty(gid, "weapon", "none");
                    String actor_spotted = m.getTileProperty(gid, "actor_spotted", "true");
                    String mission_goal = m.getTileProperty(gid, "event_goal", "no");
                    if (mission_goal.equalsIgnoreCase("yes")) {
                        m.mission_goal = new Image("data/" + m.getTileProperty(gid, "event_goal_graphic", "papers.png"));
                        //set the X/y
                        m.draw_goal_x = x;
                        m.draw_goal_y = y;
                    }
                    if (pname.equals("player")) {
                        m.player.tilex = x;
                        m.player.tiley = y;
                        m.player.name = "Riku"; //pname;
                        //m.player.weapon = "knife"; //equip a weapon
                        m.player.changeActorSpritesheet("data/" + m.getTileProperty(gid, "costume", "player00"), 218, 313);
                        //} else if (this.getTileProperty(gid, "actor_name", "none").equals("pear monster")) {
                        //void swapSoundEffects(String footsteps, String miss, String hit, String washit, String dodge, String died) {
                         m.player.swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg");
                    } else if (pname.equals("Yukari")) { //little nerd girl
                        try {
                            m.follower[follower_loop].changeActorSpritesheet("data/girl01", 218, 313);
                            //follower[follower_loop].
                            m.follower[follower_loop].tilex = x;
                            m.follower[follower_loop].tiley = y;
                            m.follower[follower_loop].visible = true;
                            m.follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    }else if (pname.equals("Miyu")) { //kendo girl
                        try {
                            m.follower[follower_loop].changeActorSpritesheet("data/girl01", 218, 313);
                            m.follower[follower_loop].swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg");
                            //follower[follower_loop].
                            m.follower[follower_loop].tilex = x;
                            m.follower[follower_loop].tiley = y;
                            m.follower[follower_loop].visible = true;
                            m.follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("Ichi")) { //slim boy
                        try {
                            m.follower[follower_loop].changeActorSpritesheet("data/boy00", 218, 313);
                            //follower[follower_loop].
                            m.follower[follower_loop].tilex = x;
                            m.follower[follower_loop].tiley = y;
                            m.follower[follower_loop].visible = true;
                            m.follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("Takeshi")) { //fat boy
                        try {
                            m.follower[follower_loop].changeActorSpritesheet("data/boy01", 218, 313);
                            m.follower[follower_loop].tilex = x;
                            m.follower[follower_loop].tiley = y;
                            m.follower[follower_loop].visible = true;
                            m.follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }

                    } else if (pname.equals("Officer Ayano")) {
                        try {
                            m.follower[follower_loop].changeActorSpritesheet("data/police01", 218, 313);
                            m.follower[follower_loop].tilex = x;
                            m.follower[follower_loop].tiley = y;
                            m.follower[follower_loop].visible = true;
                            m.follower[follower_loop].name = pname;
                            m.follower[follower_loop].attack_range = 2;
                            //if(weapon)
                            //m.monster[monster_loop].swapSoundEffects("", "pear_attack1.ogg",
                                //"pear_attack1.ogg", "pear_hit1.ogg", "pear_dodged1.ogg", "pear_died1.ogg");
                            m.follower[follower_loop].swapSoundEffects("", "pistol_shotl.ogg", "pistol_shotl.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg");
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("tutor_bully0")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/boy00", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            m.monster[monster_loop].spotted_enemy = false;
                        } else {
                            m.monster[monster_loop].spotted_enemy = true;
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 100;
                        monster_loop++;
                    } else if (pname.equals("tutor_bully1")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/girl03", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            m.monster[monster_loop].spotted_enemy = false;
                        } else {
                            m.monster[monster_loop].spotted_enemy = true;
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 100;
                        m.monster[monster_loop].swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg");
                        monster_loop++;
                    } else if (pname.equals("pear monster")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster00", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            m.monster[monster_loop].spotted_enemy = false;
                        } else {
                            m.monster[monster_loop].spotted_enemy = true;
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 4;
                        m.monster[monster_loop].swapSoundEffects("", "pear_attack1.ogg",
                                "pear_attack1.ogg", "pear_hit1.ogg", "pear_dodged1.ogg", "pear_died1.ogg");
                        monster_loop++;
                    } else if (pname.equals("butcher")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster07", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            m.monster[monster_loop].spotted_enemy = false;
                        } else {
                            m.monster[monster_loop].spotted_enemy = true;
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 0;
                        m.monster[monster_loop].swapSoundEffects("", "pear_attack1.ogg",
                                "pear_attack1.ogg", "pear_hit1.ogg", "pear_dodged1.ogg", "pear_died1.ogg");
                        monster_loop++;
                    } else if (pname.equals("skeleton")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster05", 218, 313);
                        } catch (SlickException e) {
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 0;
                        monster_loop++;
                    } else if (pname.equalsIgnoreCase("zombie1")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster10", 218, 313);
                        } catch (SlickException e) {
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 2;
                        monster_loop++;
                    } else if (pname.equalsIgnoreCase("zombie2")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster11", 218, 313);
                        } catch (SlickException e) {
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 2;
                        monster_loop++;
                    } else if (pname.equalsIgnoreCase("butcher")) {
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster07", 218, 313);
                        } catch (SlickException e) {
                        }
                        m.monster[monster_loop].health_points = 15; //boss!
                        m.monster[monster_loop].health_points_max = 15;
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 99;
                        monster_loop++;
                    }else if (pname.equals("invisible man")) {
                        System.out.println("we got to the invnisible man");
                        try {
                            m.monster[monster_loop].changeActorSpritesheet("data/monster06", 218, 313);
                        } catch (SlickException e) {
                            System.out.println("something is wrong.");
                        }
                        m.monster[monster_loop].tilex = x;
                        m.monster[monster_loop].tiley = y;
                        m.monster[monster_loop].setActorMoving(false);
                        m.monster[monster_loop].visible = true;
                        m.monster[monster_loop].name = pname;
                        m.monster[monster_loop].max_turns_till_revival = 4;
                        //set dodge scores
                        monster_loop++;
                    }// add more monsters here;
                }
            }
            for (int i = 0; i < 5; i++) {
                m.planning[i] = m.getMapProperty("planning_" + i, "end");
                if (m.planning[i].equalsIgnoreCase("end")) {
                    m.maxplanevent = i; //last one
                }
                m.charbusts[i] = new Image("data/" + m.getMapProperty("planning_" + i + "_p", "prt_player_00.png"));
            }
        }

        for (int i = 0; i < 5; i++) {
            m.planning[i] = m.getMapProperty("planning_" + i, "end");
            if (m.planning[i].equalsIgnoreCase("end")) {
                m.maxplanevent = i - 1; //last one
            }
            m.charbusts[i] = new Image("data/" + m.getMapProperty("planning_" + i + "_p", "prt_player_00.png"));
        }
        m.next_map = m.getMapProperty("nextmap", "none");
        m.RequiresGoal = m.getMapProperty("req_goal", "no");
        m.EventSpotted = m.getMapProperty("event_spotted", "none");
        if (!m.EventSpotted.equalsIgnoreCase("none")) { //if not none, load the event spotted
            m.EventSpotted_m = m.getMapProperty("event_spotted_m", "none");
            m.EventSpotted_p = new Image("data/" + m.getMapProperty("event_spotted_p", "prt_player_00.png"));
        }
    }
}
