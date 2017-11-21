/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templatesen
 * and open the template in the editor.
 */
package horrortactics;

import java.util.concurrent.ThreadLocalRandom;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet; //lets bring in their spritesheet
import org.newdawn.slick.SlickException;
//import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Graphics;
//import java.lang.Math.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Font;

/**
 * Actor class is to encap the actor methods and data
 *
 * @author tcooper
 */
public class Actor {

    String name;
    int tilex;
    int tiley; //the tile we are at (aprox)
    int tiledestx, tiledesty; //where we are going
    private int animate_frame;
    int animation_timer, move_timer, attack_timer;
    int direction;
    int draw_x, draw_y; //where we are drawing them at X,Y
    private Image spriteImage;
    Image iconImage;
    private SpriteSheet sprites;
    boolean selected; //if the actor is selected.
    private boolean move_action;
    int action_points, max_action_points;
    int turns_till_revival, max_turns_till_revival;
    int attack_range; //unless otherwise specified
    boolean visible;
    boolean dead;
    boolean spotted_enemy = true; // by default we know you are there.
    boolean canparry = false;
    boolean ismouse_over = false; //if the mouse is in your rectangle
    int parryscore;
    String directive_type;
    float speed;
    int facing_x = 0;
    int facing_y = 0;
    int speed_wait;
    int action_msg_timer; //max800
    String action_msg;
    Sound snd_footsteps;
    Sound snd_swing_hit, snd_swing_miss;
    Sound snd_washit, snd_dodging, snd_died;
    String weapon;
    //your stats.
    int health_points, health_points_max; //lose all heath and die
    int fatigue_points, fatigue_points_max; //lose all fatigue and get a action point penalty, and dodge penalty.
    int mental_points, mental_points_max; //lose all mental, and get an attack penalty.
    int stat_str, //health(on level up) and damage
            stat_speed, //movement and dodge
            stat_will, //fatigue and health (on level up)
            stat_luck; //chance to dodge / chance to hit
    int exp_level, exp_points; //level up = exp_level+1 * exp_level+1*10
    boolean newLevelUp, expForGoal, expForExitReached;
    int storyline_died; //Did you die in the story, and if you did, can you be replaced by another character.
    SpriteSheet player_knife_sprite, player_club_sprite, player_cleaver_sprite;
    boolean shadow;

    public Actor(String s, int sx, int sy) throws SlickException {
        spriteImage = new Image(s + ".png");
        iconImage = new Image(s + "_i.png");
        sprites = new SpriteSheet(spriteImage, sx, sy);
        tilex = 0; //our tile position in X
        tiley = 0; //our tile position in Y
        tiledestx = 0; //What tile are we traveling to when moving.
        tiledesty = 0;
        directive_type = "beeline"; //random, beeline, randomuntilspotted
        draw_x = 0; //Where the spite is drawn in X
        draw_y = 0; //Where the sprite is drawn in y
        animate_frame = 0;
        attack_range = 1;
        dead = false;
        direction = 0;
        animation_timer = 0;
        visible = false; //actor is visible.
        //hasturn = true;
        canparry = false;
        parryscore = -3;
        action_points = 0;
        max_action_points = 6;
        move_action = false;
        facing_x = 0;
        facing_y = 0;
        //speed = 0.05f;
        speed = 2;
        speed_wait = 0;
        action_msg_timer = 0;
        action_msg = "";
        name = "none";
        turns_till_revival = 0;
        max_turns_till_revival = 0;
        attack_timer = 0;
        storyline_died = 0;
        //no try catch!
        snd_footsteps = new Sound("data/soundeffects/steps_hallway.ogg");
        snd_swing_miss = new Sound("data/soundeffects/swing_miss.ogg");
        snd_swing_hit = new Sound("data/soundeffects/punch_hit.ogg");
        snd_washit = new Sound("data/soundeffects/guy_hit1.ogg");
        snd_dodging = new Sound("data/soundeffects/guy_dodging1.ogg");
        snd_died = new Sound("data/soundeffects/guy_die1.ogg");
        weapon = "none";
        health_points = 5;
        health_points_max = 5;
        fatigue_points = 5;
        fatigue_points_max = 5;
        mental_points = 5;
        mental_points_max = 5;
        stat_str = 1;
        stat_speed = 1;
        stat_will = 1;
        stat_luck = 1;
        exp_level = 0;
        exp_points = 0;
        newLevelUp = false;
        expForGoal = false;
        expForExitReached = false;
        player_knife_sprite = new SpriteSheet("data/player00_knife.png", 218, 313);
        player_club_sprite = new SpriteSheet("data/player00_club.png", 218, 313);
        shadow = true;
    }

