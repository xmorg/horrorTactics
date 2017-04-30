/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * THIS CLASS IS NOT USED!!!! HorrorTactics does EVERYTHING!
 * Do NOT BE FOOLED by OOP LIES!
 */
package horrortactics;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Graphics;
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
    int menux;
    int menuy;
    int menuw;
    int menuh;
    Rectangle m_rect;
    
    public TitleMenu (HorrorTactics ht) throws SlickException  {
        this.menu_state = "newgame";
        //title_screen = new Image("data/title1_01.jpg");
        //title_text = new Image("data/title_text.jpg");
        //title_text_save = new Image("data/title_text_save.jpg");
        //title_text_load = new Image("data/title_text_load.jpg");
        title_image = new Image("data/title1_01.jpg"); //placeholder for title
        title_text = new Image("data/title_text.jpg");
        title_text_save = new Image("data/title_text_save.jpg");
        title_text_load = new Image("data/title_text_load.jpg");
        //title_music = new Music("data/soundeffects/anxiety_backwards.ogg");
        //title_music = new Sound("data/soundeffects/anxiety_backwards.ogg");
        //title_music.addListener(ml);    
        //ml = new MusicListener();
        
        menux = 10;
        menuy = ht.screen_height/2;
        menuw = this.title_text_load.getWidth();
        menuh = this.title_text_load.getHeight();
        m_rect = new Rectangle(menux, menuy, menuw,menuh );
    }
    public void render(HorrorTactics ht, Graphics g) {
        //int tx = 0;
        //int ty = 0;
        int tx = ht.mouse_x;
        int ty = ht.mouse_y;
        if(ht.game_state.equalsIgnoreCase("title")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
            //this.title_text_load.draw(ht.screen_width/2,ht.screen_height/2, this.title_text_load.getWidth(),this.title_text_load.getHeight());
            this.title_text_load.draw(menux, menuy, menuw, menuh);
        }
        this.onMouseOver(ht, g, tx, ty);
    }

    //public boolean getRect()
    public void onMouseClick(HorrorTactics ht, int x, int y) {
        //running withing MouseActions.mouseWasClicked, so assuming mouse was 
        //New Game,  28,42, 334x66
        //Load Game, 28, 146, 334x66
        //Options, 28, 238, 334x66
        //Credits, 28, 324
        //Exit, 28, 414
        Rectangle newgame_rect = new Rectangle(menux+28, menuy+24, 334,66);
        Rectangle loadgame_rect = new Rectangle(menux+28, menuy+146, 334,66);
        Rectangle options_rect = new Rectangle(menux+28, menuy+238, 334,66);
        Rectangle credits_rect = new Rectangle(menux+28, menuy+324, 334,66);
        Rectangle exit_rect = new Rectangle(menux+28, menuy+414, 334,66);
        if(ht.game_state.equalsIgnoreCase("title")) {
            if (newgame_rect.contains(x, y)) {
                ht.game_state = "tactical"; //go through the list of
            } else if(loadgame_rect.contains(x,y)) {
                //load game
            } else if(options_rect.contains(x, y)) {
                //display options
            } else if(credits_rect.contains(x, y)) {
                //display credits
            } else if(exit_rect.contains(x, y)) {
                //exit the game
                ht.setGameState("exit");
                //game_state = "exit";
                //gc.exit();
            }
        }
    }
    public void onMouseOver(HorrorTactics ht, Graphics g, int x, int y) { //put a line under what is selected.
        Rectangle newgame_rect = new Rectangle(menux+28, menuy+24, 334,66);
        Rectangle loadgame_rect = new Rectangle(menux+28, menuy+146, 334,66);
        Rectangle options_rect = new Rectangle(menux+28, menuy+238, 334,66);
        Rectangle credits_rect = new Rectangle(menux+28, menuy+324, 334,66);
        Rectangle exit_rect = new Rectangle(menux+28, menuy+414, 334,66);
        if(ht.game_state.equalsIgnoreCase("title")) {
            g.setLineWidth(4);
            g.setColor(Color.red);
            if (newgame_rect.contains(x, y)) {
                //g.setColor(Color.red);
                g.drawLine(
                        newgame_rect.getX(), 
                        newgame_rect.getY()+newgame_rect.getHeight()+10, 
                        newgame_rect.getX()+newgame_rect.getWidth(), 
                        newgame_rect.getY()+newgame_rect.getHeight()+10
                );
            } else if(loadgame_rect.contains(x,y)) {
                //load game
                //g.setColor(Color.red);
                g.drawLine(
                        loadgame_rect.getX(), 
                        loadgame_rect.getY()+loadgame_rect.getHeight(), 
                        loadgame_rect.getX()+loadgame_rect.getWidth(), 
                        loadgame_rect.getY()+loadgame_rect.getHeight()
                );
            } else if(options_rect.contains(x, y)) {
                //display options
                //g.setColor(Color.red);
                g.drawLine(
                        options_rect.getX(), 
                        options_rect.getY()+options_rect.getHeight(), 
                        options_rect.getX()+options_rect.getWidth(), 
                        options_rect.getY()+options_rect.getHeight()
                );
            } else if(credits_rect.contains(x, y)) {
                //display credits
                //g.setColor(Color.red);
                g.drawLine(
                        credits_rect.getX(), 
                        credits_rect.getY()+credits_rect.getHeight(), 
                        credits_rect.getX()+credits_rect.getWidth(), 
                        credits_rect.getY()+credits_rect.getHeight()
                );
            } else if(exit_rect.contains(x, y)) {
                //g.setColor(Color.red);
                g.drawLine(
                        exit_rect.getX(), 
                        exit_rect.getY()+exit_rect.getHeight(), 
                        exit_rect.getX()+exit_rect.getWidth(), 
                        exit_rect.getY()+exit_rect.getHeight()
                );
            }
        }
    }
}
