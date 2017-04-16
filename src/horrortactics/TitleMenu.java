/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;

/**
 *
 * @author tcooper
 */
public class TitleMenu {
    public String menu_state; //newgame/ingame
    Image title_image;
    public TitleMenu () throws SlickException  {
        this.menu_state = "newgame";
        title_image = new Image("data/title1_00.jpg"); //placeholder for title
    }
    public void showMenu(HorrorTactics ht) {
        if(ht.game_state.equalsIgnoreCase("title")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
        }
    }
    public void onMouseClick(HorrorTactics ht, int x, int y) {
        //running withing MouseActions.mouseWasClicked, so assuming mouse was 
        //already clicked
        if(ht.game_state.equalsIgnoreCase("title")) {
            ht.game_state = "tactical";
        }
    }
}
