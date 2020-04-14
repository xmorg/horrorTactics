#/usr/bin/python3
import pygame
from pygame.locals import *

from button import button

# app
## keyboard
## mouse
## titlescreen

class title: #title screen
    def __init__(self):
        self.image = pygame.image.load('../data/title1_01.jpg')
        self.menuimage = pygame.image.load('../data/title_text_load.jpg')
    def draw(self,scr):
        scr.blit(self.image, (0,0))
        scr.blit(self.menuimage, (0,0))
    def input(self):
        #check for mouse
        #i = 1
        i = 
