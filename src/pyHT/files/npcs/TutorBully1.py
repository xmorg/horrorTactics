#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Oct  8 06:52:14 2019

@author: tcooper
"""

from files.Actor import Actor

class TutorBully01(Actor):
    def set_base_stats(self, x, y):
        self.changeActorSpritesheet("data/girl03", 218, 313)
        self.name = "tutor_bully1"
        self.tilex = x
        self.tiley = y
        self.setActorMoving(False)
        self.visible = True
        self.name = "tutor_bully1"
        self.max_turns_till_revival = 100
        self.swapSoundEffects("", "girl_attack1.ogg", "girl_attack1.ogg", \
                              "girl_hit2.ogg", "girl_dodging1.ogg", \
                              "girl_hit1.ogg")