#Makefile for mySDL

CC = clang -g 

LIBS = -lSDL2 -lSDL2_image -lSDL2_ttf -ltmx -lz -lxml2

FLAGS = `sdl2-config --cflags --libs` -I/usr/local/include -L/usr/local/lib -L./ 

FILES = horrorTactics.c \
	actor.c \
	resources.c \
	keyboard.c \
	tmx.c \
	mouse.c 

#src/glfunctions.c
#game.exe: src/SDL_collide.c src/actors.c src/datafunctions.c src/gameui.c
# src/groups.c src/menus.c src/resources.c src/fonts.c src/maps.c 
#src/movables.c src/render.c src/input.c src/world.c src/Main.c
#	gcc -o mySDL.exe -L /usr/local/lib -I /usr/local/include/SDL -lm -lSDL -lSDL_image *.c
#	gcc -o game.exe src/*.c `sdl-config --cflags --libs` -lSDL_image -lm

all:
	$(CC) -o ../../game $(FILES) $(FLAGS) $(LIBS)

clean:
	@echo Cleaning up...
	@echo Erasing exec
	@rm game
	@echo Erasing emacs backups
	@rm src/*.*~
	@rm src/*.o
	@rm *.core
	@echo Done.
