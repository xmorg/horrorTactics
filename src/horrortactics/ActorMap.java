/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horrortactics;

/**
 *
 * @author Tim Cooper - ActorMap is an array of where the actors on on scren
 */
public class ActorMap {
    //int[ ][ ] tiles = new int[][];
    int [][]tiles;
    public ActorMap(int x, int y) {
        tiles = new int[y][x]; //set the size
        for(int yi =0; yi < y; yi++) {
            for(int xi=0; xi < x; xi++) {
                tiles[yi][xi] = 0;
            }
        }
    }
    //publicActorMap
}
