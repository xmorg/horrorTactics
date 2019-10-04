#/usr/bin/python3

#import pyglet
from files.techWrap import HtImage
from files.MyTiledMap import MyTiledMap
from files.SaveMyFile import SaveMyFile
from files.Settings import Settings
from files.MouseActions import MouseActions
from files.KeyActions import KeyActions
from files.TitleMenu import TitleMenu



class HorrorTactics:
    #input = Input() #input class
    #map = MyTiledMap()
    #settings = Settings()
    def __init__(self):
        self.draw_x = 0
        self.draw_y = 0
        self.screen_x = 0
        self.screen_y = 0
        self.mouse_x = 0
        self.mouse_y = 0
        self.last_mouse_x =0
        self.last_mouse_y = 0
        self.mouse_tile_x = 0
        self.mouse_tile_y = 0
        self.fps = 0
        #self.delta = 0
        self.actor_move_timer = 0
        self.currentTime = 0
        self.lastTime = 0
        self.scale_x = 1
        self.screen_width = 0
        self.screen_height = 0
        self.lastframe = 0
        self.turn_count = 0
        self.popup_window = "none"
        #Color myfilter, myfiltert, myfilterd;
        #Color myfilter, myfiltert, myfilterd;

        #Actor playersave;
        #TitleMenu titlemenu;
        self.playerfile = SaveMyFile()
        #Music music;
        self.game_state = "title start" # //title start, title ingame, tactical,conversation,cutscene
        self.fullscreen_toggle = "Yes"
        self.sound_toggle = "Yes"
        #TrueTypeFont ttf;
        
        # ##################def init(self): #gamecontainer
        self.tiledmap = MyTiledMap("data/dojo01.tmx", 0, 0)
        self.settings = Settings() #//how do we save them?
        self.msa = MouseActions()
        self.ksa = KeyActions()
        self.titlemenu = TitleMenu()
        self.tiledmap.actormap.getActorLocationFromTMX(map)
        #self.fps = gc.getFPS(); #gyglet get fps
        self.actor_move_timer = 0
        self.lastTime = 0
        self.lastframe = 0
        self.turn_count = 0
        self.currentTime = gc.getTime(); #HorrorTactics.java Line 127
        self.button_items = HtImage('data/button_items.png')#HorrorTActics.java Line 137
        self.button_profile = HtImage("data/button_profile.png")
        self.button_endturn = HtImage("data/button_endturn2.png")
        self.button_menu = HtImage("data/button_menu.png")
        self.button_shadow = HtImage("data/button_shadow.png")
        self.button_punch = HtImage("data/button_punch.png")
        self.effect_biff = HtImage("data/soundeffects/biff.png")
        self.effect_wiff = HtImage("data/soundeffects/wiff.png")
        self.effect_shrack = HtImage("data/soundeffects/shrack.png")        
        self.prev_streets01 = HtImage("data/prev_streets01.jpg")
        self.prev_apartment1 = HtImage("data/prev_apartment1.jpg")
        self.prev_tutorial01 = HtImage("data/prev_tutorial01.jpg")
        self.prev_butcher_shop01 = HtImage("data/prev_butcher_shop01.jpg")        
        self.enemy_moving_message = HtImage("data/enemy_moving.png")
        
def main():
    print("the main")
main()
