/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Bug! array out of bounds when reaching the edge of the map
 */
package horrortactics;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.io.InputStream;
import org.newdawn.slick.util.ResourceLoader;
//import java.awt.FontFormatException;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Music;
//import org.newdawn.slick.SpriteSheet;
//import org.newdawn.slick.TrueTypeFont;
//import org.newdawn.slick.UnicodeFont;
//import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author tcooper
 */
public class HorrorTactics extends BasicGame {

    /**
     * @param gamename the command line arguments
     */
    public Input input;
    MyTiledMap map;
    private MouseActions msa;
    private KeyActions ksa;
    int draw_x, draw_y = 0;
    /* offset for camera scrolling*/
    int screen_x, screen_y;
    /* where on the screen?*/
    int mouse_x, mouse_y = 0;
    int last_mouse_x, last_mouse_y;
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
    String popup_window = "none";
    Color myfilter, myfiltert, myfilterd;
    Image button_endturn, button_menu, button_punch, button_items, button_profile, button_shadow;
    Image effect_biff, effect_wiff, effect_shrack;
    Image enemy_moving_message;
    Image level_up_icon; //level_up_icon.png
    Image prev_streets01,prev_apartment1, prev_tutorial01, prev_butcher_shop01;

    Actor playersave;
    TitleMenu titlemenu;
    SaveMyFile playerfile;
    Music music;
    String game_state = "title start"; //title start, title ingame, tactical,conversation,cutscene
    String fullscreen_toggle = "Yes";
    String sound_toggle = "Yes";
    TrueTypeFont ttf;
    

