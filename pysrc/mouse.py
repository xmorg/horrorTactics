#/usr/bin/python3
import pygame
from pygame.locals import *
from button import button
#state stands for which state the game is in
#"title" handles keyboard events from teh title screen.
#"save, load" screen
#"options screen"
#"tacticalmap"
#citymap
#inventory
#character

class mouse:
    def __init__(self):
        self.x, self.y = pygame.mouse.get_pos()
    def input(self, state): #does get clicks
        self.pos = pygame.mouse.get_pos()
        #self.click = pygame.mouse.
        self.x = self.pos[0]
        self.y = self.pos[1]
