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
    public void mouseWasClicked(Input input, MyTiledMap map, HorrorTactics ht) {
        mouse_x = input.getMouseX();
        mouse_y = input.getMouseY();
        map.selected_tile_x = map.mouse_over_tile_x;
        map.selected_tile_y = map.mouse_over_tile_y;
        if (input.isMousePressed(0) == true) {
            //button_endturn.draw(10, gc.getScreenHeight()-64-10);
            if (mouse_x >= 10 && mouse_y >= ht.screen_height - 64 - 10
                    && mouse_x <= 10 + 164 && mouse_y <= ht.screen_height - 10) { //press end turn button.
                if (map.getAnyActorMoving() == false) {// &&
                    //map.player.getActorMoving() == false) {//be sure we didnt click when monster is moving
                    map.turn_order = "start monster";
                }
            }
            if (playerWasSelected(map) == true) {
                onPlayerSelection(map, map.player); //select or unselect actor
            } else if (followerWasSelected(map) == true) {
                //onPlayerSelection(map, map.follower[map.selected_follower]); //select or unselect actor
                //map.follower[map.selected_follower].selected = true;//direct it!
                map.player.selected = false; //just in case
            } else if (map.turn_order.equalsIgnoreCase("player")
                    && followerIsSelected(map)) {
                
                map.follower[map.selected_follower].tiledestx = map.selected_tile_x;
                map.follower[map.selected_follower].tiledesty = map.selected_tile_y;
                System.out.println("follower"+map.selected_follower+" was given a command"
                        +map.follower[map.selected_follower].tiledestx +"X"
                        +map.follower[map.selected_follower].tiledesty);
                map.current_follower_moving = map.selected_follower;
                map.follower[map.selected_follower].setActorMoving(true);
                
            } else if (map.turn_order.equalsIgnoreCase("player")
                    && map.player.isSelected() ) { //added limits so you cant set location when a monster is moving
                if (getClickedOnPlayerAction(ht, map) == true) {
                    map.selected_tile_x = map.mouse_over_tile_x;
                    map.selected_tile_y = map.mouse_over_tile_y;
                    if (map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y) != null) { //prepare to attack
                        //map.onActorCanAttack(ht, map.player);
                        if (map.isMonsterTouchingYou(map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y))) {
                            System.out.println("setting player animation frame.");
                            map.player.setAnimationFrame(4);
                            map.player.attack_timer = 25;

                            map.player.tiledestx = map.selected_tile_x;
                            map.player.tiledesty = map.selected_tile_y;
                            map.player.updateActorDirection();

                            map.onActorAttackActor(ht, map.player,
                                    map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y));
                            map.player.tiledestx = map.player.tilex;
                            map.player.tiledesty = map.player.tiley;
                        }
                        //map.player.attack_timer = 25;
                        //map.player.setAnimationFrame(4);
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
        if (input.isMouseButtonDown(0)) {
            /*The player must be selected*/
 /*Where is the mouse in relation to the player?*/
 /*set a proposed direction*/
 /**/
        }
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
                System.out.println("follower "+i+" is selected");
                return true;
            }
        }
        System.out.println("returned false? how? Follower 0 selected" +map.follower[0].selected);
        return false;
    }

    /* check the mouse click to see if a Player was clicked */

    public boolean followerWasSelected(MyTiledMap map) {
        for (int i = 0; i < map.follower_max; i++) {
            if (map.follower[i].tilex == map.mouse_over_tile_x
                    && map.follower[i].tiley == map.mouse_over_tile_y) {
                map.selected_follower = i;
                map.follower[i].selected = true;
                System.out.println("follower ("+map.selected_follower+") was selected");
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
        map.player.onSelectActor(false);
        for (int i = 0; i < map.follower_max; i++) {
            map.follower[i].selected = false;
        }
        a.selected = true; //set the actor as selected.
    }

    public void setMouseXY(int x, int y) {
        mouse_x = x;
        mouse_y = y;
    }
}
