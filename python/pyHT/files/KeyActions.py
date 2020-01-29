#/*
# * To change this license header, choose License Headers in Project Properties.
# * To change this template file, choose Tools | Templates
# * and open the template in the editor.
# */
#package horrortactics

#import org.newdawn.slick.GameContainer
#import org.newdawn.slick.Input
#//import horrortactics.HorrorTactics
from files.techWrap import HtKeys
#/**
# *
# * @author tcooper
# */

class KeyActions():
    def __init__(self):
        self.kactions = HtKeys()
        self.k = self.kactions.keys
        #self.kactions.keys
    def on_key_press(self, ht, modifiers): #(self): 
        symbol = self.kactions.handler.on_key_press()
        if (symbol == self.k.ESCAPE):
            if(ht.game_state.equalsIgnoreCase("tactical") or ht.game_state.equalsIgnoreCase("game over")):
                ht.game_state = "title ingame"
            elif (ht.game_state.equalsIgnoreCase("title ingame")):
                ht.game_state = "tactical"
            elif(ht.game_state.equalsIgnoreCase("game over")):
                ht.game_state = "title ingame"    
        elif (symbol == self.k.KEY_UP):
            ht.draw_y = ht.draw_y+1
        elif (symbol == self.k.DOWN):
            ht.draw_y = ht.draw_y -1
        elif (symbol == self.k.LEFT):
            ht.draw_x = ht.draw_x +1
        elif (symbol == self.k.RIGHT):
            ht.draw_x = ht.draw_x+1
        elif (symbol == self.k.UP):
            ht.draw_y = ht.draw_y + 129
        elif (symbol == self.k.DOWN):
            ht.draw_y = ht.draw_y - 129
        elif (symbol == self.k.LEFT):
            ht.draw_x = ht.draw_x + 250
        elif (symbol == self.k.RIGHT):
            ht.draw_x = ht.draw_x - 250
        elif (symbol == self.k.C):
            ht.scale_x = ht.scale_x + 0.2
        elif (symbol == self.k.V):
            ht.scale_x = ht.scale_x - 0.2
        elif (symbol == self.k.SPACE):
            if(ht.getCurrentMap().turn_order.equals("player")):
                ht.getCurrentMap().turn_order = "start monster"
                ht.turn_count = ht.turn_count+1
                ht.getCurrentMap().setMonsterDirectives()
            elif(ht.getCurrentMap().turn_order.equals("monster")):
                ht.getCurrentMap().turn_order = "player"
                ht.getCurrentMap().player.action_points = 6 #//new action points.
                ht.turn_count = ht.turn_count + 1 # ++
                #//resetActorActionPoints()
                ht.getCurrentMap().player.resetActorActionPoints()
            #else:
            #    i = None
        #else:
             #i = None   

