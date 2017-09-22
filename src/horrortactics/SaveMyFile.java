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

    public void savePlayerMapData(HorrorTactics ht, String slot) { //save your progress
        try {
            FileWriter fstream = new FileWriter("Save" + slot + ".txt");
            BufferedWriter out = new BufferedWriter(fstream);
            //out.write("String newmap");
            out.write(ht.map.mapname + ";");
            out.write(ht.map.player.name + ";");
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
                System.out.println(scanner.nextLine());
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
