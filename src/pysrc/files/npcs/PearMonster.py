#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:52:14 2019

@author: tcooper
"""

from files.Actor import Actor

class PearMonster(Actor):
    def set_base_stats(self, x, y):
        try:
            self.changeActorSpritesheet("data/monster00", 218, 313)
        except:
            print("cant / changeActorSpritesheet")
        #if (actor_spotted.equalsIgnoreCase("False")):
        #    self.spotted_enemy = False
        #else: # {
        self.spotted_enemy = True
        self.tilex = x
        self.tiley = y
        self.setActorMoving(False)
        self.visible = True
        self.name = "pear_monster"
        self.max_turns_till_revival = 4
        self.swapSoundEffects("", "pear_attack1.ogg", "pear_attack1.ogg",\
                              "pear_hit1.ogg", "pear_dodged1.ogg",\
                              "pear_died1.ogg")