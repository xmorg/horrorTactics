
#include <SDL2/SDL.h>
#include "horrorTactics.h"

void init(struct Game *g);
void update(struct Game *g);
void input(struct Game *g);

void render(struct Game *g);
void load_new_map();
void set_game_state();
void load_resources(struct Game *g);
int start_sdl(struct Game *g);
SDL_Texture* render_map(struct Game *g);
int fatal_error(const char *err);
void * sdl_img_loader(const char *path);

struct Game g;

SDL_Renderer *get_game_renderer()
{
  return g.r; //get a global!
}
int main(int argc, char *argv[])
{
  /*main code here.*/
  tmx_img_load_func = (void* (*)(const char *))sdl_img_loader;
  tmx_img_free_func = (void  (*)(void *)) SDL_DestroyTexture;
  tmx_map *mmap = tmx_load("data/class_school01.tmx");  

  init(&g);
  g.map = mmap; //tmx_load("data/class_school01.tmx");
  g.game_state = GAME_INIT;
  while(g.game_state != GAME_EXIT) {
    update(&g);
    render(&g);
  } printf("exited\n");
}

int fatal_error(const char *err)
{
  printf("you made fatal error: %s\n", err);
  SDL_Quit();
  return -1;
}


void init(struct Game *g)
{
  
  load_resources(g);
  start_sdl(g);
}

/*render the game data*/
void render( struct Game * g)
{
  SDL_Texture *map_bmp;
  SDL_Rect map_drect;
  /*drSDL_Rect map_rect;aw code here*/

  map_drect.x = 0;  
  map_drect.y = 0;
  map_drect.w = g->screen_width; 
  map_drect.h = g->screen_height; 
  
  SDL_RenderClear(g->r);
  if (!(map_bmp = render_map(g))) {
    fatal_error(SDL_GetError());
  }
  SDL_RenderCopy(g->r, map_bmp, NULL, &map_drect);
  SDL_RenderPresent(g->r);
}
void update( struct Game * g)
{
  input(g);
}