    //public void changeActorSpriteSheetX
    public void changeActorSpritesheet(String s, int sx, int sy) throws SlickException {
        spriteImage = new Image(s + ".png");
        iconImage = new Image(s + "_i.png");
        sprites = new SpriteSheet(spriteImage, sx, sy);
    }

    public Image getSpriteframe() //throws SlickException
    {
        /*another function will change the animation, and direction*/
        Image i = sprites.getSubImage(animate_frame, direction);
        return i;
    }

    public Image getSpriteframe(SpriteSheet sp) //throws SlickException
    {
        /*another function will change the animation, and direction*/
        Image i = sp.getSubImage(animate_frame, direction);
        return i;
    }

    public Image getDeadSpriteframe() {
        Image i = sprites.getSubImage(3, direction);
        return i;
    }

    public void setfacingloc(int x, int y) {
        this.facing_x = x;
        this.facing_y = y;
    }

    public void setActiorDirection(int d) {
        //if(m.player.tilex > m.selected_tile_x)
        this.direction = d;
        switch (this.direction) {
            case 0:
                this.setfacingloc(1, 0);
                break;  //east
            case 1:
                this.setfacingloc(0, 1);
                break;  //south
            case 2:
                this.setfacingloc(0, -1);
                break; //north
            case 3:
                this.setfacingloc(-1, 0);
                break; //west
            default:
                this.setfacingloc(-1, 0);
                break; //east
        }
    }

    public int getAnimationFrame() {
        return this.animate_frame;
    }

    public void setAnimationFrame(int f) {
        this.animate_frame = f;
    }

    public void onWalkAnimation(int fps) { //called by MyTiledMap.onMoveDir
        int timer_max = fps / 6;
        if (this.animate_frame == 0 && this.getActorMoving() == true) {
            this.animation_timer++;
            if (this.animation_timer >= timer_max) {
                this.animate_frame = 1;
                this.animation_timer = 0;
            }
        } else if (this.animate_frame == 1 && this.getActorMoving() == true) {
            this.animation_timer++;
            if (this.animation_timer >= timer_max) {
                this.animate_frame = 2;
                this.animation_timer = 0;
            }
        } else if (this.animate_frame == 2 && this.getActorMoving() == true) {
            this.animation_timer++;
            if (this.animation_timer >= timer_max) {
                this.animate_frame = 1;
                this.animation_timer = 0;
            }
        } else {
            this.animation_timer++;
            if (this.animation_timer >= timer_max) {
                this.animate_frame = 0;
                this.animation_timer = 0;
            }
        }
    }

    public int getIsoXToScreen(int x, int y) {
        int posX = (x - y) * 250;
        return posX;
    }

    public int getIsoYToScreen(int x, int y) /*int posX = ( x - y) * 250;*/ {
        //int posY = (x + y) * 129 / 2;
        int posY = (x + y) * 130 / 2;
        return posY;
    }

    public boolean isAtTileXY(int x, int y) {
        if (tilex == x && tiley == y) {
            return true;
        }
        return false;
    }

    public void set_draw_xy(int x, int y) {
        this.draw_x = x;
        this.draw_y = y;
    }

    public int get_draw_x() {
        return this.draw_x;
    }

    public int get_draw_y() {
        return this.draw_y;
    }

