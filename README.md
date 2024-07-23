# horrorTactics +
Survive your worst fears! The player faces a seamingly endless array of spooky scenarios.  It all started on that first night, when she went back to the school to get her cell phone from her locker. The classrooms where crawling with shambling pear shaped monsters.  After surviving her first "encounter", the player returned to school the next day to find everything was normal, like it never happened.  Except her friends.  Any friend who dies, seems to have never existed!  The next night ...

STATE
This game was written in an older version of java (8?), using and older version of netbeans (8? 9?) and Slick2D (a no longer developed engine)  as such development has halted as pretty much all of its underlying tech is outdated.  It still runs, but to compile you need to recreate the netbeans environment.  Attempting to compile without netbeans creates circular dependancies.  I also attempted to use gradle and can get a jar executable but it wont run. So the game needs to be rewritten.  Hopefully a platform can emerge that allows me to use the tiled maps.  (which leads me to another issue!)  Currently the triggers and hacks i used on tiled to create encounters and logic, I cannot create on later versions of tiled!  adding extra data to objects appears to be read only, although you can still see it on the already created maps.  Some hacking can be done with a text editor, but it also seems that even the maps would need to be recreated.  So thats the long version of why development halted - technology is moving faster than I am able to develop.

I have posted the assests of this game on opengameart, using their OGA license.  Feel free to reuse the art.
https://opengameart.org/content/horrortactics-tiles-sprites

TODO's [code]
Dead monster sprites change direction in monster turn
Actors can stll face away from the attack targets
It alwasys seems to be the monster's turn when loading a saved game
players team does not regenerate health after the next level.[fixed?]
player does not regenerate fatigue on rest.

make a game over screen for render_game_over
annoying "none" still shows up on conversation

cut scenes
BUG stop all sounds (walk sounds) when new scene loads.


DONE:
right click to scroll screen
attacks need to subtract action points.
monsters get stuck on walls sometimes
Monsters still attack dead units.
In class_school01, its possible to go around the exit-fixed

Testing: Levels played to completion
100 - Tutorial01
100 - class_school01
001 - apartment1

Tutorial Text

TODO's [art]
- smooth out player's animation
- takeshi's back animation looks bad.
- [DONE]policewoman needs attack and back animations
- asylum patients need walk animations
- asylum guards need walk animations

Portraits needed
- [DONE] Player (colored and face)
- [DONE] Yukari (colored and face)
- The other boy?
- The kendo girl?
- Asylum patients
- Policewoman

Maps
- [DONE]Tutorial can meet bullies, get papers and return to class.  Should transition to class (not tested)
- [DONE]classroom can meet monsters, get cell phone and return to exit.  Does transition to appartment.
- [DONE]appartment, can meet monsters, and exit.
- [DONE]Street, zombies finished and can transition to the butcher shop.
- [DONE]butcher shop / map completed, monster completed, can it transition?
- subway (needs subway tiles!) / no monsters, no triggers, no placement in map order
- poolside Monster completed, map completed, no triggers, no placement in map order
- forrest, map completed, no monsters, no triggers, no placement in map order
- asylum, no map, no tiles or walls, monsters partially completed, folowers partially completed, no triggers, no placement in map order

Character
- [DONE]Health Points - your health, lose this and die
- [DONE]Fatigue points - fighting and running decrease fatigue points, 1 attack -2FP, 6AP used a round -1FP.
- [DONE]Mental Stability - spotting the enemy, -1, taking damage -2, witnessing death -3

 - [DONE]Experience Level / Points, gain level+1 * level+1*10 to advance.
 - [DONE]Points to be gained on any attack kill, objective completed, and level survived.
 - [DONE]Strength, how hard you hit, Every 2 points of endurace adds 1 damage. (should also do health?
 - [DONE]Speed, how fast you move. Every 2 points of speed, adds 1 action point.
 - [DONE]Willpower, ability to take a hit, and keep your cool under stress. Every 2 points adds 1MSp
 - [DONE]Luck, you make the best rolls! Every 3 points of Luck adds 1 to dice rolls.
 - [FIXED]after loading save, the planning text plays again. Needs to advance, 
 
