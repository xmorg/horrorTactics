/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet; //lets bring in their spritesheet
//import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.geom.Rectangle; //rects to click on
import org.newdawn.slick.Graphics;
import java.util.concurrent.ThreadLocalRandom;
//import org.newdawn.slick.Sound;
//import java.lang.Math.*;

/**
 *
 * @author tcooper
 */
public class MyTiledMap extends TiledMap {

    int monster_max = 10;
    int follower_max = 4;
    public Image tiles250x129, walls250x512 = null;
    public SpriteSheet tilesheet, wallsheet = null;
    int TILE_WIDTH_HALF = this.tileWidth / 2;
    int TILE_HEIGHT_HALF = this.tileHeight / 2;
    int selected_tile_x;
    int selected_tile_y;
    int mouse_over_tile_x = 0;
    int mouse_over_tile_y = 0;
    int pixel_dest_x = -1;
    int pixel_dest_y = -1;
    int light_level = 2; //default light level
    int selected_follower = 0;
    String turn_order = null;  //monster, player
    String next_map = "none";
    String old_turn_order = null;    //monster, player
    String log_msg = "";
    String[] planning = new String[20];
    Image selected_green, selected_yellow;
    Image[] charbusts = new Image[20];

    String EventSpotted = "none";
    Image EventSpotted_p = null;
    String EventSpotted_m = "none";
    boolean EventSpotted_ran = false;
    String EventSpotted1 = "none";  //TODO: make a loop like in planning.
    Image EventSpotted_p1 = null;   //LOOP: through like in planning during, 
    String EventSpotted_m1 = "none"; //render_character_busts
    boolean EventSpotted_ran1 = false;

    String EventGoal = "none";
    Image EventGoal_p = null;
    String EventGoal_m = "none";
    boolean EventGoal_ran = false;

    String EventExit = "none";
    Image EventExit_p = null;
    Image mission_goal = null;
    int draw_goal_x = -1, draw_goal_y = -1;

    String EventExit_m = "none";
    boolean EventExit_ran = false;
    String RequiresGoal = "no"; //Yes, if you need to reach a goal before exit

    int planevent = 0;
    int maxplanevent = 0;
    Trigger active_trigger = null;

    Actor player = null;
    Actor[] follower = new Actor[follower_max];
    Actor[] monster = new Actor[monster_max];
    private int m_draw_x, m_draw_y; //map draw x
    int current_monster_moving = 0; //debug
    int current_follower_moving = 0;
    String trigger_check = "no";

    public int render_min_y = 0, render_min_x = 0, render_max_y = 0, render_max_x = 0;

    public int getDrawX() {
        return m_draw_x;
    }

    public int getDrawY() {
        return m_draw_x;
    }

    public MyTiledMap(String ref, int draw_x, int draw_y) throws SlickException {
        super(ref);
        m_draw_x = draw_x;
        m_draw_y = draw_y;
        selected_tile_x = -1;
        selected_tile_y = -1;
        tiles250x129 = new Image("data/tiles250x129.png");
        selected_green = new Image("data/selected_green.png");
        selected_yellow = new Image("data/selected_yellow.png");
        player = new Actor("data/player00", 218, 313);
        //player = new Actor("data/boy01", 218, 313); //monster test
        turn_order = "planning";   //monster, player

        this.active_trigger = new Trigger("none", "none");
        /*for (int i = 0; i < 5; i++) {
            this.planning[i] = this.getMapProperty("planning_" + i, "end");
            if (this.planning[i].equalsIgnoreCase("end")) {
                this.maxplanevent = i; //last one
            }
            this.charbusts[i] = new Image("data/" + this.getMapProperty("planning_" + i + "_p", "prt_player_00.png"));
        }*/
        this.next_map = this.getMapProperty("nextmap", "none");
        this.RequiresGoal = this.getMapProperty("req_goal", "no");
        this.EventSpotted = this.getMapProperty("event_spotted", "none");
        this.EventSpotted1 = this.getMapProperty("event_spotted1", "none");
        if (!this.EventSpotted.equalsIgnoreCase("none")) { //if not none, load the event spotted
            this.EventSpotted_m = this.getMapProperty("event_spotted_m", "none");
            this.EventSpotted_p = new Image("data/" + this.getMapProperty("event_spotted_p", "prt_player_00.png"));
            if (!this.EventSpotted1.equalsIgnoreCase("none")) {
                this.EventSpotted_m1 = this.getMapProperty("event_spotted_m1", "none");
                this.EventSpotted_p1 = new Image("data/" + this.getMapProperty("event_spotted_p1", "prt_player_00.png"));
            }
        } else {
            this.EventSpotted_m = this.getMapProperty("event_spotted_m", "end.");
            this.EventSpotted_p = new Image("data/" + this.getMapProperty("event_spotted_p", "prt_player_00.png"));
        }

    }

