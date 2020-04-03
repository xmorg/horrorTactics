#/usr/bin/python3

import pygame
from pygame.locals import *

class scene:
    def __init__(self):
        self.x = 0 #draw x/y
        self.y = 0 
class title: #title screen
    def __init__(self):
        self.image = pygame.image.load('../data/title1_01.jpg')
        self.menuimage = pygame.image.load('../data/title_text_load.jpg')
    def draw(self,scr):
        scr.blit(self.image, (0,0))
        scr.blit(self.menuimage, (0,0))
    def input(self):
        #check for mouse
        i = 1
class button: #clickable buttons
    def __init__(self, x, y, w, h):
        self.x =x
        self.y =y
        self.w =w
        self.h =h
    def getclick(self, x, y):
        if( x > self.x and x < self.x+self.w):
            if (y > self.y and  y < self.y + self.w ):
                return True
            return False
class myapp: #main app
    def __init__(self):
        self.mouse_x, self.mouse_y = pygame.mouse.get_pos()
        self.gscreen = pygame.display.set_mode( (0,0), pygame.FULLSCREEN)
    def load(self):
        self.title = title()
    def draw(self):
        #pygame.draw.rect(surface, color, pygame.Rect(left, top, width, height))
        self.title.draw(self.gscreen) #if gamestate = title
        pygame.display.update()
    def input(self):
        key = pygame.key.get_pressed()
        if key[pygame.K_RIGHT]:
            print("right")
            return "playing"
        elif key[pygame.K_LEFT]:
            print("left")
            return "playing"
        elif key[pygame.K_ESCAPE]:
            return "exit"
        else:
            return "playing"
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
        game_state = app.input()
        app.draw()
    print("exit reached")
main()
