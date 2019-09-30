/*
 * The point of this class is to give the user hint/tips on how to play the game
 * The hints can be turned off by a toggle in the HorrorTactcis Class.
 * Each map has its own hint
 */
package horrortactics;

/**
 *
 * @author tcooper
 */
public class Settings {
    public boolean toggle_hints; //true its on, false its off;
    public boolean tottle_fullscreen; //fullscreen or windowed
    public boolean toggle_sound;
    public Settings() {
        //set defaults, again, how to save them?
        this.toggle_hints = true;
        this.toggle_sound = true;
        this.tottle_fullscreen = true;
    }
    
}
