#Horror TActics, python edition

#import pyglet
from files.techWrap import Color
from files.techWrap import Rectangle
from files.techWrap import HtImage as Image
from files.techWrap import HtSound



##*
# * Actor class is to encap the actor methods and data
# *
# * @author tcooper
# */


class Actor():
    def __init__(self, s, sx, sy): #Actor(String s, int sx, int sy) throws SlickException:
        self.spriteImage = Image(s + ".png")
        self.iconImage = Image(s + "_i.png")
        #sprites = new SpriteSheet(spriteImage, sx, sy)
        #new_sprite = pyglet.sprite.Sprite(img=resources.player_image, x=785-i*30, y=585, batch=batch)
        self.tilex = 0 ##our tile position in X
        self.tiley = 0 ##our tile position in Y
        self.tiledestx = 0 ##What tile are we traveling to when moving.
        self.tiledesty = 0 #
        self.directive_type = "beeline" # #random, beeline, randomuntilspotted
        self.draw_x = 0 # #Where the spite is drawn in X
        self.draw_y = 0 # #Where the sprite is drawn in y
        self.animate_frame = 0 #
        self.attack_range = 1 #
        self.dead = False #
        self.direction = 0 #
        self.animation_timer = 0 #
        self.visible = False # #actor is visible.
        ##hasturn = True #
        self.canparry = False #
        self.parryscore = -3 #
        self.action_points = 0 #
        self.max_action_points = 6 #
        self.move_action = False #
        self.facing_x = 0 #
        self.facing_y = 0 #
        ##speed = 0.05f #
        self.speed = 2 #
        self.speed_wait = 0
        self.action_msg_timer = 0
        self.action_msg = ""
        self.name = "none"
        self.turns_till_revival = 0
        self.max_turns_till_revival = 0
        self.attack_timer = 0
        self.storyline_died = 0
        ##no try catch!
        self.snd_footsteps = HtSound("data/soundeffects/steps_hallway.wav")
        self.snd_swing_miss = HtSound("data/soundeffects/swing_miss.wav")
        self.snd_swing_hit  = HtSound("data/soundeffects/punch_hit.wav")
        self.snd_washit = HtSound("data/soundeffects/guy_hit1.wav")
        self.snd_dodging = HtSound("data/soundeffects/guy_dodging1.wav")
        self.snd_died = HtSound("data/soundeffects/guy_die1.wav")
        self.weapon = "none" #"none" "knife" "cleaver" "sword" "pistol"
        self.health_points = 5
        self.health_points_max = 5
        self.fatigue_points = 5
        self.fatigue_points_max = 5
        self.mental_points = 5
        self.mental_points_max = 5
        self.stat_str = 1
        self.stat_speed = 1
        self.stat_will = 1
        self.stat_luck = 1
        self.exp_level = 0
        self.exp_points = 0
        self.newLevelUp = False
        self.expForGoal = False
        self.expForExitReached = False
        #self.player_knife_sprite = new SpriteSheet("data/player00_knife.png", 218, 313)
        #self.player_club_sprite = new SpriteSheet("data/player00_club.png", 218, 313)
        self.shadow = True
    #def changeActorSpriteSheetX
    def changeActorSpritesheet(self, s, sx, sy): #throws SlickException:
        self.spriteImage = Image(s + ".png")
        self.iconImage = Image(s + "_i.png")
        #sprites = new SpriteSheet(spriteImage, sx, sy)
    def getSpriteframe(self): #throws SlickException
        #TODO: how to pyglet spritesheets work?        
        i = 0 #sprites.getSubImage(animate_frame, direction)
        return i
    def getDeadSpriteframe(self):
        #Image i = sprites.getSubImage(3, direction)
        i = 0
        return i
    def setfacingloc(self, x, y):
        self.facing_x = x
        self.facing_y = y

    def setActiorDirection(self, d):
        #if(m.player.tilex > m.selected_tile_x)
        self.direction = d
        if(self.direction == 0):
            self.setfacingloc(1, 0)
        elif(self.direction == 1):
            self.setfacingloc(0, 1)
        elif(self.direction == 2):
            self.setfacingloc(0, -1)
        elif(self.direction == 3):
            self.setfacingloc(-1, 0)
        else:
            self.setfacingloc(-1, 0)

    def getAnimationFrame(self):
        return self.animate_frame

    def setAnimationFrame(self, f): # int f
        self.animate_frame = f

    def onWalkAnimation(self, fps): #called by MyTiledMap.onMoveDir
        timer_max = fps / 6
        if (self.animate_frame == 0 and self.getActorMoving() == True):
            self.animation_timer = self.animation_timer+1
            if (self.animation_timer >= timer_max):
                self.animate_frame = 1
                self.animation_timer = 0
        elif (self.animate_frame == 1 and self.getActorMoving() == True):
            self.animation_timer = self.animation_timer+1
            if (self.animation_timer >= timer_max):
                self.animate_frame = 2
                self.animation_timer = 0
        elif (self.animate_frame == 2 and self.getActorMoving() == True):
            self.animation_timer = self.animation_timer+1
            if (self.animation_timer >= timer_max):
                self.animate_frame = 1
                self.animation_timer = 0
        else:
            self.animation_timer = self.animation_timer+1
            if (self.animation_timer >= timer_max):
                self.animate_frame = 0
                self.animation_timer = 0
    def getIsoXToScreen(self, x, y):
        posX = (x - y) * 250
        return posX

    def getIsoYToScreen(self, x, y): ##int posX = ( x - y) * 250*/:
        #int posY = (x + y) * 129 / 2
        posY = (x + y) * 130 / 2
        return posY

    def isAtTileXY(self, x, y):
        if (self.tilex == x and self.tiley == y):
            return True
        return False

    def set_draw_xy(self, x, y):
        self.draw_x = x
        self.draw_y = y

    def get_draw_x(self):
        return self.draw_x

    def get_draw_y(self):
        return self.draw_y

    def onSelectActor(self, selection): #boolean/true/false
        self.selected = selection

    def onToggleSelection(self):
        if (self.selected == True):
            self.selected = False
        else:
            self.selected = True

    def isSelected(self):
        return self.selected

    #def getActionPoints(self):
    #    return self.action_points
    #}
    def setActionPoints(self, points):
        self.action_points = points

    def setActorDestination(self, x, y):
        self.tiledestx = x
        self.tiledesty = y

    def getActorDestX(self):
        return self.tiledestx

    def getActorDestY(self):
        return self.tiledesty

    def updateActorDirection(self):
        if (self.tilex == self.tiledestx and self.tiley == self.tiledesty):
            nothing = None
        elif (self.tilex < self.tiledestx):
            self.setActiorDirection(self.getEast())
        elif (self.tilex > self.tiledestx):
            self.setActiorDirection(self.getWest())
        elif (self.tiley > self.tiledesty):
            self.setActiorDirection(self.getNorth())
        elif (self.tiley < self.tiledesty):
            self.setActiorDirection(self.getSouth())

    def setActorMoving(self, ismoving):
        self.updateActorDirection()
        if (ismoving == False):
            self.set_draw_xy(0, 0)
            self.snd_footsteps.stop()
        self.move_action = ismoving
        #if (self.move_action == True):
        #    #self.updateActorDirection() #moved up

    def getActorMoving(self):
        if (self.action_points <= 0):
            self.move_action = False
        return self.move_action

    def drawHealthBar(self, agraphics, acolor, x, y, bar, barmax):
        health_bar = Rectangle(0, 0, 0, 0)
        health_bar_s = Rectangle(0, 0, 0, 0)
        #int ph = (int) (((double) bar / (double) barmax) * 100 / 2)
        ph = (bar / barmax) * 100 / 2
        if (self.ismouse_over == True):
            health_bar.setBounds(x + 30, y + 30 + 50 - ph, 10, ph)
            health_bar_s.setBounds(x + 29, y + 29, 12, 52)
            agraphics.setColor(Color.black)
            agraphics.fill(health_bar_s)
            #agraphics.setColor(c)
            agraphics.fill(health_bar)
            agraphics.setColor(Color.white)
            #g.drawString(self.health_points+"/"+self.health_points_max+"/"+ph, x+30,y+30)
    #def drawActor(HorrorTactics h, MyTiledMap m, int x, int y, agraphics):
    def drawActor(self, ht, x, y, g): #g = agraphics
        self.health_bar = Rectangle(0,0,0,0)
        self.health_bar_s = Rectangle(0,0,0,0)
        #int x = (int)(((double)a/(double)b) * 100)
        lg = Color(0, 0, 0, 0.8)
        textcolor = Color(1,1,1,1) #white
        #int ph = (int) (((double) self.health_points / (double) self.health_points_max) * 100 / 2)
        #ph = (self.health_points / self.health_points_max) * 100 / 2
        if (self.isAtTileXY(x, y) == True):
            pdx = ht.screen_x + ht.draw_x + self.draw_x
            pdy = ht.screen_y + ht.draw_y + self.draw_y - 230
            if (self.selected == True and ht.map.turn_order == "player"): #draw the selection if True
                try: #draw select box
                    ht.tiledmap.selected_green.draw(ht.screen_x + ht.draw_x, ht.screen_y + ht.draw_y)
                    ht.tiledmap.tiles250x129.getSubImage(0, 0, 250, 130).draw(ht.screen_x + ht.draw_x, ht.screen_y + ht.draw_y)
                except: #} catch (NullPointerException n):
                    print("caught and exception")
            if (self.dead == False): #draw actor
                if (self.ismouse_over == True):
                    self.drawHealthBar(g, Color.blue, pdx + 12, pdy + 12, self.mental_points, self.mental_points_max)
                    self.drawHealthBar(g, Color.green, pdx + 6, pdy + 6, self.fatigue_points, self.fatigue_points_max)
                    self.drawHealthBar(g, Color.red, pdx, pdy, self.health_points, self.health_points_max)
                if(self.shadow == True): # what if monster has no shadow!?!?!
                    g.setColor(lg)
                    g.fillOval(pdx + 50, pdy + self.sprites.getSprite(0, 0).getHeight() - 50, 100, 50)
                    g.setColor(Color.white)
                self.getSpriteframe().draw(pdx, pdy, h.scale_x)#draw actual actor
                if (self.name.equalsIgnoreCase("Riku") and self.weapon.equalsIgnoreCase("knife")):
                    self.getSpriteframe(self.player_knife_sprite).draw(pdx, pdy, h.scale_x)
                elif (self.name.equalsIgnoreCase("Riku") and self.weapon.equalsIgnoreCase("club")):
                    self.getSpriteframe(self.player_club_sprite).draw(pdx, pdy, h.scale_x)
                elif (self.name.equalsIgnoreCase("Riku") and self.weapon.equalsIgnoreCase("cleaver")):
                    self.getSpriteframe(self.player_cleaver_sprite).draw(pdx, pdy, h.scale_x)
                #elif (self.name.equalsIgnoreCase("Riku") and self.weapon.equalsIgnoreCase("sword")):
                    #draw sword
                #elif (self.name.equalsIgnoreCase("Riku") and self.weapon.equalsIgnoreCase("gun")):
                    #draw gun
            else: #draw actor dead
                self.getDeadSpriteframe().draw(pdx, pdy, h.scale_x)
            if (self.action_msg_timer > 0):
                if (self.action_msg.equalsIgnoreCase("miss")):
                    g.setColor(Color.white)
                    textcolor = Color.white
                else: #show damage
                    g.setColor(Color.red)
                    textcolor = Color.red

                h.ttf.drawString(pdx  + 50-2, pdy+2, self.action_msg, Color.black)
                h.ttf.drawString(pdx  + 50, pdy, self.action_msg, textcolor)              

    def drawPlayer(horrortactics, tiledmap, x, y, agraphics):
        self.drawActor(horrortactics, tiledmap, x, y, agraphics)
        #weapons are drawn in draw actor becaues of acess to pdx/pdy

    def setActorActionPoints(ap):
        self.action_points = ap
        if ( self.action_points <= 0):
            setActorMoving(False)

    def resetActorActionPoints(self):
        self.action_points = self.max_action_points

    def onAttack(horrortactics, target): #HorrorTactics ht#, Actor target*/):
        #self.tiledestx = ht.map.selected_tile_x  #monsters have to attack too, remove code.
        #self.tiledesty = ht.map.selected_tile_y
        self.updateActorDirection()
        #ht.map.onActorAttackActor(ht, ht.map.player,
        #        ht.map.getAllPlayersAtXy(ht.map.selected_tile_x, ht.map.selected_tile_y))
        self.tiledestx = self.tilex
        self.tiledesty = self.tiley
        #who is at x/y?
        m = horrortactics.tiledmap
        t = m.getAllPlayersAtXy(m.selected_tile_x, m.selected_tile_y)
        
        if (t == null):
            print("woa something is wrong monster target is null")
        else:
            print("t is not null, found " + t.name)
            #check range #note, what about ranged units? 
            #isActorTouchingActor is a temporary hack, because/we need to check range.
            #check action points
            if (self.action_points >= 3 and m.isActorTouchingActor(t, self.tilex, self.tiley) and self.dead == False):
                onActorAttackActor(horrortactics, t)
                #self.setAnimationFrame(4)
                #self.attack_timer = 25
            elif (self.action_points >= 3 and self.attack_range > 1 and self.dead == False):
                onActorAttackActor(horrortactics, t)

    def stopMoving(self):
        self.tiledestx = self.tilex
        self.tiledesty = self.tiley
        self.move_action = False
    def onMoveActor(tiledmap, fps):
        f = fps #gc.getFPS()
        if (self.getActorMoving() == True
                and self.dead == False
                #and self.spotted_enemy == True
                and tiledmap.getPassableTile(this, self.tilex + self.facing_x, self.tiley + self.facing_y) == True
                and tiledmap.getPlayerFacingMonster(self) == False
                and self.direction == getNorth()):
            self.onMoveNorth(tiledmap, f)
            if (snd_footsteps.playing() == False):
                snd_footsteps.loop()
                #footsteps.play()
        elif (self.getActorMoving() == True
                and self.dead == False
                and tiledmap.getPassableTile(this, self.tilex + self.facing_x, self.tiley + self.facing_y) == True
                and tiledmap.getPlayerFacingMonster(this) == False
                and self.direction == getSouth()):
            self.onMoveSouth(m, f)
            if (self.snd_footsteps.playing() == False):
                self.snd_footsteps.loop()
        elif (self.getActorMoving() == True
                and self.dead == False
                #and self.spotted_enemy == True
                and m.getPassableTile(this, self.tilex + self.facing_x, self.tiley + self.facing_y) == True
                and m.getPlayerFacingMonster(this) == False
                and self.direction == getEast()):
            self.onMoveEast(m, f)
            if (self.snd_footsteps.playing() == False):
                snd_footsteps.loop()
                #footsteps.play()
        elif (self.getActorMoving() == True
                and self.dead == False
                #and self.spotted_enemy == True
                and m.getPassableTile(this, self.tilex + self.facing_x, self.tiley + self.facing_y) == True
                and m.getPlayerFacingMonster(this) == False
                and self.direction == getWest()):
            self.onMoveWest(m, f)
            if (self.snd_footsteps.playing() == False):
                snd_footsteps.loop()
                #footsteps.play()
        if (tiledmap.getPassableTile(this, self.tilex + self.facing_x, self.tiley + self.facing_y) == False):
            self.setActorMoving(False) #That means you monsters!
            self.setAnimationFrame(0) #just be sure you are not still moving if you touch a wall

        if (self.tilex == self.tiledestx and self.tiley == self.tiledesty):
            #System.out.println("Arrived at destination")
            self.setActorMoving(False)
            self.setAnimationFrame(0)

        if (self.action_points <= 0):
            self.action_points = 0
            self.setActorMoving(False)
            self.setAnimationFrame(0)
        if (self.attack_timer > 0):
            self.setAnimationFrame(4) #just in case it got set to 0
        #try to check tilex/tiley for an event?
    def onUseActionPoints(m):
        if ( m.free_move == False ):
            self.action_points = self.action_points - 1
    def onMoveWest(m, delta):
        self.setActiorDirection(getWest())
        self.draw_x -= 2 * self.speed#(a.speed * delta) * 2 #a.speed#delta * a.speed
        self.draw_y -= 1 * self.speed#(a.speed * delta)
        self.set_draw_xy(self.draw_x, self.draw_y)
        self.onWalkAnimation(delta)
        if (Math.abs(self.draw_x) >= m.TILE_WIDTH_HALF):
            self.tilex = self.tilex - 1 #westr.
            m.active_trigger.name = "none" #you are in a new tile and all triggers are reset.
            self.set_draw_xy(0, 0)
            #self.action_points--
            self.onUseActionPoints(m)
            self.updateActorDirection()
    def onMoveEast(m, delta): #m=MyTiledMap
        self.setActiorDirection(getEast())
        self.draw_x += 2 * self.speed#(a.speed * delta) * 2 #a.speed#delta * a.speed
        self.draw_y += 1 * self.speed#(a.speed * delta)
        self.set_draw_xy(self.draw_x, self.draw_y)
        self.onWalkAnimation(delta)
        if (self.draw_x >= m.TILE_WIDTH_HALF):
            self.tilex+=1 #westr.
            m.active_trigger.name = "none" #you are in a new tile and all triggers are reset.
            self.set_draw_xy(0, 0)
            #a.setAnimationFrame(0)
            #self.action_points--
            self.onUseActionPoints(m)
            #self.tiledestx = self.tilex
            self.updateActorDirection()
    def onMoveSouth(m, delta):
        self.setActiorDirection(getSouth()) #south/1
        self.draw_y += 1 * self.speed#(a.speed * delta) #a.speed
        self.draw_x -= 2 * self.speed#(a.speed * delta) * 2
        self.set_draw_xy(self.draw_x, self.draw_y)
        self.onWalkAnimation(delta)
        if (self.draw_y >= Math.abs(m.TILE_HEIGHT_HALF)):
            self.tiley+=1 #south.
            m.active_trigger.name = "none" #you are in a new tile and all triggers are reset.
            self.set_draw_xy(0, 0)
            #a.setAnimationFrame(0)
            #self.action_points--
            self.onUseActionPoints(m)
            #self.tiledesty = self.tiley
            self.updateActorDirection()
    def onMoveNorth(m, delta):
        self.setActiorDirection(getNorth())#north
        self.draw_y -= 1 * self.speed#(a.speed * delta)#a.speed
        self.draw_x += 2 * self.speed#(a.speed * delta) * 2#a.speed*2
        #System.out.println("delta: " + delta + "," + a.draw_x + "," + a.draw_y)
        self.set_draw_xy(self.draw_x, self.draw_y)
        self.onWalkAnimation(delta)
        if (Math.abs(self.draw_y) >= m.TILE_HEIGHT_HALF):
            self.tiley -=1 #north.
            m.active_trigger.name = "none" #you are in a new tile and all triggers are reset.
            self.set_draw_xy(0, 0)
            #self.action_points--
            self.onUseActionPoints(m)
            #self.tiledesty = self.tiley
            self.updateActorDirection()
    def getNorth(self):
        return 2
    def getSouth(self):
        return 1
    def getEast(self):
        return 0
    def getWest(self):
        return 3
    def drawPopupWindow(ht, g): #HorrorTactics ht, Graphics g
        w = 400
        h = 400
        x = ht.screen_width / 2 - w / 2
        y = ht.screen_height / 2 - h / 2
        LevelUpControls = ""
        c = HtColor(10, 10, 10, 245)
        r = Rectangle(0, 0, 0, 0)
        s = Rectangle(0, 0, 0, 0)
        r.setBounds(x, y, w, h)
        s.setBounds(x - 1, y - 1, w + 2, h + 2)
        if (ht.popup_window.equalsIgnoreCase("profile")):
            if (self.newLevelUp == True):
                LevelUpControls = "[+] "
            else:
                LevelUpControls = ""
            #draw it
            #Note is level up == True?
            g.setColor(Color.white)
            g.fill(s)
            g.setColor(c)
            g.fill(r)
            sprites.getSubImage(0, 0).draw(x - 30, y + 50)
            g.setColor(Color.white)
            g.drawString(self.name, x + 200, y + 20)
            g.drawString("Level: " + self.exp_level, x + 200, y + 40)
            g.drawString("Health: " + self.health_points + "/" + self.health_points_max, x + 200, y + 60)
            g.drawString("Fatigue: " + self.fatigue_points + "/" + self.fatigue_points_max, x + 200, y + 80)
            g.drawString("Stress: " + self.mental_points + "/" + self.mental_points_max, x + 200, y + 100)
            if (self.newLevelUp == True): #green means you can click on a [+] #no mouse contorls yet.
                g.setColor(Color.green)
            #int x = ht.screen_width / 2 - w / 2
            #int y = ht.screen_height / 2 - h / 2
            g.drawString(LevelUpControls + "Strength: " + self.stat_str, x + 200, y + 120)
            g.drawString(LevelUpControls + "Speed: " + self.stat_speed, x + 200, y + 140)
            g.drawString(LevelUpControls + "Willpower: " + self.stat_will, x + 200, y + 160)
            g.drawString(LevelUpControls + "Luck: " + self.stat_luck, x + 200, y + 180)
            g.drawString(LevelUpControls + "Exp: " + self.exp_points, x + 200, y + 200)
            
            
            g.setColor(Color.black)
            g.drawString("Objective: " +ht.map.objective, r.getX()-1, r.getY()+r.getHeight()+32+1)
            g.setColor(Color.white)
            g.drawString("Objective: " +ht.map.objective, r.getX(), r.getY()+r.getHeight()+32)
            
            g.setColor(Color.black)
            g.drawString("Hint: " +ht.map.hint, r.getX()-1, r.getY()+r.getHeight()+64+1)
            g.setColor(Color.white)
            g.drawString("Hint: " +ht.map.hint, r.getX(), r.getY()+r.getHeight()+64)
        elif (ht.popup_window.equalsIgnoreCase("items")): #draw it            
            g.setColor(c)
            g.fill(r)
            sprites.getSubImage(0, 0).draw(x - 30, y + 50)
            g.setColor(Color.white)
            g.drawString(self.name + "'s Items", x + 200, y + 20)            
            g.setColor(Color.black)
            g.drawString("Objective: " +ht.map.objective, r.getX()-1, r.getY()+r.getHeight()+32+1)
            g.setColor(Color.white)
            g.drawString("Objective: " +ht.map.objective, r.getX(), r.getY()+r.getHeight()+32)            
            g.setColor(Color.black)
            g.drawString("Hint: " +ht.map.hint, r.getX()-1, r.getY()+r.getHeight()+64+1)
            g.setColor(Color.white)
            g.drawString("Hint: " +ht.map.hint, r.getX(), r.getY()+r.getHeight()+64)
            if (ht.map.RequiresGoal.equalsIgnoreCase("yes")
                    and ht.map.EventGoal_ran == True):
                #and x == self.map.draw_goal_x
                #and y == self.map.draw_goal_y):
                #bug? better have an image*/
                #int pdx = screen_x + draw_x
                # + self.draw_x*/
                #int pdy = screen_y + draw_y
                # + self.draw_y - 230*/
                ht.map.mission_goal.draw(x+200, y+200)
        else:
            #do nothing.
            donothing=1
    def onLevelUp(self):
        #int exp_level, exp_points #level up = exp_level+1 * exp_level+1*10
        if ((self.exp_points >= (self.exp_level + 1) * (exp_level + 1) * 10) and self.newLevelUp == False): #there was a level up.
            self.newLevelUp = True
            self.exp_level+=1
            #wait for point distrib before setting to False (not implemented)
    def copyActorStats(a):
        a.name = self.name
        a.exp_level = self.exp_level
        a.exp_points = self.exp_points
        a.stat_luck = self.stat_luck
        a.stat_speed = self.stat_speed
        a.stat_str = self.stat_str
        a.stat_will = self.stat_will
        a.health_points_max = self.health_points_max
        a.health_points = self.health_points

    def swapSoundEffects(footsteps, miss, hit, washit, dodge, died):
        #snd_footsteps = HtSound("data/soundeffects/steps_hallway.ogg")
        snd_swing_miss = HtSound("data/soundeffects/swing_miss.ogg")
        snd_swing_hit = HtSound("data/soundeffects/punch_hit.ogg")
        snd_washit = HtSound("data/soundeffects/guy_hit1.ogg")
        snd_dodging = HtSound("data/soundeffects/guy_dodging1.ogg")
        snd_died = HtSound("data/soundeffects/guy_die1.ogg")
        mpath = "data/soundeffects/"
        if (footsteps.isEmpty() == False):
           try:
               self.snd_footsteps = HtSound(mpath + footsteps)
           except:
               print("no sound for footsteps")
        if (miss.isEmpty() == False):
            try:
                self.snd_swing_miss = HtSound(mpath + miss)
            except:
                print("no sound for swing/miss")
        if (hit.isEmpty() == False):
            try:
                self.snd_swing_hit = HtSound(mpath + hit)
            except:
                print("no sound for swing/hit")
        if (washit.isEmpty() == False):
            try:
                self.snd_washit = HtSound(mpath + washit)
            except:
                print("no sound for was hit")
        if (dodge.isEmpty() == False):
            try:
                self.snd_dodging = HtSound(mpath + dodge)
            except:
                print("no sound for dodging")
        if (died.isEmpty() == False):
            try:
                self.snd_died = HtSound(mpath + died)
            except:
                print("no sound for died")

    def getAttackPenalty(self): #check scores and get a hit penalty
        if (self.mental_points < 3):
            return -1
        elif (self.mental_points <= 1):
            return -2
        elif (self.mental_points <= 0):
            return -3 #you are totally wasted
        return 0

    def getMovePenalty(self): #check scores and get a move penalty
        if (self.fatigue_points <= 3):
            return -1
        elif (self.fatigue_points <= 2):
            return -2
        elif (self.fatigue_points <= 0):
            return -3
        return 0

    def getDodgePenalty(self): #check scores and get a dodge penalty
        if (self.fatigue_points <= 3):
            return -1
        elif (self.fatigue_points <= 2):
            return -2
        elif (self.fatigue_points <= 0):
            return -3
        return 0

    def getDodgeBonus(self): #check scores and get a dodge bonus
        if (self.stat_speed > 1):
            return self.stat_speed - 2
        else:
            return 0

    def onActorAttackActor(self, ht, defender):
        #int target_parryroll
        actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + \
            self.stat_luck - 1 + self.getAttackPenalty()
        target_dodgeroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + \
            defender.stat_luck - 1 + defender.getDodgePenalty() + \
            defender.getDodgeBonus() #speed
        damage_roll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + \
            self.stat_str - 1
        #int target_saveroll = ThreadLocalRandom.current().nextInt(1, 6 + 1)
        print("target check", defender.name)
        self.setAnimationFrame(4)
        self.attack_timer = 25
        if (defender.canparry):
            target_parryroll = ThreadLocalRandom.current().nextInt(1, 6 + 1)
        else:
            target_parryroll = 0
        if (actor_attackroll > target_dodgeroll and \
                actor_attackroll > target_parryroll + defender.parryscore):
            defender.health_points -= damage_roll #hit
            if (defender.health_points <= 0):
                defender.dead = True
                self.action_msg = " " + damage_roll + " "
                self.action_msg_timer = 200
                defender.turns_till_revival = 0 #do we revive?
                ht.map.log_msg = self.name + " attacks " + defender.name +\
                    "(1d6 =" + actor_attackroll + ")" + ",(1d6 =" +\
                    target_dodgeroll + ") and hits for " + damage_roll +\
                    " points of damage, killing " + defender.name
                defender.snd_died.play()
                self.exp_points += 3
            else:
                ht.map.log_msg = self.name + " attacks " + defender.name +\
                    "(1d6 =" + actor_attackroll + ")" + ",(1d6 =" +\
                    target_dodgeroll + ") and hits for " + damage_roll +\
                    " points of damage."
                defender.snd_washit.play()
            self.snd_swing_hit.play()
            #defender.snd_washit.play()
        else:
            self.action_msg = "Miss"
            self.action_msg_timer = 200
            if (target_parryroll + defender.parryscore > actor_attackroll):
                ht.map.log_msg =self.name + " attacks " + defender.name + \
                    "(1d6 =" +actor_attackroll +",(1d6 =" + target_parryroll + \
                    " + " + defender.parryscore + ") but the attack was parried."
            else:
                ht.map.log_msg = self.name + " attacks " + defender.name +\
                    "(1d6 =" + actor_attackroll + ",(1d6 =" + target_dodgeroll + \
                    ") and misses"
            self.snd_swing_miss.play()
            defender.snd_dodging.play()
        #we already checke for action points, not remove them
        self.action_points -= 3
        self.fatigue_points -= 1
        if (self.fatigue_points < 0):
            self.fatigue_points = 0
        defender.fatigue_points -= 1
        if (defender.fatigue_points < 0):
            defender.fatigue_points = 0
        if (self.action_points < 0):
            self.action_points = 0
