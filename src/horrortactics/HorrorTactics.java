/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Bug! array out of bounds when reaching the edge of the map
 */
package horrortactics;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
//import org.newdawn.slick.SpriteSheet;
//import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;

/**
 *
 * @author tcooper
 */
public class HorrorTactics extends BasicGame {

    /**
     * @param gamename the command line arguments
     */
    public Input input;
    private MyTiledMap map;
    private MouseActions msa;
    private KeyActions ksa;
    int draw_x, draw_y = 0;
    /* offset for camera scrolling*/
    int screen_x, screen_y;
    /* where on the screen?*/
    int mouse_x, mouse_y = 0;
    int mouse_tile_x, mouse_tile_y = 0;
    int fps = 0;
    int delta;
    int actor_move_timer;
    long currentTime, lastTime;
    float scale_x = 1;
    int screen_width = 0;
    int screen_height = 0;
    long lastframe;
    int turn_count;
    Color myfilter, myfiltert, myfilterd;
    Image button_endturn, button_punch;
    Image effect_biff, effect_wiff, effect_shrack;
    Image enemy_moving_message;

    String game_state = "tactical"; //title, tactical,conversation,cutscene

    public HorrorTactics(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        map = new MyTiledMap("data/class_school01.tmx", 0, 0);
        msa = new MouseActions();
        ksa = new KeyActions();
        //input = new Input(gc.getHeight());
        map.getActorLocationFromTMX();
        fps = gc.getFPS();
        actor_move_timer = 0;
        this.lastTime = 0;
        this.lastframe = 0;
        this.turn_count = 0;
        this.currentTime = gc.getTime();
        map.tiles250x129 = new Image("data/tiles250x129.png");
        screen_height = gc.getScreenHeight();
        screen_width = gc.getScreenWidth();
        draw_x = 800; //= map.getIsoXToScreen(map.getPlayerX(), map.getPlayerY()) * -1;
        draw_y = 0; //map.getIsoYToScreen(map.getPlayerX(), map.getPlayerY()) * -1;
        this.lastframe = gc.getTime();
        button_endturn = new Image("data/button_endturn.png");
        button_punch = new Image("data/button_punch.png");
        effect_biff = new Image("data/soundeffects/biff.png");
        effect_wiff = new Image("data/soundeffects/wiff.png");
        effect_shrack = new Image("data/soundeffects/shrack.png");
        enemy_moving_message = new Image("data/enemy_moving.png");
        myfilter = new Color(1f, 1f, 1f, 1f);
        myfiltert = new Color(0.5f, 0.5f, 0.5f, 0.5f);
        myfilterd = new Color(0.2f, 0.2f, 0.2f, 0.8f); //for darkness/fog
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();
        mouse_x = input.getMouseX();
        mouse_y = input.getMouseY();
        msa.mouseWasClicked(input, map, this); //Do mouse actions
        ksa.getKeyActions(gc, input, this); //Do keyboard actions
        map.updateMapXY(draw_x, draw_y);
        actor_move_timer++;

        map.resetAttackAniFrame();
        if (actor_move_timer >= this.fps) {
            this.actor_move_timer = 0;
        }
        if (map.player.dead == true) {
            map.turn_order = "game over";
        }
        if (map.turn_order.equalsIgnoreCase("game over")) {
            this.game_state = "game over";
        } else if (map.turn_order.equalsIgnoreCase("player")) {
            if (this.actor_move_timer == 0) {
                if (map.player.selected == true) {
                    map.player.onMoveActor(map, gc.getFPS());//this.getMyDelta(gc));
                } else {
                    map.onFollowerMoving(gc, this, delta);
                }
            }
            if (this.map.active_trigger.name.equals("none")) { //not already stepped in it
                map.active_trigger.onSteppedOnTrigger(map, this.map.player.tilex, this.map.player.tiley);
            } // how do we make it null again?
        } else if (map.turn_order.equalsIgnoreCase("start follower")) { //
            this.map.setFollowerDirectives();
            map.turn_order = "follower";
        } else if (map.turn_order.equalsIgnoreCase("follower")) {

        } else if (map.turn_order.equalsIgnoreCase("start player")) {
            map.player.action_points = 100; //6; //DEBUG
            //give followers action points.
            this.map.setFollowerDirectives();
            map.turn_order = "player";
            
        } else if (map.turn_order.equalsIgnoreCase("start monster")) {
            this.map.setMonsterDirectives();
            map.turn_order = "monster";
        } else if (map.turn_order.equalsIgnoreCase("monster")) {
            if (this.actor_move_timer == 0) {
                map.resetAttackAniFrame();
                map.onMonsterMoving(gc, this, delta); //wrapper for onMoveActor
            }
        }
        map.onUpdateActorActionText();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (game_state.equalsIgnoreCase("tactical")) {
            g.scale(scale_x, scale_x); //scale the same
            this.render_background_layer(gc, g); //render floor
            this.render_walls_layer(gc, g);      //render walls (and actors!)
            this.render_game_ui(gc, g);
        } else if (game_state.equalsIgnoreCase("conversation")) {
            g.scale(scale_x, scale_x); //scale the same
            this.render_background_layer(gc, g); //render floor
            this.render_walls_layer(gc, g);      //render walls (and actors!)
            this.render_game_ui(gc, g);
            this.render_character_busts(gc, g); //bring up the busts, and talk
        } else if (game_state.equalsIgnoreCase("cutscene")) {
            this.render_cutscene(gc, g); //animations?
        } else if (game_state.equalsIgnoreCase("title")) {
            this.render_title(gc, g);

        } else if (game_state.equalsIgnoreCase("game over")) {
            this.render_game_over(gc, g);  //its game over for your kind
        }
    }