    public void onSelectActor(boolean selection) {
        this.selected = selection;
    }

    public void onToggleSelection() {
        if (this.selected == true) {
            this.selected = false;
        } else {
            this.selected = true;
        }
    }

    public boolean isSelected() {
        return this.selected;
    }

    //public int getActionPoints() {
    //    return this.action_points;
    //}
    public void setActionPoints(int t) {
        this.action_points = t;
    }

    public void setActorDestination(int x, int y) {
        this.tiledestx = x;
        this.tiledesty = y;
    }

    public int getActorDestX() {
        return this.tiledestx;
    }

    public int getActorDestY() {
        return this.tiledesty;
    }

    public void updateActorDirection() {
        if (this.tilex == this.tiledestx && this.tiley == this.tiledesty) {
        } //else if (this.tilex < this.tiledestx) {            
        //}
        //else if (this.tilex == this.tiledestx && this.tiley > this.tiledesty) {
        //    
        //}
        else if (this.tilex < this.tiledestx) {
            this.setActiorDirection(this.getEast());
        } else if (this.tilex > this.tiledestx) {
            this.setActiorDirection(this.getWest());
        } else if (this.tiley > this.tiledesty) {
            this.setActiorDirection(this.getNorth());
        } else if (this.tiley < this.tiledesty) {
            this.setActiorDirection(this.getSouth());
        }
    }

    public void setActorMoving(boolean ismoving) {
        if (ismoving == false) {
            this.set_draw_xy(0, 0);
            snd_footsteps.stop();
        }
        this.move_action = ismoving;
        if (this.move_action == true) {
            this.updateActorDirection();
        } //north or south

    }

    public boolean getActorMoving() {
        if (this.action_points <= 0) {
            this.move_action = false;
        }
        return this.move_action;
    }

    public void drawHealthBar(Graphics g, Color c, int x, int y, int bar, int barmax) {
        Rectangle health_bar = new Rectangle(0, 0, 0, 0);
        Rectangle health_bar_s = new Rectangle(0, 0, 0, 0);
        int ph = (int) (((double) bar / (double) barmax) * 100 / 2);
        if (this.ismouse_over == true) {
            health_bar.setBounds(x + 30, y + 30 + 50 - ph, 10, ph);
            health_bar_s.setBounds(x + 29, y + 29, 12, 52);
            g.setColor(Color.black);
            g.fill(health_bar_s);
            g.setColor(c);
            g.fill(health_bar);
            g.setColor(Color.white);
            //g.drawString(this.health_points+"/"+this.health_points_max+"/"+ph, x+30,y+30);
        }
    }

