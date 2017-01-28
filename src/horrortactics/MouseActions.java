/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

//import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 *
 * @author tcooper
 */
public class MouseActions {

    private int mouse_x, mouse_y = 0;
    int mouse_tile_x, mouse_tile_y = 0;

    public MouseActions() {
    }

    /* What happens when a mouse button is clicked. */
    public boolean endTurnButtonWasPressed(HorrorTactics ht) { //, MyTiledMap map) {
        if (mouse_x >= 10 && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= 10 + 164 && mouse_y <= ht.screen_height - 10) { //press end turn button.
            if (ht.map.getAnyActorMoving() == false) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }
    

    public void mouseWasClicked(Input input, MyTiledMap map, HorrorTactics ht) {
        mouse_x = input.getMouseX();
        mouse_y = input.getMouseY();
        map.selected_tile_x = map.mouse_over_tile_x;
        map.selected_tile_y = map.mouse_over_tile_y;
        int scrollspeed = 3;

        if (input.isMousePressed(0) == true) {
            //button_endturn.draw(10, gc.getScreenHeight()-64-10);
            if (map.turn_order.equalsIgnoreCase("planning")) {
                if (map.planevent == map.maxplanevent || map.planning[map.planevent].equalsIgnoreCase("end")) {
                    map.turn_order = "start player";
                } else {
                    map.planevent++;
                }
            } else if (map.turn_order.equalsIgnoreCase("event spotted")) {
                map.turn_order = map.old_turn_order; //return to previous turn after click
            } else if (map.turn_order.equalsIgnoreCase("goal reached")) {
                map.turn_order = map.old_turn_order; //return to previous turn after click
            } else if (map.turn_order.equalsIgnoreCase("exit reached")) {
                map.turn_order = "change map";
            } else if (endTurnButtonWasPressed(ht) == true) { //(mouse_x >= 10 && mouse_y >= ht.screen_height - 64 - 10
                    //&& mouse_x <= 10 + 164 && mouse_y <= ht.screen_height - 10) { //press end turn button.
                //if (map.getAnyActorMoving() == false) {// &&
                    map.turn_order = "start monster"; //End Turn.
                //}
            } else if (playerWasSelected(map) == true) {
                onPlayerSelection(map, map.player); //select or unselect actor
            } else if (followerWasSelected(map) == true) {
                map.player.selected = false; //just in case
            } else if (map.turn_order.equalsIgnoreCase("player")
                    && followerIsSelected(map)) {
                if (map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y) != null) { //prepare to attack
                    //map.onActorCanAttack(ht, map.player);
                    //BUG: if (this.isActorTouchingActor(a, this.player, a.tilex, a.tiley)) {
                    if (map.isMonsterTouchingYou(map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y))) {
                        map.follower[map.selected_follower].onAttack(ht); // Attack code here.
                    }
                } else {

                    map.selected_tile_x = map.mouse_over_tile_x;
                    map.selected_tile_y = map.mouse_over_tile_y;
                    map.follower[map.selected_follower].tiledestx = map.selected_tile_x;
                    map.follower[map.selected_follower].tiledesty = map.selected_tile_y;
                    map.current_follower_moving = map.selected_follower;
                    map.follower[map.selected_follower].setActorMoving(true);
                }

            } else if (map.turn_order.equalsIgnoreCase("player")
                    && map.player.isSelected()) { //added limits so you cant set location when a monster is moving
                if (getClickedOnPlayerAction(ht, map) == true) {
                    map.selected_tile_x = map.mouse_over_tile_x;
                    map.selected_tile_y = map.mouse_over_tile_y;
                    if (map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y) != null) { //prepare to attack
                        //map.onActorCanAttack(ht, map.player);
                        if (map.isMonsterTouchingYou(map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y))) {
                            System.out.println("setting player animation frame.");
                            map.player.onAttack(ht);
                        }
                    } else {
                        map.player.tiledestx = map.selected_tile_x;
                        map.player.tiledesty = map.selected_tile_y;
                        map.player.setActorMoving(true);
                    }
                    //}
                }
            } else { //does not have turn

            }
        }/*if (input.isMousePressed(0) == true) {*/
 /*now check if you held the mouse down*/
        if (input.isMouseButtonDown(0) && ht.map.turn_order.equalsIgnoreCase("player")) {
            //mouse_x = input.getMouseX();
            //mouse_y = input.getMouseY();
            if (mouse_x > ht.last_mouse_x) {
                ht.draw_x += scrollspeed;
            } else if (mouse_x < ht.last_mouse_x) {
                ht.draw_x -= scrollspeed;
            }

            if (mouse_y > ht.last_mouse_y) {
                ht.draw_y += scrollspeed;
            } else if (mouse_y < ht.last_mouse_y) {
                ht.draw_y -= scrollspeed;
            }
        }
        ht.last_mouse_x = mouse_x;
        ht.last_mouse_y = mouse_y;
    }

    public boolean getClickedOnPlayerAction(HorrorTactics ht, MyTiledMap m) {
        /*check if the tiles have walls.*/
 /*if (map.getTileImage(x, y, walls_layer) != null) {*/
        if (m.mouse_over_tile_x > 0 && m.mouse_over_tile_y > 0) {
            //&& m.getPassableTile(m.player,m.mouse_over_tile_x, m.mouse_over_tile_y) == true) {
            return true;
        }
        return false;
    }

    public boolean followerIsSelected(MyTiledMap map) {
        for (int i = 0; i < map.follower_max; i++) {
            if (map.follower[i].selected == true) {
                map.selected_follower = i;
                System.out.println("follower " + i + " is selected");
                return true;
            }
        }
        //System.out.println("returned false? how? Follower 0 selected" + map.follower[0].selected);
        return false;
    }

    /* check the mouse click to see if a Player was clicked */
    public boolean followerWasSelected(MyTiledMap map) {
        for (int i = 0; i < map.follower_max; i++) {
            if (map.follower[i].tilex == map.mouse_over_tile_x
                    && map.follower[i].tiley == map.mouse_over_tile_y) {
                map.selected_follower = i;
                map.follower[i].selected = true;
                //i = selected_follower;
                map.player.selected = false; //unselect everyone
                for (int s = 0; s < map.follower_max; s++) {
                    if (s != i) {
                        map.follower[s].selected = false;
                    }
                }
                System.out.println("follower (" + map.selected_follower + ") was selected");
                return true;
            }
        }
        return false;
    }

    public boolean playerWasSelected(MyTiledMap map) {
        if (map.player.tilex == map.mouse_over_tile_x
                && map.player.tiley == map.mouse_over_tile_y) {
            return true;
        } else if (mouse_x >= map.player.get_draw_x() /* The player */
                && mouse_x <= (map.player.get_draw_x() + 300)
                && mouse_y >= map.player.get_draw_y()
                && mouse_y <= (map.player.get_draw_y() + 300)) {
            return true;
            /* A player was clicked */
        } else {
            return false;
            /* No player was clicked */
        }
    }

    public boolean onSetTileSelection(HorrorTactics h, MyTiledMap m, int tx, int ty, int px, int py) {
        if (mouse_x >= px && mouse_x <= px + m.TILE_WIDTH_HALF * 2
                && mouse_y >= py && mouse_y <= py + m.TILE_HEIGHT_HALF * 2) {
            m.selected_tile_x = tx;
            m.selected_tile_y = ty;
            return true;
        } else {
            return false;
        }
    }

    /* You selected a player*/
    public void onPlayerSelection(MyTiledMap map, Actor a) {
        //reset everyone.
        boolean toselect;
        if (a.selected == true) {
            toselect = false;
        } else {
            toselect = true;
        }

        map.player.onSelectActor(false);
        for (int i = 0; i < map.follower_max; i++) {
            map.follower[i].selected = false;
        }
        a.selected = toselect;

    }

    public void setMouseXY(int x, int y) {
        mouse_x = x;
        mouse_y = y;
    }
}
