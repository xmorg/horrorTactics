
#include <SDL2/SDL.h>
#include "horrorTactics.h"

void handle_keydown(struct Game *gc);
void handle_mousebutton_down(struct Game *gc);
void input(struct Game *gc);

void input(struct Game *gc)
{
  while (SDL_PollEvent(&gc->sdl_event ) ){
    if (gc->sdl_event.type == SDL_QUIT) {//If user closes the window
      gc->game_state = GAME_EXIT;
    }
    else if (gc->sdl_event.type == SDL_KEYDOWN)//If user presses any key
      handle_keydown(gc);
    else if (gc->sdl_event.type == SDL_MOUSEBUTTONDOWN) { //If user clicks the mouse
      //handle_mousebutton_down(gc);
    }
  }
}

void handle_keydown(struct Game *gc)
{
  int k;
  k = gc->sdl_event.key.keysym.sym;
  if(k == SDLK_ESCAPE ) {
    gc->game_state = GAME_EXIT;
  }
  if(k == SDLK_LEFT && gc->game_state == GAME_PLAY_IDLE) {
  }
  else if(k == SDLK_RIGHT) {// && gc->game_state == GAME_PLAY_IDLE ) {
    gc->draw_x--;
  }
  else if(k == SDLK_UP){// && gc->game_state == GAME_PLAY_IDLE ) {
    gc->draw_y++;
  }
  else if(k == SDLK_DOWN){// && gc->game_state == GAME_PLAY_IDLE ) {
      gc->draw_y--;
  }
}