    public void drawActor(HorrorTactics h, MyTiledMap m, int x, int y, Graphics g) {
        //Rectangle health_bar = new Rectangle(0,0,0,0);
        //Rectangle health_bar_s = new Rectangle(0,0,0,0);
        //int x = (int)(((double)a/(double)b) * 100);
        Color lg = new Color(0, 0, 0, 80);
        Color textcolor = Color.white;
        
        int ph = (int) (((double) this.health_points / (double) this.health_points_max) * 100 / 2);
        if (this.isAtTileXY(x, y) == true) {
            int pdx = h.screen_x + h.draw_x + this.draw_x;
            int pdy = h.screen_y + h.draw_y + this.draw_y - 230;
            if (this.selected == true && h.map.turn_order.equalsIgnoreCase("player") ) { //draw the selection if true

                try { //draw select box
                    m.selected_green.draw(h.screen_x + h.draw_x, h.screen_y + h.draw_y);
                    m.tiles250x129.getSubImage(0, 0, 250, 130).draw(h.screen_x + h.draw_x, h.screen_y + h.draw_y);
                } catch (NullPointerException n) {
                }
            }
            if (this.dead == false) { //draw actor
                if (this.ismouse_over == true) {
                    drawHealthBar(g, Color.blue, pdx + 12, pdy + 12, this.mental_points, this.mental_points_max);
                    drawHealthBar(g, Color.green, pdx + 6, pdy + 6, this.fatigue_points, this.fatigue_points_max);
                    drawHealthBar(g, Color.red, pdx, pdy, this.health_points, this.health_points_max);

                }
                if(this.shadow) { // what if monster has no shadow!?!?!
                    g.setColor(lg);
                    g.fillOval(pdx + 50, pdy + this.sprites.getSprite(0, 0).getHeight() - 50, 100, 50);
                    g.setColor(Color.white);
                }
                this.getSpriteframe().draw(pdx, pdy, h.scale_x);//draw actual actor
                if (this.name.equalsIgnoreCase("Riku") && this.weapon.equalsIgnoreCase("knife")) {
                    this.getSpriteframe(this.player_knife_sprite).draw(pdx, pdy, h.scale_x);
                } else if (this.name.equalsIgnoreCase("Riku") && this.weapon.equalsIgnoreCase("club")) {
                    this.getSpriteframe(this.player_club_sprite).draw(pdx, pdy, h.scale_x);
                } else if (this.name.equalsIgnoreCase("Riku") && this.weapon.equalsIgnoreCase("cleaver")) {
                    this.getSpriteframe(this.player_cleaver_sprite).draw(pdx, pdy, h.scale_x);
                }

            } else { //draw actor dead
                this.getDeadSpriteframe().draw(pdx, pdy, h.scale_x);
            }
            if (this.action_msg_timer > 0) {
                if (this.action_msg.equalsIgnoreCase("miss")) {
                    g.setColor(Color.white);
                    textcolor = Color.white;
                } else { //show damage
                    g.setColor(Color.red);
                    textcolor = Color.red;
                }
                //g.pushTransform();
                //g.translate(-100, -100);
                //g.scale(2.0f, 2.0f);
                //h.ttf.drawString(pdx  + 50, pdy, this.action_msg);
                h.ttf.drawString(pdx  + 50-2, pdy+2, this.action_msg, Color.black);
                h.ttf.drawString(pdx  + 50, pdy, this.action_msg, textcolor);
                
                //h.ttf.d
                //g.drawString(this.action_msg, pdx  + 50, pdy );
                //g.popTransform();                
            }
        }
    }

    public void drawPlayer(HorrorTactics h, MyTiledMap m, int x, int y, Graphics g) {
        this.drawActor(h, m, x, y, g);
    }

    public void setActorActionPoints(int ap) {
        action_points = ap;
        if (action_points <= 0) {
            setActorMoving(false);
        }
    }

    public void resetActorActionPoints() {
        action_points = max_action_points;
    }

    public void onAttack(HorrorTactics ht/*, Actor target*/) {

        //this.tiledestx = ht.map.selected_tile_x;  //monsters have to attack too, remove code.
        //this.tiledesty = ht.map.selected_tile_y;
        this.updateActorDirection();
        //ht.map.onActorAttackActor(ht, ht.map.player,
        //        ht.map.getAllPlayersAtXy(ht.map.selected_tile_x, ht.map.selected_tile_y));
        this.tiledestx = this.tilex;
        this.tiledesty = this.tiley;
        //who is at x/y?
        Actor t = ht.map.getAllPlayersAtXy(ht.map.selected_tile_x, ht.map.selected_tile_y);
        if (t == null) {
            System.out.println("woa something is wrong monster target is null");
        } else {
            System.out.println("t is not null, found " + t.name);
            //check range //note, what about ranged units? 
            //isActorTouchingActor is a temporary hack, because/we need to check range.
            //check action points
            if (this.action_points >= 3 && ht.map.isActorTouchingActor(this, t, tilex, tiley) && this.dead == false) {
                onActorAttackActor(ht, t);
                //this.setAnimationFrame(4);
                //this.attack_timer = 25;
            } else if (this.action_points >= 3 && this.attack_range > 1 && this.dead == false) {
                onActorAttackActor(ht, t);
            }
        }

    }

