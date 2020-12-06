#/usr/bin/python3

import pygame
from pygame.locals import *

from title import title
from keyboard import keyboard
from mouse import mouse

class scene:
    def __init__(self):
        self.x = 0 #draw x/y
        self.y = 0 
class myapp: #main app
    def __init__(self):
        self.mouse_x, self.mouse_y = pygame.mouse.get_pos()
        self.gscreen = pygame.display.set_mode( (0,0), pygame.FULLSCREEN)
        self.kb = keyboard()
        self.ms = mouse()
        self.current_scene = "title"
    def load(self):
        self.title = title()
    def draw(self):
        #pygame.draw.rect(surface, color, pygame.Rect(left, top, width, height))
        if self.current_scene == "title":
            self.title.draw(self.gscreen) #if gamestate = title
        elif self.current_scene == "tactical":
            self.current_scene = "tactical"
        elif self.current_scene == "options":
            self.current_scene = "options"
        elif self.current_scene == "save":
            self.current_scene = "save"
        elif self.current_scene == "load":
            self.current_scene = "load"
            pygame.display.update()
    def input(self):
        r = self.kb.input()
        return r
def main():
    pygame.init()
    app = myapp()
    app.load()
    game_state = "playing"
    while game_state == "playing":
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                print("debug: you wanted to quit?")
                pygame.quit()
            if event.type == pygame.KEYDOWN and event.key == pygame.K_ESCAPE:
                game_state = "exit"
            if event.type == pygame.MOUSEBUTTONDOWN:
                ms.input()
        game_state = app.input()
        app.draw()
    print("exit reached")
main()
