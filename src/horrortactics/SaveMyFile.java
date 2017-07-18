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
}
