##
#* To change self license header, choose License Headers in Project Properties.
# * To change self template file, choose Tools | Templates
# * and open the template in the editor.
# */

from files.techWrap import Rectangle

##*
# *
# * @author tcooper
# */
class MouseActions: # {
    def __init__(self):
        self.mouse_x = 0 
        self.mouse_y = 0
        self.mouse_tile_x = 0
        self.mouse_tile_y = 0
    def getMSelectedActor(self, ht):
        foundselected = False
        for i in range(0, ht.tiledmap.follower_max): #(int i= 0 i < ht.tiledmap.follower_max i+=1):
            if(ht.tiledmap.follower[i].selected == True):
                foundselected = True
                #ht.tiledmap.follower[i].drawPopupWindow(self, g)
                return ht.tiledmap.follower[i]
        if(foundselected == False):
            #self.tiledmap.player.drawPopupWindow(self, g) #who is currently selected?
            return ht.tiledmap.player   
        return ht.tiledmap.player #default is player
    def  levelupButtonWasPressed(self, ht):
        w = 400 #same as whats drawn on the screen.
        h = 400
        x = ht.screen_width / 2 - w / 2
        y = ht.screen_height / 2 - h / 2
        # ON LEVEL UP, click here to increase stat        
        #g.drawString(LevelUpControls+" Strength: " + self.stat_str, x + 200, y + 120)
        #g.drawString(LevelUpControls+" Speed: " + self.stat_speed, x + 200, y + 140)
        #g.drawString(LevelUpControls+" Willpower: " + self.stat_will, x + 200, y + 160)
        #g.drawString(LevelUpControls+" Luck: " + self.stat_luck, x + 200, y + 180)
        #first, determine if it was pressed.
        xi = x+200
        yi = y+120
        rect_str = Rectangle(xi, yi, 200, 20)
        if (ht.popup_window.equalsIgnoreCase("profile")):
            if(rect_str.contains(self.mouse_x, self.mouse_y)):
            #if(self.mouse_x >= xi and self.mouse_x <= xi +200
            #        and self.mouse_y >=yi and self.mouse_y <= yi+20):
                if(self.getMSelectedActor(ht).newLevelUp):#True
                    self.getMSelectedActor(ht).stat_str+=1
                    self.getMSelectedActor(ht).health_points_max += 2
                    self.getMSelectedActor(ht).newLevelUp = False #you clicked and now its done.
                
            
            elif(self.mouse_x >= xi and self.mouse_x <= xi +200
                    and self.mouse_y >=yi+20 and self.mouse_y <= yi+20*2):
                #increase Speed
                if(self.getMSelectedActor(ht).newLevelUp):#True
                    self.getMSelectedActor(ht).stat_speed+=1 #TODO! we need to add action point bonus for speed.
                    self.getMSelectedActor(ht).health_points_max += 1
                    self.getMSelectedActor(ht).newLevelUp = False #you clicked and now its done.
                
            
            elif(self.mouse_x >= xi and self.mouse_x <= xi +200
                    and self.mouse_y >=yi+20*2 and self.mouse_y <= yi+20*3):
                #increase Willpower
                if(self.getMSelectedActor(ht).newLevelUp):#True
                    self.getMSelectedActor(ht).stat_will+=1
                    self.getMSelectedActor(ht).health_points_max += 1
                    self.getMSelectedActor(ht).fatigue_points_max += 1
                    self.getMSelectedActor(ht).newLevelUp = False #you clicked and now its done.
                
            
            elif(self.mouse_x >= xi and self.mouse_x <= xi +200
                    and self.mouse_y >=yi+20*3 and self.mouse_y <= yi+20*4):
                #increase Luck
                if(self.getMSelectedActor(ht).newLevelUp):#True
                    self.getMSelectedActor(ht).stat_luck+=1 #what is luck? on dice rolls add, +stat_luck -1
                    self.getMSelectedActor(ht).health_points_max += 1
                    self.getMSelectedActor(ht).newLevelUp = False #you clicked and now its done.

    def endTurnButtonWasOver(self, ht): #, Mytiledmap tiledmap):
        bx = ht.screen_width- 200
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10): #press end turn button.
            return True
        else:
            return False
    def endTurnButtonWasPressed(self, ht): #, Mytiledmap tiledmap):
        bx = ht.screen_width- 200
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10): #press end turn button.
            if (ht.tiledmap.getAnyActorMoving() == False):
                return True
            else:
                print("End turn was pressed, but someone is still moving?\n")
                return False
        return False
    
    def menuButtonWasOver(self, ht): #, Mytiledmap tiledmap):
        bx = ht.screen_width- 100
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10): #press end turn button.
            return True
        else:
            return False
    def menuButtonWasPressed(self, ht): #, Mytiledmap tiledmap):
        bx = ht.screen_width- 100
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10): #press end turn button.
            print("menu button was pressed.")
            if (ht.tiledmap.getAnyActorMoving() == False or ht.game_state.equalsIgnoreCase("game over")):
                #print("menu was pressed, and it worked.") #we know self is happening on gameover
                return True
            else:
                print("menu was pressed, but someone is still moving?")
                return False   
        return False
    
    def profileButtonWasOver(self, ht):
        bx = ht.screen_width- 300
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10):
            return True
        else:
            return False
         
    def profileButtonWasPressed(self, ht):
        bx = ht.screen_width- 300
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10):
            if (ht.popup_window.equalsIgnoreCase("none") ):
                ht.popup_window = "profile"            
            elif (ht.popup_window.equalsIgnoreCase("profile") ):
                ht.popup_window = "none"          
            else:
                ht.popup_window = "profile"          
            return True      
        return False   
    def itemsButtonWasOver(self, ht):
        bx = ht.screen_width- 400
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10):
            return True 
        else:
            return False
    def itemsButtonWasPressed(self, ht):
        bx = ht.screen_width- 400
        bw = 92
        if (self.mouse_x >= bx and self.mouse_y >= ht.screen_height - 64 - 10
                and self.mouse_x <= bx + bw and self.mouse_y <= ht.screen_height - 10):
            if (ht.popup_window.equalsIgnoreCase("none") ):
                ht.popup_window = "items"          
            elif (ht.popup_window.equalsIgnoreCase("items") ):
                ht.popup_window = "none"          
            else:
                ht.popup_window = "items"          
            return True        
        return False  
    def mouseWasClicked(self, iinput, tiledmap, ht):     
        mouse_x = iinput.getMouseX()
        mouse_y = iinput.getMouseY()
        tiledmap.selected_tile_x = tiledmap.mouse_over_tile_x
        tiledmap.selected_tile_y = tiledmap.mouse_over_tile_y
        scrollspeed = 3
        #ht.titlemenu.onMouseOver(ht, ht.g, mouse_x, mouse_y)        
        if (input.isMousePressed(0) == True):
            ht.titlemenu.onMouseClick(ht, mouse_x, mouse_y)
            #now we need to, stop eveything below.
            if (tiledmap.turn_order.equalsIgnoreCase("planning")):
                if (tiledmap.planevent == tiledmap.maxplanevent or tiledmap.planning[tiledmap.planevent].equalsIgnoreCase("end")):
                    tiledmap.turn_order = "start player"
                else:
                    tiledmap.planevent+=1
            elif(tiledmap.turn_order.equalsIgnoreCase("title") and ht.game_state.equalsIgnoreCase("tactical")):
                tiledmap.turn_order = "planning"            
            elif (ht.popup_window.equalsIgnoreCase("profile")):
                self.levelupButtonWasPressed(ht) #simple check
                self.profileButtonWasPressed(ht)            
            #elif (self.profileButtonWasPressed(ht)):
            #elif (self.itemsButtonWasPressed(ht)):
            elif (self.menuButtonWasPressed(ht)):
                ht.game_state = "title ingame"
            elif (self.endTurnButtonWasPressed(ht) == True): #(mouse_x >= 10 and mouse_y >= ht.screen_height - 64 - 10
                print("End turn was pressed\n")
                tiledmap.turn_order = "start monster" #End Turn.
            elif (tiledmap.turn_order.equalsIgnoreCase("event spotted")):
                tiledmap.turn_order = tiledmap.old_turn_order #return to previous turn after click
            elif (tiledmap.turn_order.equalsIgnoreCase("goal reached")):
                tiledmap.turn_order = tiledmap.old_turn_order #return to previous turn after click
            elif (tiledmap.turn_order.equalsIgnoreCase("exit reached")):
                tiledmap.turn_order = "change tiledmap"
            elif (self.playerWasSelected(tiledmap) == True):
                self.onPlayerSelection(tiledmap, tiledmap.player) #select or unselect actor
            elif (self.followerWasSelected(tiledmap) == True):
                tiledmap.player.selected = False #just in case
            elif (tiledmap.turn_order.equalsIgnoreCase("player")
                    and self.followerIsSelected(tiledmap)
                    and self.endTurnButtonWasPressed(ht) == False):
                if (self.tiledmap.getAllPlayersAtXy(tiledmap.selected_tile_x, tiledmap.selected_tile_y) is not None): #prepare to attack
                    #tiledmap.onActorCanAttack(ht, tiledmap.player)
                    #BUG: if (self.isActorTouchingActor(a, self.player, a.tilex, a.tiley)):
                    if (tiledmap.follower[tiledmap.selected_follower].attack_range > 1):
                        #make sure there is an enemey here.
                        #you have the range sir!
                        tiledmap.follower[tiledmap.selected_follower].onAttack(ht) # Attack code here.        
                    elif (tiledmap.isMonsterTouchingYou(tiledmap.getAllPlayersAtXy(tiledmap.selected_tile_x, tiledmap.selected_tile_y))):
                        tiledmap.follower[tiledmap.selected_follower].onAttack(ht) # Attack code here.                   
                else:
                    tiledmap.selected_tile_x = tiledmap.mouse_over_tile_x
                    tiledmap.selected_tile_y = tiledmap.mouse_over_tile_y
                    tiledmap.follower[tiledmap.selected_follower].tiledestx = tiledmap.selected_tile_x
                    tiledmap.follower[tiledmap.selected_follower].tiledesty = tiledmap.selected_tile_y
                    tiledmap.current_follower_moving = tiledmap.selected_follower
                    tiledmap.follower[tiledmap.selected_follower].setActorMoving(True)     
            elif (tiledmap.turn_order.equalsIgnoreCase("player")
                    and self.endTurnButtonWasPressed(ht) == False \
                    and tiledmap.player.isSelected()): #added limits so you cant set location when a monster is moving
                if (self.getClickedOnPlayerAction(ht, tiledmap) == True):
                    tiledmap.selected_tile_x = tiledmap.mouse_over_tile_x
                    tiledmap.selected_tile_y = tiledmap.mouse_over_tile_y
                    if (tiledmap.getAllPlayersAtXy(tiledmap.selected_tile_x, tiledmap.selected_tile_y) is not None): #prepare to attack
                        #tiledmap.onActorCanAttack(ht, tiledmap.player)
                        tiledmap.player.tiledestx = tiledmap.selected_tile_x
                        tiledmap.player.tiledesty = tiledmap.selected_tile_y
                        if (tiledmap.isMonsterTouchingYou(tiledmap.getAllPlayersAtXy(tiledmap.player.tiledestx, tiledmap.player.tiledesty))):
                            #print("setting player animation frame.")
                            tiledmap.player.onAttack(ht)
                        
                    else:
                        tiledmap.player.tiledestx = tiledmap.selected_tile_x
                        tiledmap.player.tiledesty = tiledmap.selected_tile_y
                        tiledmap.player.setActorMoving(True)   
        if (input.isMouseButtonDown(1) and ht.tiledmap.turn_order.equalsIgnoreCase("player")
                and self.endTurnButtonWasPressed(ht) == False):
            if (self.mouse_x > ht.last_mouse_x):
                ht.draw_x += scrollspeed
            elif (self.mouse_x < ht.last_mouse_x):
                ht.draw_x -= scrollspeed
            if (self.mouse_y > ht.last_mouse_y):
                ht.draw_y += scrollspeed
            elif (self.mouse_y < ht.last_mouse_y):
                ht.draw_y -= scrollspeed
        ht.last_mouse_x = self.mouse_x
        ht.last_mouse_y = self.mouse_y
    def getClickedOnPlayerAction(self, ht, m):
        #check if the tiles have walls.*/
        #if (tiledmap.getTileImage(x, y, walls_layer) != null):*/
        if (m.mouse_over_tile_x > 0 and m.mouse_over_tile_y > 0):
            #and m.getPassableTile(m.player,m.mouse_over_tile_x, m.mouse_over_tile_y) == True):
            return True   
        return False
    def followerIsSelected(self, tiledmap):
        for i in range(0, tiledmap.follower_max): #(int i = 0 i < tiledmap.follower_max i+=1):
            if (tiledmap.follower[i].selected == True):
                tiledmap.selected_follower = i
                print("follower " + i + " is selected")
                return True
        #print("returned False? how? Follower 0 selected" + tiledmap.follower[0].selected)
        return False
    # check the mouse click to see if a Player was clicked */
    def followerWasSelected(self, tiledmap):
        for i in range(0, tiledmap.follower_max): #(int i = 0 i < tiledmap.follower_max i+=1):
            if (tiledmap.follower[i].tilex == tiledmap.mouse_over_tile_x
                    and tiledmap.follower[i].tiley == tiledmap.mouse_over_tile_y):
                tiledmap.selected_follower = i
                tiledmap.follower[i].selected = True
                #i = selected_follower
                tiledmap.player.selected = False #unselect everyone
                for s in range(0, tiledmap.follower_max): # (int s = 0 s < tiledmap.follower_max s+=1):
                    if (s != i):
                        tiledmap.follower[s].selected = False
                print("follower (" + tiledmap.selected_follower + ") was selected")
                return True
        return False
    def playerWasSelected(self, tiledmap):
        if (tiledmap.player.tilex == tiledmap.mouse_over_tile_x
                and tiledmap.player.tiley == tiledmap.mouse_over_tile_y):
            return True
        elif (self.mouse_x >= tiledmap.player.get_draw_x() # The player */
                and self.mouse_x <= (tiledmap.player.get_draw_x() + 300)
                and self.mouse_y >= tiledmap.player.get_draw_y()
                and self.mouse_y <= (tiledmap.player.get_draw_y() + 300)):
            return True  # A player was clicked */
        else:
            return False
    def onSetTileSelection(self, h, m, tx, ty, px, py):
        if (self.mouse_x >= px and self.mouse_x <= px + m.TILE_WIDTH_HALF * 2
                and self.mouse_y >= py and self.mouse_y <= py + m.TILE_HEIGHT_HALF * 2):
            m.selected_tile_x = tx
            m.selected_tile_y = ty
            return True
        else:
            return False
    def onPlayerSelection(self, tiledmap, a):
        #reset everyone.
        #boolean toselect
        if (a.selected == True):
            toselect = False
        else:
            toselect = True
        tiledmap.player.onSelectActor(False)
        for i in range(0, tiledmap.follower_max): #(int i = 0 i < tiledmap.follower_max i+=1):
            tiledmap.follower[i].selected = False      
        a.selected = toselect
    def setMouseXY(self, x, y):
        self.mouse_x = x
        self.mouse_y = y