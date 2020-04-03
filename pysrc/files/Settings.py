#/*
# * The point of this class is to give the user hint/tips on how to play the game
# * The hints can be turned off by a toggle in the HorrorTactcis Class.
# * Each map has its own hint
# */
#package horrortactics;

#/**
# *
# * @author tcooper
# */
class Settings: # {
    def __init__(self):
        self.toggle_hints = True
        self.toggle_fullscreen = True
        self.toggle_sound = True
    def set_settings1(self, hints, fs, sound):
        self.toggle_hints = hints
        self.toggle_fullscreen = fs
        self.toggle_sound = sound