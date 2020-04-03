/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;


//import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
/**
 *
 * @author tcooper
 */
public class Cutscene {
    String mapname;
    Image[] background = new Image[5]; /* 5 possible backgrounds */
    Image[] l_character = new Image[5]; /* speaking character on the left*/
    Image[] r_character = new Image[5]; /* speaking character on the right*/
    String[] text_1 = new String[5]; /* text per slide */
    String[] text_2 = new String[5]; /* text per scene */
    String[] text_3 = new String[5]; /* text per scene */
    
    public Cutscene(MyTiledMap map, String scenename) {
        //super(scenename);
        //get the name of the map
        //match the name of the map with the data, and load the above.
    }
}
