##/*
# * To change self license header, choose License Headers in Project Properties.
# * To change self template file, choose Tools | Templates
# * and open the template in the editor.
# * Bug! array out of bounds when reaching the edge of the map
# */
#package horrortactics

#import pyglet
from files.techWrap import Color
#from files.techWrap import Rectangle
from files.techWrap import HtImage
from files.techWrap import HtTime
#from files.techWrap import HtWindow
from files.techWrap import HtSound
from files.techWrap import HtApp

from files.MyTiledMap import MyTiledMap
from files.MouseActions import MouseActions
from files.KeyActions import KeyActions
from files.Actor import Actor #        self.playersave = Actor()
from files.TitleMenu import TitleMenu #        self.titlemenu = TitleMenu()
from files.SaveMyFile import SaveMyFile #        self.playerfile = SaveMyFile()
from files.Settings import Settings

class HorrorTactics: # extends BasicGame: 
    def __init__(self):
        #self.input = Input() #input class        
        self.msa = MouseActions() # msa #key and mouse actions
        self.ksa = KeyActions() # ksa
        self.draw_x = 0
        self.draw_y = 0   #draw offset for screen?
        ##/* offset for camera scrolling*/
        self.screen_x = 0
        self.screen_y = 0
        ##/* where on the screen?*/
        self.mouse_x = 0
        self.mouse_y = 0 #where the mouse is?
        self.last_mouse_x = 0
        self.last_mouse_y = 0
        self.mouse_tile_x = 0
        self.mouse_tile_y = 0
        self.fps = 0
        self.delta =0
        self.actor_move_timer = 0
        self.currentTime = 0
        self.lastTime = 0
        self.scale_x = 1
        self.screen_width = 0
        self.screen_height = 0
        self.lastframe = 0
        self.turn_count = 0
        self.popup_window = "none"
        #Color myfilter, myfiltert, myfilterd Make a slickwraperobjbs
        self.playersave = Actor()
        self.titlemenu = TitleMenu()
        self.playerfile = SaveMyFile()
        #self.music = self.music()
        self.game_state = "title start" #title start, title ingame, tactical,conversation,cutscene
        self.fullscreen_toggle = "Yes"
        self.sound_toggle = "Yes"
        self.tiledmap = MyTiledMap("data/dojo01.tmx", 0, 0)
        #self.tiledmap. = MyTiledMap("data/tutorial01.tmx", 0, 0)
        #self.tiledmap. = MyTiledMap("data/butcher_shop01.tmx", 0, 0)
        #self.tiledmap. = MyTiledMap("data/streets01.tmx", 0, 0)
        self.settings = Settings() #how do we save them?
        self.msa = MouseActions()
        self.ksa = KeyActions()
        self.titlemenu = TitleMenu()
        #input = new Input(gc.getHeight())
        self.tiledmap.actorself.tiledmap.getActorLocationFromTMX(map)
        self.fps = HtTime.getFPS()
        self.actor_move_timer = 0
        self.lastTime = 0
        self.lastframe = 0
        self.turn_count = 0
        self.currentTime = HtTime.getTime()
        #self.screen = HtWindow()
        self.screen_height = self.screen.getScreenHeight()
        self.screen_width = self.screen.getScreenWidth()

        self.draw_x = self.tiledmap.getIsoXToScreen(self.tiledmap.player.tilex, self.tiledmap.player.tiley) * -1 + self.screen_width / 2
        self.draw_y = self.tiledmap.getIsoYToScreen(self.tiledmap.player.tilex, self.tiledmap.player.tiley) * -1 + self.screen_height / 2

        self.lastframe = HtTime.getTime()
        
        #move these buttons to UI?
        #Image button_endturn, button_menu, button_punch, button_items, button_profile, button_shadow
        #Image effect_biff, effect_wiff, effect_shrack
        #Image enemy_moving_message
        #Image level_up_icon #level_up_icon.png
        #Image prev_streets01,prev_apartment1, prev_tutorial01, prev_butcher_shop01
        #InputStream inputStream	= ResourceLoader.getResourceAsStream("myfont.ttf")
        self.button_items = HtImage("data/button_items.png")
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
        self.myfilter = Color(1, 1, 1, 1)
        self.myfiltert = Color(0.5, 0.5, 0.5, 0.5)
        self.myfilterd = Color(0.2, 0.2, 0.2, 0.8) #for darkness/fog
        self.last_mouse_x = 0#input.getMouseX()
        self.last_mouse_y = 0#input.getMouseY()
        self.playersave = Actor("data/player00", 218, 313) #to carry over the player.
        self.level_up_icon = HtImage("data/level_up_icon.png")
        self.music = HtSound("data/soundeffects/anxiety_backwards.ogg")
        self.app = HtApp()

        #try:
            #TODO: Fonts
            #InputStream inputStream = ResourceLoader.getResourceAsStream("data/School_Writing.ttf")
            #InputStream inputStream = ResourceLoader.getResourceAsStream("data/FantasqueSansMono-Bold.ttf")
            #java.awt.Font awtFont = new java.awt.Font(inputStream, java.awt.Font.BOLD, 24)
            #java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TrueTYPE_FONT, inputStream)
            #awtFont = awtFont.deriveFont(48f)
            #awtFont.deriveFont(bold)
            #ttf = new TrueTypeFont(awtFont, False)
        # catch (Exception e): 
        #    e.printStackTrace()
        #

    def update(self, gc, delta): # throws SlickException: 
        #self.input = gc.getInput()
        #game_window.push_handlers(player_ship.key_handler)
        #self.ksa.kactions.handler.
        self.mouse_x = input.getMouseX()
        self.mouse_y = input.getMouseY()
        self.msa.mouseWasClicked(self.input, self.tiledmap) #Do mouse actions
        
        self.ksa.on_key_press(self, None) #Do keyboard actions
        self.tiledmap.updateMapXY(self.draw_x, self.draw_y)
        self.actor_move_timer = self.actor_move_timer+1
        self.tiledmap.resetAttackAniFrame()
        if (self.game_state == "exit"): #: 
            gc.exit()
        elif (self.game_state == "title start" or self.game_state == "title ingame"):
            if (self.music.playing() == False): #: 
                self.music.play()
        else:
            if (self.music.playing()):
                self.music.stop()
        if (self.actor_move_timer >= self.fps):
            self.actor_move_timer = 0
        if (self.tiledmap.player.dead == True and self.game_state == "tactical"):
            self.tiledmap.turn_order = "game over"
            self.game_state = "game over"
        if (self.tiledmap.turn_order == "game over"): #update
            n = None
        elif (self.tiledmap.turn_order == "planning"):  #update
            n = None #why is self here?
            #planning phase.  Show a dialogue.
            #Accept clicks through the dialogue
            #after the last click, accept
        elif (self.tiledmap.turn_order == "exit reached"):
            #exit has been reached, transition self.tiledmap.  Do not set unless
            if (self.tiledmap.player.expForExitReached == False):
                self.tiledmap.player.exp_points += 3
                self.tiledmap.player.expForExitReached = True
                self.tiledmap.player.health_points = self.tiledmap.player.health_points_max
                self.tiledmap.player.fatigue_points = self.tiledmap.player.fatigue_points_max
                self.tiledmap.player.snd_footsteps.stop() #for x in range(0, 3):
            for i in range(0, self.tiledmap.follower_max):  #for x in range(0, 3):
                if (self.tiledmap.follower[i].dead == False):
                    if (self.tiledmap.follower[i].expForExitReached == False):
                        self.tiledmap.follower[i].exp_points += 3
                        self.tiledmap.follower[i].health_points = self.tiledmap.follower[i].health_points_max
                        self.tiledmap.follower[i].fatigue_points = self.tiledmap.follower[i].fatigue_points_max
                        self.tiledmap.follower[i].snd_footsteps.stop()
                        self.tiledmap.follower[i].expForExitReached = True
            #goal has been reached , or goal = none
        elif (self.tiledmap.turn_order == "change map"):
            n_mapname = self.tiledmap.next_map #local String n_mapname = 
            #change map here  map = new MyTiledMap("data/class_school01.tmx", 0, 0)
            #self.tiledmap.getActorLocationFromTMX()
            try:
                self.tiledmap.player.copyActorStats(self.playersave)
                self.tiledmap = MyTiledMap("data/" + n_mapname, 0, 0)
                #we lose player info Here.  
                self.tiledmap.actorself.tiledmap.getActorLocationFromTMX(map) #actor location?
                self.playersave.copyActorStats(self.tiledmap.player)
                self.tiledmap.turn_order = "planning"
                self.tiledmap.mouse_over_tile_x = 1
                self.tiledmap.mouse_over_tile_y = 1
                self.draw_x = self.tiledmap.getIsoXToScreen(self.tiledmap.player.tilex, self.tiledmap.player.tiley) * -1 + self.screen_width / 2
                self.draw_y = self.tiledmap.getIsoYToScreen(self.tiledmap.player.tilex, self.tiledmap.player.tiley) * -1 + self.screen_height / 2
            except:
                print("cant load new map " + n_mapname)
        elif (self.tiledmap.turn_order == "goal reached"):
            if (self.tiledmap.player.expForGoal == False):
                self.tiledmap.player.exp_points += 3
                self.tiledmap.player.expForGoal = True
            for i in range(0, self.tiledmap.follower_max): #for (int i = 0 i < self.tiledmap.follower_max i++): 
                if (self.tiledmap.follower[i].dead == False):
                    if (self.tiledmap.player.expForGoal == False):
                        self.tiledmap.follower[i].exp_points += 3
                        self.tiledmap.follower[i].expForGoal = True
        elif (self.tiledmap.turn_order == "player"):
            if (self.actor_move_timer == 0):
                self.tiledmap.player.onMoveActor(self.tiledmap, gc.getFPS())#self.getMyDelta(gc))
                self.tiledmap.onFollowerMoving(gc, self, delta)
                if(self.tiledmap.player.action_points <= 0 and self.tiledmap.getFollowersCanMove() == False):
                    #make it the monster turn automatically
                    self.tiledmap.turn_order = "start monster"
            if (self.tiledmap.active_trigger.name == "none"): #not already stepped in it
                self.tiledmap.active_trigger.onSteppedOnTrigger(self.tiledmap, self.tiledmap.player.tilex, self.tiledmap.player.tiley)
                # how do we make it null again?
        elif (self.tiledmap.turn_order == "start follower"): #
            self.tiledmap.setFollowerDirectives()
            self.tiledmap.turn_order = "follower"
        #elif (self.tiledmap.turn_order == ("follower")): 

        elif (self.tiledmap.turn_order == "start player"):
            self.tiledmap.player.action_points = 6 + self.tiledmap.player.stat_speed - 1 + self.tiledmap.player.getMovePenalty()
            #check for level up
            self.tiledmap.player.onLevelUp()
            for i in range(0, self.tiledmap.follower_max): #for (int i = 0 i < self.tiledmap.follower_max i++): 
                self.tiledmap.follower[i].onLevelUp()
            #give followers action points.
            self.tiledmap.setFollowerDirectives()
            self.tiledmap.turn_order = "player"

        elif (self.tiledmap.turn_order == "start monster"):
            self.tiledmap.setMonsterDirectives()
            self.tiledmap.turn_order = "monster"
        elif (self.tiledmap.turn_order == ("monster")): 
            if (self.actor_move_timer == 0): 
                self.tiledmap.resetAttackAniFrame()
                self.tiledmap.onMonsterMoving(gc, self, delta) #wrapper for onMoveActor
        self.tiledmap.onUpdateActorActionText()

    #def render(gc, g): # throws SlickException:
    def render(self, gc, g): #what args do we need?
        if (self.game_state == ("tactical")): 
            self.render_tactical_base(gc, g)
        elif (self.game_state == ("conversation")): 
            g.scale(self.scale_x, self.scale_x) #scale the same
            self.render_tactical_base(gc, g)
        elif (self.game_state == ("cutscene")): 
            self.render_cutscene(gc, g) #animations?
        elif (self.game_state == ("title start") or self.game_state == ("title ingame")): 
            #self.render_title(gc, g)
            self.titlemenu.render(self, g)
        elif (self.game_state == ("game over")): 
            self.render_game_over(gc, g)  #its game over for your kind
    def render_tactical_base(self, gc, g):
        g.scale(self.scale_x, self.scale_x) #scale the same
        self.render_background_layer(gc, g) #render floor
        self.render_walls_layer(gc, g)      #render walls (and actors!)
        self.render_game_ui(gc, g)
        self.render_character_busts(gc, g)

    def render_background_layer(self, gc, g): 
        background_layer = self.tiledmap.getLayerIndex("background_layer")
        self.tiledmap.set_party_min_renderables()
        for y in range(self.tiledmap.render_min_y - 4, self.tiledmap.render_max_y):#(int y = self.tiledmap.render_min_y - 4 y < self.tiledmap.render_max_y + 4 y++): 
            for x in range(self.tiledmap._render_min_x -4, self.tiledmap.render_max_x + 4): #for (int x = self.tiledmap.render_min_x - 4 x < self.tiledmap.render_max_x + 4 x++): 
                self.screen_x = (x - y) * self.tiledmap.TILE_WIDTH_HALF
                self.screen_y = (x + y) * self.tiledmap.TILE_HEIGHT_HALF
                try: 
                    if (self.getTileToBeRendered(x, y)): 
                        if (self.getTileToBeFiltered(x, y)): #if its outside 2 steps
                            #java.lang.ArrayIndexOutOfBoundsException: 20 
                            try: 
                                xi = self.tiledmap.getTileImage(x, y, background_layer) #Image
                                #self.tiledmap.getTileImage(x, y, background_layer).draw(
                                xi.draw(self.screen_x + self.draw_x, self.screen_y + self.draw_y, self.scale_x, self.myfilterd)
                            except: # catch (ArrayIndexOutOfBoundsException ae): 
                                print("Block the bug: xi.draw())") # # block the bug.
                        else:  #draw normal
                            self.tiledmap.getTileImage(x, y, background_layer).draw(
                                    self.screen_x + self.draw_x, self.screen_y + self.draw_y, self.scale_x)
                except: # catch (ArrayIndexOutOfBoundsException e): 
                    print("Block the bug: try if(xi.draw)") # # block the bug.

    def render_wall_by_wall(self, gc, g, x, y): 
        walls_layer = self.tiledmap.getLayerIndex("walls_layer")
        try: 
            if (self.tiledmap.getTileImage(x, y, walls_layer) == False):
                print("self.tiledmap.getTileImage(x, y, walls_layer) == False)")
            elif (self.wall_intersect_player(x, y, self.screen_x, self.screen_y) == True): 
                self.tiledmap.getTileImage(x, y, walls_layer).draw(
                        self.screen_x + self.draw_x, self.screen_y + self.draw_y - 382, self.scale_x, self.myfiltert)
            else: #inside cannot be dark
                if (x < self.tiledmap.player.tilex - self.tiledmap.light_level 
                        or x > self.tiledmap.player.tilex + self.tiledmap.light_level  
                        or y < self.tiledmap.player.tiley - self.tiledmap.light_level  
                        or y > self.tiledmap.player.tiley + self.tiledmap.light_level): 
                    if (self.getTileToBeRendered(x, y)): 
                        self.tiledmap.getTileImage(x, y, walls_layer).draw(
                                self.screen_x + self.draw_x, self.screen_y + self.draw_y - 382, self.scale_x, self.myfilterd)
                else: 
                    self.tiledmap.getTileImage(x, y, walls_layer).draw(
                            self.screen_x + self.draw_x, self.screen_y + self.draw_y - 382, self.scale_x, self.myfilter)
            if (y == self.tiledmap.selected_tile_y and x == self.tiledmap.selected_tile_x and self.tiledmap.turn_order == "player"): 
                #self.tiledmap.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y)
                self.tiledmap.selected_green.draw(self.screen_x + self.draw_x, self.screen_y + self.draw_y)
            if (y == self.tiledmap.player.tiledesty and x == self.tiledmap.player.tiledestx
                    and self.tiledmap.player.getActorMoving()): 
                self.tiledmap.selected_yellow.draw(self.screen_x + self.draw_x, self.screen_y + self.draw_y)
            for i in range(0, self.tiledmap.follower_max):#for (int i = 0 i < self.tiledmap.follower_max i++): 
                if (y == self.tiledmap.follower[i].tiledesty
                        and x == self.tiledmap.follower[i].tiledestx #){
                        and self.tiledmap.follower[i].getActorMoving()): 
                    self.tiledmap.selected_yellow.draw(self.screen_x + self.draw_x, self.screen_y + self.draw_y) 
        except: #
             print("ArrayIndexOutOfBoundsException e")
    def render_walls_layer(self, gc, g): 
        mw = self.tiledmap.getTileWidth()
        mh = self.tiledmap.getTileHeight()
        for y in range(self.tiledmap.render_min_y - 4, self.tiledmap.render_max_y + 4):#for (int y = self.tiledmap.render_min_y - 4 y < self.tiledmap.render_max_y + 4 y++): 
            ##/*Y Loop*/
            for x in range(self.tiledmap.render_min_x -4, self.tiledmap.render_max +4):#for (int x = self.tiledmap.render_min_x - 4 x < self.tiledmap.render_max_x + 4 x++): 
                #/* X Loop*/
                self.screen_x = (x - y) * self.tiledmap.TILE_WIDTH_HALF
                #/*Calculate screen/x/y*/
                self.screen_y = (x + y) * self.tiledmap.TILE_HEIGHT_HALF
                if (x >= 0 and y >= 0 and x <= mw and y <= mh): 
                    #/*loop through tiles */
                    self.mouse_x = gc.getInput().getMouseX()
                    #/*get mouse coords*/
                    self.mouse_y = gc.getInput().getMouseY()
                    sx = self.screen_x + self.draw_x + 30
                    #/*screen x/y+drawing offset*/
                    sy = self.screen_y + self.draw_y + 30
                    #/*compare mouse to sx*/
                    if (self.mouse_x >= sx and self.mouse_x <= sx + 250 - 30
                            and self.mouse_y >= sy and self.mouse_y <= sy + 130 - 30): 
                        self.tiledmap.mouse_over_tile_x = x
                        self.tiledmap.mouse_over_tile_y = y
                        #is there someone in mouse over tile?
                        self.tiledmap.mouse_over_actor(x, y) #render the selector.
                        #self.tiledmap.tiles250x129.getSubImage(0, 0, 250, 130).draw(
                        #        screen_x + draw_x, screen_y + draw_y, scale_x)
                    
                    self.tiledmap.player.drawPlayer(self, self.tiledmap, x, y, g)
                    #/*Draw your player*/
                    self.tiledmap.drawMonsters(self, x, y, g)
                    #/*self.tiledmap.monster[0].drawActor(self, map, x, y)*/
                    self.tiledmap.drawFollowers(self, x, y, g)
                    #/*draw your followers*/
                    if (self.tiledmap.RequiresGoal == ("yes")
                            and self.tiledmap.EventGoal_ran == False
                            and x == self.tiledmap.draw_goal_x
                            and y == self.tiledmap.draw_goal_y): 
                        #/*bug? better have an image*/
                        pdx = self.screen_x + self.draw_x
                        #/* + self.draw_x*/
                        pdy = self.screen_y + self.draw_y
                        #/* + self.draw_y - 230*/
                        self.tiledmap.mission_goal.draw(pdx, pdy)
                    
                    if (self.getTileToBeRendered(x, y)): 
                        self.render_wall_by_wall(gc, g, x, y) #ArrayIndexOutOfBoundsException
    #def render_game_ui(gc, g, )
    def getMouseOverBottomButtons(self, ht): 
        #if(ht.self.mapm)
        if( self.msa.menuButtonWasOver(ht) ): 
            return 100
        elif ( self.msa.endTurnButtonWasOver(ht) ): 
            return 200
        elif ( self.msa.profileButtonWasOver(ht)):
            return 300 
        elif ( self.msa.itemsButtonWasOver(ht) ): 
            return 400
        else: 
            return -100
    def render_game_ui(self,gc, g): 
        self.mouseovervar = self.getMouseOverBottomButtons(self)
        self.map.player.iconImage.draw(5, 50)
        if (self.map.player.newLevelUp == True):  #level_up_icon
            self.level_up_icon.draw(5, 50)
        
        g.drawString(("Action: " , self.map.player.action_points), 5, 50 + 75)
        for i in range(0, ):#for (int i = 0 i < self.tiledmap.follower_max i++): 
            if (self.tiledmap.follower[i].visible == True): 
                self.tiledmap.follower[i].iconImage.draw(5, 50 + (100 * (i + 1)))
                if (self.tiledmap.follower[i].newLevelUp == True): 
                    self.level_up_icon.draw(5, 50 + (100 * (i + 1)))
                
                g.drawString(("Action: " , self.tiledmap.follower[i].action_points), 5, 50 + (100 * (i + 1)) + 75)
        g.setColor(self.myfilterd)
        g.fillOval(5, 50 + 75, 12, 14)
        g.setColor(self.myfilter)
        self.button_items.draw(gc.getScreenWidth() - 400, gc.getScreenHeight() - 64 - 10)
        self.button_profile.draw(gc.getScreenWidth() - 300, gc.getScreenHeight() - 64 - 10)
        self.button_endturn.draw(gc.getScreenWidth() - 200, gc.getScreenHeight() - 64 - 10)
        self.button_menu.draw(gc.getScreenWidth() - 100, gc.getScreenHeight() - 64 - 10)
        self.button_shadow.draw(gc.getScreenWidth() -self.mouseovervar, gc.getScreenHeight() - 64 - 10) #button_shadow
        g.drawString("Player At:" + self.tiledmap.player.tilex + "X" + self.tiledmap.player.tiley + "mouse at:"
                + self.tiledmap.mouse_over_tile_x + "x" + self.tiledmap.mouse_over_tile_y + " Turn: "
                + self.tiledmap.turn_order + " mm: " + self.tiledmap.current_monster_moving + "/"
                + self.tiledmap.monster[self.tiledmap.current_monster_moving].action_points + " dst:"
                + self.tiledmap.monster[self.tiledmap.current_monster_moving].tiledestx + ","
                + self.tiledmap.monster[self.tiledmap.current_monster_moving].tiledesty,
                200, 10)#might crash?
        g.drawString(self.tiledmap.log_msg, 200, 10 + 14)
        #g.drawString("Trigger Check: " + self.tiledmap.trigger_check, 500, 100)
        if (self.tiledmap.turn_order == ("monster")): 
            self.enemy_moving_message.draw(gc.getWidth() / 2 - 200, gc.getHeight() / 2)
            #The enemy is moving, show which enemy.(so people dont get bored)
            for mi in range(0, self.tiledmap.current_monster_moving):#for (int mi = 0 mi < self.tiledmap.current_monster_moving mi++): 
                self.tiledmap.monster[mi].iconImage.draw(gc.getWidth() / 2 - 200 + (92 * mi + 2), gc.getHeight() / 2 + 92)
        #check for a follower selected
        foundselected = False
        for i in range(0, self.tiledmap.follower_max): #for (int i = 0 i < self.tiledmap.follower_max i++): 
            if (self.tiledmap.follower[i].selected == True): 
                foundselected = True
                self.tiledmap.follower[i].drawPopupWindow(self, g)
                break
        if (foundselected == False): 
            self.tiledmap.player.drawPopupWindow(self, g) #who is currently selected?  
    def render_character_busts(self, gc, g): 
        #render character busts while conversation is going on.
        black = Color(0, 0, 0, 180)
        white = Color(255, 255, 255, 255)
        if (self.tiledmap.turn_order == "planning"): 
            self.tiledmap.charbusts[self.tiledmap.planevent].draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #self.uniFont.drawString(400, gc.getScreenHeight() - 100, self.tiledmap.planning[self.tiledmap.planevent], Color.white )
            g.drawString(self.tiledmap.planning[self.tiledmap.planevent], 400, gc.getScreenHeight() - 100)
        elif (self.tiledmap.turn_order == ("event spotted")): 
            #null pointer if there is no event spotted
            self.tiledmap.EventSpotted_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.tiledmap.EventSpotted_m, Color.white)
            #set all monsters to spotted True (if False)
            g.drawString(self.tiledmap.EventSpotted_m, 400, gc.getScreenHeight() - 100)
            for i in range(0, self.tiledmap.monster_max): #for (int i = 0 i < self.tiledmap.monster_max i++): 
                self.tiledmap.monster[i].spotted_enemy = True            
        elif (self.tiledmap.turn_order == ("goal reached")): 
            self.tiledmap.EventGoal_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.tiledmap.EventGoal_m, Color.white)
            g.drawString(self.tiledmap.EventGoal_m, 400, gc.getScreenHeight() - 100)
        elif (self.tiledmap.turn_order == ("exit reached")): 
            self.tiledmap.EventExit_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.tiledmap.EventExit_m, Color.white)
            g.drawString(self.tiledmap.EventExit_m, 400, gc.getScreenHeight() - 100)
        
    #def render_event_bubbles(self):#conversation bubbles, triggered by events
    #    zz=None
    #def render_cutscene(self): #gc, g): 
    #render cutscenes
    #    zz=None

    def render_game_over(self, gc, g): #yoo dyied!
        #g.clear() #fade to black
        g.scale(self.scale_x, self.scale_x) #scale the same
        self.render_background_layer(gc, g) #render floor
        self.render_walls_layer(gc, g)      #render walls (and actors!)
        self.render_game_ui(gc, g)
        self.render_character_busts(gc, g) #bring up the busts, and talk
        g.drawString("Game Over", gc.getScreenWidth()/2 - 100, gc.getScreenHeight()/2 - 12)
    

    def wall_intersect_player(self, x, y, screen_x, screen_y): 
        #int x = self.tiledmap.player.tilex - 4 x < self.tiledmap.player.tilex + 4 x++
        for ly in range(0, 4): #for (int ly = 0 ly < 4 ly++): 
            for lx in range(0, 4): #for (int lx = 0 lx < 4 lx++): 
                if (x == self.tiledmap.player.tilex + lx and y == self.tiledmap.player.tiley + ly): 
                    return True
        return False
    
    def getMyDelta(self): 
        time = HtTime.getTime()
        tdelta = (time - self.lastframe)
        self.lastframe = time
        if (tdelta == 0): 
            return 1        
        return tdelta
    def setGameState(self, state): 
        self.game_state = state    
    def getCurrentMap(self):  #public MyTiledMap
        return self.tiledmap
    def getComicActionStrImage(self, a): #public Image HtImage
        if (a == ("Dodge")):
            return self.effect_wiff
        elif (a == ("Dead")):
            return self.effect_shrack  #return self.effect_biff
        else:
            return self.effect_wiff
    #/*public int getActiveMonsters(): 
    # int am = 0
    # for (int i = 0 i < self.tiledmap.monster_max i++): 
    # if (self.tiledmap.monster[i].visible == True): 
    # am++
    # return am 
    def getTileToBeRendered(self, x, y):
        if (x < 0):
            return False        
        if (y < 0): 
            return False        
        if (x > self.tiledmap.getWidth()): 
            return False        
        if (y > self.tiledmap.getHeight()): 
            return False
        return True
    
    def getTileToBeFiltered(self, x,  y):  #if its outside 2 steps
        if (self.getTileToBeRendered(x, y)): 
            #/*for (int i = 0 i < self.tiledmap.follower_max i++): 
            #    if (x < self.tiledmap.follower[i].tilex - 2
            #            or x > self.tiledmap.follower[i].tilex + 2
            #            or y < self.tiledmap.follower[i].tiley - 2
            #            or y > self.tiledmap.follower[i].tiley + 2): 
            #        return False
            #     
            #*/
            if (x < self.tiledmap.player.tilex - 3 \
                    or x > self.tiledmap.player.tilex + 3 \
                    or y < self.tiledmap.player.tiley - 3 \
                    or y > self.tiledmap.player.tiley + 3): 
                return True
        return False

    def loadNewMap(self, newmap): #throws SlickException: 
        self.tiledmap = MyTiledMap(newmap, 0, 0) #setup a new map
        self.tiledmap.actorself.tiledmap.getActorLocationFromTMX(self.tildmap) #get the actor info
        self.popup_window = "none" #bug with kaleb where popup window still up
    
    
    def translateToTile(self, tx, ty): #center tile x/y on the screen?
        self.draw_x -= self.tiledmap.getIsoXToScreen(tx, ty)-(self.screen_width/3)
        self.draw_y -= self.tiledmap.getIsoYToScreen(tx, ty)-(self.screen_height-200)
    def main(self,args):
        try:
            HtApp.StartApp()
            self.app.run_app()
        except:
            print("cant start app: some reason?")
        
#pyglet.app.run()
h = HorrorTactics()

