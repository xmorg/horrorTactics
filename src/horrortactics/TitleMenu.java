/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    //Sound title_music;
    //MusicListener ml;
    public TitleMenu () throws SlickException  {
        this.menu_state = "newgame";
        title_image = new Image("data/title1_00.jpg"); //placeholder for title
        //title_music = new Music("data/soundeffects/anxiety_backwards.ogg");
        //title_music = new Sound("data/soundeffects/anxiety_backwards.ogg");
        //title_music.addListener(ml);
        
        //ml = new MusicListener();
    }
    public void showMenu(HorrorTactics ht) {
        if(ht.game_state.equalsIgnoreCase("title")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
            //if(!title_music.playing()) {
            //    title_music.play();
            //}
            //title_music.
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