    @Override
    public void renderedLine(int visualX, int mapY, int layer) {
    }

    public void updateMapXY(int x, int y) {
        m_draw_x = x;
        m_draw_y = y;
    }

    public void getActorLocationFromTMX() throws SlickException {
        //We do not get stats, only locations.
        //note, how do we carry followers from map to map? (or will we ever?)
        int monster_loop = 0;
        int follower_loop = 0;
        int actor_layer = this.getLayerIndex("actors_layer");
        for (int i = 0; i < this.monster_max; i++) {
            try {
                monster[i] = new Actor("data/monster00", 218, 313);
                monster[i].visible = false; //default

            } catch (SlickException e) {
            }
        }
        for (int i = 0; i < this.follower_max; i++) {
            try {
                follower[i] = new Actor("data/girl01", 218, 313);
                follower[i].visible = false;
                follower[i].direction = follower[i].getEast();
            } catch (SlickException e) {
            }
        }
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int gid = this.getTileId(x, y, actor_layer);
                if (gid > 0) {
                    //System.out.println(this.getTileId(x, y, actor_layer));
                    String pname = this.getTileProperty(gid, "actor_name", "none");
                    String actor_spotted = this.getTileProperty(gid, "actor_spotted", "true");
                    String mission_goal = this.getTileProperty(gid, "event_goal", "no");
                    if (mission_goal.equalsIgnoreCase("yes")) {
                        this.mission_goal = new Image("data/" + this.getTileProperty(gid, "event_goal_graphic", "papers.png"));
                        //set the X/y
                        this.draw_goal_x = x;
                        this.draw_goal_y = y;
                    }
                    if (pname.equals("player")) {
                        this.player.tilex = x;
                        this.player.tiley = y;
                        this.player.name = "Riku"; //pname;
                        this.player.changeActorSpritesheet("data/" + this.getTileProperty(gid, "costume", "player00"), 218, 313);
                        //} else if (this.getTileProperty(gid, "actor_name", "none").equals("pear monster")) {
                    } else if (pname.equals("Yukari")) {
                        try {
                            follower[follower_loop].changeActorSpritesheet("data/girl01", 218, 313);
                            //follower[follower_loop].
                            follower[follower_loop].tilex = x;
                            follower[follower_loop].tiley = y;
                            follower[follower_loop].visible = true;
                            follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("Ichi")) {
                        try {
                            follower[follower_loop].changeActorSpritesheet("data/boy00", 218, 313);
                            //follower[follower_loop].
                            follower[follower_loop].tilex = x;
                            follower[follower_loop].tiley = y;
                            follower[follower_loop].visible = true;
                            follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("Takeshi")) {
                        try {
                            follower[follower_loop].changeActorSpritesheet("data/boy01", 218, 313);
                            follower[follower_loop].tilex = x;
                            follower[follower_loop].tiley = y;
                            follower[follower_loop].visible = true;
                            follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }

                    } else if (pname.equals("Officer Ayano")) {
                        try {
                            follower[follower_loop].changeActorSpritesheet("data/police01", 218, 313);
                            follower[follower_loop].tilex = x;
                            follower[follower_loop].tiley = y;
                            follower[follower_loop].visible = true;
                            follower[follower_loop].name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("tutor_bully0")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/boy00", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            monster[monster_loop].spotted_enemy = false;
                        } else {
                            monster[monster_loop].spotted_enemy = true;
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 100;
                        monster_loop++;
                    } else if (pname.equals("tutor_bully1")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/girl03", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            monster[monster_loop].spotted_enemy = false;
                        } else {
                            monster[monster_loop].spotted_enemy = true;
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 100;
                        monster_loop++;
                    } else if (pname.equals("pear monster")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster00", 218, 313);
                        } catch (SlickException e) {
                        }
                        if (actor_spotted.equalsIgnoreCase("false")) {
                            monster[monster_loop].spotted_enemy = false;
                        } else {
                            monster[monster_loop].spotted_enemy = true;
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 4;
                        monster_loop++;
                    } else if (pname.equals("skeleton")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster05", 218, 313);
                        } catch (SlickException e) {
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 0;
                        monster_loop++;
                    } else if (pname.equalsIgnoreCase("zombie1")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster10", 218, 313);
                        } catch (SlickException e) {
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 2;
                        monster_loop++;
                    } else if (pname.equalsIgnoreCase("zombie2")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster11", 218, 313);
                        } catch (SlickException e) {
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 2;
                        monster_loop++;
                    } else if (pname.equals("invisible man")) {
                        System.out.println("we got to the invnisible man");
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster06", 218, 313);
                        } catch (SlickException e) {
                            System.out.println("something is wrong.");
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 4;
                        //set dodge scores
                        monster_loop++;
                    }// add more monsters here;
                }
            }
            for (int i = 0; i < 5; i++) {
                this.planning[i] = this.getMapProperty("planning_" + i, "end");
                if (this.planning[i].equalsIgnoreCase("end")) {
                    this.maxplanevent = i; //last one
                }
                this.charbusts[i] = new Image("data/" + this.getMapProperty("planning_" + i + "_p", "prt_player_00.png"));
            }
        }

        for (int i = 0; i < 5; i++) {
            this.planning[i] = this.getMapProperty("planning_" + i, "end");
            if (this.planning[i].equalsIgnoreCase("end")) {
                this.maxplanevent = i - 1; //last one
            }
            this.charbusts[i] = new Image("data/" + this.getMapProperty("planning_" + i + "_p", "prt_player_00.png"));
        }
        this.next_map = this.getMapProperty("nextmap", "none");
        this.RequiresGoal = this.getMapProperty("req_goal", "no");
        this.EventSpotted = this.getMapProperty("event_spotted", "none");
        if (!this.EventSpotted.equalsIgnoreCase("none")) { //if not none, load the event spotted
            this.EventSpotted_m = this.getMapProperty("event_spotted_m", "none");
            this.EventSpotted_p = new Image("data/" + this.getMapProperty("event_spotted_p", "prt_player_00.png"));
        }
    }

    public void onNewTrigger(String type, String name) {
        try {
            this.active_trigger = new Trigger(type,
                    name);
        } catch (SlickException n) {
        }
        //return
    }

    //public Actor getFollowerByXy(int x, int y)
    public void setFollowerDirectives() {
        //loop through your monsters and set a path for them to follow
        //if they are controllable, they shall already have destinations.
        this.current_follower_moving = 0;
        for (int i = 0; i < this.follower_max; i++) {
            if (follower[i].visible == true) {
                follower[i].action_points = 6 + follower[i].stat_speed - 1;
                follower[i].setActorMoving(false);
                follower[i].setActorDestination(follower[i].tilex,
                        follower[i].tiley);
            }
        }
    }

    public void drawFollowers(HorrorTactics ht, int x, int y, Graphics g) {
        //map.monster[0].drawActor(this, map, x, y);
        for (int i = 0; i < this.follower_max; i++) {
            if (this.follower[i].visible == true) { //just to be sure
                this.follower[i].drawActor(ht, this, x, y, g);
            }
        }
    }

    public void drawMonsters(HorrorTactics ht, int x, int y, Graphics g) {
        //map.monster[0].drawActor(this, map, x, y);
        for (int i = 0; i < this.monster_max; i++) {
            if (this.monster[i].visible == true) { //just to be sure
                //energy bar here?

                this.monster[i].drawActor(ht, this, x, y, g);
                if (this.monster[i].tilex == x && this.monster[i].tiley == y) {
                    if (this.EventSpotted_ran == false) {
                        this.EventSpotted_ran = true; //they are "spotted"
                        //run event spotted
                        System.out.println("debug: event spotted ran");
                        this.old_turn_order = this.turn_order;
                        this.turn_order = "event spotted";
                    }
                }
            }
        }
    }

    public void setMonsterToActorDestination(Actor monster, Actor player) {
        if (player.tilex == monster.tilex && player.tiley > monster.tiley) {
            monster.tiledestx = player.tilex - 1;
            monster.tiledesty = player.tiley;
        } else if (player.tilex == monster.tilex && player.tiley < monster.tiley) {
            monster.tiledestx = player.tilex + 1;
            monster.tiledesty = player.tiley;
        } else if (player.tilex > monster.tilex && player.tiley == monster.tiley) {
            monster.tiledestx = player.tilex;
            monster.tiledesty = player.tiley - 1;
        } else if (player.tilex < monster.tilex && player.tiley == monster.tiley) {
            monster.tiledestx = player.tilex;
            monster.tiledesty = player.tiley + 1;
        } //need the other 4?
    }

    public boolean monsterfollowerInTile(int x, int y) {
        for (int i = 0; i < this.monster_max; i++) {
            if (x == monster[i].tilex && y == monster[i].tiley) {
                return true;
            }
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (x == follower[i].tilex && y == follower[i].tiley) {
                return true;
            }
        }
        return false;
    }

    public boolean getPassableTile(Actor a, int x, int y) {
        //true=go, false = stop
        //int tdestx = a.tilex+a.facing_x;
        //int tdesty = a.tiley+a.facing_y;
        int walls_layer = getLayerIndex("walls_layer");
        if (getTileImage(x, y, walls_layer) == null) { //There are no walls.
            if (this.turn_order.equals("monster")) { //does monster collide with someone.
                if (this.monsterfollowerInTile(x, y) == true) {
                    return false; //encountered a monster or follower?
                }
                if (x == this.player.tilex && y == this.player.tiley) {
                    return false; //found player.
                }
            }

            return true; //There are no walls.
        }
        //System.out.println("Encountered a wall");
        return false;//there are walls
    }

    //return false; //there might be a wall
    public void onClickOnMap(int mouse_tile_x, int mouse_tile_y) { //given Mouse Pixels, decide what to do
        //did we click on the players rectangle as it is rendered in the map
        if (mouse_tile_x == player.tilex && mouse_tile_y == player.tiley) //if(pixelX >= 0 && pixelY >=0 && pixelX <= 128 && pixelY <=128)
        { //you clicked on player now set the player to selected, and mode to
            if (player.isSelected() == false) {
                player.onSelectActor(true); //omg you selected an actor!
            } else {
                player.onSelectActor(false);
            }
        }
    }

    public int getScreenToIsoX(int screenx, int screeny, HorrorTactics ht) {
        int isoX = (screenx / TILE_WIDTH_HALF + screeny / TILE_HEIGHT_HALF) / 2;
        return isoX;
    }

    public int getScreenToIsoY(int screenx, int screeny, HorrorTactics ht) {
        int isoY = (screeny / TILE_HEIGHT_HALF - (screenx / TILE_WIDTH_HALF)) / 2;
        return isoY;
    }

    public int getIsoXToScreen(int x, int y) {
        int posX = (x - y) * (250 / 2);
        return posX;
    }

    public int getIsoYToScreen(int x, int y) {
        //int posX = ( x - y) * 250;
        //int posY = (x + y) * 129 / 2;
        int posY = (x + y) * 130 / 2;
        return posY;
    }

    public boolean getAnyActorMoving() //is anyone moving? return true
    { ///hack, if you are dead you are not moving!
        boolean m = false;
        if (this.player.getActorMoving() == true) {
            System.out.print("player was still moving");
            if (this.player.dead == false) {
                return true;
            }
        } else {
            m = false;
        }
        for (int i = 0; i < monster_max; i++) {
            if (this.monster[i].dead == false && this.monster[i].getActorMoving() == true) {
                System.out.print("Monster [" + i + "] was still moving\n");
                if (this.monster[i].dead == false) {
                    return true;
                }
            } else {
                m = false;
            }
        }
        for (int i = 0; i < follower_max; i++) {
            if (this.follower[i].getActorMoving() == true) {
                System.out.print("Follower [" + i + "] was still moving\n");
                if (this.follower[i].dead == false) {
                    return true;
                }
            } else {
                m = false;
            }
        }
        return m;
    }

    public boolean isActorTouchingActor(Actor a, Actor b, int x, int y) {
        //a=monster, b=player //x, and y not used?
        if (a.tilex - 1 == b.tilex && a.tiley == b.tiley) {
            //a.tiledestx = b.tilex;
            //a.tiledesty = b.tiley;
            return true;
        } else if (a.tilex + 1 == b.tilex && a.tiley == b.tiley) {
            //a.tiledestx = b.tilex;
            //a.tiledesty = b.tiley;
            return true;
        } else if (a.tilex == b.tilex && a.tiley - 1 == b.tiley) {
            //a.tiledestx = b.tilex;
            //a.tiledesty = b.tiley;
            return true;
        } else if (a.tilex == b.tilex && a.tiley + 1 == b.tiley) {
            //a.tiledestx = b.tilex;
            //a.tiledesty = b.tiley;
            return true; //you are touching the player.
        }
        return false;
    }

    public boolean isPlayerTouchingMonster() {
        for (int i = 0; i < this.monster_max; i++) { //for(int i)
            if (this.isActorTouchingActor(player, monster[i], player.tilex, player.tiley)) {
                return true;
            }
        }
        return false;
    }

    public boolean getPlayerFacingMonInDir(Actor p, Actor m, int direction, int x, int y) {
        if (p.direction == direction
                && p.tilex + x == m.tilex
                && p.tiley + y == m.tiley) {
            return true;
        }
        return false;
    }

    public boolean getPlayerFacingMonster(Actor p) {
        if (this.isPlayerTouchingMonster() == true) {
            for (int i = 0; i < this.monster_max; i++) {
                if (getPlayerFacingMonInDir(p, monster[i], p.getEast(), 1, 0)) {
                    return true;
                }
                if (getPlayerFacingMonInDir(p, monster[i], p.getWest(), -1, 0)) {
                    return true;
                }
                if (getPlayerFacingMonInDir(p, monster[i], p.getNorth(), 0, -1)) {
                    return true;
                }
                if (getPlayerFacingMonInDir(p, monster[i], p.getSouth(), 0, 1)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean isMonsterTouchingYou(Actor a) {
        //given tilex, tiley, return true if any items in grid are touching you.
        if (this.isActorTouchingActor(a, this.player, a.tilex, a.tiley)) {//you are touching the player.
            return true;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.isActorTouchingActor(a, this.follower[i], a.tilex, a.tiley)) { //you are touching the player.
                return true;
            } //returned true, and set monster dest to the victims tilex,tily
        }
        return false; //nobody touching monster, 
    }

    public boolean getActorAtXy(Actor a, int x, int y) {
        if (a.tilex == x && a.tiley == y) {
            return true;
        } else {
            return false;
        }
    }

    Actor getAllPlayersAtXy(int x, int y) {
        Actor t;
        t = null;
        if (this.getActorAtXy(player, x, y)) {
            t = player;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.getActorAtXy(follower[i], x, y)) {
                t = this.follower[i];
            }
        }
        for (int i = 0; i < this.monster_max; i++) {
            if (this.getActorAtXy(monster[i], x, y)) {
                t = this.monster[i];
            }
        }
        return t; //found nothing
    }

    Actor getAllMonstersAtXy(int x, int y) {
        for (int i = 0; i < this.monster_max; i++) {
            if (this.getActorAtXy(monster[i], x, y)) {
                return this.monster[i];
            } else {
                return null;
            }
        }
        return null;
    }

    public void onActorAttackActor(HorrorTactics ht, Actor attacker, Actor defender) {
        int target_parryroll;
        int actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + attacker.stat_luck - 1;
        int target_dodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + defender.stat_luck - 1;
        int damage_roll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + attacker.stat_str - 1;
        //int target_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        System.out.println("target check " + defender.name);
        attacker.setAnimationFrame(4);
        attacker.attack_timer = 25;
        if (defender.canparry) {
            target_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        } else {
            target_parryroll = 0;
        }
        if (actor_attackroll > target_dodgeroll && actor_attackroll > target_parryroll + defender.parryscore) {
            defender.health_points -= damage_roll;
            if (defender.health_points <= 0) {
                defender.dead = true;
                defender.action_msg = " " + damage_roll + " ";
                defender.action_msg_timer = 200;
                defender.turns_till_revival = 0; //do we revive?
                log_msg = attacker.name + " attacks " + defender.name
                        + "(1d6 =" + actor_attackroll + ")" + ",(1d6 =" + target_dodgeroll + ") and hits for " + damage_roll + " points of damage, killing " + defender.name;
                attacker.exp_points += 3;
            } else {
                log_msg = attacker.name + " attacks " + defender.name
                        + "(1d6 =" + actor_attackroll + ")" + ",(1d6 =" + target_dodgeroll + ") and hits for " + damage_roll + " points of damage.";
            }

        } else {
            defender.action_msg = "Miss";
            defender.action_msg_timer = 200;
            if (target_parryroll + defender.parryscore > actor_attackroll) {
                log_msg = attacker.name + " attacks " + defender.name
                        + "(1d6 =" + actor_attackroll + ",(1d6 =" + target_parryroll + " + " + defender.parryscore
                        + ") but the attack was parried.";
            } else {
                log_msg = attacker.name + " attacks " + defender.name
                        + "(1d6 =" + actor_attackroll + ",(1d6 =" + target_dodgeroll + ") and misses";
            }
        }
        //we already checke for action points, not remove them
        attacker.action_points -= 3;
        attacker.fatigue_points -= 1;
        if (attacker.fatigue_points < 0) {
            attacker.fatigue_points = 0;
        }
        defender.fatigue_points -= 1;
        if (defender.fatigue_points < 0) {
            defender.fatigue_points = 0;
        }
        if (attacker.action_points < 0) {
            attacker.action_points = 0;
        }
    }

    public void onMonsterCanAttack(GameContainer gc, HorrorTactics ht) {
        if (this.isMonsterTouchingYou(monster[this.current_monster_moving])) {
            Actor t = this.getAllPlayersAtXy(
                    monster[this.current_monster_moving].tiledestx,
                    monster[this.current_monster_moving].tiledesty);
            if (t == null) {
                System.out.println("woa something is wrong monster target is null");
            } else {
                System.out.println("t is not null, found " + t.name);
            }
            monster[this.current_monster_moving].onAttack(ht);
            this.onActorAttackActor(ht, monster[this.current_monster_moving], t);
            //player at the monster destination is not null

        } else {
            System.out.println("Monster " + this.current_monster_moving + " found nobody there?");
        }//nobody was there.
    }

    public boolean getMonsterIsMoving(int i) {
        if (this.monster[i].action_points <= 0) {
            return false;
        }
        if (this.monster[i].visible == false) {
            return false;
        }
        if (this.monster[i].dead == true) {
            return false;
        }
        if (this.monster[i].getActorMoving() == false) {
            return false;
        }
        //if (this.getPassableTile(this.monster[i], i, i))
        return true; //all conditions are good.
    }

    public void onFollowerMoving(GameContainer gc, HorrorTactics ht, int delta) { //taken from update.
        //this.follower[this.current_follower_moving].onMoveActor(
        //        this, gc.getFPS());
        for (int i = 0; i < this.follower_max; i++) {
            this.follower[i].onMoveActor(this, gc.getFPS());
        }
        //if (this.follower[this.current_follower_moving].dead == true) {
        //    this.current_monster_moving++;
        //}
        //if (this.follower[this.current_follower_moving].getActorMoving()
        //        == false) {
        //Why did follower stop? (because you turn him to attack)
        //this.whyDidMonsterStop(gc, ht);
        //}
        //if (this.current_follower_moving >= this.follower_max) {
        //    this.current_follower_moving = 0;
        //this.turn_order = "start player";
        //we still start monster in another way.
        //}
    }

    public void onMonsterMoving(GameContainer gc, HorrorTactics ht, int delta) { //taken from update.
        if (monster[0].spotted_enemy == true) {
            this.monster[this.current_monster_moving].onMoveActor(
                    this, gc.getFPS());
            if (this.monster[this.current_monster_moving].dead == true) {
                this.current_monster_moving++;
            }
            if (this.monster[this.current_monster_moving].getActorMoving()
                    == false) {
                this.whyDidMonsterStop(gc, ht);
            }
            if (this.current_monster_moving >= this.monster_max) {
                this.current_monster_moving = 0;
                this.turn_order = "start player";
            }
        } else {
            //this.current_monster_moving = 0;
            this.monster[this.current_monster_moving].stopMoving();
            this.current_monster_moving++;
            if (this.current_monster_moving >= this.monster_max) {
                this.current_monster_moving = 0;
                this.turn_order = "start player";
            }
        }
    }

    void onUpdateActorActionText() {
        if (player.action_msg_timer > 0) {
            player.action_msg_timer--;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (follower[i].action_msg_timer > 0) {
                follower[i].action_msg_timer--;
            }
        }
        for (int i = 0; i < this.monster_max; i++) {
            if (monster[i].action_msg_timer > 0) {
                monster[i].action_msg_timer--;
            }
        }
    }

    //map.monster[0].drawActor(this, map, x, y);
    public int getDistanceOfActors2(Actor a, Actor b) {
        int xs = (a.tilex - b.tilex) * (a.tilex - b.tilex);
        int ys = (a.tiley - b.tiley) * (a.tiley - b.tiley);

        double dsr = Math.sqrt(xs + ys);
        int distance = (int) dsr;
        return distance;
    }

    public void setMonsterDirectives() {
        //loop through your monsters and set a path for them to follow
        //directive types: random,randomuntilspotted,beeline
        int proposed_x, proposed_y;
        this.current_monster_moving = 0;
        for (int i = 0; i < this.monster_max; i++) {
            if (monster[i].visible == true && monster[i].dead == false) { //there was a monster here(and alive)
                monster[i].action_points = 6; //update action points
                monster[i].setActorMoving(true);
                monster[i].setActorDestination(monster[i].tilex, monster[i].tiley);//there initial destination is their pos
            } else {
                monster[i].setActorMoving(false);
                monster[i].action_points = 0;
            }
            if (monster[i].max_turns_till_revival > 0 && monster[i].dead == true) {
                if (monster[i].turns_till_revival >= monster[i].max_turns_till_revival) {
                    monster[i].dead = false;
                    monster[i].turns_till_revival = 0;
                } else {
                    monster[i].turns_till_revival++;
                }
            }
        }
        for (int i = 0; i < this.monster_max; i++) {
            if (monster[i].visible == true) { //there was a monster here.
                monster[i].action_points = 6; //update action points
                if (monster[i].directive_type.equalsIgnoreCase("beeline")) {
                    //how can we make the monster's dest to the next to player/not the players location
                    //this will prvent a forced stop.  How do we calulate the shortest distance to travel
                    //Euclidian plane? find the shortest distance, then make the route
                    System.out.print("Spotted player: " + monster[i].spotted_enemy + "\n");

                    if (monster[i].spotted_enemy == true) { // you must spot the enemy to beline
                        monster[i].tiledestx = player.tilex;
                        monster[i].tiledesty = player.tiley;
                        int best_distance = this.getDistanceOfActors2(player, monster[i]);
                        int try_distance = 0;
                        for (int d = 0; d < this.follower_max; d++) {
                            if (follower[d].visible == true) {
                                try_distance = this.getDistanceOfActors2(follower[d], monster[i]);
                                if (try_distance < best_distance) {
                                    monster[i].tiledestx = follower[d].tilex;
                                    monster[i].tiledesty = follower[d].tiley;
                                }
                            }
                        }
                    } else {
                        monster[i].tiledestx = monster[i].tilex;
                        monster[i].tiledesty = monster[i].tiley;
                    }

                    //monster[i].setActorMoving(true);
                } else if (monster[i].directive_type.equalsIgnoreCase("random")) { //randomly move around.
                    for (int count = 0; count < 6; count++) {
                        proposed_y = (int) Math.floor(Math.random()) - (int) Math.floor(Math.random());
                        proposed_x = (int) Math.floor(Math.random()) - (int) Math.floor(Math.random());
                        System.out.print("proposed_x: " + Integer.toString(proposed_x)
                                + " proposed_y: " + Integer.toString(proposed_y));
                        if (this.getPassableTile(monster[i], monster[i].tilex + proposed_x,
                                monster[i].tiley + proposed_y) == true) {
                            monster[i].setActorDestination(monster[i].tilex + proposed_x,
                                    monster[i].tiley + proposed_y);
                        }
                    }
                }
            }
        }
    }

    public void resetAttackAniFrame() {
        for (int i = 0; i < this.monster_max; i++) {
            if (this.monster[i].attack_timer > 0) {
                this.monster[i].attack_timer--;
                //this.monster[i].setAnimationFrame(0);
            } else if (this.monster[i].getAnimationFrame() == 4) {
                this.monster[i].setAnimationFrame(0);
            }
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.follower[i].attack_timer > 0) {
                this.follower[i].attack_timer--;
                //this.monster[i].setAnimationFrame(0);
            } else if (this.follower[i].getAnimationFrame() == 4) {
                this.follower[i].setAnimationFrame(0);
            }
        }
        if (this.player.attack_timer > 0) {
            this.player.attack_timer--;
        } else if (this.player.getAnimationFrame() == 4) {
            this.player.setAnimationFrame(0);
        }
    }

    public void whyDidMonsterStop(GameContainer gc, HorrorTactics ht) {
        System.out.println("Monster stopped with remaining AP");
        if (this.monster[this.current_monster_moving].visible == false) {
            this.current_monster_moving++;
        } else if (this.monster[this.current_monster_moving].dead == true) {
            this.current_monster_moving++;
        } else if (this.monster[this.current_monster_moving].action_points >= 2) {
            this.onMonsterCanAttack(gc, ht); //Do we see the player?
            this.monster[this.current_monster_moving].action_points = 0;
            //this.monster[this.current_monster_moving].setAnimationFrame(0);
            //we can also try doing this at the beginning of update.
            this.current_monster_moving++;
        } else if (this.getPassableTile(monster[this.current_monster_moving],
                monster[this.current_monster_moving].tilex
                + monster[this.current_monster_moving].facing_x,
                monster[this.current_monster_moving].tiley
                + monster[this.current_monster_moving].facing_y) == false) {
            this.current_monster_moving++;
        } else if (this.monster[this.current_monster_moving].action_points <= 0) {
            this.current_monster_moving++;
        } else if (this.monster[this.current_monster_moving].tilex == this.monster[this.current_monster_moving].tiledestx
                && this.monster[this.current_monster_moving].tiley == this.monster[this.current_monster_moving].tiledesty) {
            this.current_monster_moving++;
        } else if (this.monster[this.current_monster_moving].getActorMoving() == false) {
            //generic stop
            this.current_monster_moving++;
        } else {//you stopped and cant do anything anyways
            System.out.println("Monster had not enough points to attack");
            this.monster[this.current_monster_moving].action_points = 0;
            this.current_monster_moving++;
        }
        //allmonstersmoved = false;
    }

    public void set_party_min_renderables() {
        int current_min_y = 0;
        int current_min_x = 0;
        int current_max_y = 0;
        int current_max_x = 0;
        if (this.player.tiley < current_min_y) {
            current_min_y = this.player.tiley;
        }
        if (this.player.tilex < current_min_x) {
            current_min_y = this.player.tilex;
        }
        if (this.player.tiley > current_max_y) {
            current_max_y = this.player.tiley;
        }
        if (this.player.tilex > current_max_x) {
            current_max_x = this.player.tilex;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.follower[i].tiley < current_min_y) {
                current_min_y = this.follower[i].tiley;
            }
            if (this.follower[i].tilex < current_min_x) {
                current_min_x = this.follower[i].tilex;
            }
            if (this.follower[i].tiley > current_max_y) {
                current_max_y = this.follower[i].tiley;
            }
            if (this.follower[i].tilex > current_max_x) {
                current_max_x = this.follower[i].tilex;
            }
        }
        this.render_min_y = current_min_y;
        this.render_min_x = current_min_x;
        this.render_max_y = current_max_y;
        this.render_max_x = current_max_x;
    }

    void mouse_over_actor(int x, int y) {
        if (this.player.tilex == x && this.player.tiley == y) {
            this.player.ismouse_over = true;
        } else {
            this.player.ismouse_over = false;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.follower[i].tilex == x && this.follower[i].tiley == y) {
                this.follower[i].ismouse_over = true;
            } else {
                this.follower[i].ismouse_over = false;
            }
        }
        for (int i = 0; i < this.monster_max; i++) {
            if (this.monster[i].tilex == x && this.monster[i].tiley == y) {
                this.monster[i].ismouse_over = true;
            } else {
                this.monster[i].ismouse_over = false;
            }
        }

    }
}
