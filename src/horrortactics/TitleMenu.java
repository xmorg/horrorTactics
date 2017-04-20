/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * THIS CLASS IS NOT USED!!!! HorrorTactics does EVERYTHING!
 * Do NOT BE FOOLED by OOP LIES!
 */
package horrortactics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
//import org.newdawn.slick.Music;
//import org.newdawn.slick.MusicListener;
//import org.newdawn.slick.state.BasicGameState;
//import org.newdawn.slick.Sound;


/**
 *
 * @author tcooper
 */
public class TitleMenu {
    public String menu_state; //newgame/ingame
    Image title_image;
    Image title_text, title_text_save, title_text_load;
    //Sound title_music;
    //MusicListener ml;
    public TitleMenu () throws SlickException  {
        this.menu_state = "newgame";
        title_image = new Image("data/title1_00.jpg"); //placeholder for title
        title_text = new Image("data/title_text.jpg");
        title_text_save = new Image("data/title_text_save.jpg");
        title_text_load = new Image("data/title_text_load.jpg");
        //title_music = new Music("data/soundeffects/anxiety_backwards.ogg");
        //title_music = new Sound("data/soundeffects/anxiety_backwards.ogg");
        //title_music.addListener(ml);
        
        //ml = new MusicListener();
    }
    public void showMenu(HorrorTactics ht) {
        int tx = 0;
        int ty = 0;
        if(ht.game_state.equalsIgnoreCase("title")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
            this.title_text_load.draw(ht.screen_width/2,ht.screen_height/2, this.title_text_load.getWidth(),this.title_text_load.getHeight());
        }
    }
    public void onMouseClick(HorrorTactics ht, int x, int y) {
        //running withing MouseActions.mouseWasClicked, so assuming mouse was 
        //already clicked
        if(ht.game_state.equalsIgnoreCase("title")) {
            ht.game_state = "tactical";
            //title_music.stop();
        }
    }
}
