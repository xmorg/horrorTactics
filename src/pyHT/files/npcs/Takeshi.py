#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:52:14 2019

@author: tcooper
"""

from files.Actor import Actor

class Takeshi(Actor):
    def set_base_stats(self, x, y):
        self.changeActorSpritesheet("data/boy01", 218, 313)
        self.tilex = x
        self.tiley = y
        self.visible = True
        self.name = "Takeshi"