#include <SDL2/SDL.h>
#include <SDL2/SDL_ttf.h>
#include "tmx.h"

/*game states*/
enum { 
  GAME_INIT,
  GAME_EXIT,
  GAME_PLAY_IDLE
};

struct Actor {
  SDL_Texture *sprite_image;
  int tilex,
    tiley,
    tiledestx, 
    tiledesty,
    animate_frame,
    direction,
    draw_x, 
    draw_y;
  unsigned short int selected,
    hasturn,
    visible;
};

/*tmx for tiled map?*/
struct Game {
  /*pointer to Map*/
  tmx_map *map;
  //pointer to SDL events(mouse actions, key actions)
  int player_x, 
    player_y, 
    player_animate, 
    animate_timer,
    draw_x,
    draw_y,
    mouse_x, 
    mouse_y,
    scale_x,
    screen_width,
    screen_height,
    game_state;
  SDL_Renderer *r;
  SDL_Window *win;
  TTF_Font *font0;
  SDL_Event sdl_event;
  SDL_DisplayMode current_dmode;
  SDL_Texture *tiles250x129;
  SDL_Texture *walls250x512;
  SDL_Texture *tactics_in_distress00;
  SDL_Texture *tactics_in_distress01;
};

