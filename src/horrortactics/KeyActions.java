/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
//import horrortactics.HorrorTactics;

/**
 *
 * @author tcooper
 */
public class KeyActions {

    public KeyActions() {

    }

    public void getKeyActions(GameContainer gc, Input input, HorrorTactics ht) {
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            ht.setGameState("exit");
            //game_state = "exit";
            gc.exit();
        } else if (input.isKeyDown(Input.KEY_UP)) {
            ht.draw_y++;
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            ht.draw_y--;
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            ht.draw_x++;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            ht.draw_x--;
        } else if (input.isKeyPressed(Input.KEY_UP)) {
            ht.draw_y = ht.draw_y + 129;
        } else if (input.isKeyPressed(Input.KEY_DOWN)) {
            ht.draw_y = ht.draw_y - 129;
        } else if (input.isKeyPressed(Input.KEY_LEFT)) {
            ht.draw_x = ht.draw_x + 250;
        } else if (input.isKeyPressed(Input.KEY_RIGHT)) {
            ht.draw_x = ht.draw_x - 250;
        } else if (input.isKeyPressed(Input.KEY_C)) {
            ht.scale_x = ht.scale_x + 0.2f;
        } else if (input.isKeyPressed(Input.KEY_V)) {
            ht.scale_x = ht.scale_x - 0.2f;
        }else if (input.isKeyPressed(Input.KEY_SPACE)) {
            //advance turn order
            if(ht.getCurrentMap().turn_order.equals("player")) {
                ht.getCurrentMap().turn_order = "monster";
                
            } else if(ht.getCurrentMap().turn_order.equals("monster")) {
                ht.getCurrentMap().turn_order = "player";
                //resetActorActionPoints();
                ht.getCurrentMap().player.resetActorActionPoints();
            }
        } else {
        }

    }
}
