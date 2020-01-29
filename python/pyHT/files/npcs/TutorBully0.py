#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:52:14 2019

@author: tcooper
"""

from files.Actor import Actor

class TutorBully0(Actor):
    def set_base_stats(self, x, y):
        self.changeActorSpritesheet("data/boy00", 218, 313)
        self.tilex = x
        self.tiley = y
        self.visible = True
        self.setActorMoving(False)
        self.name = "tutor_bully0"
        self.max_turns_till_revival = 100
        #try:
        #                    m.monster[monster_loop].changeActorSpritesheet("data/boy00", 218, 313)
        #                except:
        #                    print("cannot change sprite Tutor Bully")
        #                if (actor_spotted == "False"):
        #                    m.monster[monster_loop].spotted_enemy = False
        #                else:
        #                    m.monster[monster_loop].spotted_enemy = True
        #                m.monster[monster_loop].tilex = x
        #                m.monster[monster_loop].tiley = y
        #                m.monster[monster_loop].setActorMoving(False)
        #                m.monster[monster_loop].visible = True
        #                m.monster[monster_loop].name = pname
        #                m.monster[monster_loop].max_turns_till_revival = 100
        #                monster_loop+=1