#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:52:14 2019

@author: tcooper
"""

from files.Actor import Actor

class OfficerAyano(Actor):
    def set_base_stats(self, x, y):
        self.changeActorSpritesheet("data/police01", 218, 313)
        self.tilex = x
        self.tiley = y
        self.visible = True
        self.name = "Officer_Ayano"
        self.attack_range = 2
        self.swapSoundEffects("", "pistol_shotl.ogg", "pistol_shotl.ogg", \
                              "girl_hit2.ogg", "girl_dodging1.ogg", \
                              "girl_hit1.ogg")