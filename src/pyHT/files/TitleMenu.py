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
    Rectangle slot1; // = new Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100);
    Rectangle slot2; // = new Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100);
    Rectangle slot3; // = new Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100);
    SaveMyFile savefile; // savefile = new SaveMyFile(); //to get the functions.

    public TitleMenu(HorrorTactics ht) throws SlickException {
        menu_state = "newgame";
        show_window = "none";
        title_image = new Image("data/title1_01.jpg"); //placeholder for title
        title_text = new Image("data/title_text.jpg"); 
        title_text_save = new Image("data/title_text_save.jpg");  //depriciated? resume, new, save, options, credits, exit
        title_text_load = new Image("data/title_text_load.jpg");  //new, load, optsion, credits, exit
        title_text_resume = new Image("data/title_text_resume2.jpg"); //resume, load, save, options, exit
        menux = 10;
        menuy = ht.screen_height / 2;
        menuw = this.title_text_load.getWidth();
        menuh = this.title_text_load.getHeight();
        m_rect = new Rectangle(menux, menuy, menuw, menuh);
        popup = new Rectangle(menux + menuw + 1, 100, 600, 600); //rectangle for inner window
        //title_music = new Music("data/soundeffects/anxiety_backwards.ogg");
        //title_music = new Sound("data/soundeffects/anxiety_backwards.ogg");
        //title_music.addListener(ml);    
        //ml = new MusicListener();
        wc = new Color(20, 20, 20, 200);
        slot1 = new Rectangle(popup.getX(), popup.getY() + 35, popup.getWidth(), 100);
        slot2 = new Rectangle(popup.getX(), popup.getY() + 35 + 103, popup.getWidth(), 100);
        slot3 = new Rectangle(popup.getX(), popup.getY() + 35 + 206, popup.getWidth(), 100);
        savefile = new SaveMyFile(); //to get the functions.
    }

    public void render(HorrorTactics ht, Graphics g) {
        int tx = ht.mouse_x;
        int ty = ht.mouse_y;

        if (ht.game_state.equalsIgnoreCase("title start")) {
            this.title_image.draw(0, 0, ht.screen_width, ht.screen_height); //draw the menu
            //this.title_text_load.draw(ht.screen_width/2,ht.screen_height/2, this.title_text_load.getWidth(),this.title_text_load.getHeight());
            this.title_text_load.draw(menux, menuy, menuw, menuh);
        } else if (ht.game_state.equalsIgnoreCase("title ingame")) {
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
        Rectangle newgame_rect = new Rectangle(menux + 28, menuy + 24, 334, 66); //new, resume
        Rectangle loadgame_rect = new Rectangle(menux + 28, menuy + 146, 334, 66); //load or save
        Rectangle options_rect = new Rectangle(menux + 28, menuy + 238, 334, 66);
        Rectangle savegame_rect = new Rectangle(menux + 28, menuy + 238, 334, 66);
        Rectangle credits_rect = new Rectangle(menux + 28, menuy + 324, 334, 66);
        Rectangle exit_rect = new Rectangle(menux + 28, menuy + 414, 334, 66);
        //Rectangle slot1 = new Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100);
        //Rectangle slot2 = new Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100);
        //Rectangle slot3 = new Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100);
        if (ht.game_state.equalsIgnoreCase("title start") || ht.game_state.equalsIgnoreCase("title ingame")) {
            if (newgame_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                ht.game_state = "tactical"; //go through the list of               
            } else if (loadgame_rect.contains(x, y) && show_window.equalsIgnoreCase("none") && ht.game_state.equalsIgnoreCase("title start")) {
                //load game
                this.show_window = "load";
            } else if (loadgame_rect.contains(x, y) && show_window.equalsIgnoreCase("none") && ht.game_state.equalsIgnoreCase("title ingame")) {
                //save game
                this.show_window = "load";
            } else if (savegame_rect.contains(x, y) && show_window.equalsIgnoreCase("none") && ht.game_state.equalsIgnoreCase("title ingame")) {
                //save game
                this.show_window = "save";
            } else if (options_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                //display options
                this.show_window = "options";
            } else if (credits_rect.contains(x, y) && show_window.equalsIgnoreCase("none")) {
                //display credits
                this.show_window = "credits";
            } else if (exit_rect.contains(x, y) && show_window.equalsIgnoreCase("none") && ht.game_state.equalsIgnoreCase("title ingame")) {
                //exit the game
                ht.setGameState("title start");
            }else if (exit_rect.contains(x, y) && show_window.equalsIgnoreCase("none") && ht.game_state.equalsIgnoreCase("title start")) {
                //exit the game
                ht.setGameState("exit");
            } else if (show_window.equalsIgnoreCase("load")) { //define a button here
                show_window = "none";
                if (this.slot1.contains(x, y)) { //you clicked to load save 1
                    if (this.savefile.readFile("Save1.txt")) {
                        try {
                            ht.loadNewMap("data/" + this.savefile.checkForMapInSaveFile("Save1.txt"));
                            this.savefile.checkMapSettingsInSaveFile(ht, "Save1.txt");
                            ht.game_state = "tactical";
                            ht.map.turn_order = "player";
                            ht.translateToTile(ht.map.player.tilex, ht.map.player.tiley);
                            //After loading you need to scroll to the player
                        } catch (SlickException e) {
                            //?
                        }
                    }
                } else if (this.slot2.contains(x, y)) { //you clicked to load save 2
                    if (this.savefile.readFile("Save2.txt")) {
                        try {
                            ht.loadNewMap("data/" + this.savefile.checkForMapInSaveFile("Save2.txt"));
                            this.savefile.checkMapSettingsInSaveFile(ht, "Save2.txt");
                            ht.game_state = "tactical";
                            ht.map.turn_order = "player";
                            ht.translateToTile(ht.map.player.tilex, ht.map.player.tiley);
                            //After loading you need to scroll to the player
                        } catch (SlickException e) {
                        }
                    }
                } else if (this.slot3.contains(x, y)) { //you click to load save 3
                    if (this.savefile.readFile("Save3.txt")) {
                        try {
                            ht.loadNewMap("data/" + this.savefile.checkForMapInSaveFile("Save3.txt"));
                            this.savefile.checkMapSettingsInSaveFile(ht, "Save2.txt");
                            ht.game_state = "tactical";
                            ht.map.turn_order = "player";
                            ht.translateToTile(ht.map.player.tilex, ht.map.player.tiley);
                            //After loading you need to scroll to the player
                        } catch (SlickException e) {
                        }
                    }
                }
            } else if (show_window.equalsIgnoreCase("save")) { //define a button here
                //You wanted to save the game at slot ?
                if (this.slot1.contains(x, y)) {
                    //save to this file
                    ht.playerfile.savePlayerMapData(ht, "1");
                } else if (this.slot2.contains(x, y)) {
                    ht.playerfile.savePlayerMapData(ht, "2");
                } else if (this.slot3.contains(x, y)) {
                    ht.playerfile.savePlayerMapData(ht, "3");
                }
                show_window = "none";
            } else if (show_window.equalsIgnoreCase("credits")) {
                show_window = "none";
            } else if (show_window.equalsIgnoreCase("options")) {
                show_window = "none";
            }
        }
    }

    public void onMouseOver(HorrorTactics ht, Graphics g, int x, int y) { //put a line under what is selected.
        Rectangle newgame_rect = new Rectangle(menux + 28, menuy + 24, 334, 66);
        Rectangle loadgame_rect = new Rectangle(menux + 28, menuy + 146, 334, 66);
        Rectangle options_rect = new Rectangle(menux + 28, menuy + 238, 334, 66);
        Rectangle credits_rect = new Rectangle(menux + 28, menuy + 324, 334, 66);
        Rectangle exit_rect = new Rectangle(menux + 28, menuy + 414, 334, 66);
        if (ht.game_state.equalsIgnoreCase("title start") || ht.game_state.equalsIgnoreCase("title ingame")) {
            g.setLineWidth(4);
            g.setColor(Color.red);
            if (newgame_rect.contains(x, y)) {
                //g.setColor(Color.red);
                g.drawLine(
                        newgame_rect.getX(),
                        newgame_rect.getY() + newgame_rect.getHeight() + 10,
                        newgame_rect.getX() + newgame_rect.getWidth(),
                        newgame_rect.getY() + newgame_rect.getHeight() + 10
                );
            } else if (loadgame_rect.contains(x, y)) {
                //load game //g.setColor(Color.red);
                g.drawLine(
                        loadgame_rect.getX(),
                        loadgame_rect.getY() + loadgame_rect.getHeight(),
                        loadgame_rect.getX() + loadgame_rect.getWidth(),
                        loadgame_rect.getY() + loadgame_rect.getHeight()
                );
            } else if (options_rect.contains(x, y)) {
                //display options //g.setColor(Color.red);
                g.drawLine(
                        options_rect.getX(),
                        options_rect.getY() + options_rect.getHeight(),
                        options_rect.getX() + options_rect.getWidth(),
                        options_rect.getY() + options_rect.getHeight()
                );
            } else if (credits_rect.contains(x, y)) {
                //display credits //g.setColor(Color.red);
                g.drawLine(
                        credits_rect.getX(),
                        credits_rect.getY() + credits_rect.getHeight(),
                        credits_rect.getX() + credits_rect.getWidth(),
                        credits_rect.getY() + credits_rect.getHeight()
                );
            } else if (exit_rect.contains(x, y)) {
                //g.setColor(Color.red);
                g.drawLine(
                        exit_rect.getX(),
                        exit_rect.getY() + exit_rect.getHeight(),
                        exit_rect.getX() + exit_rect.getWidth(),
                        exit_rect.getY() + exit_rect.getHeight()
                );
            }
        }
    }

    public void renderCredits(HorrorTactics ht, Graphics g) {
        if (this.show_window.equalsIgnoreCase("credits")) {
            //	drawRect(float x1, float y1, float width, float height)             
            g.setColor(this.wc);
            g.fillRect(popup.getX(), popup.getY(), popup.getWidth(), popup.getHeight());
            g.setColor(Color.white);
            g.drawString("Everything Done by Tim Cooper", popup.getX() + 10, popup.getY() + 10);
        }
    }

    public void renderOptions(HorrorTactics ht, Graphics g) {
        if (this.show_window.equalsIgnoreCase("options")) {
            g.setColor(this.wc);
            g.fillRect(popup.getX(), popup.getY(), popup.getWidth(), popup.getHeight());
            g.setColor(Color.white);
            g.drawString("Game Options", popup.getX() + 200, popup.getY() + 10);
            g.drawString("Fullscreen: [" + ht.fullscreen_toggle + "]", popup.getX() + 20, popup.getY() + 10 + (20 * 1));
            g.drawString("Sound: [" + ht.sound_toggle + "]", popup.getX() + 20, popup.getY() + 10 + (20 * 2));
        }
    }

    public void renderLoadGame(HorrorTactics ht, Graphics g) {
        if (this.show_window.equalsIgnoreCase("load") || this.show_window.equalsIgnoreCase("save")) {
            //Rectangle slot1 = new Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100);
            //Rectangle slot2 = new Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100);
            //Rectangle slot3 = new Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100);
            g.setColor(this.wc);
            g.fillRect(popup.getX(), popup.getY(), popup.getWidth(), popup.getHeight());
            g.setColor(Color.black);
            g.fillRect(slot1.getX(), slot1.getY(), slot1.getWidth(), slot1.getHeight());
            g.fillRect(slot2.getX(), slot2.getY(), slot2.getWidth(), slot2.getHeight());
            g.fillRect(slot3.getX(), slot3.getY(), slot3.getWidth(), slot3.getHeight());
            g.setColor(Color.white);
            if (this.show_window.equalsIgnoreCase("load")) {
                g.drawString("Load Game", popup.getX() + 200, popup.getY() + 10);
            } else {
                g.drawString("Save Game", popup.getX() + 200, popup.getY() + 10);
            }
            g.setColor(Color.white);
            //see if the slots exist (filenames) then post a pic of they do.
            renderSaveSlot(ht, g, "Save1.txt");
            renderSaveSlot(ht, g, "Save2.txt");
            renderSaveSlot(ht, g, "Save3.txt");
        }
    }

    public Rectangle getSaveSlot(String s) {
        if (s.equalsIgnoreCase("Save1.txt")) {
            return slot1;
        } else if (s.equalsIgnoreCase("Save2.txt")) {
            return slot2;
        } else if (s.equalsIgnoreCase("Save3.txt")) {
            return slot3;
        }
        return slot1;
    }

    public void renderSaveSlot(HorrorTactics ht, Graphics g, String f) {
        if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("tutorial01.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_tutorial01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("First Class", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        } else if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("class_school01.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_streets01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("After class nightmare", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        } else if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("apartment1.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_streets01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("Invisible", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        } else if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("streets01.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_streets01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("Zombie apocalypse", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        } else if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("butcher_shop01.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_streets01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("Dead meat", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        } else if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("subway01.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_streets01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("Zombie line", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        } else if (ht.playerfile.checkForMapInSaveFile(f).equalsIgnoreCase("poolside01.tmx")) { //"Save1ps.txt"
            g.drawImage(ht.prev_streets01, getSaveSlot(f).getX(), getSaveSlot(f).getY());
            g.drawString("Drowned Girls", getSaveSlot(f).getX() + 10, getSaveSlot(f).getY() + 10);
        }

        //prev_tutorial01
    }
}
