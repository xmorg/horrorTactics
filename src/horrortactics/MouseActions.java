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
        if (input.isMousePressed(0) == true) {
            if (playerWasSelected(map) == true) {
                onPlayerSelection(map); //select or unselect actor
            } else if (map.player.isSelected() == true) {
                if (map.player.isHasTurn() == true) {
                    if (getClickedOnPlayerAction(ht, map) == true) {
                        map.player.onSelectActor(false);
                        map.selected_tile_x = map.mouse_over_tile_x;
                        map.selected_tile_y = map.mouse_over_tile_y;
                        //map.player.tilex = map.selected_tile_x;
                        //map.player.tiley = map.selected_tile_y;
                        map.player.setActorMoving(true);
                        //onSetTileSelection(ht, map, map.selected_tile_x, 
                        //        map.selected_tile_y);
                    }
                }
            } else { //does not have turn

            }
        }/*if (input.isMousePressed(0) == true) {*/
        /*now check if you held the mouse down*/
        if(input.isMouseButtonDown(0)) {
            /*The player must be selected*/
            /*Where is the mouse in relation to the player?*/
            /*set a proposed direction*/
            /**/
        }
    }
    
    public boolean getClickedOnPlayerAction(HorrorTactics ht, MyTiledMap m)
    {
        /*check if the tiles have walls.*/
        /*if (map.getTileImage(x, y, walls_layer) != null) {*/
        if(m.mouse_over_tile_x > 0  && m.mouse_over_tile_y > 0
                && m.getPassableTile(m.mouse_over_tile_x, m.mouse_over_tile_y) == true) {
            return true;
        }
        return false;
    }
    
    /* check the mouse click to see if a Player was clicked */
    public boolean playerWasSelected(MyTiledMap map) {
        if (map.player.tilex == map.mouse_over_tile_x && 
                map.player.tiley == map.mouse_over_tile_y) {
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
    
    /*public boolean tileWasSelected(MyTiledMap map, int px, int py) {
        if (mouse_x >= px && mouse_x <= px + 250 && mouse_y >= py && mouse_y <= py + 130) {
            return true;
        } else {
            return false;
        }
    }*/
    
     /* given X,Y(tiles) from tiles cycled through by onSetTileSelection 
        set map.selected_tile_x/y to what tile was selected.*/
    public boolean onSetTileSelection(HorrorTactics h, MyTiledMap m, int tx, int ty, int px, int py) {
        if (mouse_x >= px && mouse_x <= px + m.TILE_WIDTH_HALF * 2
                && mouse_y >= py && mouse_y <= py + m.TILE_HEIGHT_HALF * 2) {
            m.selected_tile_x = tx;
            m.selected_tile_y = ty;
            return true;
        }
        else {
            return false;
        }
    }
    
    /* You selected a player*/
    public void onPlayerSelection(MyTiledMap map) {
        map.player.onSelectActor(!map.player.isSelected());
    }
    public void setMouseXY(int x, int y) {
        mouse_x = x;
        mouse_y = y;
    }
}
