#hello

from files.techWrap import HtImage

class MyTiledMap: # extends TiledMap {
    def __init__(self, ref, draw_x, draw_y):
        self.monster_max = 10
        self.follower_max = 4
        #self.tiles250x129 = None #HtImage()
        self.walls250x512 = None #HtImage()
        self.tilesheet
        self.wallsheet = None #SpriteSheet
        self.tileWidth = 2 #what is the default?
        self.tileHeight = 2
        self.TILE_WIDTH_HALF = self.tileWidth / 2
        self.TILE_HEIGHT_HALF = self.tileHeight / 2
        self.selected_tile_x = -1
        self.selected_tile_y = -1
        self.mouse_over_tile_x = 0
        self.mouse_over_tile_y = 0
        self.pixel_dest_x = -1
        self.pixel_dest_y = -1
        self.light_level = 2 ##default light level
        self.selected_follower = 0
        self.free_move = False
        #self.turn_order = None  #monster, player, freemove
        #self.next_map = "none"
        self.maptitle = "unknown map"
        self.mapname = "none"
        self.old_turn_order = None    #monster, player
        self.log_msg = ""
        self.objective = ""
        self.hint = ""
        self.planning = ""
        #HtImage selected_green, selected_yellow
        #Image[] charbusts = HtImage[20]
        self.EventSpotted = "none"
        self.EventSpotted_p = None #image event spotted
        self.EventSpotted_m = "none"
        self.EventSpotted_ran = False #SAVEME!
        self.EventSpotted1 = "none"  #TODO: make a loop like in planning.
        self.EventSpotted_p1 = None   #LOOP: through like in planning during, 
        self.EventSpotted_m1 = "none" #render_character_busts
        self.EventSpotted_ran1 = False
        self.EventGoal = "none"
        self.EventGoal_p = None
        self.EventGoal_m = "none"
        self.EventGoal_ran = False

        self.EventExit = "none"
        self.EventExit_p = None
        self.mission_goal = None
        self.draw_goal_x = -1
        self.draw_goal_y = -1

        self.EventExit_m = "none"
        self.EventExit_ran = False
        self.RequiresGoal = "no" #Yes, if you need to reach a goal before exit

        self.planevent = 0
        self.maxplanevent = 0
        self.turn_order = "title"   #monster, player, planning, cutscene #make it planning after you start a new game
        self.active_trigger = Trigger("none", "none")

        self.tiles250x129 = HtImage("data/tiles250x129.png")
        self.selected_green = HtImage("data/selected_green.png")
        self.selected_yellow = HtImage("data/selected_yellow.png")
        self.player = Actor("data/player00", 218, 313)
        self.follower = [] #Actor[] follower = new Actor[follower_max]
        self.monster = []  #Actor[] monster = new Actor[monster_max]
        self.actormap = ActorMap()
        self.m_draw_x = draw_x
        self.m_draw_y = draw_y
        self.current_monster_moving = 0 #debug
        self.current_follower_moving = 0
        self.trigger_check = "no"

        self.render_min_y = 0
        self.render_min_x = 0
        self.render_max_y = 0
        self.render_max_x = 0
        
        self.next_map = self.getMapProperty("nextmap", "none")
        self.maptitle = self.getMapProperty("maptitle", "unkown map")
        self.mapname = self.getMapProperty("mapname", "none")  #load the mapname (to save later)
        self.objective = self.getMapProperty("obj", "none") #objective
        self.hint = self.getMapProperty("hint", "none") #hint
        self.RequiresGoal = self.getMapProperty("req_goal", "no")
        self.EventSpotted = self.getMapProperty("event_spotted", "none")
        self.EventSpotted1 = self.getMapProperty("event_spotted1", "none")
        if (self.EventSpotted == "none"): # { #if not none, load the event spotted
            self.EventSpotted_m = self.getMapProperty("event_spotted_m", "none")
            self.EventSpotted_p = HtImage("data/" + self.getMapProperty("event_spotted_p", "prt_player_00.png"))
            if (self.EventSpotted1 == "none"):
                self.EventSpotted_m1 = self.getMapProperty("event_spotted_m1", "none")
                self.EventSpotted_p1 = HtImage("data/" + self.getMapProperty("event_spotted_p1", "prt_player_00.png"))
        else:
            self.EventSpotted_m = self.getMapProperty("event_spotted_m", "end.")
            self.EventSpotted_p = HtImage("data/" + self.getMapProperty("event_spotted_p", "prt_player_00.png"))

    def getDrawX(self):
        return self.m_draw_x

    def getDrawY(self):
        return self.m_draw_x

    def updateMapXY(self, x, y):
        self.m_draw_x = x
        self.m_draw_y = y

    def onNewTrigger(self, ttype, name): # {
        try:
            self.active_trigger = Trigger(ttype, name)
        except:
            print("cannot create new trigger")
    #public Actor getFollowerByXy(int x, int y)
    def setFollowerDirectives(self): # {
        #loop through your followers and set a path for them to follow
        #if they are controllable, they shall already have destinations.
        self.current_follower_moving = 0
        for i in range(0, self.follower_max): #(int i = 0 i < self.follower_max i+=1):
            if (self.follower[i].visible == True):
                self.follower[i].action_points = 6 + self.follower[i].stat_speed - 1 + self.follower[i].getMovePenalty()
                self.follower[i].setActorMoving(False)
                self.follower[i].setActorDestination(self.follower[i].tilex, self.follower[i].tiley)

    def drawFollowers(self, ht, x, y, g):
        #map.monster[0].drawActor(self, map, x, y)
        for i in range(0, self.follower_max): #(int i = 0 i < self.follower_max i+=1):
            if (self.follower[i].visible == True): #just to be sure
                self.follower[i].drawActor(ht, self, x, y, g)

    def drawMonsters(self, ht,  x,  y, g):
        #map.monster[0].drawActor(self, map, x, y)
        for i in range(0, self.monster_max): #(int i = 0 i < self.monster_max i+=1):
            if (self.monster[i].visible == True): #just to be sure
                #energy bar here?
                self.monster[i].drawActor(ht, self, x, y, g)
                if (self.monster[i].tilex == x and self.monster[i].tiley == y):
                    if (self.EventSpotted_ran == False):
                        self.EventSpotted_ran = True #they are "spotted"
                        #run event spotted
                        System.out.println("debug: event spotted ran")
                        self.old_turn_order = self.turn_order
                        self.turn_order = "event spotted"

    def setMonsterToActorDestination(self, monster, player): #Actor monster, Actor player
        if (player.tilex == monster.tilex and player.tiley > monster.tiley):
            monster.tiledestx = player.tilex - 1
            monster.tiledesty = player.tiley
        elif(player.tilex == monster.tilex and player.tiley < monster.tiley):
            monster.tiledestx = player.tilex + 1
            monster.tiledesty = player.tiley
        elif(player.tilex > monster.tilex and player.tiley == monster.tiley):
            monster.tiledestx = player.tilex
            monster.tiledesty = player.tiley - 1
        elif(player.tilex < monster.tilex and player.tiley == monster.tiley):
            monster.tiledestx = player.tilex
            monster.tiledesty = player.tiley + 1

    def monsterfollowerInTile(self, x, y):
        for i in range(0, self.monster_max): #for (int i = 0 i < self.monster_max i+=1):
            if (x == self.monster[i].tilex and y == self.monster[i].tiley
                    and self.monster[i].dead == False):
                return True
        for i in range(0, self.monster_max): #(int i = 0 i < self.follower_max i+=1):
            if (x == follower[i].tilex and y == follower[i].tiley
                    and follower[i].dead == False): #you can step over dead enemies
                        #ai hack, our ai is so bad that we will allow realism!
                return True
        return False

    def getPassableTile(self, a, x, y): #Actor a, int x, int y
        #True=go, False = stop
        #int tdestx = a.tilex+a.facing_x
        #int tdesty = a.tiley+a.facing_y
        walls_layer = getLayerIndex("walls_layer")
        if (getTileImage(x, y, walls_layer) == None): #There are no walls.
            if (self.turn_order.equals("monster")): #does monster collide with someone.
                if (self.monsterfollowerInTile(x, y) == True):
                    return False #encountered a monster or follower?
                if (x == self.player.tilex and y == self.player.tiley):
                    return False #found player.
            return True #There are no walls.
        #System.out.println("Encountered a wall")
        return False#there are walls

    #return False #there might be a wall
    def onClickOnMap(self, mouse_tile_x, mouse_tile_y): #given Mouse Pixels, decide what to do
        #did we click on the players rectangle as it is rendered in the map
        if (mouse_tile_x == player.tilex and mouse_tile_y == player.tiley):
            #if(pixelX >= 0 and pixelY >=0 and pixelX <= 128 and pixelY <=128)
            #{ #you clicked on player now set the player to selected, and mode to
            if (player.isSelected() == False):
                player.onSelectActor(True) #omg you selected an actor!
            elif:
                player.onSelectActor(False)

    def getScreenToIsoX(self, screenx, screeny, ht): #(int screenx, int screeny, HorrorTactics ht)
        isoX = (screenx / self.TILE_WIDTH_HALF + screeny / self.TILE_HEIGHT_HALF) / 2
        return isoX
    def getScreenToIsoY(self, screenx, screeny, ht): #(int screenx, int screeny, HorrorTactics ht):
        isoY = (screeny / TILE_HEIGHT_HALF - (screenx / TILE_WIDTH_HALF)) / 2
        return isoY
    def getIsoXToScreen(self, x, y):
        posX = (x - y) * (250 / 2)
        return posX

    def getIsoYToScreen(self, x, y):
        posY = (x + y) * 130 / 2
        return posY

    self getAnyActorMoving(self) #is anyone moving? return True
        m = False
        if (self.player.getActorMoving() == True):
            System.out.print("player was still moving")
            if (self.player.dead == False):
                return True
        elif:
            m = False
        for i in range(0, self.monster_max): #(int i = 0 i < monster_max i+=1):
            if (self.monster[i].dead == False and self.monster[i].getActorMoving() == True):
                print("Monster [" + i + "] was still moving\n")
                if (self.monster[i].dead == False):
                    return True
            elif:
                m = False
        for (int i = 0 i < follower_max i+=1):
            if (self.follower[i].getActorMoving() == True):
                System.out.print("Follower [" + i + "] was still moving\n")
                if (self.follower[i].dead == False):
                    return True
                }
            } else {
                m = False
            }
        }
        return m
    }

    public boolean isActorTouchingActor(Actor a, Actor b, int x, int y):
        #a=monster, b=player #x, and y not used?
        if (a.tilex - 1 == b.tilex and a.tiley == b.tiley):
            return True
        elif(a.tilex + 1 == b.tilex and a.tiley == b.tiley):
            return True
        elif(a.tilex == b.tilex and a.tiley - 1 == b.tiley):
            return True
        elif(a.tilex == b.tilex and a.tiley + 1 == b.tiley):
            return True #you are touching the player.
        }
        return False
    }

    public boolean isPlayerTouchingMonster():
        for (int i = 0 i < self.monster_max i+=1): #for(int i)
            if (self.isActorTouchingActor(player, monster[i], player.tilex, player.tiley)):
                return True
            }
        }
        return False
    }

    public boolean getPlayerFacingMonInDir(Actor p, Actor m, int direction, int x, int y):
        if (p.direction == direction
                and p.tilex + x == m.tilex
                and p.tiley + y == m.tiley):
            return True
        }
        return False
    }

    public boolean getPlayerFacingMonster(Actor p):
        if (self.isPlayerTouchingMonster() == True):
            for (int i = 0 i < self.monster_max i+=1):
                if (getPlayerFacingMonInDir(p, monster[i], p.getEast(), 1, 0)):
                    return True
                }
                if (getPlayerFacingMonInDir(p, monster[i], p.getWest(), -1, 0)):
                    return True
                }
                if (getPlayerFacingMonInDir(p, monster[i], p.getNorth(), 0, -1)):
                    return True
                }
                if (getPlayerFacingMonInDir(p, monster[i], p.getSouth(), 0, 1)):
                    return True
                }
                return False
            }
        }
        return False
    }

    public boolean isMonsterTouchingYou(Actor a):
        #given tilex, tiley, return True if any items in grid are touching you.
        if (self.isActorTouchingActor(a, self.player, a.tilex, a.tiley)):#you are touching the player.
            return True
        }
        for (int i = 0 i < self.follower_max i+=1):
            if (self.isActorTouchingActor(a, self.follower[i], a.tilex, a.tiley) 
                    and self.follower[i].dead == False): #you are touching the player.
                #avoid touching a dead follower and attacking player from afar.
                return True
            } #returned True, and set monster dest to the victims tilex,tily
        }
        return False #nobody touching monster, 
    }

    public boolean getActorAtXy(Actor a, int x, int y):
        if (a.tilex == x and a.tiley == y):
            return True
        } else {
            return False
        }
    }

    Actor getAllPlayersAtXy(int x, int y):
        Actor t
        t = None
        if (self.getActorAtXy(player, x, y)):
            t = player
        }
        for (int i = 0 i < self.follower_max i+=1):
            if (self.getActorAtXy(follower[i], x, y)):
                t = self.follower[i]
            }
        }
        for (int i = 0 i < self.monster_max i+=1):
            if (self.getActorAtXy(monster[i], x, y)):
                t = self.monster[i]
            }
        }
        return t #found nothing
    }

    Actor getAllMonstersAtXy(int x, int y):
        for (int i = 0 i < self.monster_max i+=1):
            if (self.getActorAtXy(monster[i], x, y)):
                return self.monster[i]
            } else {
                return None
            }
        }
        return None
    }

    public void onMonsterCanAttack(GameContainer gc, HorrorTactics ht):
        if (self.isMonsterTouchingYou(monster[self.current_monster_moving])):
            Actor t = self.getAllPlayersAtXy(
                    monster[self.current_monster_moving].tiledestx,
                    monster[self.current_monster_moving].tiledesty)
            if (t == None):
                System.out.println("woa something is wrong monster target is None")
            } else {
                System.out.println("t is not None, found " + t.name)
            }
            if (t.dead == False):
                monster[self.current_monster_moving].onAttack(ht)
                monster[self.current_monster_moving].onActorAttackActor(ht, t)
            }
            #player at the monster destination is not None

        } else {
            System.out.println("Monster " + self.current_monster_moving + " found nobody there?")
        }#nobody was there.
    }

    public boolean getMonsterIsMoving(int i):
        if (self.monster[i].action_points <= 0):
            return False
        }
        if (self.monster[i].visible == False):
            return False
        }
        if (self.monster[i].dead == True):
            return False
        }
        if (self.monster[i].getActorMoving() == False):
            return False
        }
        #if (self.getPassableTile(self.monster[i], i, i))
        return True #all conditions are good.
    }
    public boolean getFollowersCanMove():
       int total_action_points = 0       
       for (int i = 0 i < self.follower_max i+=1):
            #self.follower[i].onMoveActor(self, gc.getFPS())
            total_action_points += self.follower[i].action_points
        }       
       if(total_action_points > 0): #False means no one can move
           return True
       }
       return False
    }
    public void onFollowerMoving(GameContainer gc, HorrorTactics ht, int delta): #taken from update.
        #self.follower[self.current_follower_moving].onMoveActor(
        #        self, gc.getFPS())
        
        for (int i = 0 i < self.follower_max i+=1):
            self.follower[i].onMoveActor(self, gc.getFPS())
            #self.follower[i].action_points += total_action_points
        }
        
        
        #if (self.follower[self.current_follower_moving].dead == True):
        #    self.current_monster_moving+=1
        #}
        #if (self.follower[self.current_follower_moving].getActorMoving()
        #        == False):
        #Why did follower stop? (because you turn him to attack)
        #self.whyDidMonsterStop(gc, ht)
        #}
        #if (self.current_follower_moving >= self.follower_max):
        #    self.current_follower_moving = 0
        #self.turn_order = "start player"
        #we still start monster in another way.
        #}
    }

    public void onMonsterMoving(GameContainer gc, HorrorTactics ht, int delta): #taken from update.
        if (monster[0].spotted_enemy == True):
            self.monster[self.current_monster_moving].onMoveActor(
                    self, gc.getFPS())
            if (self.monster[self.current_monster_moving].dead == True):
                self.current_monster_moving+=1
            }
            if (self.monster[self.current_monster_moving].getActorMoving()
                    == False):
                self.whyDidMonsterStop(gc, ht)
            }
            if (self.current_monster_moving >= self.monster_max):
                self.current_monster_moving = 0
                self.turn_order = "start player"
            }
        } else {
            #self.current_monster_moving = 0
            self.monster[self.current_monster_moving].stopMoving()
            self.current_monster_moving+=1
            if (self.current_monster_moving >= self.monster_max):
                self.current_monster_moving = 0
                self.turn_order = "start player"
            }
        }
    }

    void onUpdateActorActionText():
        if (player.action_msg_timer > 0):
            player.action_msg_timer--
        }
        for (int i = 0 i < self.follower_max i+=1):
            if (follower[i].action_msg_timer > 0):
                follower[i].action_msg_timer--
            }
        }
        for (int i = 0 i < self.monster_max i+=1):
            if (monster[i].action_msg_timer > 0):
                monster[i].action_msg_timer--
            }
        }
    }

    #map.monster[0].drawActor(self, map, x, y)
    public int getDistanceOfActors2(Actor a, Actor b):
        int xs = (a.tilex - b.tilex) * (a.tilex - b.tilex)
        int ys = (a.tiley - b.tiley) * (a.tiley - b.tiley)

        double dsr = Math.sqrt(xs + ys)
        int distance = (int) dsr
        return distance
    }

    public int getMaxFollowers():
        int count = 0
        #if(player)#assume player is alive
        for (int i = 0 i < self.follower_max i+=1):
            if (follower[i].visible and follower[i].dead == False):
                count+=1
            }
        }
        return count
    }

    public void setMonsterDirectives():
        #loop through your monsters and set a path for them to follow
        #directive types: random,randomuntilspotted,beeline,
        int proposed_x, proposed_y
        self.current_monster_moving = 0
        int max_active_followers = self.getMaxFollowers()
        for (int i = 0 i < self.monster_max i+=1):
            if (monster[i].visible == True and monster[i].dead == False): #there was a monster here(and alive)
                monster[i].action_points = 6 #update action points
                monster[i].setActorMoving(True)
                monster[i].setActorDestination(monster[i].tilex, monster[i].tiley)#there initial destination is their pos
            } else {
                monster[i].setActorMoving(False)
                monster[i].action_points = 0
            }
            if (monster[i].max_turns_till_revival > 0 and monster[i].dead == True):
                if (monster[i].turns_till_revival >= monster[i].max_turns_till_revival):
                    monster[i].dead = False
                    monster[i].turns_till_revival = 0
                } else {
                    monster[i].turns_till_revival+=1
                }
            }
        }
        for (int i = 0 i < self.monster_max i+=1):
            if (monster[i].visible == True): #there was a monster here.
                monster[i].action_points = 6 #update action points
                if (monster[i].directive_type.equalsIgnoreCase("beeline")):
                    #how can we make the monster's dest to the next to player/not the players location
                    #self will prvent a forced stop.  How do we calulate the shortest distance to travel
                    #Euclidian plane? find the shortest distance, then make the route
                    System.out.print("Spotted player: " + monster[i].spotted_enemy + "\n")
                    #int actor_attackroll = ThreadLocalRandom.current().nextInt(1, 6 + 1) + self.stat_luck - 1 + self.getAttackPenalty()
                    #int targetActor = ThreadLocalRandom.current().nextInt(0, max_active_followers)
                    if (monster[i].spotted_enemy == True): # you must spot the enemy to beline
                        monster[i].tiledestx = player.tilex
                        monster[i].tiledesty = player.tiley
                        int best_distance = self.getDistanceOfActors2(player, monster[i])
                        int try_distance = 0
                        for (int d = 0 d < self.follower_max d+=1):
                            if (follower[d].visible == True and follower[d].dead == False): #check if visible and alive(9/22/2018)
                                try_distance = self.getDistanceOfActors2(follower[d], monster[i])
                                if (try_distance < best_distance):
                                    monster[i].tiledestx = follower[d].tilex
                                    monster[i].tiledesty = follower[d].tiley
                                }
                            }
                        }
                    } else {
                        monster[i].tiledestx = monster[i].tilex
                        monster[i].tiledesty = monster[i].tiley
                    }
                elif(monster[i].directive_type.equalsIgnoreCase("random")): #randomly move around.
                    for (int count = 0 count < 6 count+=1):
                        proposed_y = (int) Math.floor(Math.random()) - (int) Math.floor(Math.random())
                        proposed_x = (int) Math.floor(Math.random()) - (int) Math.floor(Math.random())
                        System.out.print("proposed_x: " + Integer.toString(proposed_x)
                                + " proposed_y: " + Integer.toString(proposed_y))
                        if (self.getPassableTile(monster[i], monster[i].tilex + proposed_x,
                                monster[i].tiley + proposed_y) == True):
                            monster[i].setActorDestination(monster[i].tilex + proposed_x,
                                    monster[i].tiley + proposed_y)
                        }
                    }
                }
            }
        }
    }

    public void resetAttackAniFrame():
        for (int i = 0 i < self.monster_max i+=1):
            if (self.monster[i].attack_timer > 0):
                self.monster[i].attack_timer--
                #self.monster[i].setAnimationFrame(0)
            elif(self.monster[i].getAnimationFrame() == 4):
                self.monster[i].setAnimationFrame(0)
            }
        }
        for (int i = 0 i < self.follower_max i+=1):
            if (self.follower[i].attack_timer > 0):
                self.follower[i].attack_timer--
                #self.monster[i].setAnimationFrame(0)
            elif(self.follower[i].getAnimationFrame() == 4):
                self.follower[i].setAnimationFrame(0)
            }
        }
        if (self.player.attack_timer > 0):
            self.player.attack_timer--
        elif(self.player.getAnimationFrame() == 4):
            self.player.setAnimationFrame(0)
        }
    }

    public void whyDidMonsterStop(GameContainer gc, HorrorTactics ht):
        System.out.println("Monster stopped with remaining AP")
        if (self.monster[self.current_monster_moving].visible == False):
            self.current_monster_moving+=1
        elif(self.monster[self.current_monster_moving].dead == True):
            self.current_monster_moving+=1
        elif(self.monster[self.current_monster_moving].action_points >= 2):
            self.onMonsterCanAttack(gc, ht) #Do we see the player?
            self.monster[self.current_monster_moving].action_points = 0
            #self.monster[self.current_monster_moving].setAnimationFrame(0)
            #we can also try doing self at the beginning of update.
            self.current_monster_moving+=1
        elif(self.getPassableTile(monster[self.current_monster_moving],
                monster[self.current_monster_moving].tilex
                + monster[self.current_monster_moving].facing_x,
                monster[self.current_monster_moving].tiley
                + monster[self.current_monster_moving].facing_y) == False):
            #see if monster can move around tiles
            #set back to the original desination?
            self.current_monster_moving+=1
        elif(self.monster[self.current_monster_moving].action_points <= 0):
            self.current_monster_moving+=1
        elif(self.monster[self.current_monster_moving].tilex == self.monster[self.current_monster_moving].tiledestx
                and self.monster[self.current_monster_moving].tiley == self.monster[self.current_monster_moving].tiledesty):
            self.current_monster_moving+=1
        elif(self.monster[self.current_monster_moving].getActorMoving() == False):
            #generic stop
            self.current_monster_moving+=1
        } else {#you stopped and cant do anything anyways
            System.out.println("Monster had not enough points to attack")
            self.monster[self.current_monster_moving].action_points = 0
            self.current_monster_moving+=1
        }
        #allmonstersmoved = False
    }

    public void set_party_min_renderables():
        int current_min_y = self.player.tiley 
        int current_min_x = self.player.tilex
        int current_max_y = 0
        int current_max_x = 0
        if (self.player.tiley < current_min_y):
            current_min_y = self.player.tiley
        }
        if (self.player.tilex < current_min_x):
            current_min_y = self.player.tilex
        }
        if (self.player.tiley > current_max_y):
            current_max_y = self.player.tiley
        }
        if (self.player.tilex > current_max_x):
            current_max_x = self.player.tilex
        }
        for (int i = 0 i < self.follower_max i+=1):
            if (self.follower[i].visible and self.follower[i].tiley < current_min_y):
                current_min_y = self.follower[i].tiley
            }
            if (self.follower[i].visible and self.follower[i].tilex < current_min_x):
                current_min_x = self.follower[i].tilex
            }
            if (self.follower[i].visible and self.follower[i].tiley > current_max_y):
                current_max_y = self.follower[i].tiley
            }
            if (self.follower[i].visible and self.follower[i].tilex > current_max_x):
                current_max_x = self.follower[i].tilex
            }
        }
        self.render_min_y = current_min_y
        self.render_min_x = current_min_x
        self.render_max_y = current_max_y
        self.render_max_x = current_max_x
        #System.out.print(
        #        "render_min_x:"+ self.render_min_x+ 
        #        " render_min_y:"+self.render_min_y + 
        #        " render_max_x:"+self.render_max_x+ 
        #        " render_max_y:" +self.render_max_y  )
    }

    void mouse_over_actor(int x, int y):
        if (self.player.tilex == x and self.player.tiley == y):
            self.player.ismouse_over = True
        } else {
            self.player.ismouse_over = False
        }
        for (int i = 0 i < self.follower_max i+=1):
            if (self.follower[i].tilex == x and self.follower[i].tiley == y):
                self.follower[i].ismouse_over = True
            } else {
                self.follower[i].ismouse_over = False
            }
        }
        for (int i = 0 i < self.monster_max i+=1):
            if (self.monster[i].tilex == x and self.monster[i].tiley == y):
                self.monster[i].ismouse_over = True
            } else {
                self.monster[i].ismouse_over = False
            }
        }

    }
}