    public void stopMoving() {
        this.tiledestx = this.tilex;
        this.tiledesty = this.tiley;
        this.move_action = false;
    }

    public void onMoveActor(MyTiledMap m, int fps) {
        int f = fps; //gc.getFPS();
        if (this.getActorMoving() == true
                && this.dead == false
                //&& this.spotted_enemy == true
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getNorth()) {
            this.onMoveNorth(m, f);
            if (!snd_footsteps.playing()) {
                snd_footsteps.loop();
                //footsteps.play();
            }
        } else if (this.getActorMoving() == true
                && this.dead == false
                //&& this.spotted_enemy == true
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getSouth()) {
            this.onMoveSouth(m, f);
            if (!snd_footsteps.playing()) {
                snd_footsteps.loop();
                //footsteps.play();
            }
        } else if (this.getActorMoving() == true
                && this.dead == false
                //&& this.spotted_enemy == true
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getEast()) {
            this.onMoveEast(m, f);
            if (!snd_footsteps.playing()) {
                snd_footsteps.loop();
                //footsteps.play();
            }
        } else if (this.getActorMoving() == true
                && this.dead == false
                //&& this.spotted_enemy == true
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getWest()) {
            this.onMoveWest(m, f);
            if (!snd_footsteps.playing()) {
                snd_footsteps.loop();
                //footsteps.play();
            }
        }

        if (m.getPassableTile(this,
                this.tilex + this.facing_x,
                this.tiley + this.facing_y) == false) {
            this.setActorMoving(false); //That means you monsters!
            this.setAnimationFrame(0); //just be sure you are not still moving if you touch a wall
        }

        if (this.tilex == this.tiledestx && this.tiley == this.tiledesty) {
            //System.out.println("Arrived at destination");
            this.setActorMoving(false);
            this.setAnimationFrame(0);
        }

        if (this.action_points <= 0) {
            this.action_points = 0;
            this.setActorMoving(false);
            this.setAnimationFrame(0);
        }

        if (this.attack_timer > 0) {
            this.setAnimationFrame(4); //just in case it got set to 0
        }

        //try to check tilex/tiley for an event?
    }

    public void onMoveWest(MyTiledMap m, int delta) {
        this.setActiorDirection(getWest());
        this.draw_x -= 2 * this.speed;//(a.speed * delta) * 2; //a.speed;//delta * a.speed;
        this.draw_y -= 1 * this.speed;//(a.speed * delta);
        this.set_draw_xy(this.draw_x, this.draw_y);
        this.onWalkAnimation(delta);
        if (Math.abs(this.draw_x) >= m.TILE_WIDTH_HALF) {
            this.tilex--; //westr.
            m.active_trigger.name = "none"; //you are in a new tile and all triggers are reset.
            this.set_draw_xy(0, 0);
            this.action_points--;
            this.updateActorDirection();
        }
    }

    public void onMoveEast(MyTiledMap m, int delta) {
        this.setActiorDirection(getEast());
        this.draw_x += 2 * this.speed;//(a.speed * delta) * 2; //a.speed;//delta * a.speed;
        this.draw_y += 1 * this.speed;//(a.speed * delta);
        this.set_draw_xy(this.draw_x, this.draw_y);
        this.onWalkAnimation(delta);
        if (this.draw_x >= m.TILE_WIDTH_HALF) {
            this.tilex++; //westr.
            m.active_trigger.name = "none"; //you are in a new tile and all triggers are reset.
            this.set_draw_xy(0, 0);
            //a.setAnimationFrame(0);
            this.action_points--;
            //this.tiledestx = this.tilex;
            this.updateActorDirection();
        }
    }

