#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:32:15 2019

@author: tcooper
"""

from files.Actor import Actor

class Miyu(Actor):
    def set_base_stats(self, x, y):
         self.changeActorSpritesheet("data/girl02", 218, 313)
         self.swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", "girl_hit2.ogg", "girl_dodging1.ogg", "girl_hit1.ogg")
         #follower[follower_loop].]
         self.tilex = x
         self.tiley = y
         self.visible = True
         self.stat_str+= 2 #self.stat_str+2
         self.health_points = self.health_points+2
         self.health_points_max = self.health_points_max+2
         self.exp_level = 2
         self.stat_luck+=1
         self.name = "Miyu"