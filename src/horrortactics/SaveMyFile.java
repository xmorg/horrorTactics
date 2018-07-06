/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author tcooper
 */
public class SaveMyFile {

    String Slot = "1";

    public SaveMyFile() {
    }

    public void saveData(HorrorTactics ht, String slot) {
        try {
            FileWriter fstream = new FileWriter("Save" + slot + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("Hello Java");
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveActorData(Actor a, String a_type, BufferedWriter out) throws java.lang.Exception {
        //Fix, it should be a_type,a.name,....   ; is for the first split and , is for the second split
        out.write(a_type + ","+a.name + ",");
        out.write(Integer.toString(a.draw_x)+",");
        out.write(Integer.toString(a.draw_y)+",");
        //more stuff.
        out.write(";");
    }
    public void savePlayerMapData(HorrorTactics ht, String slot) { //save your progress
        try { //putting the relevent map, player, follower, monster info into a file.
            FileWriter fstream = new FileWriter("Save" + slot + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            //out.write("String newmap");
            out.write(ht.map.mapname + ";");
            saveActorData(ht.map.player, "player" ,out);
            for(int i=0; i < ht.map.follower_max; i++) {
                saveActorData(ht.map.follower[i], "follower", out);
            }
            for(int i=0; i < ht.map.monster_max; i++) {
                saveActorData(ht.map.monster[i], "monster", out);
            }
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine()); //we have the file
                //parse the string?
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        //butcher_shop01.tmx;player{Riku;0;0;}follower{Officer Ayano;0;0;}follower{Takeshi;0;0;}follower{none;0;0;}follower{none;0;0;}monster{butcher;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}monster{none;0;0;}
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
                for(i=0; i < ht.map.follower_max; i++) { //loop through each follower
                    //for each follower, 
                    String p[] = t[i+2].split(","); //split the split string
                    //now for p
                    
                }
                for(i=0; i < ht.map.monster_max; i++) { //loop through each monster
                    String m[] = t[i+2+ht.map.follower_max].split(","); //split the split string
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //return "none"; // there was no map
    }
}
