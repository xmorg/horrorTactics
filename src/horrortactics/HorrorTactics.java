/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;

/**
 *
 * @author tcooper
 */
public class HorrorTactics extends BasicGame {

    /**
     * @param gamename the command line arguments
     */
    private Image tiles250x129,  walls250x512 = null;
    private Image tactics_in_distress00, tactics_in_distress01 = null;
    private SpriteSheet tilesheet, wallsheet = null;
    private SpriteSheet sg1sheet = null; //218x313, sg2sheet = null;
    private TiledMap map;
    
    int draw_x, draw_y = 0;
    float scale_x = 1;
    String game_state = "running";
    
    public HorrorTactics(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
            tiles250x129 = new Image("data/tiles250x129.png");
            walls250x512 = new Image("data/walls250x512.png");
            tactics_in_distress00 = new Image("data/tactics_in_distress00.png");
            tactics_in_distress01 = new Image("data/tactics_in_distress01.png");
            tilesheet = new SpriteSheet(tiles250x129, 250,129);
            wallsheet = new SpriteSheet(walls250x512, 250,512);
            sg1sheet = new SpriteSheet(tactics_in_distress00, 218,313); //fixme! 200x300 is fine?
            map = new TiledMap("data/class_school01.tmx");
        }
        public void loadNewMap(String newmap)
        {
            try
            {
                map = new TiledMap(newmap);
            }
            catch (SlickException ex)
		{
			Logger.getLogger(HorrorTactics.class.getName()).log(Level.SEVERE, null, ex);
		}
        }

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
            //keys?
            Input input = gc.getInput();
            if (input.isKeyPressed(Input.KEY_ESCAPE)) 
            {
                game_state = "exit";
                gc.exit();
            }
            if (input.isKeyDown(Input.KEY_UP)){draw_y++;}
            if (input.isKeyDown(Input.KEY_DOWN)){draw_y--;}
            if (input.isKeyDown(Input.KEY_LEFT)){draw_x++;}
            if (input.isKeyDown(Input.KEY_RIGHT)){draw_x--;}
            if (input.isKeyPressed(Input.KEY_UP)){draw_y=draw_y+129;}
            if (input.isKeyPressed(Input.KEY_DOWN)){draw_y=draw_y-129;}
            if (input.isKeyPressed(Input.KEY_LEFT)){draw_x=draw_x+250;}
            if (input.isKeyPressed(Input.KEY_RIGHT)){draw_x=draw_x-250;}
            if (input.isKeyPressed(Input.KEY_C)){scale_x = scale_x+0.2f;}
            if (input.isKeyPressed(Input.KEY_V)){scale_x = scale_x-0.2f;}
        }

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
            g.scale(scale_x, scale_x); //scale the same
            map.render(0+draw_x, 0+draw_y); //render tild map
            //g.drawString("Howdy!", 10, 10);
            //tilesheet.startUse();
            //tilesheet.getSubImage(4, 0).drawEmbedded(200, 200, 250,129);//, 0, 0);
            //tilesheet.endUse();
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new HorrorTactics("Horror Tactics"));
                        
			appgc.setDisplayMode(appgc.getScreenWidth(), 
                                appgc.getScreenHeight(), 
                                true);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(HorrorTactics.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    
}
