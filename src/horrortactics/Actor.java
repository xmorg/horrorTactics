/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet; //lets bring in their spritesheet
import org.newdawn.slick.SlickException;
import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;

/**
 * Actor class is to encap the actor methods and data
 *
 * @author tcooper
 */
public class Actor {

    int tilex;
    int tiley; //the tile we are at (aprox)
    int tiledestx, tiledesty; //where we are going
    private int animate_frame;
    int animation_timer, move_timer;
    private int direction;
    int draw_x, draw_y; //where we are drawing them at X,Y
    private Image spriteImage;
    Image iconImage;
    private SpriteSheet sprites;
    private boolean selected; //if the actor is selected.
    private boolean move_action;
    int action_points, max_action_points;
    //private boolean hasturn;
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
        visible = true; //actor is visible.
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

    public void setActiorDirection(MyTiledMap m, int d) {
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
                this.setfacingloc(1, 0);
                break; //east
        }
    }

    public int getAnimationFrame() {
        return this.animate_frame;
    }

    public void setAnimationFrame(int f) {
        this.animate_frame = f;
    }

    public void onWalkAnimation(GameContainer gc) { //called by MyTiledMap.onMoveDir
        int timer_max = gc.getFPS() / 6;
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
                //this.animate_frame = 0;
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

    public void setActorMoving(boolean ismoving) {
        if (ismoving == false) {
            this.set_draw_xy(0, 0);
        }
        this.move_action = ismoving;
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
            //this.set_draw_xy(pdx, pdy);
            //map.getTileImage(x, y, background_layer).draw(
            //screen_x + draw_x, screen_y + draw_y, scale_x);
            if (this.dead == false) {
                this.getSpriteframe().draw(pdx, pdy, h.scale_x);
            } else {
                this.getDeadSpriteframe().draw(pdx, pdy, h.scale_x);
            }
        }
    }

    public void drawPlayer(HorrorTactics h, MyTiledMap m, int x, int y) {
        if (h.getTileToBeRendered(x, y)) {
            if(this.isSelected() && x == this.tilex && y == this.tiley) {
                try{
                    Image xi = m.getTileImage(x, y, m.getLayerIndex("walls_layer"));
                    if(xi == null) {
                        m.tiles250x129.getSubImage(0, 0, 250, 129).draw(
                                h.screen_x + h.draw_x, h.screen_y + h.draw_y);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {}
            }
        }
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
}
