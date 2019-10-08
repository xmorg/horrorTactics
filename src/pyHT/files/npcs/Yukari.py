#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:12:36 2019

@author: tcooper
"""

from files.Actor import Actor

class Yukari(Actor):
    def set_base_stats(self, x, y):
        self.changeActorSpritesheet("data/girl01", 218, 313)
        #follower[follower_loop].
        self.tilex = x
        self.tiley = y
        self.visible = True
        self.name = Yukari