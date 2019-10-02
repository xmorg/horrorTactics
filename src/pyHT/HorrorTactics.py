##/*
# * To change this license header, choose License Headers in Project Properties.
# * To change this template file, choose Tools | Templates
# * and open the template in the editor.
# * Bug! array out of bounds when reaching the edge of the map
# */
#package horrortactics

import pyglet
from files.slickWrap import Color
from files.slickWrap import Rectangle

##/**
# *
# * @author tcooper
# */

class HorrorTactics: # extends BasicGame: 
    def __init__(self):
        self.input = Input() #input class
        
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
        #TrueTypeFont ttf
        #move these buttons to UI?
        #Image button_endturn, button_menu, button_punch, button_items, button_profile, button_shadow
        #Image effect_biff, effect_wiff, effect_shrack
        #Image enemy_moving_message
        #Image level_up_icon #level_up_icon.png
        #Image prev_streets01,prev_apartment1, prev_tutorial01, prev_butcher_shop01
        #InputStream inputStream	= ResourceLoader.getResourceAsStream("myfont.ttf")
 
		#Font awtFont2 = Font.createFont(Font.TrueTYPE_FONT, inputStream)
		#awtFont2 = awtFont2.deriveFont(24f) # set font size
		#font2 = new TrueTypeFont(awtFont2, antiAlias)
        #moved to init
        #self.map = MyTiledMap() # map     #map class for current map
        #self.settings = Settings() # settings

        #@Override
    def init(self): #, GameContainer gc): # throws SlickException: 
        self.map = MyTiledMap("data/dojo01.tmx", 0, 0)
        #self.map = MyTiledMap("data/tutorial01.tmx", 0, 0)
        #self.map = MyTiledMap("data/butcher_shop01.tmx", 0, 0)
        #self.map = MyTiledMap("data/streets01.tmx", 0, 0)
        self.settings = Settings() #how do we save them?
        self.msa = MouseActions()
        self.ksa = KeyActions()
        self.titlemenu = TitleMenu(this)
        #input = new Input(gc.getHeight())
        self.mapactorself.map.getActorLocationFromTMX(map)
        self.fps = gc.getFPS()
        self.actor_move_timer = 0
        self.lastTime = 0
        self.lastframe = 0
        self.turn_count = 0
        self.currentTime = gc.getTime()

        self.screen_height = gc.getScreenHeight()
        self.screen_width = gc.getScreenWidth()

        self.draw_x = self.map.getIsoXToScreen(self.map.player.tilex, self.map.player.tiley) * -1 + self.screen_width / 2
        self.draw_y = self.map.getIsoYToScreen(self.map.player.tilex, self.map.player.tiley) * -1 + self.screen_height / 2

        self.lastframe = gc.getTime()

        self.button_items = pyglet.resource.image("data/button_items.png")
        self.button_profile = pyglet.resource.image("data/button_profile.png")
        self.button_endturn = pyglet.resource.image("data/button_endturn2.png")
        self.button_menu = pyglet.resource.image("data/button_menu.png")
        self.button_shadow = pyglet.resource.image("data/button_shadow.png")
        self.button_punch = pyglet.resource.image("data/button_punch.png")
        self.effect_biff = pyglet.resource.image("data/soundeffects/biff.png")
        self.effect_wiff = pyglet.resource.image("data/soundeffects/wiff.png")
        self.effect_shrack = pyglet.resource.image("data/soundeffects/shrack.png")
        
        self.prev_streets01 = pyglet.resource.image("data/prev_streets01.jpg")
        self.prev_apartment1 = pyglet.resource.image("data/prev_apartment1.jpg")
        self.prev_tutorial01 = pyglet.resource.image("data/prev_tutorial01.jpg")
        self.prev_butcher_shop01 = pyglet.resource.image("data/prev_butcher_shop01.jpg")
        
        self.enemy_moving_message = pyglet.resource.image("data/enemy_moving.png")
        self.myfilter = Color(1, 1, 1, 1)
        self.myfiltert = Color(0.5, 0.5, 0.5, 0.5)
        self.myfilterd = Color(0.2, 0.2, 0.2, 0.8) #for darkness/fog
        self.last_mouse_x = 0#input.getMouseX()
        self.last_mouse_y = 0#input.getMouseY()
        self.playersave = Actor("data/player00", 218, 313) #to carry over the player.
        self.level_up_icon = pyglet.resource.image("data/level_up_icon.png")
        self.music = pyglet.media.load("data/soundeffects/anxiety_backwards.ogg", streaming=False)

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
        self.input = gc.getInput()
        self.mouse_x = input.getMouseX()
        self.mouse_y = input.getMouseY()
        self.msa.mouseWasClicked(input, map, this) #Do mouse actions
        self.ksa.getKeyActions(gc, input, this) #Do keyboard actions
        self.mapupdateMapXY(draw_x, draw_y)
        self.actor_move_timer = self.actor_move_timer+1
        self.mapresetAttackAniFrame()
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
        if (self.map.player.dead == True and self.game_state == "tactical"):
            self.map.turn_order = "game over"
            self.game_state = "game over"
        if (self.map.turn_order == "game over"): #update
            n = None
        elif (self.map.turn_order == "planning"):  #update
            n = None #why is this here?
            #planning phase.  Show a dialogue.
            #Accept clicks through the dialogue
            #after the last click, accept
        elif (self.map.turn_order == "exit reached"):
            #exit has been reached, transition self.map  Do not set unless
            if (self.map.player.expForExitReached == False):
                self.map.player.exp_points += 3
                self.map.player.expForExitReached = True
                self.map.player.health_points = self.map.player.health_points_max
                self.map.player.fatigue_points = self.map.player.fatigue_points_max
                self.map.player.snd_footsteps.stop() #for x in range(0, 3):
            for i in range(0, self.map.follower_max):  #for x in range(0, 3):
                if (self.map.follower[i].dead == False):
                    if (self.map.follower[i].expForExitReached == False):
                        self.map.follower[i].exp_points += 3
                        self.map.follower[i].health_points = self.map.follower[i].health_points_max
                        self.map.follower[i].fatigue_points = self.map.follower[i].fatigue_points_max
                        self.map.follower[i].snd_footsteps.stop()
                        self.map.follower[i].expForExitReached = True
            #goal has been reached , or goal = none
        elif (self.map.turn_order == "change map"):
            n_mapname = self.mapnext_map #local String n_mapname = 
            #change map here  map = new MyTiledMap("data/class_school01.tmx", 0, 0)
            #self.map.getActorLocationFromTMX()
            try:
                self.map.player.copyActorStats(playersave)
                self.map = MyTiledMap("data/" + n_mapname, 0, 0)
                #we lose player info Here.  
                self.mapactorself.map.getActorLocationFromTMX(map) #actor location?
                self.playersave.copyActorStats(self.map.player)
                self.map.turn_order = "planning"
                self.mapmouse_over_tile_x = 1
                self.mapmouse_over_tile_y = 1
                draw_x = self.map.getIsoXToScreen(self.map.player.tilex, self.map.player.tiley) * -1 + self.screen_width / 2
                draw_y = self.map.getIsoYToScreen(self.map.player.tilex, self.map.player.tiley) * -1 + self.screen_height / 2
            except:
                print("cant load new map " + n_mapname)
        elif (self.map.turn_order == "goal reached"):
            if (self.map.player.expForGoal == False):
                self.map.player.exp_points += 3
                self.map.player.expForGoal = True
            for i in range(0, self.map.follower_max): #for (int i = 0 i < self.map.follower_max i++): 
                if (self.map.follower[i].dead == False):
                    if (self.map.player.expForGoal == False):
                        self.map.follower[i].exp_points += 3
                        self.map.follower[i].expForGoal = True
        elif (self.map.turn_order == "player"):
            if (self.actor_move_timer == 0):
                self.map.player.onMoveActor(self.map, gc.getFPS())#self.getMyDelta(gc))
                self.maponFollowerMoving(gc, this, delta)
                if(self.map.player.action_points <= 0 and self.map.getFollowersCanMove() == False):
                    #make it the monster turn automatically
                    self.map.turn_order = "start monster"
            if (self.mapactive_trigger.name == "none"): #not already stepped in it
                self.mapactive_trigger.onSteppedOnTrigger(self.map, self.map.player.tilex, self.map.player.tiley)
                # how do we make it null again?
        elif (self.map.turn_order == "start follower"): #
            self.mapsetFollowerDirectives()
            self.map.turn_order = "follower"
        #elif (self.map.turn_order == ("follower")): 

        elif (self.map.turn_order == "start player"):
            self.map.player.action_points = 6 + self.map.player.stat_speed - 1 + self.map.player.getMovePenalty()
            #check for level up
            self.map.player.onLevelUp()
            for i in range(0, self.map.follower_max): #for (int i = 0 i < self.map.follower_max i++): 
                self.map.follower[i].onLevelUp()
            #give followers action points.
            self.map.setFollowerDirectives()
            self.map.turn_order = "player"

        elif (self.map.turn_order == "start monster"):
            self.map.setMonsterDirectives()
            self.map.turn_order = "monster"
        elif (self.map.turn_order == ("monster")): 
            if (self.actor_move_timer == 0): 
                self.map.resetAttackAniFrame()
                self.map.onMonsterMoving(gc, self, delta) #wrapper for onMoveActor
        self.map.onUpdateActorActionText()

    #def render(gc, g): # throws SlickException:
    def render(gc, g): #what args do we need?
        if (self.game_state == ("tactical")): 
            render_tactical_base(gc, g)
        elif (self.game_state == ("conversation")): 
            g.scale(scale_x, scale_x) #scale the same
            render_tactical_base(gc, g)
        elif (self.game_state == ("cutscene")): 
            self.render_cutscene(gc, g) #animations?
        elif (self.game_state == ("title start") or self.game_state == ("title ingame")): 
            #self.render_title(gc, g)
            self.titlemenu.render(this, g)
        elif (self.game_state == ("game over")): 
            self.render_game_over(gc, g)  #its game over for your kind
    def render_tactical_base(gc, g):
        g.scale(scale_x, scale_x) #scale the same
        self.render_background_layer(gc, g) #render floor
        self.render_walls_layer(gc, g)      #render walls (and actors!)
        self.render_game_ui(gc, g)
        self.render_character_busts(gc, g)

    def render_background_layer(gc, g): 
        background_layer = self.map.getLayerIndex("background_layer")
        self.mapset_party_min_renderables()
        for y in range(self.map.render_min_y - 4, self.map.render_max_y):#(int y = self.map.render_min_y - 4 y < self.map.render_max_y + 4 y++): 
            for x in range(self.map_render_min_x -4, self.map.render_max_x + 4): #for (int x = self.map.render_min_x - 4 x < self.map.render_max_x + 4 x++): 
                self.screen_x = (x - y) * self.mapTILE_WIDTH_HALF
                self.screen_y = (x + y) * self.mapTILE_HEIGHT_HALF
                try: 
                    if (self.getTileToBeRendered(x, y)): 
                        if (self.getTileToBeFiltered(x, y)): #if its outside 2 steps
                            #java.lang.ArrayIndexOutOfBoundsException: 20 
                            try: 
                                xi = self.map.getTileImage(x, y, background_layer) #Image
                                #self.map.getTileImage(x, y, background_layer).draw(
                                xi.draw(screen_x + draw_x, screen_y + draw_y, scale_x, self.myfilterd)
                            except: # catch (ArrayIndexOutOfBoundsException ae): 
                                print("Block the bug: xi.draw())") # # block the bug.
                        else:  #draw normal
                            self.map.getTileImage(x, y, background_layer).draw(
                                    screen_x + draw_x, screen_y + draw_y, scale_x)
                except: # catch (ArrayIndexOutOfBoundsException e): 
                    print("Block the bug: try if(xi.draw)") # # block the bug.

    def render_wall_by_wall(gc, g, x, y): 
        walls_layer = self.map.getLayerIndex("walls_layer")
        try: 
            if (self.map.getTileImage(x, y, walls_layer) == False):
                print("self.map.getTileImage(x, y, walls_layer) == False)")
            elif (self.wall_intersect_player(x, y, screen_x, screen_y) == True): 
                self.map.getTileImage(x, y, walls_layer).draw(
                        screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfiltert)
            else: #inside cannot be dark
                if (x < self.map.player.tilex - self.maplight_level 
                        or x > self.map.player.tilex + self.maplight_level  
                        or y < self.map.player.tiley - self.maplight_level  
                        or y > self.map.player.tiley + self.maplight_level): 
                    if (self.getTileToBeRendered(x, y)): 
                        self.map.getTileImage(x, y, walls_layer).draw(
                                screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfilterd)
                else: 
                    self.map.getTileImage(x, y, walls_layer).draw(
                            screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfilter)
            if (y == self.mapselected_tile_y and x == self.mapselected_tile_x and self.map.turn_order == "player"): 
                #self.map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y)
                self.mapselected_green.draw(screen_x + draw_x, screen_y + draw_y)
            if (y == self.map.player.tiledesty and x == self.map.player.tiledestx
                    and self.map.player.getActorMoving()): 
                self.map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y)
            for i in range(0, self.map.follower_max):#for (int i = 0 i < self.map.follower_max i++): 
                if (y == self.map.follower[i].tiledesty
                        and x == self.map.follower[i].tiledestx #){
                        and self.map.follower[i].getActorMoving()): 
                    self.map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y) 
        except: #
             print("ArrayIndexOutOfBoundsException e")
    def render_walls_layer(gc, g): 
        mw = self.map.getTileWidth()
        mh = self.map.getTileHeight()
        for y in range(self.map.render_min_y - 4, self.map.render_max_y + 4):#for (int y = self.map.render_min_y - 4 y < self.map.render_max_y + 4 y++): 
            ##/*Y Loop*/
            for x in range(self.map.render_min_x -4, self.map.render_max +4):#for (int x = self.map.render_min_x - 4 x < self.map.render_max_x + 4 x++): 
                #/* X Loop*/
                self.screen_x = (x - y) * self.map.TILE_WIDTH_HALF
                #/*Calculate screen/x/y*/
                self.screen_y = (x + y) * self.mapTILE_HEIGHT_HALF
                if (x >= 0 and y >= 0 and x <= mw and y <= mh): 
                    #/*loop through tiles */
                    self.mouse_x = gc.getInput().getMouseX()
                    #/*get mouse coords*/
                    self.mouse_y = gc.getInput().getMouseY()
                    sx = screen_x + self.draw_x + 30
                    #/*screen x/y+drawing offset*/
                    sy = screen_y + self.draw_y + 30
                    #/*compare mouse to sx*/
                    if (mouse_x >= sx and mouse_x <= sx + 250 - 30
                            and mouse_y >= sy and mouse_y <= sy + 130 - 30): 
                        self.mapmouse_over_tile_x = x
                        self.mapmouse_over_tile_y = y
                        #is there someone in mouse over tile?
                        self.mapmouse_over_actor(x, y) #render the selector.
                        #self.maptiles250x129.getSubImage(0, 0, 250, 130).draw(
                        #        screen_x + draw_x, screen_y + draw_y, scale_x)
                    
                    self.map.player.drawPlayer(this, self.map, x, y, g)
                    #/*Draw your player*/
                    self.mapdrawMonsters(this, x, y, g)
                    #/*self.mapmonster[0].drawActor(this, map, x, y)*/
                    self.mapdrawFollowers(this, x, y, g)
                    #/*draw your followers*/
                    if (self.mapRequiresGoal == ("yes")
                            and self.mapEventGoal_ran == False
                            and x == self.mapdraw_goal_x
                            and y == self.mapdraw_goal_y): 
                        #/*bug? better have an image*/
                        pdx = self.screen_x + self.draw_x
                        #/* + self.draw_x*/
                        pdy = self.screen_y + self.draw_y
                        #/* + self.draw_y - 230*/
                        self.mapmission_goal.draw(pdx, pdy)
                    
                    if (self.getTileToBeRendered(x, y)): 
                        render_wall_by_wall(gc, g, x, y) #ArrayIndexOutOfBoundsException
    #def render_game_ui(gc, g, )
    def getMouseOverBottomButtons(ht): 
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
        
    
    def render_game_ui(gc, g): 
        mouseovervar = getMouseOverBottomButtons(this)
        self.map.player.iconImage.draw(5, 50)
        if (self.map.player.newLevelUp == True):  #level_up_icon
            self.level_up_icon.draw(5, 50)
        
        g.drawString("Action: " + Integer.toString(self.map.player.action_points),
                5, 50 + 75)
        for (int i = 0 i < self.map.follower_max i++): 
            if (self.map.follower[i].visible == True): 
                self.map.follower[i].iconImage.draw(5, 50 + (100 * (i + 1)))
                if (self.map.follower[i].newLevelUp == True): 
                    self.level_up_icon.draw(5, 50 + (100 * (i + 1)))
                
                g.drawString("Action: " + Integer.toString(self.map.follower[i].action_points),
                        #5, 50 + 75)# (100 * (i + 1) + 75))
                        5, 50 + (100 * (i + 1)) + 75)
            
        
        g.setColor(myfilterd)
        g.fillOval(5, 50 + 75, 12, 14)
        g.setColor(myfilter)
        button_items.draw(gc.getScreenWidth() - 400, gc.getScreenHeight() - 64 - 10)
        button_profile.draw(gc.getScreenWidth() - 300, gc.getScreenHeight() - 64 - 10)
        button_endturn.draw(gc.getScreenWidth() - 200, gc.getScreenHeight() - 64 - 10)
        button_menu.draw(gc.getScreenWidth() - 100, gc.getScreenHeight() - 64 - 10)
        button_shadow.draw(gc.getScreenWidth() -mouseovervar, gc.getScreenHeight() - 64 - 10) #button_shadow
        g.drawString("Player At:" + self.map.player.tilex + "X" + self.map.player.tiley + "mouse at:"
                + self.mapmouse_over_tile_x + "x" + self.mapmouse_over_tile_y + " Turn: "
                + self.map.turn_order + " mm: " + self.mapcurrent_monster_moving + "/"
                + self.mapmonster[self.mapcurrent_monster_moving].action_points + " dst:"
                + self.mapmonster[self.mapcurrent_monster_moving].tiledestx + ","
                + self.mapmonster[self.mapcurrent_monster_moving].tiledesty,
                200, 10)#might crash?
        g.drawString(self.maplog_msg, 200, 10 + 14)
        #g.drawString("Trigger Check: " + self.maptrigger_check, 500, 100)
        if (self.map.turn_order == ("monster")): 
            self.enemy_moving_message.draw(gc.getWidth() / 2 - 200, gc.getHeight() / 2)
            #The enemy is moving, show which enemy.(so people dont get bored)
            for (int mi = 0 mi < self.mapcurrent_monster_moving mi++): 
                self.mapmonster[mi].iconImage.draw(gc.getWidth() / 2 - 200 + (92 * mi + 2), gc.getHeight() / 2 + 92)
            
        
        #check for a follower selected
        boolean foundselected = False
        for (int i = 0 i < self.map.follower_max i++): 
            if (self.map.follower[i].selected == True): 
                foundselected = True
                self.map.follower[i].drawPopupWindow(this, g)
                break
            
        
        if (foundselected == False): 
            self.map.player.drawPopupWindow(this, g) #who is currently selected?
        
    
    
    def render_character_busts(gc, g): 
        #render character busts while conversation is going on.
        Color black = new Color(0, 0, 0, 180)
        Color white = new Color(255, 255, 255, 255)
        if (self.map.turn_order == ("planning")): 
            self.mapcharbusts[self.mapplanevent].draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #self.uniFont.drawString(400, gc.getScreenHeight() - 100, self.mapplanning[self.mapplanevent], Color.white )
            g.drawString(self.mapplanning[self.mapplanevent], 400, gc.getScreenHeight() - 100)
        elif (self.map.turn_order == ("event spotted")): 
            #null pointer if there is no event spotted
            self.mapEventSpotted_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.mapEventSpotted_m, Color.white)
            #set all monsters to spotted True (if False)
            g.drawString(self.mapEventSpotted_m, 400, gc.getScreenHeight() - 100)
            for (int i = 0 i < self.mapmonster_max i++): 
                self.mapmonster[i].spotted_enemy = True
            
        elif (self.map.turn_order == ("goal reached")): 
            self.mapEventGoal_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.mapEventGoal_m, Color.white)
            g.drawString(self.mapEventGoal_m, 400, gc.getScreenHeight() - 100)
        elif (self.map.turn_order == ("exit reached")): 
            self.mapEventExit_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.mapEventExit_m, Color.white)
            g.drawString(self.mapEventExit_m, 400, gc.getScreenHeight() - 100)
        
    

    def render_event_bubbles(gc, g): 
        #conversation bubbles, triggered by events
    

    def render_cutscene(gc, g): 
        #render cutscenes
    

    def render_game_over(gc, g): #yoo dyied!
        #g.clear() #fade to black
        g.scale(scale_x, scale_x) #scale the same
        self.render_background_layer(gc, g) #render floor
        self.render_walls_layer(gc, g)      #render walls (and actors!)
        self.render_game_ui(gc, g)
        self.render_character_busts(gc, g) #bring up the busts, and talk
        g.drawString("Game Over", gc.getScreenWidth()/2 - 100, gc.getScreenHeight()/2 - 12)
    

    public boolean wall_intersect_player(int x, int y, int screen_x, int screen_y): 
        #int x = self.map.player.tilex - 4 x < self.map.player.tilex + 4 x++
        for (int ly = 0 ly < 4 ly++): 
            for (int lx = 0 lx < 4 lx++): 
                if (x == self.map.player.tilex + lx and y == self.map.player.tiley + ly): 
                    return True
                
            
        
        return False
    

    public int getMyDelta(GameContainer gc): 
        long time = gc.getTime()
        int tdelta = (int) (time - self.lastframe)
        self.lastframe = time
        if (tdelta == 0): 
            return 1
        
        return tdelta
    

    public static void main(String[] args): 
        try: 
            AppGameContainer appgc
            appgc = new AppGameContainer(new HorrorTactics("Horror Tactics"))

            
            appgc.setDisplayMode( #1024, 768, False
                    appgc.getScreenWidth(),
                    appgc.getScreenHeight(),
                    True
            )
            appgc.setTargetFrameRate(60) #trying to slow down fast computers.
            appgc.start()

         catch (SlickException ex): 
        
    

    def setGameState(String state): 
        self.game_state = state
    

    public MyTiledMap getCurrentMap(): 
        return self.map
    

    public Image getComicActionStrImage(String a): 
        if (a == ("Dodge")): 
            return self.effect_wiff
        elif (a == ("Dead")): 
            return self.effect_shrack
            #return self.effect_biff
         else: 
            return self.effect_wiff
        
    

    #/*public int getActiveMonsters(): 
     int am = 0
     for (int i = 0 i < self.mapmonster_max i++): 
     if (self.mapmonster[i].visible == True): 
     am++
     
     
     return am 
     */
    public boolean getTileToBeRendered(int x, int y): 
        if (x < 0): 
            return False
        
        if (y < 0): 
            return False
        
        if (x > self.map.getWidth()): 
            return False
        
        if (y > self.map.getHeight()): 
            return False
        
        return True
    

    public boolean getTileToBeFiltered(int x, int y):  #if its outside 2 steps
        if (getTileToBeRendered(x, y)): 
            #/*for (int i = 0 i < self.map.follower_max i++): 
                if (x < self.map.follower[i].tilex - 2
                        or x > self.map.follower[i].tilex + 2
                        or y < self.map.follower[i].tiley - 2
                        or y > self.map.follower[i].tiley + 2): 
                    return False
                 
            */
            if (x < self.map.player.tilex - 3
                    or x > self.map.player.tilex + 3
                    or y < self.map.player.tiley - 3
                    or y > self.map.player.tiley + 3): 
                return True
            
        
        return False
    

    def loadNewMap(String newmap) throws SlickException: 
        map = new MyTiledMap(newmap, 0, 0) #setup a new map
        self.mapactorself.map.getActorLocationFromTMX(map) #get the actor info
        self.popup_window = "none" #bug with kaleb where popup window still up
    
    
    def translateToTile(int tx, int ty)#center tile x/y on the screen?
   : 
        self.draw_x -= self.map.getIsoXToScreen(tx, ty)-(self.screen_width/3)
        self.draw_y -= self.map.getIsoYToScreen(tx, ty)-(self.screen_height-200)
    

