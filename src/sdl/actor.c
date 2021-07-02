#include <SDL2/SDL.h>
#include "horrorTactics.h"

#define TRUE     1
#define FALSE    0

void init_actor(struct Actor *a);

void init_actor(struct Actor *a)
{
    
    a->tilex = 0;
    a->tiley = 0;
    a->draw_x =0;
    a->draw_y = 0;
    a->tiledestx = 0;
    a->tiledesty = 0;
    a->animate_frame = 0;
    a->direction = 0;
    a->visible = TRUE; //actor is visible.
    a->hasturn = TRUE;
}
