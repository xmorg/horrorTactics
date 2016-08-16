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
import java.lang.Math.*;

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
    String turn_order = null;

    Actor player = null;
    Actor[] follower = new Actor[follower_max];
    Actor[] monster = new Actor[monster_max];
    private int m_draw_x, m_draw_y; //map draw x
    int current_monster_moving = 0; //debug

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

        player = new Actor("data/tactics_in_distress00", 218, 313);
        turn_order = "start player";
    }

    @Override
    public void renderedLine(int visualX, int mapY, int layer) {
    }

    public void updateMapXY(int x, int y) {
        m_draw_x = x;
        m_draw_y = y;
    }

    public void getActorLocationFromTMX() {
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
                follower[i] = new Actor("data/tactics_in_distress00", 218, 313);
                follower[i].visible = false;
            } catch (SlickException e) {
            }
        }

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int gid = this.getTileId(x, y, actor_layer);
                if (gid > 0) {
                    System.out.println(this.getTileId(x, y, actor_layer));
                    if (this.getTileProperty(gid, "actor_name", "player").equals("player")) {
                        this.player.tilex = x;
                        this.player.tiley = y;
                    } else if (this.getTileProperty(gid, "actor_name", "pear monster").equals("pear monster")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster00.png", 218, 313);
                        } catch (SlickException e) {
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster_loop++;
                    } // add more monsters here;
                }
            }
        }
    }

    public void onMoveActor(GameContainer gc, Actor a, int delta) {
        /* map.player.getActorMoving() == true*/
        //a.speed_wait++;
        //if (a.speed_wait >= delta) {
        if (a.getActorMoving() == true && a.tiley > a.tiledesty) {
            this.onMoveNorth(gc, a, delta);
        } else if (a.getActorMoving() == true && a.tiley < a.tiledesty) {
            this.onMoveSouth(gc, a, delta);
        } else if (a.getActorMoving() == true && a.tilex < a.tiledestx) {
            this.onMoveEast(gc, a, delta);
        } else if (a.getActorMoving() == true && a.tilex > a.tiledestx) {
            this.onMoveWest(gc, a, delta);
        }

        if (a.tilex == a.tiledestx && a.tiley == a.tiledesty) {
            a.setActorMoving(false);
            a.setAnimationFrame(0);
        }
        if (a.action_points <= 0) {
            a.action_points = 0;
            a.setActorMoving(false);
            a.setAnimationFrame(0);
        }
        if (getPassableTile(a.tilex + a.facing_x, a.tiley + a.facing_y) == false) {
            //can we try to turn?
            a.setActorMoving(false);
            a.setAnimationFrame(0);
        }
        //a.speed_wait = 0;
        //}
    }

    public void setMonsterDirectives() {
        //loop through your monsters and set a path for them to follow
        //directive types: random,randomuntilspotted,beeline
        int proposed_x, proposed_y;
        this.current_monster_moving = 0;
        for (int i = 0; i < this.monster_max; i++) {
            if (monster[i].visible == true) { //there was a monster here.
                monster[i].action_points = 6; //update action points
                monster[i].setActorMoving(true);
                monster[i].setActorDestination(monster[i].tilex, monster[i].tiley);//there initial destination is their pos
            }
        }
        for (int i = 0; i < this.monster_max; i++) {
            if (monster[i].visible == true) { //there was a monster here.
                if (monster[i].directive_type.equalsIgnoreCase("random")) { //randomly move around.
                    for (int count = 0; count < 6; count++) {
                        proposed_y = (int) Math.floor(Math.random()) - (int) Math.floor(Math.random());
                        proposed_x = (int) Math.floor(Math.random()) - (int) Math.floor(Math.random());
                        System.out.print("proposed_x: " + Integer.toString(proposed_x)
                                + " proposed_y: " + Integer.toString(proposed_y));
                        if (this.getPassableTile(monster[i].tilex + proposed_x,
                                monster[i].tiley + proposed_y) == true) {
                            monster[i].setActorDestination(monster[i].tilex + proposed_x,
                                    monster[i].tiley + proposed_y);
                        }
                    }
                } else if (monster[i].directive_type.equalsIgnoreCase("beeline")) {
                    monster[i].tiledestx = player.tilex;
                    monster[i].tiledesty = player.tiley;
                    monster[i].action_points = 6; //update action points
                    monster[i].setActorMoving(true);
                }
            }
        }
    }

    public void onMoveWest(GameContainer gc, Actor a, int delta) {
        a.setActiorDirection(this, 3);
        a.draw_x -= 2;//(a.speed * delta) * 2; //a.speed;//delta * a.speed;
        a.draw_y -= 1;//(a.speed * delta);
        a.set_draw_xy(a.draw_x, a.draw_y);
        a.onWalkAnimation(gc);
        if (Math.abs(a.draw_x) >= this.TILE_WIDTH_HALF) {
            a.tilex--; //westr.
            a.set_draw_xy(0, 0);
            //a.setAnimationFrame(0);
            //a.setActionPoints(a.action_points--);
            a.action_points--;
        }
    }

    public void onMoveEast(GameContainer gc, Actor a, int delta) {
        a.setActiorDirection(this, 0);
        a.draw_x += 2;//(a.speed * delta) * 2; //a.speed;//delta * a.speed;
        a.draw_y += 1;//(a.speed * delta);
        a.set_draw_xy(a.draw_x, a.draw_y);
        a.onWalkAnimation(gc);
        if (a.draw_x >= this.TILE_WIDTH_HALF) {
            a.tilex++; //westr.
            a.set_draw_xy(0, 0);
            //a.setAnimationFrame(0);
            a.action_points--;
        }
    }

    public void onMoveSouth(GameContainer gc, Actor a, int delta) {
        a.setActiorDirection(this, 1);
        a.draw_y += 1;//(a.speed * delta); //a.speed;
        a.draw_x -= 2;//(a.speed * delta) * 2;
        a.set_draw_xy(a.draw_x, a.draw_y);
        a.onWalkAnimation(gc);
        if (a.draw_y >= Math.abs(this.TILE_HEIGHT_HALF)) {
            a.tiley++; //south.
            a.set_draw_xy(0, 0);
            //a.setAnimationFrame(0);
            a.action_points--;
        }
    }

    public void onMoveNorth(GameContainer gc, Actor a, int delta) {
        a.setActiorDirection(this, 2);
        a.draw_y -= 1;//(a.speed * delta);//a.speed;
        a.draw_x += 2;//(a.speed * delta) * 2;//a.speed*2;
        System.out.println("delta: " + delta + "," + a.draw_x + "," + a.draw_y);
        a.set_draw_xy(a.draw_x, a.draw_y);
        a.onWalkAnimation(gc);
        if (Math.abs(a.draw_y) >= this.TILE_HEIGHT_HALF) {
            a.tiley--; //north.
            a.set_draw_xy(0, 0);
            //a.setAnimationFrame(0);
            a.action_points--;
        }
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

    public boolean getPassableTile(int x, int y) {
        //true=go, false = stop
        int walls_layer = getLayerIndex("walls_layer");
        if (getTileImage(x, y, walls_layer) == null) { //we know doorways are bugged
            if (x == player.tilex && y == player.tiley) {
                return false;
            } else if (this.monsterfollowerInTile(x, y)) {
                //queue the ATTACKS!
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

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
        int posY = (x + y) * 129 / 2;
        return posY;
    }

    public boolean getAnyActorMoving() //is anyone moving? return true
    {
        boolean m = false;
        if (this.player.getActorMoving() == true) {
            return true;
        } else {
            m = false;
        }
        for (int i = 0; i < monster_max; i++) {
            if (this.monster[i].getActorMoving() == true) {
                return true;
            } else {
                m = false;
            }
        }
        for (int i = 0; i < follower_max; i++) {
            if (this.follower[i].getActorMoving() == true) {
                return true;
            } else {
                m = false;
            }
        }

        return m;
    }
}
