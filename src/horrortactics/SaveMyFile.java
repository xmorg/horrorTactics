/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics; //this one works!

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author tcooper blah blah blah
 */
public class SaveMyFile {

    String Slot = "1";

    public SaveMyFile() {
    }

    public void saveData(HorrorTactics ht, String slot) {
        try {
            FileWriter fstream = new FileWriter("Save" + slot + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            //out.write("Hello Java");
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveActorData(Actor a, MyTiledMap m, String a_type, BufferedWriter out) throws java.lang.Exception {
        //Fix, it should be a_type,a.name,....   ; is for the first split and , is for the second split
        out.write(a_type + ","+a.name + ",");
        out.write(Integer.toString(a.tilex)+","); //tilex/y
        out.write(Integer.toString(a.tiley)+","); //more stuff.        
        out.write(Boolean.toString(a.dead)+","); //boolean dead;        
        out.write(Integer.toString(a.health_points)+",");//int health_points, 
        out.write(Integer.toString(a.health_points_max)+",");//health_points_max; //lose all heath and die
        out.write(Integer.toString(a.fatigue_points)+",");//int fatigue_points, 
        out.write(Integer.toString(a.fatigue_points_max)+",");//fatigue_points_max;
        out.write(Integer.toString(a.mental_points)+",");//int mental_points, 
        out.write(Integer.toString(a.mental_points_max)+",");//mental_points_max; //lose all mental, and get an attack penalty.        
        out.write(Integer.toString(a.stat_str)+",");//int stat_str, //health(on level up) and damage
        out.write(Integer.toString(a.stat_speed)+",");//stat_speed, //movement and dodge
        out.write(Integer.toString(a.stat_will)+",");//stat_will, //fatigue and health (on level up)
        out.write(Integer.toString(a.stat_luck)+",");//stat_luck; //chance to dodge / chance to hit
        out.write(Integer.toString(a.exp_level)+",");//int exp_level, 
        out.write(Integer.toString(a.exp_points)+",");//exp_points; //level up = exp_level+1 * exp_level+1*10
        out.write(Boolean.toString(m.EventSpotted_ran)+",");//boolean EventSpotted_ran = false;
        out.write(Boolean.toString(m.EventSpotted_ran1)+",");//boolean EventSpotted_ran = false;EventSpotted_ran1
        out.write(Boolean.toString(m.EventGoal_ran)+",");//boolean EventGoal_ran = false;
        out.write(Boolean.toString(m.EventExit_ran)+",");//boolean EventExit_ran = false;
        //out.write(m.maptitle+",");
        //save the map name, and triggers, (intro, quest, and saw enemy)
        //Its still running the intro.
        out.write(";");
    }
    public void savePlayerMapData(HorrorTactics ht, String slot) { //save your progress
        try { //putting the relevent map, player, follower, monster info into a file.
            FileWriter fstream = new FileWriter("Save" + slot + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            //out.write("String newmap");
            out.write(ht.map.mapname + ";");
            saveActorData(ht.map.player, ht.map, "player" ,out);
            for(int i=0; i < ht.map.follower_max; i++) {
                saveActorData(ht.map.follower[i], ht.map, "follower", out);
            }
            for(int i=0; i < ht.map.monster_max; i++) {
                saveActorData(ht.map.monster[i], ht.map, "monster", out);
            }
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public boolean readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine()); //we have the file
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public String checkForMapInSaveFile(String filename) //if a save file exist, get the mapname
    {
        //test if the file exists
        String filedata;
        String mapname;
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            try {
                Scanner scanner = new Scanner(f);
                //while (scanner.hasNextLine()) {
                //    System.out.println(scanner.nextLine());  
                //}
                filedata = scanner.next();
                String t[] = filedata.split(";");
                mapname = t[0];
                scanner.close();
                return mapname;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "none"; // there was no map
    }
    public void checkMapSettingsInSaveFile(HorrorTactics ht, String filename) //if a save file exist, get the mapname
    {
        //example of what the save file looks like.
        //tutorial01.tmx;player,Riku,9,7,;follower,Takeshi,10,2,;follower,none,0,0,;follower,none,0,0,;follower,none,0,0,;monster,tutor_bully1,23,2,;monster,tutor_bully0,22,5,;monster,none,0,0,;monster,none,0,0,;monster,none,0,0,;monster,none,0,0,;monster,none,0,0,;monster,none,0,0,;monster,none,0,0,;monster,none,0,0,;
        String filedata;
        //String mapname;
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            try {
                int i=0;
                Scanner scanner = new Scanner(f);
                filedata = scanner.next();
                String t[] = filedata.split(";");
                //mapname = t[0];
                //map=0;player=1 i=1+(iteratthrough1+i to map.follower_max,follower=2,follower=3-5, monster=6-
                //monster= i, i= 1+map.follower_max to 1+map.follower_max+map.monster_max
                //String p[] = t[i].split(",");
                //NOTE!~ we assume that the current map has already been loaded at this point and can access follower_max and monster_max
                //^- there is no delination between monsters/followers in the parsing. although type,name,stats and you can test for "type"
                String r[] = t[1].split(","); //player,Riku,9,7,
                if( r[0].equalsIgnoreCase("player") ){ //its Riku
                    this.loadActorFromParsedData(ht.map, ht.map.player, t[1]);
                }
                for(i=0; i < ht.map.follower_max; i++) { //loop through each follower
                    //for each follower, 
                    //String p[] = t[i+2].split(","); //split the split string
                    this.loadActorFromParsedData(ht.map, ht.map.follower[i], t[i+2]);                    
                }
                for(i=0; i < ht.map.monster_max; i++) { //loop through each monster
                    //String m[] = t[i+2+ht.map.follower_max].split(","); //split the split string
                    this.loadActorFromParsedData(ht. map, ht.map.monster[i], t[i+2+ht.map.follower_max]);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //return "none"; // there was no map
    }
    public void loadActorFromParsedData(MyTiledMap m, Actor a, String data) {
        String r[] = data.split(",");
        a.name = r[1]; //Actor.name
        a.tilex = Integer.parseInt(r[2]); //Actor.tilex
        a.tiley = Integer.parseInt(r[3]); //Actor.tiley
        a.dead = Boolean.valueOf(r[4]);//out.write(Boolean.toString(a.dead)+","); //boolean dead;        
        a.health_points = Integer.parseInt(r[5]); //out.write(Integer.toString(a.health_points)+",");//int health_points, 
        a.health_points_max = Integer.parseInt(r[6]); //out.write(Integer.toString(a.health_points_max)+",");//health_points_max;
        a.fatigue_points = Integer.parseInt(r[7]);//out.write(Integer.toString(a.fatigue_points)+",");//int fatigue_points, 
        a.fatigue_points_max = Integer.parseInt(r[8]);//out.write(Integer.toString(a.fatigue_points_max)+",");//fatigue_points_max;
        a.mental_points = Integer.parseInt(r[9]);//out.write(Integer.toString(a.mental_points)+",");//int mental_points, 
        a.mental_points_max = Integer.parseInt(r[10]);//out.write(Integer.toString(a.mental_points_max)+",");//mental_points_max; //lose all mental, and get an attack penalty.        
        a.stat_str = Integer.parseInt(r[11]);//out.write(Integer.toString(a.stat_str)+",");//int stat_str, //health(on level up) and damage
        a.stat_speed = Integer.parseInt(r[12]);//out.write(Integer.toString(a.stat_speed)+",");//stat_speed, //movement and dodge
        a.stat_will = Integer.parseInt(r[13]);//out.write(Integer.toString(a.stat_will)+",");//stat_will, //fatigue and health (on level up)
        a.stat_luck = Integer.parseInt(r[14]);//out.write(Integer.toString(a.stat_luck)+",");//stat_luck; //chance to dodge / chance to hit
        a.exp_level = Integer.parseInt(r[15]);//out.write(Integer.toString(a.exp_level)+",");//int exp_level, 
        a.exp_points = Integer.parseInt(r[16]);//out.write(Integer.toString(a.exp_points)+",");//exp_points; //level up = exp_level+1 * exp_level+1*10
        m.EventSpotted_ran = Boolean.parseBoolean(r[17]);//out.write(Boolean.toString(m.EventSpotted_ran))
        m.EventSpotted_ran1 = Boolean.parseBoolean(r[18]);
        m.EventGoal_ran = Boolean.parseBoolean(r[19]);//out.write(Boolean.toString(m.EventGoal_ran));
        m.EventExit_ran = Boolean.parseBoolean(r[20]);//out.write(Boolean.toString(m.EventExit_ran));
        
        //out.write(m.maptitle+",");
        //out.write(";");
        //After loading you need to scroll to the player
    }
}
