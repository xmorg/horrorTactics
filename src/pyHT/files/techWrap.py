#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Sep 30 14:38:45 2019

@author: tcooper
"""

import pyglet
from pyglet import Clock


class Rectangle():
    def __init__(self, x, y, w, h):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
    def setBounds(self, x, y, w, h):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
    def getBoundsT(self): #return bounds as a tupple
        return (self.x, self.y, self.w, self.h)
class Color():
    def __init__(self, r, g, b, a):
        self.red = r
        self.green = g
        self.blue = b
        self.alpha = a
    def white(self): #set all values to white (decimal)
        self.red = 1
        self.green = 1
        self.blue = 1
        self.alpha = 1
    def getValuesT(self): #return all values as a tuple
        return (self.red, self.green, self.blue, self.alpha)
class HtImage():
    def __init__(self, filename):
        self.data = pyglet.image(filename)
    def draw(self,x,y ): #draw the image
        self.data.blit(x,y,0)
    def draw3d(self,x,y,z):
        self.data.blit(x,y,z)
        
class HtFont():
    def __init__(self, fontname):
        self.font = fontname #init pyglet
class HtTime():
    def __init__(self):
        
        
        
        