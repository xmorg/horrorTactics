#/usr/bin/python3
import pygame
from pygame.locals import *

#state stands for which state the game is in
#"title" handles keyboard events from teh title screen.
#"save, load" screen
#"options screen"
#"tacticalmap"
#citymap
#inventory
#character

class keyboard:
    def __init__(self):
        self.key = None
        self.lastkey = None
    def input(self, state):
        self.lastkey = self.key
        self.key = pygame.key.get_pressed()
        if self.key[pygame.K_RIGHT]:
            print("right")
            return "playing"
        elif self.key[pygame.K_LEFT]:
            print("left")
            return "playing"
        elif self.key[pygame.K_ESCAPE]:
            return "exit"
        else:
            return "playing"
