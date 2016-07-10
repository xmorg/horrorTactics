/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Bug! array out of bounds when reaching the edge of the map
 */
package horrortactics;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
//import org.newdawn.slick.SpriteSheet;
//import org.newdawn.slick.tiled.TiledMap;
//import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 *
 * @author tcooper
 */
public class HorrorTactics extends BasicGame {

    /**
     * @param gamename the command line arguments
     */
    public Input input;
    private MyTiledMap map;
    private MouseActions msa;
    private KeyActions ksa;
    int draw_x, draw_y = 0;
    /* offset for camera scrolling*/
    int screen_x, screen_y;
    /* where on the screen?*/
    int mouse_x, mouse_y = 0;
    int mouse_tile_x, mouse_tile_y = 0;
    int fps = 0; int delta;
    long currentTime, lastTime;
    float scale_x = 1;
    int screen_width = 0;
    int screen_height = 0;
    long lastframe;

    String game_state = "running";

    public HorrorTactics(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        map = new MyTiledMap("data/class_school01.tmx", 0, 0);
        msa = new MouseActions();
        ksa = new KeyActions();
        //input = new Input(gc.getHeight());
        map.getActorLocationFromTMX();
        fps = gc.getFPS();
        this.lastTime = 0; this.lastframe = 0;
        this.currentTime = gc.getTime();
        map.tiles250x129 = new Image("data/tiles250x129.png");
        screen_height = gc.getScreenHeight();
        screen_width = gc.getScreenWidth();
        draw_x = 800; //= map.getIsoXToScreen(map.getPlayerX(), map.getPlayerY()) * -1;
        draw_y = 0; //map.getIsoYToScreen(map.getPlayerX(), map.getPlayerY()) * -1;
        this.lastframe = gc.getTime();
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();
        mouse_x = input.getMouseX();
        mouse_y = input.getMouseY();
        fps = gc.getFPS();
        lastTime = currentTime;
        currentTime = gc.getTime();
        this.delta = this.getMyDelta(gc);
        msa.mouseWasClicked(input, map, this); //Do mouse actions
        ksa.getKeyActions(gc, input, this); //Do keyboard actions
        map.updateMapXY(draw_x, draw_y);
        
        if(map.turn_order == "player") {
            for (int y = map.player.tiley - 3; y < map.player.tiley + 3; y++) {
                for (int x = map.player.tilex - 3; x < map.player.tilex + 3; x++) {
                    //if player turn, if monster turn
                    map.onMoveActor(gc, map.player, this.delta);//this.getMyDelta(gc));
                    //map.monster[0].set_draw_xy(0, 0);
                }
            }
        } else
        if(map.turn_order == "monster") {
            for (int y = map.player.tiley - 3; y < map.player.tiley + 3; y++) {
                for (int x = map.player.tilex - 3; x < map.player.tilex + 3; x++) {
                    //if player turn, if monster turn
                    //map.onMoveActor(map.player, delta);//this.getMyDelta(gc));
                    map.monster[0].set_draw_xy(0, 0);
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.scale(scale_x, scale_x); //scale the same
        int background_layer = map.getLayerIndex("background_layer");
        for (int y = map.player.tiley - 4; y < map.player.tiley + 4; y++) {
            for (int x = map.player.tilex - 4; x < map.player.tilex + 4; x++) {
                screen_x = (x - y) * map.TILE_WIDTH_HALF;
                screen_y = (x + y) * map.TILE_HEIGHT_HALF;
                //check to thissee if mouse is inside screen_x/screen_y rect.
                //asign the selected tile.
                if (x >= 0 && y >= 0 && x <= map.getWidth() && y <= map.getHeight()) {
                    map.getTileImage(x, y, background_layer).draw(
                            screen_x + draw_x, screen_y + draw_y, scale_x);
                    mouse_x = gc.getInput().getMouseX();
                    mouse_y = gc.getInput().getMouseY();
                    int sx = screen_x + draw_x + 30;
                    int sy = screen_y + draw_y + 30;
                    if (mouse_x >= sx && mouse_x <= sx + 250 - 30
                            && mouse_y >= sy && mouse_y <= sy + 130 - 30) {
                        map.mouse_over_tile_x = x;
                        map.mouse_over_tile_y = y;
                        map.tiles250x129.getSubImage(0, 0, 250, 129).draw(
                                screen_x + draw_x, screen_y + draw_y, scale_x);
                    }
                    if (map.player.isSelected() == false
                            && x == map.selected_tile_x
                            && y == map.selected_tile_y) {
                        map.tiles250x129.getSubImage(0, 0, 250, 129).draw(
                                screen_x + draw_x, screen_y + draw_y, scale_x);
                    }
                }
            }
        }

        for (int y = map.player.tiley - 4; y < map.player.tiley + 4; y++) {
            for (int x = map.player.tilex - 4; x < map.player.tilex + 4; x++) {
                screen_x = (x - y) * map.TILE_WIDTH_HALF;
                screen_y = (x + y) * map.TILE_HEIGHT_HALF;
                if (x >= 0 && y >= 0 && x <= map.getTileWidth() && y <= map.getTileHeight()) {
                    int walls_layer = map.getLayerIndex("walls_layer");

                    map.player.drawPlayer(this, map, x, y);
                    map.monster[0].drawActor(this, map, x, y);

                    if (map.getTileImage(x, y, walls_layer) != null) {
                        map.getTileImage(x, y, walls_layer).draw(
                                screen_x + draw_x, screen_y + draw_y - 382, scale_x);
                    }
                }
            }
        }
        map.player.iconImage.draw(5, 50);
        map.monster[0].iconImage.draw(5, 200);
        g.drawString("Player At:" + map.player.tilex + "X" + map.player.tiley + "mouse at:"
                + map.mouse_over_tile_x + "x" + map.mouse_over_tile_y,
                200, 10);
    }

    public int getMyDelta(GameContainer gc)
    {
        long time = gc.getTime();
        int delta = (int) (time - this.lastframe);
        this.lastframe = time;
        if(delta == 0) {return 1;}
        return delta;
    }
    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new HorrorTactics("Horror Tactics"));

            appgc.setDisplayMode(appgc.getScreenWidth(),
                    appgc.getScreenHeight(),
                    true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(HorrorTactics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadNewMap(String newmap) {
        try {
            map = new MyTiledMap(newmap, 0, 0);
        } catch (SlickException ex) {
            Logger.getLogger(HorrorTactics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGameState(String state) {
        game_state = state;
    }
    public MyTiledMap getCurrentMap()
    {
        return this.map;
    }
}
