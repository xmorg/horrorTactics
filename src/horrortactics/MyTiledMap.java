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
    String turn_order = null;
    Trigger active_trigger = null;

    Actor player = null;
    Actor[] follower = new Actor[follower_max];
    Actor[] monster = new Actor[monster_max];
    private int m_draw_x, m_draw_y; //map draw x
    int current_monster_moving = 0; //debug
    int current_follower_moving = 0;
    String trigger_check = "no";

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
        //player = new Actor("data/monster05", 218, 313);
        turn_order = "start player";
        this.active_trigger = new Trigger("none", "none");
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
                    //System.out.println(this.getTileId(x, y, actor_layer));
                    String pname = this.getTileProperty(gid, "actor_name", "none");
                    if (pname.equals("player")) {
                        this.player.tilex = x;
                        //this.player.tilex += 10;
                        this.player.tiley = y;
                        this.player.name = pname;
                        //} else if (this.getTileProperty(gid, "actor_name", "none").equals("pear monster")) {
                    } else if (pname.equals("Yukari")) {
                        try {
                            follower[follower_loop].changeActorSpritesheet("data/tactics_in_distress01", 218, 313);
                            follower[follower_loop].tilex = x;
                            follower[follower_loop].tiley = y;
                            follower[follower_loop].visible = true;
                            this.player.name = pname;
                            follower_loop++;
                        } catch (SlickException e) {
                        }
                    } else if (pname.equals("pear monster")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster00", 218, 313);
                        } catch (SlickException e) {
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 4;
                        monster_loop++;
                    } // add more monsters here;
                    else if (pname.equals("skeleton monster")) {
                        try {
                            monster[monster_loop].changeActorSpritesheet("data/monster05.png", 218, 313);
                        } catch (SlickException e) {
                        }
                        monster[monster_loop].tilex = x;
                        monster[monster_loop].tiley = y;
                        monster[monster_loop].setActorMoving(false);
                        monster[monster_loop].visible = true;
                        monster[monster_loop].name = pname;
                        monster[monster_loop].max_turns_till_revival = 0;
                        monster_loop++;
                    } // add more monsters here;
                }
            }
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
                follower[i].action_points = 6;
                follower[i].setActorMoving(true);
                follower[i].setActorDestination(follower[i].tiledestx,
                        follower[i].tiledesty);
            }
        }
    }

    public void drawFollowers(HorrorTactics ht, int x, int y) {
        //map.monster[0].drawActor(this, map, x, y);
        for (int i = 0; i < this.follower_max; i++) {
            if (this.follower[i].visible == true) { //just to be sure
                this.follower[i].drawActor(ht, this, x, y);
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
        if (this.getActorAtXy(player, x, y)) {
            return player;
        }
        for (int i = 0; i < this.follower_max; i++) {
            if (this.getActorAtXy(follower[i], x, y)) {
                return this.follower[i];
            }
        }
        for (int i = 0; i < this.monster_max; i++) {
            if (this.getActorAtXy(monster[i], x, y)) {
                return this.monster[i];
            }
        }
        return null; //found nothing
    }

    public void onActorAttackActor(HorrorTactics ht, Actor attacker, Actor defender) {
        int dx = attacker.tiledestx;
        int dy = attacker.tiledesty;
        int actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_dodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_counterroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int actor_cdodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        //attacker.attack_timer = 45;
        //attacker.setAnimationFrame(4);
        //attacker.updateActorDirection();
        if (actor_attackroll > target_dodgeroll) {
            defender.dead = true;
            defender.action_msg = "Dead";
            defender.action_msg_timer = 400;
            defender.turns_till_revival = 0; //do we revive?
        } else {
            defender.action_msg = "Dodge";
            defender.action_msg_timer = 400;
        }
    }

    /*public void onActorCanAttack(HorrorTactics ht, Actor a) {
        //players and followers
        int dx = a.tiledestx;
        int dy = a.tiledesty;
        int actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_dodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int target_counterroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int actor_cdodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        if (actor_attackroll > target_dodgeroll
                && this.getAllPlayersAtXy(dx, dy) != null) {
            this.getAllPlayersAtXy(dx, dy).dead = true;
            this.getAllPlayersAtXy(dx, dy).action_msg = "Dead";
            this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
        } //else {
        //   this.getAllPlayersAtXy(dx, dy).action_msg = "Dodge";
        //    this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
        //}
    }*/
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
            monster[this.current_monster_moving].setAnimationFrame(4);
            monster[this.current_monster_moving].attack_timer = 20;
            if (monster_attackroll > player_dodgeroll) {
                this.getAllPlayersAtXy(dx, dy).dead = true;
                this.getAllPlayersAtXy(dx, dy).action_msg = "Dead";
                this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
            } else {
                this.getAllPlayersAtXy(dx, dy).action_msg = "Dodge";
                this.getAllPlayersAtXy(dx, dy).action_msg_timer = 400;
                //Counter Attack.
                if (player_counterroll > monster_cdodgeroll) {
                    //monster[this.current_monster_moving].dead = true;
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
        this.follower[this.current_follower_moving].onMoveActor(
                this, gc.getFPS());
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
                    monster[i].tiledestx = player.tilex;
                    monster[i].tiledesty = player.tiley;
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
}