    public void onMoveSouth(MyTiledMap m, int delta) {
        this.setActiorDirection(getSouth()); //south/1
        this.draw_y += 1 * this.speed;//(a.speed * delta); //a.speed;
        this.draw_x -= 2 * this.speed;//(a.speed * delta) * 2;
        this.set_draw_xy(this.draw_x, this.draw_y);
        this.onWalkAnimation(delta);
        if (this.draw_y >= Math.abs(m.TILE_HEIGHT_HALF)) {
            this.tiley++; //south.
            m.active_trigger.name = "none"; //you are in a new tile and all triggers are reset.
            this.set_draw_xy(0, 0);
            //a.setAnimationFrame(0);
            this.action_points--;
            //this.tiledesty = this.tiley;
            this.updateActorDirection();
        }
    }

    public void onMoveNorth(MyTiledMap m, int delta) {
        this.setActiorDirection(getNorth());//north
        this.draw_y -= 1 * this.speed;//(a.speed * delta);//a.speed;
        this.draw_x += 2 * this.speed;//(a.speed * delta) * 2;//a.speed*2;
        //System.out.println("delta: " + delta + "," + a.draw_x + "," + a.draw_y);
        this.set_draw_xy(this.draw_x, this.draw_y);
        this.onWalkAnimation(delta);
        if (Math.abs(this.draw_y) >= m.TILE_HEIGHT_HALF) {
            this.tiley--; //north.
            m.active_trigger.name = "none"; //you are in a new tile and all triggers are reset.
            this.set_draw_xy(0, 0);
            this.action_points--;
            //this.tiledesty = this.tiley;
            this.updateActorDirection();
        }
    }

    public int getNorth() {
        return 2;
    }

    public int getSouth() {
        return 1;
    }

    public int getEast() {
        return 0;
    }

    public int getWest() {
        return 3;
    }

    public void drawPopupWindow(HorrorTactics ht, Graphics g) {
        int w = 400;
        int h = 400;
        int x = ht.screen_width / 2 - w / 2;
        int y = ht.screen_height / 2 - h / 2;
        String LevelUpControls;
        Color c = new Color(10, 10, 10, 245);
        Rectangle r = new Rectangle(0, 0, 0, 0);
        Rectangle s = new Rectangle(0, 0, 0, 0);
        r.setBounds(x, y, w, h);
        s.setBounds(x - 1, y - 1, w + 2, h + 2);
        if (ht.popup_window.equalsIgnoreCase("profile")) {

            if (this.newLevelUp == true) {
                LevelUpControls = "[+] ";
            } else {
                LevelUpControls = "";
            }
            //draw it
            //Note is level up == true?
            g.setColor(Color.white);
            g.fill(s);
            g.setColor(c);
            g.fill(r);
            sprites.getSubImage(0, 0).draw(x - 30, y + 50);
            g.setColor(Color.white);
            g.drawString(this.name, x + 200, y + 20);
            g.drawString("Level: " + this.exp_level, x + 200, y + 40);
            g.drawString("Health: " + this.health_points + "/" + this.health_points_max, x + 200, y + 60);
            g.drawString("Fatigue: " + this.fatigue_points + "/" + this.fatigue_points_max, x + 200, y + 80);
            g.drawString("Stress: " + this.mental_points + "/" + this.mental_points_max, x + 200, y + 100);
            if (this.newLevelUp == true) { //green means you can click on a [+] //no mouse contorls yet.
                g.setColor(Color.green);
            }
            //int x = ht.screen_width / 2 - w / 2;
            //int y = ht.screen_height / 2 - h / 2;
            g.drawString(LevelUpControls + "Strength: " + this.stat_str, x + 200, y + 120);
            g.drawString(LevelUpControls + "Speed: " + this.stat_speed, x + 200, y + 140);
            g.drawString(LevelUpControls + "Willpower: " + this.stat_will, x + 200, y + 160);
            g.drawString(LevelUpControls + "Luck: " + this.stat_luck, x + 200, y + 180);
            g.drawString(LevelUpControls + "Exp: " + this.exp_points, x + 200, y + 200);
            
            
            g.setColor(Color.black);
            g.drawString("Objective: " +ht.map.objective, r.getX()-1, r.getY()+r.getHeight()+32+1);
            g.setColor(Color.white);
            g.drawString("Objective: " +ht.map.objective, r.getX(), r.getY()+r.getHeight()+32);
            
            g.setColor(Color.black);
            g.drawString("Hint: " +ht.map.hint, r.getX()-1, r.getY()+r.getHeight()+64+1);
            g.setColor(Color.white);
            g.drawString("Hint: " +ht.map.hint, r.getX(), r.getY()+r.getHeight()+64);
        } else if (ht.popup_window.equalsIgnoreCase("items")) {
            //draw it
            g.setColor(c);
            g.fill(r);
            sprites.getSubImage(0, 0).draw(x - 30, y + 50);
            g.setColor(Color.white);
            g.drawString(this.name + "'s Items", x + 200, y + 20);
            
            g.setColor(Color.black);
            g.drawString("Objective: " +ht.map.objective, r.getX()-1, r.getY()+r.getHeight()+32+1);
            g.setColor(Color.white);
            g.drawString("Objective: " +ht.map.objective, r.getX(), r.getY()+r.getHeight()+32);
            
            g.setColor(Color.black);
            g.drawString("Hint: " +ht.map.hint, r.getX()-1, r.getY()+r.getHeight()+64+1);
            g.setColor(Color.white);
            g.drawString("Hint: " +ht.map.hint, r.getX(), r.getY()+r.getHeight()+64);

            if (ht.map.RequiresGoal.equalsIgnoreCase("yes")
                    && ht.map.EventGoal_ran == true) {
                //&& x == this.map.draw_goal_x
                //&& y == this.map.draw_goal_y) {
                /*bug? better have an image*/
                //int pdx = screen_x + draw_x;
                /* + this.draw_x;*/
                //int pdy = screen_y + draw_y;
                /* + this.draw_y - 230;*/
                ht.map.mission_goal.draw(x+200, y+200);
                
            }

        } else {
            //do nothing.
        }
        
    }

