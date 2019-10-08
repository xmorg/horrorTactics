#/*
# * To change this license header, choose License Headers in Project Properties.
# * To change this template file, choose Tools | Templates
# * and open the template in the editor.
# * all this stuff got moved out of "MyTiledMap"
# */
#package horrortactics

#import org.newdawn.slick.Image
#import org.newdawn.slick.SlickException

from files.techWrap import Image
from files.Actor import Actor
from files.npcs.Yukari import Yukari
from files.npcs.Miyu import Miyu

#/**
# *
# * @author Tim Cooper - ActorMap is the database of who actors are from tiled.
# */
class ActorMap: # {
    def __init__(self):     ##publicActorMap
        tiles = None
    def getActorLocationFromTMX(m): #MyTiledMap
        ##We do not get stats, only locations.
        ##note, how do we carry followers from map to map? (or will we ever?)
        monster_loop = 0
        follower_loop = 0
        actor_layer = m.getLayerIndex("actors_layer")
        for i in range(0, m.monster_max): #(int i = 0 i < m.monster_max i++):
            try:
                m.monster[i] = Actor("data/monster00", 218, 313)
                m.monster[i].visible = False #default
            except:
                print("exception: cant make a monster")
        for i in range(0, m.follower_max): #(int i = 0 i < m.follower_max i++):
            try:
                m.follower[i] = Actor("data/girl01", 218, 313)
                m.follower[i].visible = False
                m.follower[i].direction = m.follower[i].getEast()
            except: # except:
                print("cannot create a follower")
        for y in range(0, m.getHeight()): #(int y = 0 y < m.getHeight() y++):
            for x in range(0, m.getWidth()): #(int x = 0 x < m.getWidth() x++):
                gid = m.getTileId(x, y, actor_layer)
                if (gid > 0):
                    #System.out.println(this.getTileId(x, y, actor_layer))
                    pname = m.getTileProperty(gid, "actor_name", "none")
                    weapon = m.getTileProperty(gid, "weapon", "none")
                    actor_spotted = m.getTileProperty(gid, "actor_spotted", "true")
                    mission_goal = m.getTileProperty(gid, "event_goal", "no")
                    if (mission_goal.equalsIgnoreCase("yes")):
                        m.mission_goal = Image("data/" + m.getTileProperty(gid, "event_goal_graphic", "papers.png"))
                        #set the X/y
                        m.draw_goal_x = x
                        m.draw_goal_y = y
                    
                    if (pname.equals("player")):
                        m.player.tilex = x
                        m.player.tiley = y
                        m.player.name = "Riku" #pname
                        #m.player.weapon = "knife" #equip a weapon
                        m.player.changeActorSpritesheet("data/" + m.getTileProperty(gid, "costume", "player00"), 218, 313)
                        # elif (this.getTileProperty(gid, "actor_name", "none").equals("pear monster")):
                        #void swapSoundEffects(String footsteps, String miss, String hit, String washit, String dodge, String died):
                        m.player.swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg")
                    elif (pname.equals("Yukari")): #little nerd girl
                        try:
                            m.follower[follower_loop] = Yukari(x, y)
                            m.follower[follower_loop].set_base_stats()
                            follower_loop +=1
                        except:
                            print("cannot change sprite, yukari")
                    elif (pname.equals("Miyu")): #kendo girl
                        try:
                            m.follower[follower_loop] = Miyu(x, y)
                            m.follower[follower_loop].set_base_stats()
                            follower_loop+=1
                        except:
                             print("cannot change sprite for actor Miyu")
                    elif (pname.equals("Ichi")): #slim boy
                        try:
                            m.follower[follower_loop] = Ichi(x, y)
                            m.follower[follower_loop].set_base_stats()
                            follower_loop+=1
                        except:
                             print("cannot change sprite for actor Ichi")
                    elif (pname == "Takeshi"): #fat boy
                        try:
                            m.follower[follower_loop].changeActorSpritesheet("data/boy01", 218, 313)
                            m.follower[follower_loop].tilex = x
                            m.follower[follower_loop].tiley = y
                            m.follower[follower_loop].visible = true
                            m.follower[follower_loop].name = pname
                            follower_loop+=1
                        except:
                            print("cannot change sprite for actor Takeshi")
                    elif (pname.equals("Officer_Ayano")):
                        try:
                            m.follower[follower_loop].changeActorSpritesheet("data/police01", 218, 313)
                            m.follower[follower_loop].tilex = x
                            m.follower[follower_loop].tiley = y
                            m.follower[follower_loop].visible = true
                            m.follower[follower_loop].name = pname
                            m.follower[follower_loop].attack_range = 2
                            #if(weapon)
                            #m.monster[monster_loop].swapSoundEffects("", "pear_attack1.ogg",
                                #"pear_attack1.ogg", "pear_hit1.ogg", "pear_dodged1.ogg", "pear_died1.ogg")
                            m.follower[follower_loop].swapSoundEffects("", "pistol_shotl.ogg", "pistol_shotl.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg")
                            follower_loop+=1
                        except:
                            print("cannot change sprite Officer Ayano")
                    elif (pname.equals("tutor_bully0")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/boy00", 218, 313)
                        except:
                            print("cannot change sprite Tutor Bully")
                        if (actor_spotted == "False"):
                            m.monster[monster_loop].spotted_enemy = False
                        else:
                            m.monster[monster_loop].spotted_enemy = True
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 100
                        monster_loop+=1
                    elif (pname.equals("tutor_bully1")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/girl03", 218, 313)
                         except:
                             print("")
                        if (actor_spotted.equalsIgnoreCase("False")):
                            m.monster[monster_loop].spotted_enemy = False
                         else {
                            m.monster[monster_loop].spotted_enemy = true
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 100
                        m.monster[monster_loop].swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg")
                        monster_loop++
                     elif (pname.equals("pear_monster")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster00", 218, 313)
                         except:
                        
                        if (actor_spotted.equalsIgnoreCase("False")):
                            m.monster[monster_loop].spotted_enemy = False
                         else {
                            m.monster[monster_loop].spotted_enemy = true
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 4
                        m.monster[monster_loop].swapSoundEffects("", "pear_attack1.ogg",
                                "pear_attack1.ogg", "pear_hit1.ogg", "pear_dodged1.ogg", "pear_died1.ogg")
                        monster_loop++
                     elif (pname.equals("butcher")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster07", 218, 313)
                         except:
                        
                        if (actor_spotted.equalsIgnoreCase("False")):
                            m.monster[monster_loop].spotted_enemy = False
                         else {
                            m.monster[monster_loop].spotted_enemy = true
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 0
                        m.monster[monster_loop].health_points_max = 20
                        m.monster[monster_loop].health_points = 20
                        m.monster[monster_loop].swapSoundEffects("", "pear_attack1.ogg",
                                "pear_attack1.ogg", "pear_hit1.ogg", "pear_dodged1.ogg", "pear_died1.ogg")
                        monster_loop++
                     elif (pname.equals("skeleton")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster05", 218, 313)
                         except:
                             print("cannot change actor spritesheet")
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 0
                        monster_loop++
                     elif (pname.equalsIgnoreCase("zombie1")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster10", 218, 313)
                         except:
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 2
                        monster_loop++
                     elif (pname.equalsIgnoreCase("zombie2")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster11", 218, 313)
                         except:
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 2
                        monster_loop++
                     elif (pname.equalsIgnoreCase("zombie3")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster12", 218, 313)
                         except:
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 2
                        monster_loop++
                      elif (pname.equalsIgnoreCase("butcher")):
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster07", 218, 313)
                         except:
                        
                        m.monster[monster_loop].health_points = 15 #boss!
                        m.monster[monster_loop].health_points_max = 15
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 99
                        monster_loop++
                    elif (pname.equals("invisible_man")):
                        System.out.println("we got to the invnisible man")
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster06", 218, 313)
                         except:
                            System.out.println("something is wrong.")
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 4
                        m.monster[monster_loop].shadow = False
                        m.monster[monster_loop].stat_luck = 5 #hard to hit, and hits often!
                        #set dodge scores
                        monster_loop++
                    elif (pname.equals("shadowninja")):
                        System.out.println("we got to shadowninja")
                        try:
                            m.monster[monster_loop].changeActorSpritesheet("data/monster13", 218, 313)
                         except:
                            System.out.println("something is wrong.")
                        
                        m.monster[monster_loop].tilex = x
                        m.monster[monster_loop].tiley = y
                        m.monster[monster_loop].setActorMoving(False)
                        m.monster[monster_loop].visible = true
                        m.monster[monster_loop].name = pname
                        m.monster[monster_loop].max_turns_till_revival = 4
                        m.monster[monster_loop].shadow = False
                        m.monster[monster_loop].stat_luck = 2 #hard to hit, and hits often!
                        #set dodge scores
                        monster_loop++
                    # add more monsters here# add more monsters here
                
            
            for (int i = 0 i < 5 i++):
                m.planning[i] = m.getMapProperty("planning_" + i, "end")
                if (m.planning[i].equalsIgnoreCase("end")):
                    m.maxplanevent = i #last one
                
                m.charbusts[i] = new Image("data/" + m.getMapProperty("planning_" + i + "_p", "prt_player_00.png"))
            
        

        for (int i = 0 i < 5 i++):
            m.planning[i] = m.getMapProperty("planning_" + i, "end")
            if (m.planning[i].equalsIgnoreCase("end")):
                m.maxplanevent = i - 1 #last one
            
            m.charbusts[i] = new Image("data/" + m.getMapProperty("planning_" + i + "_p", "prt_player_00.png"))
        
        m.next_map = m.getMapProperty("nextmap", "none")
        m.RequiresGoal = m.getMapProperty("req_goal", "no")
        m.EventSpotted = m.getMapProperty("event_spotted", "none")
        if (!m.EventSpotted.equalsIgnoreCase("none")): #if not none, load the event spotted
            m.EventSpotted_m = m.getMapProperty("event_spotted_m", "none")
            m.EventSpotted_p = new Image("data/" + m.getMapProperty("event_spotted_p", "prt_player_00.png"))
        
    

