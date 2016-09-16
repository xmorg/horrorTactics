/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templatesen
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet; //lets bring in their spritesheet
import org.newdawn.slick.SlickException;
//import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;
import java.lang.Math.*;

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
    boolean visible;
    boolean dead;
    String directive_type;
    float speed;
    int facing_x = 0;
    int facing_y = 0;
    int speed_wait;
    int action_msg_timer; //max800
    String action_msg;

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
        dead = false;
        direction = 0;
        animation_timer = 0;
        visible = false; //actor is visible.
        //hasturn = true;
        action_points = 0;
        max_action_points = 6;
        move_action = false;
        facing_x = 0;
        facing_y = 0;
        speed = 0.05f;
        speed_wait = 0;
        action_msg_timer = 0;
        action_msg = "";
        name = "none";
        turns_till_revival = 0;
        max_turns_till_revival = 0;
        attack_timer = 0;
        //spriteImage.set
    }

    public void changeActorSpritesheet(String s, int sx, int sy) throws SlickException {
        spriteImage = new Image(s);
        sprites = new SpriteSheet(spriteImage, sx, sy);
    }

    public Image getSpriteframe() //throws SlickException
    {
        /*another function will change the animation, and direction*/
        Image i = sprites.getSubImage(animate_frame, direction);
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
        int posY = (x + y) * 129 / 2;
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
        if(this.selected == true) {this.selected = false;}
        else {this.selected = true;}
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

    public void drawActor(HorrorTactics h, MyTiledMap m, int x, int y) {
        if (this.isAtTileXY(x, y) == true) {
            int pdx = h.screen_x + h.draw_x + this.draw_x;
            int pdy = h.screen_y + h.draw_y + this.draw_y - 230;
            if (this.selected == true) { //draw the selection if true
                m.tiles250x129.getSubImage(0, 0, 250, 129).draw(
                        h.screen_x + h.draw_x, h.screen_y + h.draw_y);
            }
            if (this.dead == false) { //draw actor
                this.getSpriteframe().draw(pdx, pdy, h.scale_x);
            } else { //draw actor dead
                this.getDeadSpriteframe().draw(pdx, pdy, h.scale_x);
            }
        }
    }

    public void drawPlayer(HorrorTactics h, MyTiledMap m, int x, int y) {
        this.drawActor(h, m, x, y);
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

    public void onMoveActor(MyTiledMap m, int fps) {
        int f = fps; //gc.getFPS();
        if (this.getActorMoving() == true
                && this.dead == false
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getNorth()) {
            this.onMoveNorth(m, f);
        } else if (this.getActorMoving() == true
                && this.dead == false
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getSouth()) {
            this.onMoveSouth(m, f);
        } else if (this.getActorMoving() == true
                && this.dead == false
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getEast()) {
            this.onMoveEast(m, f);
        } else if (this.getActorMoving() == true
                && this.dead == false
                && m.getPassableTile(this, this.tilex + this.facing_x, this.tiley + this.facing_y) == true
                && m.getPlayerFacingMonster(this) == false
                && this.direction == getWest()) {
            this.onMoveWest(m, f);
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
    }

    public void onMoveWest(MyTiledMap m, int delta) {
        this.setActiorDirection(getWest());
        this.draw_x -= 2;//(a.speed * delta) * 2; //a.speed;//delta * a.speed;
        this.draw_y -= 1;//(a.speed * delta);
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
        this.draw_x += 2;//(a.speed * delta) * 2; //a.speed;//delta * a.speed;
        this.draw_y += 1;//(a.speed * delta);
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
        this.draw_y += 1;//(a.speed * delta); //a.speed;
        this.draw_x -= 2;//(a.speed * delta) * 2;
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
        this.draw_y -= 1;//(a.speed * delta);//a.speed;
        this.draw_x += 2;//(a.speed * delta) * 2;//a.speed*2;
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
}