    public HorrorTactics(String gamename) {
        super(gamename);
        
        try {

            //Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            //awtFont2 = awtFont2.deriveFont(24f); // set font size
            //font2 = new TrueTypeFont(awtFont2, antiAlias);
            /*
            try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("myfont.ttf");
 
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			font2 = new TrueTypeFont(awtFont2, antiAlias);
                        //http://ninjacave.com/slickutil3
		}
            */
            
            //ttf = new TrueTypeFont(font, true);
            
            
            playerfile = new SaveMyFile();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        //map = new MyTiledMap("data/apartment1.tmx", 0, 0);
        map = new MyTiledMap("data/tutorial01.tmx", 0, 0);
        //map = new MyTiledMap("data/butcher_shop01.tmx", 0, 0);
        //map = new MyTiledMap("data/streets01.tmx", 0, 0);
        msa = new MouseActions();
        ksa = new KeyActions();
        titlemenu = new TitleMenu(this);
        //input = new Input(gc.getHeight());
        map.actormap.getActorLocationFromTMX(map);
        fps = gc.getFPS();
        actor_move_timer = 0;
        this.lastTime = 0;
        this.lastframe = 0;
        this.turn_count = 0;
        this.currentTime = gc.getTime();

        screen_height = gc.getScreenHeight();
        screen_width = gc.getScreenWidth();

        draw_x = map.getIsoXToScreen(map.player.tilex, map.player.tiley) * -1 + this.screen_width / 2;
        draw_y = map.getIsoYToScreen(map.player.tilex, map.player.tiley) * -1 + this.screen_height / 2;

        this.lastframe = gc.getTime();

        button_items = new Image("data/button_items.png");
        button_profile = new Image("data/button_profile.png");
        button_endturn = new Image("data/button_endturn2.png");
        button_menu = new Image("data/button_menu.png");
        button_shadow = new Image("data/button_shadow.png");
        button_punch = new Image("data/button_punch.png");
        effect_biff = new Image("data/soundeffects/biff.png");
        effect_wiff = new Image("data/soundeffects/wiff.png");
        effect_shrack = new Image("data/soundeffects/shrack.png");
        
        prev_streets01 = new Image("data/prev_streets01.jpg");
        prev_apartment1 = new Image("data/prev_apartment1.jpg");
        prev_tutorial01 = new Image("data/prev_tutorial01.jpg");
        prev_butcher_shop01 = new Image("data/prev_butcher_shop01.jpg");
        
        enemy_moving_message = new Image("data/enemy_moving.png");
        myfilter = new Color(1f, 1f, 1f, 1f);
        myfiltert = new Color(0.5f, 0.5f, 0.5f, 0.5f);
        myfilterd = new Color(0.2f, 0.2f, 0.2f, 0.8f); //for darkness/fog
        last_mouse_x = 0;//input.getMouseX();
        last_mouse_y = 0;//input.getMouseY();
        playersave = new Actor("data/player00", 218, 313); //to carry over the player.
        level_up_icon = new Image("data/level_up_icon.png");
        music = new Music("data/soundeffects/anxiety_backwards.ogg");

        try {
            //InputStream inputStream = ResourceLoader.getResourceAsStream("data/School_Writing.ttf");
            InputStream inputStream = ResourceLoader.getResourceAsStream("data/FantasqueSansMono-Bold.ttf");
            //java.awt.Font awtFont = new java.awt.Font(inputStream, java.awt.Font.BOLD, 24);
            java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(48f);
            //awtFont.deriveFont(bold)
            ttf = new TrueTypeFont(awtFont, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        if (this.game_state.equalsIgnoreCase("exit")) {
            gc.exit();
        } else if (this.game_state.equalsIgnoreCase("title start") || this.game_state.equalsIgnoreCase("title ingame")) {
            if (!music.playing()) {
                music.play();
            }
        } else {
            if (music.playing()) {
                music.stop();
            }
        }
        if (actor_move_timer >= this.fps) {
            this.actor_move_timer = 0;
        }
        if (map.player.dead == true && this.game_state.equalsIgnoreCase("tactical")) {
            map.turn_order = "game over";
            this.game_state = "game over";
        }
        if (map.turn_order.equalsIgnoreCase("game over")) { //update
            //this.game_state = "game over";
            //map.turn_order = "";
        } else if (map.turn_order.equalsIgnoreCase("planning")) { //update
            //planning phase.  Show a dialogue.
            //Accept clicks through the dialogue
            //after the last click, accept
        } else if (map.turn_order.equalsIgnoreCase("exit reached")) {
            //exit has been reached, transition map.  Do not set unless
            if (map.player.expForExitReached == false) {
                map.player.exp_points += 3;
                map.player.expForExitReached = true;
                map.player.health_points = map.player.health_points_max;
            }
            for (int i = 0; i < map.follower_max; i++) {
                if (map.follower[i].dead == false) {
                    if (map.follower[i].expForExitReached == false) {
                        map.follower[i].exp_points += 3;
                        map.follower[i].health_points = map.follower[1].health_points_max;
                        map.follower[i].expForExitReached = true;
                    }
                }
            }
            //goal has been reached , or goal = none

        } else if (map.turn_order.equalsIgnoreCase("change map")) {
            String n_mapname = this.map.next_map;
            //change map here  map = new MyTiledMap("data/class_school01.tmx", 0, 0);
            //map.getActorLocationFromTMX();
            try {
                this.map.player.copyActorStats(playersave);
                this.map = new MyTiledMap("data/" + n_mapname, 0, 0);
                //we lose player info Here.  
                map.actormap.getActorLocationFromTMX(map); //actor location?
                this.playersave.copyActorStats(map.player);
                this.map.turn_order = "planning";
                map.mouse_over_tile_x = 1;
                map.mouse_over_tile_y = 1;
                draw_x = map.getIsoXToScreen(map.player.tilex, map.player.tiley) * -1 + this.screen_width / 2;
                draw_y = map.getIsoYToScreen(map.player.tilex, map.player.tiley) * -1 + this.screen_height / 2;
            } catch (SlickException e) {
                System.out.println("cant load new map " + n_mapname);
            }
        } else if (map.turn_order.equalsIgnoreCase("goal reached")) {
            if (map.player.expForGoal == false) {
                map.player.exp_points += 3;
                map.player.expForGoal = true;
            }
            for (int i = 0; i < map.follower_max; i++) {
                if (map.follower[i].dead == false) {
                    if (map.player.expForGoal == false) {
                        map.follower[i].exp_points += 3;
                        map.follower[i].expForGoal = true;
                    }
                }
            }
        } else if (map.turn_order.equalsIgnoreCase("player")) {
            if (this.actor_move_timer == 0) {
                map.player.onMoveActor(map, gc.getFPS());//this.getMyDelta(gc));
                map.onFollowerMoving(gc, this, delta);
                if(map.player.action_points <= 0 && map.getFollowersCanMove() == false) {
                    //make it the monster turn automatically
                    map.turn_order = "start monster";
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
            map.player.action_points = 6 + map.player.stat_speed - 1 + map.player.getMovePenalty();
            //check for level up
            map.player.onLevelUp();
            for (int i = 0; i < map.follower_max; i++) {
                map.follower[i].onLevelUp();
            }
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
            render_tactical_base(gc, g);
        } else if (game_state.equalsIgnoreCase("conversation")) {
            g.scale(scale_x, scale_x); //scale the same
            render_tactical_base(gc, g);
        } else if (game_state.equalsIgnoreCase("cutscene")) {
            this.render_cutscene(gc, g); //animations?
        } else if (game_state.equalsIgnoreCase("title start") || game_state.equalsIgnoreCase("title ingame")) {
            //this.render_title(gc, g);
            this.titlemenu.render(this, g);
        } else if (game_state.equalsIgnoreCase("game over")) {
            this.render_game_over(gc, g);  //its game over for your kind
        }
    }

    public void render_tactical_base(GameContainer gc, Graphics g) {
        g.scale(scale_x, scale_x); //scale the same
        this.render_background_layer(gc, g); //render floor
        this.render_walls_layer(gc, g);      //render walls (and actors!)
        this.render_game_ui(gc, g);
        this.render_character_busts(gc, g);
    }

    public void render_background_layer(GameContainer gc, Graphics g) {
        int background_layer = map.getLayerIndex("background_layer");
        map.set_party_min_renderables();
        for (int y = map.render_min_y - 4; y < map.render_max_y + 4; y++) {
            for (int x = map.render_min_x - 4; x < map.render_max_x + 4; x++) {
                screen_x = (x - y) * map.TILE_WIDTH_HALF;
                screen_y = (x + y) * map.TILE_HEIGHT_HALF;
                try {
                    if (this.getTileToBeRendered(x, y)) {
                        if (this.getTileToBeFiltered(x, y)) {//if its outside 2 steps
                            //java.lang.ArrayIndexOutOfBoundsException: 20 
                            try {
                                Image xi = map.getTileImage(x, y, background_layer);
                                //map.getTileImage(x, y, background_layer).draw(
                                xi.draw(screen_x + draw_x, screen_y + draw_y, scale_x, this.myfilterd);
                            } catch (ArrayIndexOutOfBoundsException ae) {
                            }
                        } else { //draw normal
                            map.getTileImage(x, y, background_layer).draw(
                                    screen_x + draw_x, screen_y + draw_y, scale_x);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                } // block the bug.
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
            {
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
            }
            if (y == map.selected_tile_y && x == map.selected_tile_x && this.map.turn_order.equalsIgnoreCase("player")) {
                //map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y);
                map.selected_green.draw(screen_x + draw_x, screen_y + draw_y);
            }
            if (y == map.player.tiledesty && x == map.player.tiledestx
                    && map.player.getActorMoving()) {
                map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y);
            }
            for (int i = 0; i < map.follower_max; i++) {
                if (y == map.follower[i].tiledesty
                        && x == map.follower[i].tiledestx //){
                        && map.follower[i].getActorMoving()) {
                    map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void render_walls_layer(GameContainer gc, Graphics g) {
        int mw = map.getTileWidth();
        int mh = map.getTileHeight();
        for (int y = map.render_min_y - 4; y < map.render_max_y + 4; y++) {
            /*Y Loop*/
            for (int x = map.render_min_x - 4; x < map.render_max_x + 4; x++) {
                /* X Loop*/
                screen_x = (x - y) * map.TILE_WIDTH_HALF;
                /*Calculate screen/x/y*/
                screen_y = (x + y) * map.TILE_HEIGHT_HALF;
                if (x >= 0 && y >= 0 && x <= mw && y <= mh) {
                    /*loop through tiles */
                    mouse_x = gc.getInput().getMouseX();
                    /*get mouse coords*/
                    mouse_y = gc.getInput().getMouseY();
                    int sx = screen_x + draw_x + 30;
                    /*screen x/y+drawing offset*/
                    int sy = screen_y + draw_y + 30;
                    /*compare mouse to sx*/
                    if (mouse_x >= sx && mouse_x <= sx + 250 - 30
                            && mouse_y >= sy && mouse_y <= sy + 130 - 30) {
                        map.mouse_over_tile_x = x;
                        map.mouse_over_tile_y = y;
                        //is there someone in mouse over tile?
                        map.mouse_over_actor(x, y); //render the selector.
                        //map.tiles250x129.getSubImage(0, 0, 250, 130).draw(
                        //        screen_x + draw_x, screen_y + draw_y, scale_x);
                    }
                    map.player.drawPlayer(this, map, x, y, g);
                    /*Draw your player*/
                    map.drawMonsters(this, x, y, g);
                    /*map.monster[0].drawActor(this, map, x, y);*/
                    map.drawFollowers(this, x, y, g);
                    /*draw your followers*/
                    if (this.map.RequiresGoal.equalsIgnoreCase("yes")
                            && this.map.EventGoal_ran == false
                            && x == this.map.draw_goal_x
                            && y == this.map.draw_goal_y) {
                        /*bug? better have an image*/
                        int pdx = screen_x + draw_x;
                        /* + this.draw_x;*/
                        int pdy = screen_y + draw_y;
                        /* + this.draw_y - 230;*/
                        this.map.mission_goal.draw(pdx, pdy);
                    }
                    if (this.getTileToBeRendered(x, y)) {
                        render_wall_by_wall(gc, g, x, y); //ArrayIndexOutOfBoundsException
                    }
                }
            }
        }
    }

    //public void render_game_ui(GameContainer gc, Graphics g, )
    public int getMouseOverBottomButtons(HorrorTactics ht) {
        //if(ht.map.m)
        if( msa.menuButtonWasOver(ht) ) {return 100;}
        else if( msa.endTurnButtonWasOver(ht) ) {return 200;}
        else if( msa.profileButtonWasOver(ht)) { return 300; }
        else if( msa.itemsButtonWasOver(ht) ) {return 400;}
        else {
            return -100;
        }
    }
    public void render_game_ui(GameContainer gc, Graphics g) {
        int mouseovervar;
        mouseovervar = getMouseOverBottomButtons(this);
        map.player.iconImage.draw(5, 50);
        if (map.player.newLevelUp == true) { //level_up_icon
            this.level_up_icon.draw(5, 50);
        }
        g.drawString("Action: " + Integer.toString(map.player.action_points),
                5, 50 + 75);
        for (int i = 0; i < this.map.follower_max; i++) {
            if (this.map.follower[i].visible == true) {
                this.map.follower[i].iconImage.draw(5, 50 + (100 * (i + 1)));
                if (map.follower[i].newLevelUp == true) {
                    this.level_up_icon.draw(5, 50 + (100 * (i + 1)));
                }
                g.drawString("Action: " + Integer.toString(map.follower[i].action_points),
                        //5, 50 + 75);// (100 * (i + 1) + 75));
                        5, 50 + (100 * (i + 1)) + 75);
            }
        }
        g.setColor(myfilterd);
        g.fillOval(5, 50 + 75, 12, 14);
        g.setColor(myfilter);
        button_items.draw(gc.getScreenWidth() - 400, gc.getScreenHeight() - 64 - 10);
        button_profile.draw(gc.getScreenWidth() - 300, gc.getScreenHeight() - 64 - 10);
        button_endturn.draw(gc.getScreenWidth() - 200, gc.getScreenHeight() - 64 - 10);
        button_menu.draw(gc.getScreenWidth() - 100, gc.getScreenHeight() - 64 - 10);
        button_shadow.draw(gc.getScreenWidth() -mouseovervar, gc.getScreenHeight() - 64 - 10); //button_shadow
        g.drawString("Player At:" + map.player.tilex + "X" + map.player.tiley + "mouse at:"
                + map.mouse_over_tile_x + "x" + map.mouse_over_tile_y + " Turn: "
                + map.turn_order + " mm: " + map.current_monster_moving + "/"
                + map.monster[map.current_monster_moving].action_points + " dst:"
                + map.monster[map.current_monster_moving].tiledestx + ","
                + map.monster[map.current_monster_moving].tiledesty,
                200, 10);//might crash?
        g.drawString(this.map.log_msg, 200, 10 + 14);
        //g.drawString("Trigger Check: " + map.trigger_check, 500, 100);
        if (this.map.turn_order.equalsIgnoreCase("monster")) {
            this.enemy_moving_message.draw(gc.getWidth() / 2 - 200, gc.getHeight() / 2);
            //The enemy is moving, show which enemy.(so people dont get bored)
            for (int mi = 0; mi < this.map.current_monster_moving; mi++) {
                this.map.monster[mi].iconImage.draw(gc.getWidth() / 2 - 200 + (92 * mi + 2), gc.getHeight() / 2 + 92);
            }
        }
        //check for a follower selected
        boolean foundselected = false;
        for (int i = 0; i < map.follower_max; i++) {
            if (map.follower[i].selected == true) {
                foundselected = true;
                this.map.follower[i].drawPopupWindow(this, g);
                break;
            }
        }
        if (foundselected == false) {
            this.map.player.drawPopupWindow(this, g); //who is currently selected?
        }
    }
    
    public void render_character_busts(GameContainer gc, Graphics g) {
        //render character busts while conversation is going on.
        Color black = new Color(0, 0, 0, 180);
        Color white = new Color(255, 255, 255, 255);
        if (this.map.turn_order.equalsIgnoreCase("planning")) {
            this.map.charbusts[this.map.planevent].draw(-100, gc.getScreenHeight() - 600);
            g.setColor(black);
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150);
            g.setColor(white);
            //this.uniFont.drawString(400, gc.getScreenHeight() - 100, this.map.planning[this.map.planevent], Color.white );
            g.drawString(this.map.planning[this.map.planevent], 400, gc.getScreenHeight() - 100);
        } else if (this.map.turn_order.equalsIgnoreCase("event spotted")) {
            //null pointer if there is no event spotted
            map.EventSpotted_p.draw(-100, gc.getScreenHeight() - 600);
            g.setColor(black);
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150);
            g.setColor(white);
            //uniFont.drawString(400, gc.getScreenHeight() - 100, map.EventSpotted_m, Color.white);
            //set all monsters to spotted true (if false)
            g.drawString(map.EventSpotted_m, 400, gc.getScreenHeight() - 100);
            for (int i = 0; i < map.monster_max; i++) {
                map.monster[i].spotted_enemy = true;
            }
        } else if (this.map.turn_order.equalsIgnoreCase("goal reached")) {
            this.map.EventGoal_p.draw(-100, gc.getScreenHeight() - 600);
            g.setColor(black);
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150);
            g.setColor(white);
            //uniFont.drawString(400, gc.getScreenHeight() - 100, this.map.EventGoal_m, Color.white);
            g.drawString(this.map.EventGoal_m, 400, gc.getScreenHeight() - 100);
        } else if (this.map.turn_order.equalsIgnoreCase("exit reached")) {
            this.map.EventExit_p.draw(-100, gc.getScreenHeight() - 600);
            g.setColor(black);
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150);
            g.setColor(white);
            //uniFont.drawString(400, gc.getScreenHeight() - 100, this.map.EventExit_m, Color.white);
            g.drawString(this.map.EventExit_m, 400, gc.getScreenHeight() - 100);
        }
    }

    public void render_event_bubbles(GameContainer gc, Graphics g) {
        //conversation bubbles, triggered by events
    }

    public void render_cutscene(GameContainer gc, Graphics g) {
        //render cutscenes
    }

    public void render_game_over(GameContainer gc, Graphics g) {//yoo dyied!
        //g.clear(); //fade to black
        g.scale(scale_x, scale_x); //scale the same
        this.render_background_layer(gc, g); //render floor
        this.render_walls_layer(gc, g);      //render walls (and actors!)
        this.render_game_ui(gc, g);
        this.render_character_busts(gc, g); //bring up the busts, and talk
        g.drawString("Game Over", gc.getScreenWidth()/2 - 100, gc.getScreenHeight()/2 - 12);
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

            
            appgc.setDisplayMode( //1024, 768, false
                    appgc.getScreenWidth(),
                    appgc.getScreenHeight(),
                    true
            );
            appgc.setTargetFrameRate(60); //trying to slow down fast computers.
            appgc.start();

        } catch (SlickException ex) {
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
            /*for (int i = 0; i < map.follower_max; i++) {
                if (x < map.follower[i].tilex - 2
                        || x > map.follower[i].tilex + 2
                        || y < map.follower[i].tiley - 2
                        || y > map.follower[i].tiley + 2) {
                    return false;
                } 
            }*/
            if (x < map.player.tilex - 3
                    || x > map.player.tilex + 3
                    || y < map.player.tiley - 3
                    || y > map.player.tiley + 3) {
                return true;
            }
        }
        return false;
    }

    public void loadNewMap(String newmap) throws SlickException {
        map = new MyTiledMap(newmap, 0, 0); //setup a new map
        map.actormap.getActorLocationFromTMX(map); //get the actor info
    }
    
    public void translateToTile(int tx, int ty)//center tile x/y on the screen?
    {
        this.draw_x -= this.map.getIsoXToScreen(tx, ty)-(this.screen_width/3);
        this.draw_y -= this.map.getIsoYToScreen(tx, ty)-(this.screen_height-200);
    }
}