    void onLevelUp() {
        //int exp_level, exp_points; //level up = exp_level+1 * exp_level+1*10
        if ((this.exp_points >= (this.exp_level + 1) * (exp_level + 1) * 10) && this.newLevelUp == false) { //there was a level up.
            this.newLevelUp = true;
            this.exp_level++;
        } //wait for point distrib before setting to false (not implemented)
    }

    void copyActorStats(Actor a) {
        a.name = this.name;
        a.exp_level = this.exp_level;
        a.exp_points = this.exp_points;
        a.stat_luck = this.stat_luck;
        a.stat_speed = this.stat_speed;
        a.stat_str = this.stat_str;
        a.stat_will = this.stat_will;
        a.health_points_max = this.health_points_max;
        a.health_points = this.health_points;
    }

    void swapSoundEffects(String footsteps, String miss, String hit, String washit, String dodge, String died) {
        /*snd_footsteps = new Sound("data/soundeffects/steps_hallway.ogg");
         snd_swing_miss = new Sound("data/soundeffects/swing_miss.ogg");
         snd_swing_hit = new Sound("data/soundeffects/punch_hit.ogg");
         snd_washit = new Sound("data/soundeffects/guy_hit1.ogg");
         snd_dodging = new Sound("data/soundeffects/guy_dodging1.ogg");
         snd_died = new Sound("data/soundeffects/guy_die1.ogg");*/
        String mpath = "data/soundeffects/";
        if (!footsteps.isEmpty()) {
            try {
                this.snd_footsteps = new Sound(mpath + footsteps);
            } catch (SlickException e) {
            };
        }
        if (!miss.isEmpty()) {
            try {
                this.snd_swing_miss = new Sound(mpath + miss);
            } catch (SlickException e) {
            };
        }
        if (!hit.isEmpty()) {
            try {
                this.snd_swing_hit = new Sound(mpath + hit);
            } catch (SlickException e) {
            };
        }
        if (!washit.isEmpty()) {
            try {
                this.snd_washit = new Sound(mpath + washit);
            } catch (SlickException e) {
            };
        }
        if (!dodge.isEmpty()) {
            try {
                this.snd_dodging = new Sound(mpath + dodge);
            } catch (SlickException e) {
            };
        }
        if (!died.isEmpty()) {
            try {
                this.snd_died = new Sound(mpath + died);
            } catch (SlickException e) {
            };
        }
    }