    public void render_background_layer(GameContainer gc, Graphics g) {
        int background_layer = map.getLayerIndex("background_layer");
        for (int y = map.player.tiley - 4; y < map.player.tiley + 4; y++) {
            for (int x = map.player.tilex - 4; x < map.player.tilex + 4; x++) {
                screen_x = (x - y) * map.TILE_WIDTH_HALF;
                screen_y = (x + y) * map.TILE_HEIGHT_HALF;
                if (this.getTileToBeRendered(x, y)) {
                    if (this.getTileToBeFiltered(x, y)) {//if its outside 2 steps
                        //java.lang.ArrayIndexOutOfBoundsException: 20 
                        try {
                            Image xi = map.getTileImage(x, y, background_layer);
                            //map.getTileImage(x, y, background_layer).draw(
                            xi.draw(screen_x + draw_x, screen_y + draw_y, scale_x, this.myfilterd);
                        } catch (ArrayIndexOutOfBoundsException a) {
                        }
                    } else { //draw normal
                        map.getTileImage(x, y, background_layer).draw(
                                screen_x + draw_x, screen_y + draw_y, scale_x);
                    }
                }

            }
        }
    }

    public void render_wall_by_wall(GameContainer gc, Graphics g, int x, int y) {
        int walls_layer = map.getLayerIndex("walls_layer");
        try {
            if (map.getTileImage(x, y, walls_layer) == null) {
            } else if (this.wall_intersect_player(x, y, screen_x, screen_y) == true) {
                map.getTileImage(x, y, walls_layer).draw(
                        screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfiltert);
            } else //inside cannot be dark
             if (x < map.player.tilex - map.light_level
                        || x > map.player.tilex + map.light_level
                        || y < map.player.tiley - map.light_level
                        || y > map.player.tiley + map.light_level) {
                    if (this.getTileToBeRendered(x, y)) {
                        map.getTileImage(x, y, walls_layer).draw(
                                screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfilterd);
                    }
                } else {
                    map.getTileImage(x, y, walls_layer).draw(
                            screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfilter);
                }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void render_walls_layer(GameContainer gc, Graphics g) {
        for (int y = map.player.tiley - 4; y < map.player.tiley + 4; y++) {
            for (int x = map.player.tilex - 4; x < map.player.tilex + 4; x++) {
                screen_x = (x - y) * map.TILE_WIDTH_HALF;
                screen_y = (x + y) * map.TILE_HEIGHT_HALF;
                if (x >= 0 && y >= 0 && x <= map.getTileWidth() && y <= map.getTileHeight()) {

                    mouse_x = gc.getInput().getMouseX();
                    mouse_y = gc.getInput().getMouseY();
                    int sx = screen_x + draw_x + 30;
                    int sy = screen_y + draw_y + 30;
                    if (mouse_x >= sx && mouse_x <= sx + 250 - 30
                            && mouse_y >= sy && mouse_y <= sy + 130 - 30) {
                        map.mouse_over_tile_x = x;
                        map.mouse_over_tile_y = y;
                        map.tiles250x129.getSubImage(0, 0, 250, 129).draw(
                                screen_x + draw_x, screen_y + draw_y, scale_x);
                    }
                    map.player.drawPlayer(this, map, x, y);
                    if (map.isPlayerTouchingMonster() && x == map.player.tilex
                            && y == map.player.tiley) { //we are touching monster?
                        this.button_punch.draw(this.screen_x + this.draw_x,
                                this.screen_y + this.draw_y - 200);
                    }
                    if (map.player.action_msg_timer > 0 && x == map.player.tilex && y == map.player.tiley) {
                        //g.drawString(map.player.action_msg, screen_x + draw_x,
                        //        screen_y + draw_y - 200);
                        this.getComicActionStrImage(map.player.action_msg).draw(screen_x + draw_x,
                                screen_y + draw_y - 200);
                    }
                    map.drawMonsters(this, x, y); //map.monster[0].drawActor(this, map, x, y);
                    map.drawFollowers(this, x, y);
                    if (this.getTileToBeRendered(x, y)) {
                        render_wall_by_wall(gc, g, x, y); //ArrayIndexOutOfBoundsException
                    }

                }
            }
        }
    }

    public void render_game_ui(GameContainer gc, Graphics g) {
        map.player.iconImage.draw(5, 50);
        g.setColor(myfilterd);
        g.fillOval(5, 50 + 75, 12, 14);
        g.setColor(myfilter);
        g.drawString(Integer.toString(map.player.action_points), 5, 50 + 75);
        //map.monster[0].iconImage.draw(5, 200);
        button_endturn.draw(10, gc.getScreenHeight() - 64 - 10);
        g.drawString("Player At:" + map.player.tilex + "X" + map.player.tiley + "mouse at:"
                + map.mouse_over_tile_x + "x" + map.mouse_over_tile_y + " Turn: "
                + map.turn_order + " mm: " + map.current_monster_moving + "/"
                + map.monster[map.current_monster_moving].action_points + " dst:"
                + map.monster[map.current_monster_moving].tiledestx + ","
                + map.monster[map.current_monster_moving].tiledesty,
                200, 10);//might crash?
        g.drawString("Trigger Check: " + map.trigger_check, 500, 100);
        if (this.map.turn_order.equalsIgnoreCase("monster")) {
            this.enemy_moving_message.draw(gc.getWidth() / 2 - 200, gc.getHeight() / 2);
        }
    }

    public void render_character_busts(GameContainer gc, Graphics g) {
        //render character busts while conversation is going on.
    }

    public void render_event_bubbles(GameContainer gc, Graphics g) {
        //conversation bubbles, triggered by events
    }

    public void render_cutscene(GameContainer gc, Graphics g) {
        //render cutscenes
    }

    public void render_title(GameContainer gc, Graphics g) {
        //render title menu
    }

    public void render_game_over(GameContainer gc, Graphics g) {//yoo dyied!
        //g.clear(); //fade to black
        g.scale(scale_x, scale_x); //scale the same
        this.render_background_layer(gc, g); //render floor
        this.render_walls_layer(gc, g);      //render walls (and actors!)
        this.render_game_ui(gc, g);
        this.render_character_busts(gc, g); //bring up the busts, and talk
        g.drawString("Game Over", gc.getScreenWidth() - 100, gc.getScreenHeight() - 12);
    }

    public boolean wall_intersect_player(int x, int y, int screen_x, int screen_y) {
        //int x = map.player.tilex - 4; x < map.player.tilex + 4; x++
        for (int ly = 0; ly < 4; ly++) {
            for (int lx = 0; lx < 4; lx++) {
                if (x == map.player.tilex + lx && y == map.player.tiley + ly) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getMyDelta(GameContainer gc) {
        long time = gc.getTime();
        int tdelta = (int) (time - this.lastframe);
        this.lastframe = time;
        if (tdelta == 0) {
            return 1;
        }
        return tdelta;
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new HorrorTactics("Horror Tactics"));

            appgc.setDisplayMode(appgc.getScreenWidth(),
                    appgc.getScreenHeight(),
                    true);
            appgc.setTargetFrameRate(60); //trying to slow down fast computers.
            appgc.start();

        } catch (SlickException ex) {
            Logger.getLogger(HorrorTactics.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGameState(String state) {
        game_state = state;
    }

    public MyTiledMap getCurrentMap() {
        return this.map;
    }

    public Image getComicActionStrImage(String a) {
        if (a.equalsIgnoreCase("Dodge")) {
            return this.effect_wiff;
        } else if (a.equalsIgnoreCase("Dead")) {
            return this.effect_shrack;
            //return this.effect_biff;
        } else {
            return this.effect_wiff;
        }
    }

    /*public int getActiveMonsters() {
        int am = 0;
        for (int i = 0; i < map.monster_max; i++) {
            if (map.monster[i].visible == true) {
                am++;
            }
        }
        return am; 
    }*/
    public boolean getTileToBeRendered(int x, int y) {
        if (x < 0) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (x > map.getWidth()) {
            return false;
        }
        if (y > map.getHeight()) {
            return false;
        }
        return true;
    }

    public boolean getTileToBeFiltered(int x, int y) { //if its outside 2 steps
        if (getTileToBeRendered(x, y)) {
            if (x < map.player.tilex - 2
                    || x > map.player.tilex + 2
                    || y < map.player.tiley - 2
                    || y > map.player.tiley + 2) {
                return true;
            }
        }
        return false;
    }

    public void loadNewMap(String newmap) throws SlickException {
        map = new MyTiledMap(newmap, 0, 0); //setup a new map
        map.getActorLocationFromTMX(); //get the actor info
    }

}
