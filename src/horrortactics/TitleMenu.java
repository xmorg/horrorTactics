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
    public String show_window;
    Image title_image;
    Image title_text, title_text_save, title_text_load, title_text_resume;
    //Sound title_music;
    //MusicListener ml;
    int menux;
    int menuy;
    int menuw;
    int menuh;
    Rectangle m_rect;
    Rectangle popup;
    Color wc;
    
    public TitleMenu (HorrorTactics ht) throws SlickException  {
        menu_state = "newgame";
        show_window = "none";
        title_image = new Image("data/title1_01.jpg"); //placeholder for title
        title_text = new Image("data/title_text.jpg");
        title_text_save = new Image("data/title_text_save.jpg");
        title_text_load = new Image("data/title_text_load.jpg");
        title_text_resume = new Image("data/title_text_resume.jpg");
        menux = 10;
        menuy = ht.screen_height/2;
        menuw = this.title_text_load.getWidth();
        menuh = this.title_text_load.getHeight();
        m_rect = new Rectangle(menux, menuy, menuw,menuh );
        popup = new Rectangle(menux+menuw+1, 100, 600,600); //rectangle for inner window
        //title_music = new Music("data/soundeffects/anxiety_backwards.ogg");
        //title_music = new Sound("data/soundeffects/anxiety_backwards.ogg");
        //title_music.addListener(ml);    
        //ml = new MusicListener();
        wc = new Color(20,20,20,200);
           
    }
    public void render(HorrorTactics ht, Graphics g) {
        int tx = ht.mouse_x;
        int ty = ht.mouse_y;
        
        if(ht.game_state.equalsIgnoreCase("title start")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
            //this.title_text_load.draw(ht.screen_width/2,ht.screen_height/2, this.title_text_load.getWidth(),this.title_text_load.getHeight());
            this.title_text_load.draw(menux, menuy, menuw, menuh);
        } else if(ht.game_state.equalsIgnoreCase("title ingame")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
            this.title_text_resume.draw(menux, menuy, menuw, menuh);
        }
        this.onMouseOver(ht, g, tx, ty);
        this.renderCredits(ht, g);
        this.renderLoadGame(ht, g);
        this.renderOptions(ht, g);
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
        if(ht.game_state.equalsIgnoreCase("title start") || ht.game_state.equalsIgnoreCase("title ingame")) {
            if (newgame_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                ht.game_state = "tactical"; //go through the list of               
            } else if(loadgame_rect.contains(x,y) && show_window.equalsIgnoreCase("none")) {
                //load game
                this.show_window = "load";
            } else if(options_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                //display options
                this.show_window = "options";
            } else if(credits_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                //display credits
                this.show_window = "credits";
            } else if(exit_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                //exit the game
                ht.setGameState("exit");
                //gc.exit();
            } else if(show_window.equalsIgnoreCase("load")) { //define a button here
                show_window = "none";
            } else if(show_window.equalsIgnoreCase("credits")) {
                show_window = "none";
            } else if(show_window.equalsIgnoreCase("options")) {
                show_window = "none";
            }
        }
    }
    public void onMouseOver(HorrorTactics ht, Graphics g, int x, int y) { //put a line under what is selected.
        Rectangle newgame_rect = new Rectangle(menux+28, menuy+24, 334,66);
        Rectangle loadgame_rect = new Rectangle(menux+28, menuy+146, 334,66);
        Rectangle options_rect = new Rectangle(menux+28, menuy+238, 334,66);
        Rectangle credits_rect = new Rectangle(menux+28, menuy+324, 334,66);
        Rectangle exit_rect = new Rectangle(menux+28, menuy+414, 334,66);
        if(ht.game_state.equalsIgnoreCase("title start") || ht.game_state.equalsIgnoreCase("title ingame")) {
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
    public void renderCredits(HorrorTactics ht, Graphics g) {
        if(this.show_window.equalsIgnoreCase("credits")) {
            //	drawRect(float x1, float y1, float width, float height)             
            g.setColor(this.wc);
            g.fillRect(popup.getX(),popup.getY(), popup.getWidth(), popup.getHeight());
            g.setColor(Color.white);
            g.drawString("Everything Done by Tim Cooper", popup.getX()+10, popup.getY()+10);
        }
    }
    public void renderOptions(HorrorTactics ht, Graphics g) {
        if(this.show_window.equalsIgnoreCase("options")) {
            g.setColor(this.wc);
            g.fillRect(popup.getX(),popup.getY(), popup.getWidth(), popup.getHeight());
            g.setColor(Color.white);
            g.drawString("Game Options", popup.getX()+200, popup.getY()+10);
            g.drawString("Fullscreen: ["+ht.fullscreen_toggle+"]", popup.getX()+20, popup.getY()+10+(20*1));
            g.drawString("Sound: ["+ht.sound_toggle+"]", popup.getX()+20, popup.getY()+10+(20*2));
        }
    }
    public void renderLoadGame(HorrorTactics ht, Graphics g) {
        if(this.show_window.equalsIgnoreCase("load")) {
            
            Rectangle slot1 = new Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100);
            Rectangle slot2 = new Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100);
            Rectangle slot3 = new Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100);
            g.setColor(this.wc);
            g.fillRect(popup.getX(),popup.getY(), popup.getWidth(), popup.getHeight());
            g.setColor(Color.white);
            g.fillRect(slot1.getX(), slot1.getY(), slot1.getWidth(), slot1.getHeight());
            g.fillRect(slot2.getX(), slot2.getY(), slot2.getWidth(), slot2.getHeight());
            g.fillRect(slot3.getX(), slot3.getY(), slot3.getWidth(), slot3.getHeight());
            g.setColor(Color.white);
            g.drawString("Load Game", popup.getX()+200, popup.getY()+10);
            g.setColor(Color.black);
            g.drawString("Save Slot 1", slot1.getX()+10, slot1.getY()+10);
            g.drawString("Save Slot 2", slot2.getX()+10, slot2.getY()+10);
            g.drawString("Save Slot 3", slot3.getX()+10, slot3.getY()+10);
        }
    }
}
