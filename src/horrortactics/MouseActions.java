/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

//import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author tcooper
 */
public class MouseActions {

    private int mouse_x, mouse_y = 0;
    int mouse_tile_x, mouse_tile_y = 0;

    public MouseActions() {
    }
    
    Actor getMSelectedActor(HorrorTactics ht) {
        boolean foundselected = false;
        for(int i= 0; i < ht.map.follower_max; i++) {
            if(ht.map.follower[i].selected == true) {
                foundselected = true;
                //ht.map.follower[i].drawPopupWindow(this, g);
                return ht.map.follower[i];
            }
        }
        if(foundselected == false) {
            //this.map.player.drawPopupWindow(this, g); //who is currently selected?
            return ht.map.player;
        }
        return ht.map.player; //default is player
    }
    
    public void levelupButtonWasPressed(HorrorTactics ht) {
        int w = 400; //same as whats drawn on the screen.
        int h = 400;
        int x = ht.screen_width / 2 - w / 2;
        int y = ht.screen_height / 2 - h / 2;
        /* ON LEVEL UP, click here to increase stat        
        g.drawString(LevelUpControls+" Strength: " + this.stat_str, x + 200, y + 120);
        g.drawString(LevelUpControls+" Speed: " + this.stat_speed, x + 200, y + 140);
        g.drawString(LevelUpControls+" Willpower: " + this.stat_will, x + 200, y + 160);
        g.drawString(LevelUpControls+" Luck: " + this.stat_luck, x + 200, y + 180);
        first, determine if it was pressed.
        */
        int xi = x+200;
        int yi = y+120;
        Rectangle rect_str = new Rectangle(xi, yi, 200, 20);
        if (ht.popup_window.equalsIgnoreCase("profile")) {
            if(rect_str.contains(this.mouse_x, this.mouse_y)) {
            //if(this.mouse_x >= xi && this.mouse_x <= xi +200
            //        && this.mouse_y >=yi && this.mouse_y <= yi+20) {
                if(getMSelectedActor(ht).newLevelUp) {//true
                    getMSelectedActor(ht).stat_str++;
                    getMSelectedActor(ht).health_points_max += 2;
                    getMSelectedActor(ht).newLevelUp = false; //you clicked and now its done.
                }
            }
            else if(this.mouse_x >= xi && this.mouse_x <= xi +200
                    && this.mouse_y >=yi+20 && this.mouse_y <= yi+20*2) {
                //increase Speed
                if(getMSelectedActor(ht).newLevelUp) {//true
                    getMSelectedActor(ht).stat_speed++; //TODO! we need to add action point bonus for speed.
                    getMSelectedActor(ht).health_points_max += 1;
                    getMSelectedActor(ht).newLevelUp = false; //you clicked and now its done.
                }
            }
            else if(this.mouse_x >= xi && this.mouse_x <= xi +200
                    && this.mouse_y >=yi+20*2 && this.mouse_y <= yi+20*3) {
                //increase Willpower
                if(getMSelectedActor(ht).newLevelUp) {//true
                    getMSelectedActor(ht).stat_will++;
                    getMSelectedActor(ht).health_points_max += 1;
                    getMSelectedActor(ht).fatigue_points_max += 1;
                    getMSelectedActor(ht).newLevelUp = false; //you clicked and now its done.
                }
            }
            else if(this.mouse_x >= xi && this.mouse_x <= xi +200
                    && this.mouse_y >=yi+20*3 && this.mouse_y <= yi+20*4) {
                //increase Luck
                if(getMSelectedActor(ht).newLevelUp) {//true
                    getMSelectedActor(ht).stat_luck++; //what is luck? on dice rolls add, +stat_luck -1
                    getMSelectedActor(ht).health_points_max += 1;
                    getMSelectedActor(ht).newLevelUp = false; //you clicked and now its done.
                }
            }
        }
    }
    
