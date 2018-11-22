#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include "horrorTactics.h"
#include "tmx.h"

//static SDL_Renderer *global_r = NULL;

SDL_Texture *load_texture(SDL_Renderer *r, char *filename);
void load_resources(struct Game *g);
int start_sdl(struct Game *gc);
int fatal_error(char *err);
SDL_Renderer *get_game_renderer();


void * sdl_img_loader(const char *path)
{
  printf("loaded %s", path);
  return IMG_LoadTexture(get_game_renderer(),path);
}

SDL_Texture *load_texture(SDL_Renderer *r, char *filename)
{
  SDL_Surface *s;
  SDL_Texture *t;
  s = IMG_Load(filename);
  t = SDL_CreateTextureFromSurface(r, s);
  return t;
}
/*g->tiles250x129 = load_texture(SDL_Renderer *r, char *filename);*/
void load_resources(struct Game *g)
{
  //global_r = g->r;
  //g->tiles250x129 = load_texture(g->r, "data/tiles250x129.png");
  //g->walls250x512 = load_texture(g->r, "data/walls250x512.png");
  //g->tactics_in_distress00 = load_texture(g->r, "data/tactics_in_distress00.png");
  //g->tactics_in_distress01 = load_texture(g->r, "data/tactics_in_distress01.png");

  //sdl_img_loader(SDL_Renderer *ren, const char *path)
  
  //if (!(g->map = tmx_load("data/class_school01.tmx"))) fatal_error("(!(map = tmx_load(...");
}

int start_sdl(struct Game *gc)
{
  printf("startsdl\n");
  //Temp settings
  gc->screen_width = 1024; //1360; //640; //set screensize. (why hardcode again?)
  gc->screen_height = 600; //768; //480; //v---- run SDL_Init()
  if (SDL_Init( SDL_INIT_VIDEO | SDL_INIT_TIMER | SDL_INIT_EVENTS
          | SDL_WINDOW_FULLSCREEN_DESKTOP) == -1) {
    printf("SDL_Init: .... \n"  );
    return 1;
  }
  //start_ttf(gc);  //load the fonts
  SDL_GetCurrentDisplayMode(0, &gc->current_dmode);
  gc->screen_width = gc->current_dmode.w;
  gc->screen_height = gc->current_dmode.h;
  
  SDL_RendererInfo displayRendererInfo; //new
  SDL_CreateWindowAndRenderer(gc->screen_width,
			      gc->screen_height,
			      //SDL_WINDOW_OPENGL,
			      SDL_WINDOW_FULLSCREEN_DESKTOP,
			      &gc->win,
			      &gc->r );
  SDL_GetRendererInfo(gc->r, &displayRendererInfo);
  if (gc->win == NULL){ //error check window
    printf("%s \n", SDL_GetError() );
    return 1;
  }
  if (gc->r == NULL){
    printf("%s \n", SDL_GetError() );
    return 1;
  }
  //gc->font0 = TTF_OpenFont("data/font.ttf", 14);//font size?
  //gc->current_font = gc->font0;
  if (gc->font0 == NULL) {
    printf("Error no font (data/font.ttf) ->from resources.c\n");
    return 1;
  }

  gc->game_state = 1; //the game started
  return 0;
}
