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

import java.util.concurrent.ThreadLocalRandom;
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
    int light_level = 2; //default light level
    String turn_order = null;

    Trigger active_trigger = null;

    Actor player = null;
    Actor[] follower = new Actor[follower_max];
    Actor[] monster = new Actor[monster_max];
    private int m_draw_x, m_draw_y; //map draw x
    int current_monster_moving = 0; //debug
    int current_follower_moving = 0;

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
        //player = new Actor("data/tactics_in_distress00", 218, 313);
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
                    String pname = this.getTileProperty(gid, "actor_name", "none");
                    if (pname.equals("player")) {
                        this.player.tilex = x;
                        this.player.tiley = y;
                        this.player.name = pname;
                        //} else if (this.getTileProperty(gid, "actor_name", "none").equals("pear monster")) {
                    } else if (pname.equals("pear monster")) {
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

    public void onSteppedOnTrigger(int x, int y) {
        int actors_layer = this.getLayerIndex("actors_layer");
        int gid = this.getTileId(x, y, actors_layer);
        //long list of triggers!
        if (this.getTileProperty(gid,
                "audio_trigger", "none").equals("trapped girl")) {
            try {
                this.active_trigger = new Trigger("audio_trigger",
                        "trapped girl");
                this.active_trigger.onSetSoundToBePlayed("trappedgirl.ogg");
            } catch (SlickException n) {
                this.active_trigger = null;
            }
        }
    }

    public void onMoveActor(GameContainer gc, Actor a, int delta) {
        if (a.getActorMoving() == true
                //&& getPassableTile(a.tilex + a.facing_x, a.tiley + a.facing_y) ==false
                && a.tiley > a.tiledesty) {
            this.onMoveNorth(gc, a, delta);
        } else if (a.getActorMoving() == true
                //&& getPassableTile(a.tilex + a.facing_x, a.tiley + a.facing_y) ==false
                && a.tiley < a.tiledesty) {
            this.onMoveSouth(gc, a, delta);
        } else if (a.getActorMoving() == true
                //&& getPassableTile(a.tilex + a.facing_x, a.tiley + a.facing_y) ==false
                && a.tilex < a.tiledestx) {
            this.onMoveEast(gc, a, delta);
        } else if (a.getActorMoving() == true
                //&& getPassableTile(a.tilex + a.facing_x, a.tiley + a.facing_y) ==false
                && a.tilex > a.tiledestx) {
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

    public void setFollowerDirectives() {
        //loop through your monsters and set a path for them to follow
        //if they are controllable, they shall already have destinations.
        this.current_follower_moving = 0;
        for (int i = 0; i < this.follower_max; i++) {
            if (follower[i].visible == true) {
                follower[i].action_points = 6;
                follower[i].setActorMoving(true);
                follower[i].setActorDestination(follower[i].tiledestx,
                        follower[i].tiledesty);
            }
        }
    }

    public void drawMonsters(HorrorTactics ht, int x, int y) {
        //map.monster[0].drawActor(this, map, x, y);
        for (int i = 0; i < this.monster_max; i++) {
            if (this.monster[i].visible == true) { //just to be sure
                this.monster[i].drawActor(ht, this, x, y);
            }
        }
    }

    //map.monster[0].drawActor(this, map, x, y);
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
                monster[i].action_points = 6; //update action points

                if (monster[i].directive_type.equalsIgnoreCase("beeline")) {

                    monster[i].tiledestx = player.tilex;
                    monster[i].tiledesty = player.tiley;
                    //monster[i].setActorMoving(true);
                } else if (monster[i].directive_type.equalsIgnoreCase("random")) { //randomly move around.
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
        //System.out.println("delta: " + delta + "," + a.draw_x + "," + a.draw_y);
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
                System.out.print("cant get from tile:"
                        + Integer.toString(this.player.tilex) + ","
                        + Integer.toString(this.player.tiley) + " to "
                        + Integer.toString(x) + "," + Integer.toString(y)
                );
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

    public boolean isActorTouchingActor(Actor a, Actor b, int x, int y) {
        //a=monster, b=player
        if (a.tilex - 1 == b.tilex && a.tiley == b.tiley) {
            a.tiledestx = b.tilex;
            a.tiledesty = b.tiley;
            return true;
        } else if (a.tilex + 1 == b.tilex && a.tiley == b.tiley) {
            a.tiledestx = b.tilex;
            a.tiledesty = b.tiley;
            return true;
        } else if (a.tilex == b.tilex && a.tiley - 1 == b.tiley) {
            a.tiledestx = b.tilex;
            a.tiledesty = b.tiley;
            return true;
        } else if (a.tilex == b.tilex && a.tiley + 1 == b.tiley) {
            a.tiledestx = b.tilex;
            a.tiledesty = b.tiley;
            return true; //you are touching the player.
        }
        return false;
    }

    public boolean isPlayerTouchingMonster() {
        //for(int i)
        for (int i = 0; i < this.monster_max; i++) {
            if (this.isActorTouchingActor(player, monster[i], player.tilex, player.tiley)) {
                return true;
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
        if (this.getActorAtXy(player, x, y)) {
            return player;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.getActorAtXy(follower[i], x, y)) {
                return this.follower[i];
            }
        }
        return null; //found nothing
    }

    public void onActorCanAttack(GameContainer gc, HorrorTactics ht, Actor a) {
        //players and followers
        int dx = a.tiledestx;
        int dy = a.tiledesty;
        int actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_dodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_counterroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int actor_cdodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        if (actor_attackroll > target_dodgeroll) {
            this.getAllPlayersAtXy(dx, dy).dead = true;
            this.getAllPlayersAtXy(dx, dy).action_msg = "Dead";
            this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
        } else {
            this.getAllPlayersAtXy(dx, dy).action_msg = "Dodge";
            this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
        }
    }

    public void onMonsterCanAttack(GameContainer gc, HorrorTactics ht) {
        if (this.isMonsterTouchingYou(monster[this.current_monster_moving])) {
            //ATTACK! (monster at tiledestx, tiledesty
            int dx = monster[this.current_monster_moving].tiledestx;
            int dy = monster[this.current_monster_moving].tiledesty;
            int monster_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            int player_dodgeroll = 100;//ThreadLocalRandom.current().nextInt(1, 6 + 1);
            int player_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            int player_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            int player_counterroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            int monster_cdodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            System.out.println("MonsterRoll:" + Integer.toString(monster_attackroll)
                    + " Dodge Roll:" + Integer.toString(player_dodgeroll)
            );
            //player at the monster destination is not null
            if (monster_attackroll > player_dodgeroll) {
                this.getAllPlayersAtXy(dx, dy).dead = true;
                this.getAllPlayersAtXy(dx, dy).action_msg = "Dead";
                this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
            } else {
                this.getAllPlayersAtXy(dx, dy).action_msg = "Dodge";
                this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
                //Counter Attack.
                if (player_counterroll > monster_cdodgeroll) {
                    monster[this.current_monster_moving].dead = true;
                    monster[this.current_monster_moving].action_msg = "Dead";
                    monster[this.current_monster_moving].action_msg_timer = 400;
                } else {
                    monster[this.current_monster_moving].action_msg = "Dodge";
                    monster[this.current_monster_moving].action_msg_timer = 400;
                }
            }
        } else {
            System.out.println("Monster " + this.current_monster_moving + " found nobody there?");
        }//nobody was there.
    }

    public boolean getMonsterIsMoving(int i) {
        if (this.monster[i].action_points <= 0) {
            this.current_monster_moving++;
            return false;
        }
        if (this.monster[i].visible == false) {
            this.current_monster_moving++;
            return false;
        }
        if (this.monster[i].dead == true) {
            this.current_monster_moving++;
            return false;
        }
        //if (this.monster[i].getActorMoving() == false) { 
        //    this.current_monster_moving++;
        //    return false; 
        //}
        return true; //all conditions are good.
    }

    public void whyDidMonsterStop(GameContainer gc, HorrorTactics ht) {
        System.out.println("Monster stopped with remaining AP");
        if (this.monster[this.current_monster_moving].action_points >= 2) {
            this.onMonsterCanAttack(gc, ht); //Do we see the player?
            this.monster[this.current_monster_moving].action_points = 0;
            this.current_monster_moving++;
        } else {//you stopped and cant do anything anyways
            System.out.println("Monster had not enough points to attack");
            this.monster[this.current_monster_moving].action_points = 0;
            this.current_monster_moving++;
        }
        //allmonstersmoved = false;
    }

    public int getCurrentMonsterMoving() { //wrapper to avoid out of bounds
        int active_monster = 0;
        for (int i = 0; i < this.monster_max; i++) {
            if (this.monster[i].visible == true) {
                active_monster++;
            }
        }
        if (this.current_monster_moving < active_monster) {
            return this.current_monster_moving;
        } else {
            return active_monster;
        }
    }

    public void onMonsterMoving(GameContainer gc, HorrorTactics ht, int delta) { //taken from update.
        if (this.getMonsterIsMoving(this.current_monster_moving)) {
            this.onMoveActor(gc, this.monster[this.current_monster_moving], delta);
            if (this.monster[this.current_monster_moving].getActorMoving() == false) {
                this.whyDidMonsterStop(gc, ht);
            }
        }
        if (this.current_monster_moving >= this.monster_max) {
            this.current_monster_moving = 0;
            this.turn_order = "start player";
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
}
