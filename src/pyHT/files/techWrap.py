#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Sep 30 14:38:45 2019

@author: tcooper
"""

import pyglet

#https://git.savannah.nongnu.org/git/python-tmx.git
#Doc: http://www.nongnu.org/python-tmx/doc/
# clone and run python3 setup.py install
#from tmx import TiledMap
#import tmx
from pyglet import gl
from pyglet import clock
from pyglet.window import key
from pyglet.resource import image as Image

#from tmx import TileMap
        
class SpriteSheet:
    def __init__(self):
        self.i = 0
class Rectangle:
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
    def getX(self):
        return self.x
    def getY(self):
        return self.y
    def getWidth(self):
        return self.w
    def getHeight(self):
        return self.h
class Color:
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
    def green(self):
        self.red = 0
        self.green=1
        self.blue=0
        self.alpha=0
    def getValuesT(self): #return all values as a tuple
        return (self.red, self.green, self.blue, self.alpha)
class HtImage:
    def __init__(self, filename):
        self.data = Image(filename)  #pyglet.image(filename)
    def draw(self,x,y ): #draw the image
        self.data.blit(x,y,0)
    def draw3d(self,x,y,z):
        self.data.blit(x,y,z)
    def getWidth(self):
        return self.data.width
    def getHeight(self):
        return self.data.height     
class HtSound: #sound and music
    #EOS_PAUSE='pause',#EOS_LOOP='loop', #EOS_NEXT='next',#EOS_STOP='stop'
    def __init__(self, filename):
        self.data = pyglet.media.load(filename)
        self.player = pyglet.media.Player()
    def playing(self): #check if the sound is playing
        return False
    def play(self): #play a sound
        self.data.play()
    def loop(self):
        self.player.queue(self.data)
        self.player.eos_action = 'loop'
        self.player.play()
    def stop(self):
        self.player.seek(0)
        self.player.pause()
class HtKeys:
    def __init__(self):
        self.keys = key #.KeyStateHandler()
        self.handler = key.KeyStateHandler()      
class HtFont:
    def __init__(self, fontname):
        self.font = fontname #init pyglet
class HtTime:
    def __init__(self):
        self.time = 0
    def getFPS():
        return 0
    def getTime():
        return 0
class HtWindow:
    def __init__(self):
        self.window = pyglet.window.Window()
        #self.screen = pyglet.window.Screen() #AttributeError: module 'pyglet.window' has no attribute 'Screen'
        #self.window.set_fullscreen()
    def getScreenHeight(self): #(self):
        return self.screen.height
    def getScreenWidth(self):
        return self.screen.width
class Graphics:
    def __init__(self):
        self.current_color = Color(0,0,0,0)
    def setColor(self, c):
        self.current_color = c
        gl.glColor(self.current_color.red, self.current_color.green, self.current_color.blue, self.current_color.alpha)
    def drawString(self, t, locx, locy):
        label =  pyglet.text.Label( text=t, font_name='Times New Roman', \
                                   font_size=12, x=locx, y=locy )#, \
                                   #anchor_x='center', anchor_y='center')
        label.draw()
    def fillOval(self, x,y,z,a):
        return 1 #todo
    def fillRect(self, x, y, w, h):
        return 1 #todo
    def scale(self, s):
        return 1 #todo
class HtApp:
    def __init__(self):
        #self.screen = HtWindow()
        self.screen = HtWindow()
    def run_app(self):
        pyglet.app.run()