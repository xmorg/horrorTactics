--his is blah

require("mytiledmap")
require("data/class_school01")

HorrorTactics = {
   --input1
   --mouse_actions?
   --key_actions?
   data_dir = "data/",
   
   draw_x = 0,   draw_y = 0,
   screen_x = 0,   screen_y = 0,
   mouse_x = 0,   mouse_y = 0,
   last_mouse_x = 0,   last_mosue_y = 0,
   mouse_tile_x = 0, mouse_tile_y = 0,
   fps = 60,
   delta = nil,
   actor_move_timer = 0,
   --currentTime, lastTime --make your own delta?
   scale_x = 1,
   screen_width = 0, screen_hieght = 0,
   --last_frame (long in java?)
   turn_count = 0,
   myfilter = nil, -- love.graphics.color?
   myfiltert = nil,
   myfilterd = nil,
   button_endturn = nil,
   button_punch = nil,
   --effect_biff, effect_wiff, effect_shrack,
   enemy_moving_message = nil,
   game_state = "tactical",
   --map = nil --{}
}

function init(ht)
   ht.map = nill
   --map.getActorLocationFromTMX();
   ht.fps = love.timer.getFPS()
   --actor_move_timer = 0;
   --this.lastTime = 0;
   --this.lastframe = 0;
   --this.turn_count = 0;
   ht.screen_height = love.graphics.getHeight()
   ht.screen_width  = love.graphics.getWidth()

   --draw_x = map.getIsoXToScreen(map.player.tilex, map.player.tiley) * -1 + this.screen_width / 2;
   --draw_y = map.getIsoYToScreen(map.player.tilex, map.player.tiley) * -1 + this.screen_height / 2;
   --HorrorTactics = love.timer.getTime() --//?check code
   ht.button_endturn = love.graphics.newImage(ht.data_dir.."button_endturn.png")
   ht.button_punch = love.graphics.newImage(ht.data_dir.."button_punch.png")

   ht.effect_biff = love.graphics.newImage(ht.data_dir.."soundeffects/biff.png")
   ht.effect_wiff = love.graphics.newImage(ht.data_dir.."soundeffects/wiff.png")
   ht.effect_shrack = love.graphics.newImage(ht.data_dir.."soundeffects/shrack.png")
   ht.enemy_moving_message = love.graphics.newImage(ht.data_dir.."enemy_moving.png")
   
   ht.myfilter = {255, 255, 255, 255}
   ht.myfiltert = {128, 128, 128, 128}
   ht.myfilterd = {50, 50, 50, 204} --//for darkness/fog
   ht.last_mouse_x = 0 --//input.getMouseX();
   ht.last_mouse_y = 0 --//input.getMouseY();
   

   --mapload_function_here_data
   local gid = 1
   local im = ht.tiles_image
   local fp = "data/class_school01.lua"
   ht.map = love.filesystem.load(fp)()   
   ht.tiles_image = love.graphics.newImage("data/" .. ht.map.tilesets[gid].image)
   ht.tile_quads = {}
   ht.wall_quads = {}
   for y=0, ht.map.tilesets[gid].imageheight / ht.map.tilesets[gid].tileheight do
      for x=0, ht.map.tilesets[gid].imagewidth / ht.map.tilesets[gid].tilewidth do
	 table.insert(ht.tile_quads,
		      love.graphics.newQuad(x*ht.map.tilesets[gid].tilewidth,
					    y*ht.map.tilesets[gid].tileheight,
					    ht.map.tilesets[gid].tilewidth,
					    ht.map.tilesets[gid].tileheight,
					    ht.tiles_image:getDimensions() ) )
      end
   end
   gid = gid+1
   ht.walls_image = love.graphics.newImage("data/"..ht.map.tilesets[gid].image)
   for y=0, ht.map.tilesets[gid].imageheight / ht.map.tilesets[gid].tileheight do
      for x=0, ht.map.tilesets[gid].imagewidth / ht.map.tilesets[gid].tilewidth do
	 table.insert(ht.wall_quads,
		      love.graphics.newQuad(x*ht.map.tilesets[gid].tilewidth,
					    y*ht.map.tilesets[gid].tileheight,
					    ht.map.tilesets[gid].tilewidth,
					    ht.map.tilesets[gid].tileheight,
					    ht.tiles_image:getDimensions() ) )
      end
   end
   
end

function render()
end


function love.load()
   init(HorrorTactics)
end

function love.keypressed(key)
   -- Exit test
   if key == "escape" then
      love.event.quit()
   elseif key =="left" then
      HorrorTactics.draw_x = HorrorTactics.draw_x+250
   elseif key == "right" then
      HorrorTactics.draw_x = HorrorTactics.draw_x-250
   elseif key == "up" then
      HorrorTactics.draw_y = HorrorTactics.draw_y-130
   elseif key == "down" then
      HorrorTactics.draw_y = HorrorTactics.draw_y+130
   end
end

function love.update(dt)
   HorrorTactics.mouse_x = love.mouse.getX()
   HorrorTactics.mouse_y = love.mouse.getY()   
end

function love.draw()
   for y=1,5 do
      for x=1,5 do
	 --print(HorrorTactics.layers["background_layer"].data[y][x]
	 --love.graphics.draw(HorrorTactics.layers["background_layer"].data[y][x])
      end
   end

end


