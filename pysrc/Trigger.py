#/*
# * To change self license header, choose License Headers in Project Properties.
# * To change self template file, choose Tools | Templates
# * and open the template in the editor.
# */
#package horrortactics

#import org.newdawn.slick.SlickException
#import org.newdawn.slick.Image
from files.techWrap import HtImage as Image

#/**
# *
# * @author tcooper Right now we need to activate something when the player steps
# * on a tile. when map.player (moving) check the actors layer for not null if
# * its not null, get the custom properties (all out of trigger)
# */
class Trigger: #
    #tilex
    #tiley #the tile we are at (aprox)
    #ttype, name, action

    def __init__(self, trigger_type, trigger_name): # throws SlickException 
        self.ttype = trigger_type
        self.name = trigger_name
        self.action = "none" #prompt/dialogue/play sound/visible player
        self.tilex = 1
        self.tiley = 1
    
    def updateTrigger(self, trigger_type, trigger_name):
        self.ttype = trigger_type
        self.name = trigger_name
    

    def onSetXY(self, x, y):
        self.tilex = x
        self.tiley = y    

    def onSteppedOnTrigger(self, m, x, y):
        actors_layer = m.getLayerIndex("actors_layer")
        gid = m.getTileId(x, y, actors_layer)
        #long list of triggers!
        if (m.getTileProperty(gid, "audio_trigger", "none") == "trapped_girl"):
            m.active_trigger.updateTrigger("audio_trigger", "trapped girl")
        elif(m.getTileProperty(gid, "activate_trigger", "none") == "release yukari"):
            m.active_trigger.updateTrigger("activate_trigger", "release yukari")
        elif(m.getTileProperty(gid, "event_goal", "none") == "none"): #! not equals?
            #you stepped on teh event goal, run charbust?
            m.active_trigger.updateTrigger("event_goal",m.getTileProperty(gid, "event_goal", "none") )
            if (m.active_trigger.name == "none"): #still blank. !noteuqals?
                #System.out.println("reached event goal")
                #assuming self exists?
                if(m.EventGoal_ran == False):
                    m.EventGoal_p = Image("data/" + m.getTileProperty(gid,"event_goal_p", "prt_player_00.png"))
                    m.EventGoal_m = m.getTileProperty(gid,"event_goal_m", "end")
                    m.EventGoal_ran = True
                    m.old_turn_order = m.turn_order
                    m.turn_order = "goal reached"
        elif(m.getTileProperty(gid, "event_exit", "end") == "end"):
            #you stepped on teh event goal, run charbust?
            m.active_trigger.updateTrigger("event_exit",m.getTileProperty(gid, "event_exit", "end") )
            if (m.active_trigger.name == "exit"):  #still blank.
                #System.out.println("reached event goal")
                #assuming self exists?
                if(m.EventExit_ran == False):
                    #event goal equals none, or (event goal != none and ran = True)                    
                    if(m.RequiresGoal.equalsIgnoreCase("yes") and m.EventGoal_ran == True ):
                        m.EventExit_p = Image("data/" + m.getTileProperty(gid,"event_goal_p", "prt_player_00.png"))
                        m.EventExit_m = m.getTileProperty(gid,"event_goal_m", "end")
                        m.EventExit_ran = True
                        m.old_turn_order = m.turn_order
                        #System.out.println("if(m.RequiresGoal.equalsIgnoreCase(\"yes\") and m")
                        m.turn_order = "exit reached"
                    elif(m.RequiresGoal.equalsIgnoreCase("no")): #EventGoal.equalsIgnoreCase("none") #requires goal?
                        m.EventExit_p = Image("data/" + m.getTileProperty(gid,"event_goal_p", "prt_player_00.png"))
                        m.EventExit_m = m.getTileProperty(gid,"event_exit_m", "none")
                        m.EventExit_ran = True
                        m.old_turn_order = m.turn_order
                        #System.out.println("m.RequiresGoal.equalsIgnoreCase(\"no\")")
                        m.turn_order = "exit reached"
        else:
            m.trigger_check = "none"
            m.active_trigger.name = "none"        
        m.trigger_check = m.active_trigger.name
        m.active_trigger.runTrigger(m)
    
    def runTrigger(self, m):
        if (self.name.equals("trapped_girl")): 
            self.onSetSoundToBePlayed("trappedgirl.ogg")
        elif(self.name.equals("release yukari")):
            m.trigger_check = m.active_trigger.name
            for i in range(0, m.follower_max): #(int i = 0 i < m.follower_max i++):
                if (m.follower[i].tilex == 9 and m.follower[i].tiley == 2):
                    if (m.follower[i].visible == False):
                        m.follower[i].visible = True

    #def onActivateTrigger(int x, int y, )
    def onSetSoundToBePlayed(s):
        print("do nothing onSetSoundToBePlayed")

    