    public int getAttackPenalty() { //check scores and get a hit penalty
        if (this.mental_points < 3) {
            return -1;
        } else if (this.mental_points <= 1) {
            return -2;
        } else if (this.mental_points <= 0) {
            return -3; //you are totally wasted
        }
        return 0;
    }

    public int getMovePenalty() { //check scores and get a move penalty
        if (this.fatigue_points <= 3) {
            return -1;
        } else if (this.fatigue_points <= 2) {
            return -2;
        } else if (this.fatigue_points <= 0) {
            return -3;
        }
        return 0;
    }

    public int getDodgePenalty() { //check scores and get a dodge penalty
        if (this.fatigue_points <= 3) {
            return -1;
        } else if (this.fatigue_points <= 2) {
            return -2;
        } else if (this.fatigue_points <= 0) {
            return -3;
        }
        return 0;
    }

    public int getDodgeBonus() { //check scores and get a dodge bonus
        if (this.stat_speed > 1) {
            return this.stat_speed - 2;
        } else {
            return 0;
        }
    }

    public void onActorAttackActor(HorrorTactics ht, Actor defender) {
        int target_parryroll;
        int actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + this.stat_luck - 1 + this.getAttackPenalty();
        int target_dodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + defender.stat_luck - 1 + defender.getDodgePenalty() //luck
                + defender.getDodgeBonus(); //speed
        int damage_roll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + this.stat_str - 1;
        //int target_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        System.out.println("target check " + defender.name);
        this.setAnimationFrame(4);
        this.attack_timer = 25;
        if (defender.canparry) {
            target_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        } else {
            target_parryroll = 0;
        }
        if (actor_attackroll > target_dodgeroll && actor_attackroll > target_parryroll + defender.parryscore) {
            //hit
            defender.health_points -= damage_roll;
            if (defender.health_points <= 0) {
                defender.dead = true;
                defender.action_msg = " " + damage_roll + " ";
                defender.action_msg_timer = 200;
                defender.turns_till_revival = 0; //do we revive?
                ht.map.log_msg = this.name + " attacks " + defender.name + "(1d6 =" + actor_attackroll + ")" + ",(1d6 =" + target_dodgeroll + ") and hits for " + damage_roll + " points of damage, killing " + defender.name;
                defender.snd_died.play();
                this.exp_points += 3;
            } else {
                ht.map.log_msg = this.name + " attacks " + defender.name + "(1d6 =" + actor_attackroll + ")" + ",(1d6 =" + target_dodgeroll + ") and hits for " + damage_roll + " points of damage.";
                defender.snd_washit.play();
            }
            this.snd_swing_hit.play();
            //defender.snd_washit.play();
        } else {
            //miss
            defender.action_msg = "Miss";
            defender.action_msg_timer = 200;
            if (target_parryroll + defender.parryscore > actor_attackroll) {
                ht.map.log_msg = this.name + " attacks " + defender.name + "(1d6 =" + actor_attackroll + ",(1d6 =" + target_parryroll + " + " + defender.parryscore + ") but the attack was parried.";
            } else {
                ht.map.log_msg = this.name + " attacks " + defender.name + "(1d6 =" + actor_attackroll + ",(1d6 =" + target_dodgeroll + ") and misses";
            }
            this.snd_swing_miss.play();
            defender.snd_dodging.play();
        }
        //we already checke for action points, not remove them
        this.action_points -= 3;
        this.fatigue_points -= 1;
        if (this.fatigue_points < 0) {
            this.fatigue_points = 0;
        }
        defender.fatigue_points -= 1;
        if (defender.fatigue_points < 0) {
            defender.fatigue_points = 0;
        }
        if (this.action_points < 0) {
            this.action_points = 0;
        }
    }
}
