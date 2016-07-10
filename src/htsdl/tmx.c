#include <SDL2/SDL.h>
#include <SDL2/SDL_image.h>
#include "tmx.h"
#include "horrorTactics.h"

int fatal_error(const char *err);

//void* sdl_img_loader(SDL_Renderer *ren, const char *path) {
  //SDL_Renderer *ren;
//  return IMG_LoadTexture(ren, path);
//}

void set_color(SDL_Renderer *ren, int color) {
  unsigned char r, g, b;
  
  r = (color >> 16) & 0xFF;
  g = (color >>  8) & 0xFF;
  b = (color)       & 0xFF;
  
  SDL_SetRenderDrawColor(ren, r, g, b, SDL_ALPHA_OPAQUE);
}



void draw_polyline(SDL_Renderer *ren, double **points, double x, double y, int pointsc) {
  int i;
  for (i=1; i<pointsc; i++) {
    SDL_RenderDrawLine(ren, x+points[i-1][0], y+points[i-1][1], x+points[i][0], y+points[i][1]);
  }
}

void draw_polygon(SDL_Renderer *ren, double **points, double x, double y, int pointsc) {
  draw_polyline(ren, points, x, y, pointsc);
  if (pointsc > 2) {
    SDL_RenderDrawLine(ren, x+points[0][0], y+points[0][1], x+points[pointsc-1][0], y+points[pointsc-1][1]);
  }
}

void draw_objects(SDL_Renderer *ren, tmx_object_group *objgr) {
  SDL_Rect rect;
  set_color(ren, objgr->color);
  tmx_object *head = objgr->head;
  /* FIXME line thickness */
  while (head) {
    if (head->visible) {
      if (head->shape == S_SQUARE) {
	rect.x =     head->x;  rect.y =      head->y;
	rect.w = head->width;  rect.h = head->height;
	SDL_RenderDrawRect(ren, &rect);
      } else if (head->shape  == S_POLYGON) {
	draw_polygon(ren, head->points, head->x, head->y, head->points_len);
      } else if (head->shape == S_POLYLINE) {
	draw_polyline(ren, head->points, head->x, head->y, head->points_len);
      } else if (head->shape == S_ELLIPSE) {
	/* FIXME: no function in SDL2 */
      }
    }
    head = head->next;
  }
}

unsigned int gid_clear_flags(unsigned int gid) {
  return gid & TMX_FLIP_BITS_REMOVAL;
}

void draw_layer(struct Game *g, tmx_map *map, tmx_layer *layer) {
  unsigned long y, x;
  unsigned int gid;
  float op;
  tmx_tileset *ts;
  tmx_image *im;
  SDL_Rect srcrect, dstrect;
  SDL_Texture* tileset;
  op = layer->opacity;
  for (y=0; y<map->height; y++) {
    for (x=0; x<map->width; x++) {
      gid = gid_clear_flags(layer->content.gids[(y*map->width)+x]);
      if (map->tiles[gid] != NULL) {
	ts = map->tiles[gid]->tileset;
	im = map->tiles[gid]->image;
	/*source tiles (from the image)*/
	srcrect.x = map->tiles[gid]->ul_x;
	srcrect.y = map->tiles[gid]->ul_y;
	srcrect.w = dstrect.w = ts->tile_width;
	srcrect.h = dstrect.h = ts->tile_height;
	/*Destination rect/where we are rendering to*/
	//dstrect.x = x*ts->tile_width + g->draw_x;  
	//dstrect.y = y*ts->tile_height + g->draw_y;
	dstrect.x = x * (ts->tile_width/2) - y * (ts->tile_width/2) +g->draw_x;
	dstrect.y = x * (ts->tile_height/2) + y * (ts->tile_height/2) +g->draw_y;
	dstrect.w = ts->tile_width;
	dstrect.h = ts->tile_height;
	/* TODO Opacity and Flips */
	if (im) {
	  tileset = (SDL_Texture*)im->resource_image;
	}
 	else {
	  tileset = (SDL_Texture*)ts->image->resource_image;
	}
	SDL_RenderCopy(g->r, tileset, &srcrect, &dstrect);
      }
    }
  }
}

void draw_image_layer(SDL_Renderer *ren, tmx_image *img) {
  SDL_Rect dim;
  dim.x = dim.y = 0;
  SDL_QueryTexture((SDL_Texture*)img->resource_image, NULL, NULL, &(dim.w), &(dim.h));
  SDL_RenderCopy(ren, (SDL_Texture*)img->resource_image, NULL, &dim); 
}

SDL_Texture* render_map(struct Game *g) {
  SDL_Texture *res;
  tmx_layer *layers = g->map->ly_head;
  int w, h;
  w=g->screen_width;
  h=g->screen_height;  
  /*w = map->width * map->tile_width /   4;*/  /* Bitmap's width and height */
  /*h = map->height * map->tile_height / 4; */ 
  if (!(res = SDL_CreateTexture(g->r, SDL_PIXELFORMAT_RGBA8888, 
				SDL_TEXTUREACCESS_TARGET, w, h))) {
    //printf("SDL_CreateT...\n");
    fatal_error(SDL_GetError());
  }
  SDL_SetRenderTarget(g->r, res);
  /*set_color(ren, map->backgroundcolor);*/
  SDL_SetRenderDrawColor(g->r, 0, 0, 0, 100);//_OPAQUE);
  SDL_RenderClear(g->r);
  while (layers) {
    if (layers->visible) {
      if (layers->type == L_OBJGR) {
	//draw_objects(g->r, layers->content.objgr);
      } else if (layers->type == L_IMAGE) {
	draw_image_layer(g->r, layers->content.image);
      } else if (layers->type == L_LAYER) {
	//draw_layer(g, g->map, layers);
      }
    }
    layers = layers->next;
  }
  SDL_SetRenderTarget(g->r, NULL);
  return res;
}
