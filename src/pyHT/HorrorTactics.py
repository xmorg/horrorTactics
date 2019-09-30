#/*
# * To change this license header, choose License Headers in Project Properties.
# * To change this template file, choose Tools | Templates
# * and open the template in the editor.
# * Bug! array out of bounds when reaching the edge of the map
# */
#package horrortactics

import pyglet
from files.slickWrap import Color
from files.slickWrap import Rectangle

#/**
# *
# * @author tcooper
# */

class HorrorTactics: # extends BasicGame {
    def __init__(self):
        self.input = Input() #input class
        
        self.msa = MouseActions() # msa #key and mouse actions
        self.ksa = KeyActions() # ksa
        self.draw_x = 0
        self.draw_y = 0   #draw offset for screen?
        #/* offset for camera scrolling*/
        self.screen_x = 0
        self.screen_y = 0
        #/* where on the screen?*/
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
 
		#Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream)
		#awtFont2 = awtFont2.deriveFont(24f) # set font size
		#font2 = new TrueTypeFont(awtFont2, antiAlias)
        #moved to init
        #self.map = MyTiledMap() # map     #map class for current map
        #self.settings = Settings() # settings

        #@Override
    def init(self): #, GameContainer gc): # throws SlickException {
        self.map = MyTiledMap("data/dojo01.tmx", 0, 0)
        #map = new MyTiledMap("data/tutorial01.tmx", 0, 0)
        #map = new MyTiledMap("data/butcher_shop01.tmx", 0, 0)
        #map = new MyTiledMap("data/streets01.tmx", 0, 0)
        self.settings = Settings() #how do we save them?
        self.msa = MouseActions()
        self.ksa = KeyActions()
        self.titlemenu = TitleMenu(this)
        #input = new Input(gc.getHeight())
        self.map.actormap.getActorLocationFromTMX(map)
        self.fps = gc.getFPS()
        self.actor_move_timer = 0
        self.lastTime = 0
        self.lastframe = 0
        self.turn_count = 0
        self.currentTime = gc.getTime()

        self.screen_height = gc.getScreenHeight()
        self.screen_width = gc.getScreenWidth()

        self.draw_x = self.map.getIsoXToScreen(self.self.map.player.tilex, self.self.map.player.tiley) * -1 + self.screen_width / 2
        self.draw_y = self.map.getIsoYToScreen(self.self.map.player.tilex, self.self.map.player.tiley) * -1 + self.screen_height / 2

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
            #java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream)
            #awtFont = awtFont.deriveFont(48f)
            #awtFont.deriveFont(bold)
            #ttf = new TrueTypeFont(awtFont, false)
        #} catch (Exception e) {
        #    e.printStackTrace()
        #}
        

    def update(self, gc, delta): # throws SlickException {
        self.input = gc.getInput()
        self.mouse_x = input.getMouseX()
        self.mouse_y = input.getMouseY()
        self.msa.mouseWasClicked(input, map, this) #Do mouse actions
        self.ksa.getKeyActions(gc, input, this) #Do keyboard actions
        self.map.updateMapXY(draw_x, draw_y)
        self.actor_move_timer = self.actor_move_timer+1
        self.map.resetAttackAniFrame()
        if (self.game_state.equalsIgnoreCase("exit")): # {
            gc.exit()
        elif (self.game_state.equalsIgnoreCase("title start") or self.game_state.equalsIgnoreCase("title ingame")): # {
            if (self.music.playing() == False): # {
                self.music.play()
        else:
            if (self.music.playing()):
                self.music.stop()
        if (self.actor_move_timer >= self.fps) {
            self.actor_move_timer = 0
        }
        if (self.map.player.dead == true and self.game_state.equalsIgnoreCase("tactical")) {
            self.map.turn_order = "game over"
            self.game_state = "game over"
        }
        if (map.turn_order.equalsIgnoreCase("game over")) { #update
            
        } else if (map.turn_order.equalsIgnoreCase("planning")) { #update
            #planning phase.  Show a dialogue.
            #Accept clicks through the dialogue
            #after the last click, accept
        } else if (map.turn_order.equalsIgnoreCase("exit reached")) {
            #exit has been reached, transition map.  Do not set unless
            if (self.map.player.expForExitReached == false) {
                self.map.player.exp_points += 3
                self.map.player.expForExitReached = true
                self.map.player.health_points = self.map.player.health_points_max
                self.map.player.fatigue_points = self.map.player.fatigue_points_max
                self.map.player.snd_footsteps.stop()
            }
            for (int i = 0 i < self.map.follower_max i++) {
                if (self.map.follower[i].dead == false) {
                    if (self.map.follower[i].expForExitReached == false) {
                        self.map.follower[i].exp_points += 3
                        self.map.follower[i].health_points = self.map.follower[i].health_points_max
                        self.map.follower[i].fatigue_points = self.map.follower[i].fatigue_points_max
                        self.map.follower[i].snd_footsteps.stop()
                        self.map.follower[i].expForExitReached = true
                    }
                }
            }
            #goal has been reached , or goal = none

        } else if (map.turn_order.equalsIgnoreCase("change map")) {
            String n_mapname = self.map.next_map
            #change map here  map = new MyTiledMap("data/class_school01.tmx", 0, 0)
            #map.getActorLocationFromTMX()
            try {
                self.self.map.player.copyActorStats(playersave)
                self.map = new MyTiledMap("data/" + n_mapname, 0, 0)
                #we lose player info Here.  
                map.actormap.getActorLocationFromTMX(map) #actor location?
                self.playersave.copyActorStats(self.map.player)
                self.map.turn_order = "planning"
                map.mouse_over_tile_x = 1
                map.mouse_over_tile_y = 1
                draw_x = map.getIsoXToScreen(self.map.player.tilex, self.map.player.tiley) * -1 + self.screen_width / 2
                draw_y = map.getIsoYToScreen(self.map.player.tilex, self.map.player.tiley) * -1 + self.screen_height / 2
            } catch (SlickException e) {
                System.out.println("cant load new map " + n_mapname)
            }
        } else if (map.turn_order.equalsIgnoreCase("goal reached")) {
            if (self.map.player.expForGoal == false) {
                self.map.player.exp_points += 3
                self.map.player.expForGoal = true
            }
            for (int i = 0 i < self.map.follower_max i++) {
                if (self.map.follower[i].dead == false) {
                    if (self.map.player.expForGoal == false) {
                        self.map.follower[i].exp_points += 3
                        self.map.follower[i].expForGoal = true
                    }
                }
            }
        } else if (map.turn_order.equalsIgnoreCase("player")) {
            if (self.actor_move_timer == 0) {
                self.map.player.onMoveActor(map, gc.getFPS())#self.getMyDelta(gc))
                map.onFollowerMoving(gc, this, delta)
                if(self.map.player.action_points <= 0 and map.getFollowersCanMove() == false) {
                    #make it the monster turn automatically
                    map.turn_order = "start monster"
                }
            }
            if (self.map.active_trigger.name.equals("none")) { #not already stepped in it
                map.active_trigger.onSteppedOnTrigger(map, self.self.map.player.tilex, self.self.map.player.tiley)
            } # how do we make it null again?
        } else if (map.turn_order.equalsIgnoreCase("start follower")) { #
            self.map.setFollowerDirectives()
            map.turn_order = "follower"
        } else if (map.turn_order.equalsIgnoreCase("follower")) {

        } else if (map.turn_order.equalsIgnoreCase("start player")) {
            self.map.player.action_points = 6 + self.map.player.stat_speed - 1 + self.map.player.getMovePenalty()
            #check for level up
            self.map.player.onLevelUp()
            for (int i = 0 i < self.map.follower_max i++) {
                self.map.follower[i].onLevelUp()
            }
            #give followers action points.
            self.map.setFollowerDirectives()
            map.turn_order = "player"

        } else if (map.turn_order.equalsIgnoreCase("start monster")) {
            self.map.setMonsterDirectives()
            map.turn_order = "monster"
        } else if (map.turn_order.equalsIgnoreCase("monster")) {
            if (self.actor_move_timer == 0) {
                map.resetAttackAniFrame()
                map.onMonsterMoving(gc, this, delta) #wrapper for onMoveActor
            }
        }
        map.onUpdateActorActionText()
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (game_state.equalsIgnoreCase("tactical")) {
            render_tactical_base(gc, g)
        } else if (game_state.equalsIgnoreCase("conversation")) {
            g.scale(scale_x, scale_x) #scale the same
            render_tactical_base(gc, g)
        } else if (game_state.equalsIgnoreCase("cutscene")) {
            self.render_cutscene(gc, g) #animations?
        } else if (game_state.equalsIgnoreCase("title start") or game_state.equalsIgnoreCase("title ingame")) {
            #self.render_title(gc, g)
            self.titlemenu.render(this, g)
        } else if (game_state.equalsIgnoreCase("game over")) {
            self.render_game_over(gc, g)  #its game over for your kind
        }
    }

    public void render_tactical_base(GameContainer gc, Graphics g) {
        g.scale(scale_x, scale_x) #scale the same
        self.render_background_layer(gc, g) #render floor
        self.render_walls_layer(gc, g)      #render walls (and actors!)
        self.render_game_ui(gc, g)
        self.render_character_busts(gc, g)
    }

    public void render_background_layer(GameContainer gc, Graphics g) {
        int background_layer = map.getLayerIndex("background_layer")
        map.set_party_min_renderables()
        for (int y = map.render_min_y - 4 y < map.render_max_y + 4 y++) {
            for (int x = map.render_min_x - 4 x < map.render_max_x + 4 x++) {
                screen_x = (x - y) * map.TILE_WIDTH_HALF
                screen_y = (x + y) * map.TILE_HEIGHT_HALF
                try {
                    if (self.getTileToBeRendered(x, y)) {
                        if (self.getTileToBeFiltered(x, y)) {#if its outside 2 steps
                            #java.lang.ArrayIndexOutOfBoundsException: 20 
                            try {
                                Image xi = map.getTileImage(x, y, background_layer)
                                #map.getTileImage(x, y, background_layer).draw(
                                xi.draw(screen_x + draw_x, screen_y + draw_y, scale_x, self.myfilterd)
                            } catch (ArrayIndexOutOfBoundsException ae) {
                            }
                        } else { #draw normal
                            map.getTileImage(x, y, background_layer).draw(
                                    screen_x + draw_x, screen_y + draw_y, scale_x)
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                } # block the bug.
            }
        }
    }

    public void render_wall_by_wall(GameContainer gc, Graphics g, int x, int y) {
        int walls_layer = map.getLayerIndex("walls_layer")
        try {
            if (map.getTileImage(x, y, walls_layer) == null) {
            } else if (self.wall_intersect_player(x, y, screen_x, screen_y) == true) {
                map.getTileImage(x, y, walls_layer).draw(
                        screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfiltert)
            } else #inside cannot be dark
            {
                if (x < self.map.player.tilex - self.map.light_level
                        or x > self.map.player.tilex + self.map.light_level
                        or y < self.map.player.tiley - self.map.light_level
                        or y > self.map.player.tiley + self.map.light_level) {
                    if (self.getTileToBeRendered(x, y)) {
                        map.getTileImage(x, y, walls_layer).draw(
                                screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfilterd)
                    }
                } else {
                    map.getTileImage(x, y, walls_layer).draw(
                            screen_x + draw_x, screen_y + draw_y - 382, scale_x, myfilter)
                }
            }
            if (y == map.selected_tile_y and x == map.selected_tile_x and self.map.turn_order.equalsIgnoreCase("player")) {
                #map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y)
                map.selected_green.draw(screen_x + draw_x, screen_y + draw_y)
            }
            if (y == self.map.player.tiledesty and x == self.map.player.tiledestx
                    and self.map.player.getActorMoving()) {
                map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y)
            }
            for (int i = 0 i < self.map.follower_max i++) {
                if (y == self.map.follower[i].tiledesty
                        and x == self.map.follower[i].tiledestx #){
                        and self.map.follower[i].getActorMoving()) {
                    map.selected_yellow.draw(screen_x + draw_x, screen_y + draw_y)
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void render_walls_layer(GameContainer gc, Graphics g) {
        int mw = map.getTileWidth()
        int mh = map.getTileHeight()
        for (int y = map.render_min_y - 4 y < map.render_max_y + 4 y++) {
            /*Y Loop*/
            for (int x = map.render_min_x - 4 x < map.render_max_x + 4 x++) {
                /* X Loop*/
                screen_x = (x - y) * map.TILE_WIDTH_HALF
                /*Calculate screen/x/y*/
                screen_y = (x + y) * map.TILE_HEIGHT_HALF
                if (x >= 0 and y >= 0 and x <= mw and y <= mh) {
                    /*loop through tiles */
                    mouse_x = gc.getInput().getMouseX()
                    /*get mouse coords*/
                    mouse_y = gc.getInput().getMouseY()
                    int sx = screen_x + draw_x + 30
                    /*screen x/y+drawing offset*/
                    int sy = screen_y + draw_y + 30
                    /*compare mouse to sx*/
                    if (mouse_x >= sx and mouse_x <= sx + 250 - 30
                            and mouse_y >= sy and mouse_y <= sy + 130 - 30) {
                        map.mouse_over_tile_x = x
                        map.mouse_over_tile_y = y
                        #is there someone in mouse over tile?
                        map.mouse_over_actor(x, y) #render the selector.
                        #map.tiles250x129.getSubImage(0, 0, 250, 130).draw(
                        #        screen_x + draw_x, screen_y + draw_y, scale_x)
                    }
                    self.map.player.drawPlayer(this, map, x, y, g)
                    /*Draw your player*/
                    map.drawMonsters(this, x, y, g)
                    /*map.monster[0].drawActor(this, map, x, y)*/
                    map.drawFollowers(this, x, y, g)
                    /*draw your followers*/
                    if (self.map.RequiresGoal.equalsIgnoreCase("yes")
                            and self.map.EventGoal_ran == false
                            and x == self.map.draw_goal_x
                            and y == self.map.draw_goal_y) {
                        /*bug? better have an image*/
                        int pdx = screen_x + draw_x
                        /* + self.draw_x*/
                        int pdy = screen_y + draw_y
                        /* + self.draw_y - 230*/
                        self.map.mission_goal.draw(pdx, pdy)
                    }
                    if (self.getTileToBeRendered(x, y)) {
                        render_wall_by_wall(gc, g, x, y) #ArrayIndexOutOfBoundsException
                    }
                }
            }
        }
    }

    #public void render_game_ui(GameContainer gc, Graphics g, )
    public int getMouseOverBottomButtons(HorrorTactics ht) {
        #if(ht.map.m)
        if( msa.menuButtonWasOver(ht) ) {return 100}
        else if( msa.endTurnButtonWasOver(ht) ) {return 200}
        else if( msa.profileButtonWasOver(ht)) { return 300 }
        else if( msa.itemsButtonWasOver(ht) ) {return 400}
        else {
            return -100
        }
    }
    public void render_game_ui(GameContainer gc, Graphics g) {
        int mouseovervar
        mouseovervar = getMouseOverBottomButtons(this)
        self.map.player.iconImage.draw(5, 50)
        if (self.map.player.newLevelUp == true) { #level_up_icon
            self.level_up_icon.draw(5, 50)
        }
        g.drawString("Action: " + Integer.toString(self.map.player.action_points),
                5, 50 + 75)
        for (int i = 0 i < self.self.map.follower_max i++) {
            if (self.self.map.follower[i].visible == true) {
                self.self.map.follower[i].iconImage.draw(5, 50 + (100 * (i + 1)))
                if (self.map.follower[i].newLevelUp == true) {
                    self.level_up_icon.draw(5, 50 + (100 * (i + 1)))
                }
                g.drawString("Action: " + Integer.toString(self.map.follower[i].action_points),
                        #5, 50 + 75)# (100 * (i + 1) + 75))
                        5, 50 + (100 * (i + 1)) + 75)
            }
        }
        g.setColor(myfilterd)
        g.fillOval(5, 50 + 75, 12, 14)
        g.setColor(myfilter)
        button_items.draw(gc.getScreenWidth() - 400, gc.getScreenHeight() - 64 - 10)
        button_profile.draw(gc.getScreenWidth() - 300, gc.getScreenHeight() - 64 - 10)
        button_endturn.draw(gc.getScreenWidth() - 200, gc.getScreenHeight() - 64 - 10)
        button_menu.draw(gc.getScreenWidth() - 100, gc.getScreenHeight() - 64 - 10)
        button_shadow.draw(gc.getScreenWidth() -mouseovervar, gc.getScreenHeight() - 64 - 10) #button_shadow
        g.drawString("Player At:" + self.map.player.tilex + "X" + self.map.player.tiley + "mouse at:"
                + map.mouse_over_tile_x + "x" + map.mouse_over_tile_y + " Turn: "
                + map.turn_order + " mm: " + map.current_monster_moving + "/"
                + map.monster[map.current_monster_moving].action_points + " dst:"
                + map.monster[map.current_monster_moving].tiledestx + ","
                + map.monster[map.current_monster_moving].tiledesty,
                200, 10)#might crash?
        g.drawString(self.map.log_msg, 200, 10 + 14)
        #g.drawString("Trigger Check: " + map.trigger_check, 500, 100)
        if (self.map.turn_order.equalsIgnoreCase("monster")) {
            self.enemy_moving_message.draw(gc.getWidth() / 2 - 200, gc.getHeight() / 2)
            #The enemy is moving, show which enemy.(so people dont get bored)
            for (int mi = 0 mi < self.map.current_monster_moving mi++) {
                self.map.monster[mi].iconImage.draw(gc.getWidth() / 2 - 200 + (92 * mi + 2), gc.getHeight() / 2 + 92)
            }
        }
        #check for a follower selected
        boolean foundselected = false
        for (int i = 0 i < self.map.follower_max i++) {
            if (self.map.follower[i].selected == true) {
                foundselected = true
                self.self.map.follower[i].drawPopupWindow(this, g)
                break
            }
        }
        if (foundselected == false) {
            self.self.map.player.drawPopupWindow(this, g) #who is currently selected?
        }
    }
    
    public void render_character_busts(GameContainer gc, Graphics g) {
        #render character busts while conversation is going on.
        Color black = new Color(0, 0, 0, 180)
        Color white = new Color(255, 255, 255, 255)
        if (self.map.turn_order.equalsIgnoreCase("planning")) {
            self.map.charbusts[self.map.planevent].draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #self.uniFont.drawString(400, gc.getScreenHeight() - 100, self.map.planning[self.map.planevent], Color.white )
            g.drawString(self.map.planning[self.map.planevent], 400, gc.getScreenHeight() - 100)
        } else if (self.map.turn_order.equalsIgnoreCase("event spotted")) {
            #null pointer if there is no event spotted
            map.EventSpotted_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, map.EventSpotted_m, Color.white)
            #set all monsters to spotted true (if false)
            g.drawString(map.EventSpotted_m, 400, gc.getScreenHeight() - 100)
            for (int i = 0 i < map.monster_max i++) {
                map.monster[i].spotted_enemy = true
            }
        } else if (self.map.turn_order.equalsIgnoreCase("goal reached")) {
            self.map.EventGoal_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.map.EventGoal_m, Color.white)
            g.drawString(self.map.EventGoal_m, 400, gc.getScreenHeight() - 100)
        } else if (self.map.turn_order.equalsIgnoreCase("exit reached")) {
            self.map.EventExit_p.draw(-100, gc.getScreenHeight() - 600)
            g.setColor(black)
            g.fillRect(0, gc.getScreenHeight() - 150, gc.getScreenWidth(), 150)
            g.setColor(white)
            #uniFont.drawString(400, gc.getScreenHeight() - 100, self.map.EventExit_m, Color.white)
            g.drawString(self.map.EventExit_m, 400, gc.getScreenHeight() - 100)
        }
    }

    public void render_event_bubbles(GameContainer gc, Graphics g) {
        #conversation bubbles, triggered by events
    }

    public void render_cutscene(GameContainer gc, Graphics g) {
        #render cutscenes
    }

    public void render_game_over(GameContainer gc, Graphics g) {#yoo dyied!
        #g.clear() #fade to black
        g.scale(scale_x, scale_x) #scale the same
        self.render_background_layer(gc, g) #render floor
        self.render_walls_layer(gc, g)      #render walls (and actors!)
        self.render_game_ui(gc, g)
        self.render_character_busts(gc, g) #bring up the busts, and talk
        g.drawString("Game Over", gc.getScreenWidth()/2 - 100, gc.getScreenHeight()/2 - 12)
    }

    public boolean wall_intersect_player(int x, int y, int screen_x, int screen_y) {
        #int x = self.map.player.tilex - 4 x < self.map.player.tilex + 4 x++
        for (int ly = 0 ly < 4 ly++) {
            for (int lx = 0 lx < 4 lx++) {
                if (x == self.map.player.tilex + lx and y == self.map.player.tiley + ly) {
                    return true
                }
            }
        }
        return false
    }

    public int getMyDelta(GameContainer gc) {
        long time = gc.getTime()
        int tdelta = (int) (time - self.lastframe)
        self.lastframe = time
        if (tdelta == 0) {
            return 1
        }
        return tdelta
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc
            appgc = new AppGameContainer(new HorrorTactics("Horror Tactics"))

            
            appgc.setDisplayMode( #1024, 768, false
                    appgc.getScreenWidth(),
                    appgc.getScreenHeight(),
                    true
            )
            appgc.setTargetFrameRate(60) #trying to slow down fast computers.
            appgc.start()

        } catch (SlickException ex) {
        }
    }

    public void setGameState(String state) {
        game_state = state
    }

    public MyTiledMap getCurrentMap() {
        return self.map
    }

    public Image getComicActionStrImage(String a) {
        if (a.equalsIgnoreCase("Dodge")) {
            return self.effect_wiff
        } else if (a.equalsIgnoreCase("Dead")) {
            return self.effect_shrack
            #return self.effect_biff
        } else {
            return self.effect_wiff
        }
    }

    /*public int getActiveMonsters() {
     int am = 0
     for (int i = 0 i < map.monster_max i++) {
     if (map.monster[i].visible == true) {
     am++
     }
     }
     return am 
     }*/
    public boolean getTileToBeRendered(int x, int y) {
        if (x < 0) {
            return false
        }
        if (y < 0) {
            return false
        }
        if (x > map.getWidth()) {
            return false
        }
        if (y > map.getHeight()) {
            return false
        }
        return true
    }

    public boolean getTileToBeFiltered(int x, int y) { #if its outside 2 steps
        if (getTileToBeRendered(x, y)) {
            /*for (int i = 0 i < self.map.follower_max i++) {
                if (x < self.map.follower[i].tilex - 2
                        or x > self.map.follower[i].tilex + 2
                        or y < self.map.follower[i].tiley - 2
                        or y > self.map.follower[i].tiley + 2) {
                    return false
                } 
            }*/
            if (x < self.map.player.tilex - 3
                    or x > self.map.player.tilex + 3
                    or y < self.map.player.tiley - 3
                    or y > self.map.player.tiley + 3) {
                return true
            }
        }
        return false
    }

    public void loadNewMap(String newmap) throws SlickException {
        map = new MyTiledMap(newmap, 0, 0) #setup a new map
        map.actormap.getActorLocationFromTMX(map) #get the actor info
        self.popup_window = "none" #bug with kaleb where popup window still up
    }
    
    public void translateToTile(int tx, int ty)#center tile x/y on the screen?
    {
        self.draw_x -= self.map.getIsoXToScreen(tx, ty)-(self.screen_width/3)
        self.draw_y -= self.map.getIsoYToScreen(tx, ty)-(self.screen_height-200)
    }
}
