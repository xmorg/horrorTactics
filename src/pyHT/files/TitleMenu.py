#/*
# * To change self license header, choose License Headers in Project Properties.
# * To change self template file, choose Tools | Templates
# * and open the template in the editor.
# * self CLASS IS NOT USED!!!! HorrorTactics does EVERYTHING!
# * Do NOT BE FOOLED by OOP LIES!
# */
#package horrortactics

#import org.newdawn.slick.Color
from files.techWrap import Color
from files.SaveMyFile import SaveMyFile

#import org.newdawn.slick.Image
from files.techWrap import HtImage as Image
from files.techWrap import Rectangle
#import org.newdawn.slick.geom.Rectangle
#import org.newdawn.slick.Graphics
#import org.newdawn.slick.Music
#import org.newdawn.slick.MusicListener
#import org.newdawn.slick.state.BasicGameState
#import org.newdawn.slick.Sound

#/**
# *
# * @author tcooper
# */

class TitleMenu:
    #def __init__(self):
    #public String menu_state #newgame/ingame
    #public String show_window
    #Image title_image
    #Image title_text, title_text_save, title_text_load, title_text_resume
    #Sound title_music
    #MusicListener ml
    #int menux
    #int menuy
    #int menuw
    #int menuh
    #Rectangle m_rect
    #Rectangle popup
    #Color wc
    #Rectangle slot1 # = Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100)
    #Rectangle slot2 # = Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100)
    #Rectangle slot3 # = Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100)
    #SaveMyFile savefile # savefile = SaveMyFile() #to get the functions.

    def __init__(self, ht): # throws SlickException {
        self.menu_state = "newgame"
        self.show_window = "none"
        self.title_image = Image("data/title1_01.jpg") #placeholder for title
        self.title_text = Image("data/title_text.jpg") 
        self.title_text_save = Image("data/title_text_save.jpg")  #depriciated? resume, new, save, options, credits, exit
        self.title_text_load = Image("data/title_text_load.jpg")  #new, load, optsion, credits, exit
        self.title_text_resume = Image("data/title_text_resume2.jpg") #resume, load, save, options, exit
        self.menux = 10
        self.menuy = ht.screen_height / 2
        self.menuw = self.title_text_load.getWidth()
        self.menuh = self.title_text_load.getHeight()
        self.m_rect = Rectangle(self.menux, self.menuy, self.menuw, self.menuh)
        self.popup = Rectangle(self.menux + self.menuw + 1, 100, 600, 600) #rectangle for inner window
        #title_music = Music("data/soundeffects/anxiety_backwards.ogg")
        #title_music = Sound("data/soundeffects/anxiety_backwards.ogg")
        #title_music.addListener(ml)    
        #ml = MusicListener()
        self.wc = Color(20, 20, 20, 200)
        self.slot1 = Rectangle(self.popup.getX(), self.popup.getY() + 35, self.popup.getWidth(), 100)
        self.slot2 = Rectangle(self.popup.getX(), self.popup.getY() + 35 + 103, self.popup.getWidth(), 100)
        self.slot3 = Rectangle(self.popup.getX(), self.popup.getY() + 35 + 206, self.popup.getWidth(), 100)
        self.savefile = SaveMyFile() #to get the functions.

    def render(self, ht, g):
        tx = ht.mouse_x
        ty = ht.mouse_y
        if (ht.game_state == "title start"):
            self.title_image.draw(0, 0, ht.screen_width, ht.screen_height) #draw the menu
            #self.title_text_load.draw(ht.screen_width/2,ht.screen_height/2, self.title_text_load.getWidth(),self.title_text_load.getHeight())
            self.title_text_load.draw(self.menux, self.menuy, self.menuw, self.menuh)
        elif (ht.game_state == "title ingame"):
            self.title_image.draw(0, 0, ht.screen_width, ht.screen_height) #draw the menu
            self.title_text_resume.draw(self.menux, self.menuy, self.menuw, self.menuh)
        self.onMouseOver(ht, g, tx, ty)
        self.renderCredits(ht, g)
        self.renderLoadGame(ht, g)
        self.renderOptions(ht, g)
        self.flip()
    def onMouseClick(self, ht, x, y):
        #running withing MouseActions.mouseWasClicked, so assuming mouse was 
        #Game,  28,42, 334x66
        #Load Game, 28, 146, 334x66
        #Options, 28, 238, 334x66
        #Credits, 28, 324
        #Exit, 28, 414
        newgame_rect = Rectangle(self.menux + 28, self.menuy + 24, 334, 66) #new, resume
        loadgame_rect = Rectangle(self.menux + 28, self.menuy + 146, 334, 66) #load or save
        options_rect = Rectangle(self.menux + 28, self.menuy + 238, 334, 66)
        savegame_rect = Rectangle(self.menux + 28, self.menuy + 238, 334, 66)
        credits_rect = Rectangle(self.menux + 28, self.menuy + 324, 334, 66)
        exit_rect = Rectangle(self.menux + 28, self.menuy + 414, 334, 66)
        #Rectangle slot1 = Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100)
        #Rectangle slot2 = Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100)
        #Rectangle slot3 = Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100)
        if (ht.game_state == "title start") or (ht.game_state == "title ingame"):
            if (newgame_rect.contains(x, y) and (self.show_window == "none")):
                ht.game_state = "tactical" #go through the list of               
            elif (loadgame_rect.contains(x, y) and self.show_window == "none") and (ht.game_state == "title start"):
                #load game
                self.show_window = "load"
            elif (loadgame_rect.contains(x, y) and self.show_window == "none") and (ht.game_state == "title ingame"):
                #save game
                self.show_window = "load"
            elif (savegame_rect.contains(x, y) and self.show_window == "none") and (ht.game_state == "title ingame"):
                #save game
                self.show_window = "save"
            elif (options_rect.contains(x, y) and self.show_window == "none"):
                #display options
                self.show_window = "options"
            elif (credits_rect.contains(x, y) and self.show_window == "none"):
                #display credits
                self.show_window = "credits"
            elif (exit_rect.contains(x, y) and self.show_window == "none") and (ht.game_state == "title ingame"):
                #exit the game
                ht.setGameState("title start")
            elif (exit_rect.contains(x, y) and self.show_window == "none") and (ht.game_state == "title start"):
                #exit the game
                ht.setGameState("exit")
            elif (self.show_window == "load"): #define a button here
                self.show_window = "none"
                if (self.slot1.contains(x, y)): #you clicked to load save 1
                    if (self.savefile.readFile("Save1.txt")):
                        try:
                            ht.loadNewMap("data/" + self.savefile.checkForMapInSaveFile("Save1.txt"))
                            self.savefile.checkMapSettingsInSaveFile(ht, "Save1.txt")
                            ht.game_state = "tactical"
                            ht.map.turn_order = "player"
                            ht.translateToTile(ht.map.player.tilex, ht.map.player.tiley)
                            #After loading you need to scroll to the player
                        except:
                            print("cannot load save slot 1")
                elif (self.slot2.contains(x, y)): #you clicked to load save 2
                    if (self.savefile.readFile("Save2.txt")):
                        try:
                            ht.loadNewMap("data/" + self.savefile.checkForMapInSaveFile("Save2.txt"))
                            self.savefile.checkMapSettingsInSaveFile(ht, "Save2.txt")
                            ht.game_state = "tactical"
                            ht.map.turn_order = "player"
                            ht.translateToTile(ht.map.player.tilex, ht.map.player.tiley)
                            #After loading you need to scroll to the player
                        except:
                            print("cannot load save slot 2")
                    
                elif (self.slot3.contains(x, y)): #you click to load save 3
                    if (self.savefile.readFile("Save3.txt")):
                        try:
                            ht.loadNewMap("data/" + self.savefile.checkForMapInSaveFile("Save3.txt"))
                            self.savefile.checkMapSettingsInSaveFile(ht, "Save2.txt")
                            ht.game_state = "tactical"
                            ht.map.turn_order = "player"
                            ht.translateToTile(ht.map.player.tilex, ht.map.player.tiley)
                            #After loading you need to scroll to the player
                        except:
                            print("cannot load save slot 3")
            elif (self.show_window == "save"): #define a button here
                #You wanted to save the game at slot ?
                if (self.slot1.contains(x, y)):
                    #save to self file
                    ht.playerfile.savePlayerMapData(ht, "1")
                elif (self.slot2.contains(x, y)):
                    ht.playerfile.savePlayerMapData(ht, "2")
                elif (self.slot3.contains(x, y)):
                    ht.playerfile.savePlayerMapData(ht, "3")
                self.show_window = "none"
            elif (self.show_window == "credits"):
                self.show_window = "none"
            elif (self.show_window == "options"):
                self.show_window = "none"
    def onMouseOver(self, ht, g, x, y): #put a line under what is selected.
        newgame_rect = Rectangle(self.menux + 28, self.menuy + 24, 334, 66)
        loadgame_rect = Rectangle(self.menux + 28, self.menuy + 146, 334, 66)
        options_rect = Rectangle(self.menux + 28, self.menuy + 238, 334, 66)
        credits_rect = Rectangle(self.menux + 28, self.menuy + 324, 334, 66)
        exit_rect = Rectangle(self.menux + 28, self.menuy + 414, 334, 66)
        if (ht.game_state == "title start") or (ht.game_state == "title ingame"):
            g.setLineWidth(4)
            g.setColor(Color.red)
            if (newgame_rect.contains(x, y)):
                #g.setColor(Color.red)
                g.drawLine(
                        newgame_rect.getX(),
                        newgame_rect.getY() + newgame_rect.getHeight() + 10,
                        newgame_rect.getX() + newgame_rect.getWidth(),
                        newgame_rect.getY() + newgame_rect.getHeight() + 10
                )
            elif (loadgame_rect.contains(x, y)):
                #load game #g.setColor(Color.red)
                g.drawLine(
                        loadgame_rect.getX(),
                        loadgame_rect.getY() + loadgame_rect.getHeight(),
                        loadgame_rect.getX() + loadgame_rect.getWidth(),
                        loadgame_rect.getY() + loadgame_rect.getHeight()
                )
            elif (options_rect.contains(x, y)):
                #display options #g.setColor(Color.red)
                g.drawLine(
                        options_rect.getX(),
                        options_rect.getY() + options_rect.getHeight(),
                        options_rect.getX() + options_rect.getWidth(),
                        options_rect.getY() + options_rect.getHeight()
                )
            elif (credits_rect.contains(x, y)):
                #display credits #g.setColor(Color.red)
                g.drawLine(
                        credits_rect.getX(),
                        credits_rect.getY() + credits_rect.getHeight(),
                        credits_rect.getX() + credits_rect.getWidth(),
                        credits_rect.getY() + credits_rect.getHeight()
                )
            elif (exit_rect.contains(x, y)):
                #g.setColor(Color.red)
                g.drawLine(
                        exit_rect.getX(),
                        exit_rect.getY() + exit_rect.getHeight(),
                        exit_rect.getX() + exit_rect.getWidth(),
                        exit_rect.getY() + exit_rect.getHeight()
                )
    def renderCredits(self, ht, g):
        if (self.show_window == "credits"):
            #	drawRect(float x1, float y1, float width, float height)             
            g.setColor(self.wc)
            g.fillRect(self.popup.getX(), self.popup.getY(), self.popup.getWidth(), self.popup.getHeight())
            g.setColor(Color.white)
            g.drawString("Everything Done by Tim Cooper", self.popup.getX() + 10, self.popup.getY() + 10)
    def renderOptions(self, ht, g):
        if (self.show_window == "options"):
            g.setColor(self.wc)
            g.fillRect(self.popup.getX(), self.popup.getY(), self.popup.getWidth(), self.popup.getHeight())
            g.setColor(Color.white)
            g.drawString("Game Options", self.popup.getX() + 200, self.popup.getY() + 10)
            g.drawString("Fullscreen: [" + ht.fullscreen_toggle + "]", self.popup.getX() + 20, self.popup.getY() + 10 + (20 * 1))
            g.drawString("Sound: [" + ht.sound_toggle + "]", self.popup.getX() + 20, self.popup.getY() + 10 + (20 * 2))
    def getSaveSlot(self, s):
        if (s == "Save1.txt"):
            return self.slot1
        elif (s == "Save2.txt"):
            return self.slot2
        elif (s == "Save3.txt"):
            return self.slot3
        return self.slot1
    def renderSaveSlot(self, ht, g, f):
        if (ht.playerfile.checkForMapInSaveFile(f) == "tutorial01.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_tutorial01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("First Class", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        elif (ht.playerfile.checkForMapInSaveFile(f) == "class_school01.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_streets01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("After class nightmare", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        elif (ht.playerfile.checkForMapInSaveFile(f) == "apartment1.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_streets01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("Invisible", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        elif (ht.playerfile.checkForMapInSaveFile(f) == "streets01.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_streets01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("Zombie apocalypse", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        elif (ht.playerfile.checkForMapInSaveFile(f) == "butcher_shop01.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_streets01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("Dead meat", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        elif (ht.playerfile.checkForMapInSaveFile(f) == "subway01.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_streets01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("Zombie line", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        elif (ht.playerfile.checkForMapInSaveFile(f) == "poolside01.tmx"): #"Save1ps.txt"
            g.drawImage(ht.prev_streets01, self.getSaveSlot(f).getX(), self.getSaveSlot(f).getY())
            g.drawString("Drowned Girls", self.getSaveSlot(f).getX() + 10, self.getSaveSlot(f).getY() + 10)
        #prev_tutorial01
    def renderLoadGame(self, ht, g):
        if (self.show_window == "load") or (self.show_window == "save"):
            #Rectangle slot1 = Rectangle(popup.getX(), popup.getY()+35, popup.getWidth(), 100)
            #Rectangle slot2 = Rectangle(popup.getX(), popup.getY()+35+103, popup.getWidth(), 100)
            #Rectangle slot3 = Rectangle(popup.getX(), popup.getY()+35+206, popup.getWidth(), 100)
            g.setColor(self.wc)
            g.fillRect(self.popup.getX(), self.popup.getY(), self.popup.getWidth(), self.popup.getHeight())
            g.setColor(Color.black)
            g.fillRect(self.slot1.getX(), self.slot1.getY(), self.slot1.getWidth(), self.slot1.getHeight())
            g.fillRect(self.slot2.getX(), self.slot2.getY(), self.slot2.getWidth(), self.slot2.getHeight())
            g.fillRect(self.slot3.getX(), self.slot3.getY(), self.slot3.getWidth(), self.slot3.getHeight())
            g.setColor(Color.white)
            if (self.show_window == "load"):
                g.drawString("Load Game", self.popup.getX() + 200, self.popup.getY() + 10)
            else:
                g.drawString("Save Game", self.popup.getX() + 200, self.popup.getY() + 10)
            g.setColor(Color.white)
            #see if the slots exist (filenames) then post a pic of they do.
            self.renderSaveSlot(ht, g, "Save1.txt")
            self.renderSaveSlot(ht, g, "Save2.txt")
            self.renderSaveSlot(ht, g, "Save3.txt")
    
    

