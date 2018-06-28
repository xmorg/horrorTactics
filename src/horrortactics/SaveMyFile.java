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
        //out.write(mapname + ";");
        out.write(a_type + "{"+a.name + ";");
        out.write(Integer.toString(a.draw_x)+";");
        out.write(Integer.toString(a.draw_y)+";");
        //more stuff.
        out.write("}");
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
}