    public boolean endTurnButtonWasOver(HorrorTactics ht) { //, MyTiledMap map) {
        int bx = ht.screen_width- 200;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) { //press end turn button.
            return true;
        } else {
            return false;
        }
    }
    public boolean endTurnButtonWasPressed(HorrorTactics ht) { //, MyTiledMap map) {
        int bx = ht.screen_width- 200;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) { //press end turn button.
            if (ht.map.getAnyActorMoving() == false) {
                return true;
            } else {
                System.out.print("End turn was pressed, but someone is still moving?\n");
                return false;
            }
        }
        return false;
    }
    public boolean menuButtonWasOver(HorrorTactics ht) { //, MyTiledMap map) {
        int bx = ht.screen_width- 100;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) { //press end turn button.
            return true;
        } else {
            return false;
        }
    }
    public boolean menuButtonWasPressed(HorrorTactics ht) { //, MyTiledMap map) {
        int bx = ht.screen_width- 100;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) { //press end turn button.
            System.out.println("menu button was pressed.");
            if (ht.map.getAnyActorMoving() == false || ht.game_state.equalsIgnoreCase("game over")) {
                //System.out.println("menu was pressed, and it worked."); //we know this is happening on gameover
                return true;
            } else {
                System.out.println("menu was pressed, but someone is still moving?");
                return false;
            }
        }
        return false;
    }
    public boolean profileButtonWasOver(HorrorTactics ht) {
        int bx = ht.screen_width- 300;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) {
            return true;
        } else {
            return false;
        }
    }
    public boolean profileButtonWasPressed(HorrorTactics ht) {
        int bx = ht.screen_width- 300;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) {
            if (ht.popup_window.equalsIgnoreCase("none") ) {
                ht.popup_window = "profile";
            }
            else if (ht.popup_window.equalsIgnoreCase("profile") ) {
                ht.popup_window = "none";
            }
            else {
                ht.popup_window = "profile";
            }
            return true;
        }
        return false;
    }
    public boolean itemsButtonWasOver(HorrorTactics ht) {
        int bx = ht.screen_width- 400;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean itemsButtonWasPressed(HorrorTactics ht) {
        int bx = ht.screen_width- 400;
        int bw = 92;
        if (mouse_x >= bx && mouse_y >= ht.screen_height - 64 - 10
                && mouse_x <= bx + bw && mouse_y <= ht.screen_height - 10) {
            if (ht.popup_window.equalsIgnoreCase("none") ) {
                ht.popup_window = "items";
            }
            else if (ht.popup_window.equalsIgnoreCase("items") ) {
                ht.popup_window = "none";
            }
            else {
                ht.popup_window = "items";
            }
            return true;
        }
        return false;
    }

    public void mouseWasClicked(Input input, MyTiledMap map, HorrorTactics ht) {
        
        mouse_x = input.getMouseX();
        mouse_y = input.getMouseY();
        map.selected_tile_x = map.mouse_over_tile_x;
        map.selected_tile_y = map.mouse_over_tile_y;
        int scrollspeed = 3;
        //ht.titlemenu.onMouseOver(ht, ht.g, mouse_x, mouse_y);        
        if (input.isMousePressed(0) == true) {
            ht.titlemenu.onMouseClick(ht, mouse_x, mouse_y);
            //now we need to, stop eveything below.
            if (map.turn_order.equalsIgnoreCase("planning")) {
                if (map.planevent == map.maxplanevent || map.planning[map.planevent].equalsIgnoreCase("end")) {
                    map.turn_order = "start player";
                } else {
                    map.planevent++;
                }
            }
            else if(map.turn_order.equalsIgnoreCase("title") && ht.game_state.equalsIgnoreCase("tactical")) {
                map.turn_order = "planning";
            }
            else if (ht.popup_window.equalsIgnoreCase("profile")) {
                levelupButtonWasPressed(ht); //simple check
                this.profileButtonWasPressed(ht);
            }
            else if (this.profileButtonWasPressed(ht)) { 
            }
            else if (this.itemsButtonWasPressed(ht)) {                
            }
            else if (this.menuButtonWasPressed(ht)) {  
                ht.game_state = "title ingame";
            }
            else if (endTurnButtonWasPressed(ht) == true) { //(mouse_x >= 10 && mouse_y >= ht.screen_height - 64 - 10
                System.out.print("End turn was pressed\n");
                map.turn_order = "start monster"; //End Turn.
            } else if (map.turn_order.equalsIgnoreCase("event spotted")) {
                map.turn_order = map.old_turn_order; //return to previous turn after click
            } else if (map.turn_order.equalsIgnoreCase("goal reached")) {
                map.turn_order = map.old_turn_order; //return to previous turn after click
            } else if (map.turn_order.equalsIgnoreCase("exit reached")) {
                map.turn_order = "change map";
            } else if (playerWasSelected(map) == true) {
                onPlayerSelection(map, map.player); //select or unselect actor
            } else if (followerWasSelected(map) == true) {
                map.player.selected = false; //just in case
            } else if (map.turn_order.equalsIgnoreCase("player")
                    && followerIsSelected(map)
                    && endTurnButtonWasPressed(ht) == false) {
                if (map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y) != null) { //prepare to attack
                    //map.onActorCanAttack(ht, map.player);
                    //BUG: if (this.isActorTouchingActor(a, this.player, a.tilex, a.tiley)) {
                    if (map.follower[map.selected_follower].attack_range > 1) {
                        //make sure there is an enemey here.
                        //you have the range sir!
                        map.follower[map.selected_follower].onAttack(ht); // Attack code here.
                    }
                    else if (map.isMonsterTouchingYou(map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y))) {
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
                    && endTurnButtonWasPressed(ht) == false
                    && map.player.isSelected()) { //added limits so you cant set location when a monster is moving
                if (getClickedOnPlayerAction(ht, map) == true) {
                    map.selected_tile_x = map.mouse_over_tile_x;
                    map.selected_tile_y = map.mouse_over_tile_y;
                    if (map.getAllPlayersAtXy(map.selected_tile_x, map.selected_tile_y) != null) { //prepare to attack
                        //map.onActorCanAttack(ht, map.player);
                        map.player.tiledestx = map.selected_tile_x;
                        map.player.tiledesty = map.selected_tile_y;
                        if (map.isMonsterTouchingYou(map.getAllPlayersAtXy(map.player.tiledestx, map.player.tiledesty))) {
                            //System.out.println("setting player animation frame.");
                            map.player.onAttack(ht);
                        }
                    } else {
                        map.player.tiledestx = map.selected_tile_x;
                        map.player.tiledesty = map.selected_tile_y;
                        map.player.setActorMoving(true);
                    }
                }
            } else { //does not have turn

            }
        }
        if (input.isMouseButtonDown(1) && ht.map.turn_order.equalsIgnoreCase("player")
                && this.endTurnButtonWasPressed(ht) == false) {
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
