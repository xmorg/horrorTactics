#/*
# * To change self license header, choose License Headers in Project Properties.
# * To change self template file, choose Tools | Templates
# * and open the template in the editor.
# */
#package horrortactics #self one works!

#import java.io.*
#import java.util.Scanner

#/**
#3 *
# * @author tcooper blah blah blah
# */
class SaveMyFile: # {
    def __init__(self): # SaveMyFile():
            self.Slot = "1"
    def saveData(self, ht, slot):
        try: #python on writing files
            fstream = FileWriter("Save" + slot + ".txt")
            out = BufferedWriter(fstream)
            #out.write("Hello Java")
            out.close()
        except:#Catch exception if any
            print("Error: " + e.getMessage())
    def saveActorData(self, a, m, a_type, out): # throws java.lang.Exception {
        #Fix, it should be a_type,a.name,....    is for the first split and , is for the second split
        out.write(a_type + ","+a.name + ",")
        out.write(Integer.toString(a.tilex)+",") #tilex/y
        out.write(Integer.toString(a.tiley)+",") #more stuff.        
        out.write(Boolean.toString(a.dead)+",") #boolean dead        
        out.write(Integer.toString(a.health_points)+",")#int health_points, 
        out.write(Integer.toString(a.health_points_max)+",")#health_points_max #lose all heath and die
        out.write(Integer.toString(a.fatigue_points)+",")#int fatigue_points, 
        out.write(Integer.toString(a.fatigue_points_max)+",")#fatigue_points_max
        out.write(Integer.toString(a.mental_points)+",")#int mental_points, 
        out.write(Integer.toString(a.mental_points_max)+",")#mental_points_max #lose all mental, and get an attack penalty.        
        out.write(Integer.toString(a.stat_str)+",")#int stat_str, #health(on level up) and damage
        out.write(Integer.toString(a.stat_speed)+",")#stat_speed, #movement and dodge
        out.write(Integer.toString(a.stat_will)+",")#stat_will, #fatigue and health (on level up)
        out.write(Integer.toString(a.stat_luck)+",")#stat_luck #chance to dodge / chance to hit
        out.write(Integer.toString(a.exp_level)+",")#int exp_level, 
        out.write(Integer.toString(a.exp_points)+",")#exp_points #level up = exp_level+1 * exp_level+1*10
        out.write(Boolean.toString(m.EventSpotted_ran)+",")#boolean EventSpotted_ran = False
        out.write(Boolean.toString(m.EventSpotted_ran1)+",")#boolean EventSpotted_ran = FalseEventSpotted_ran1
        out.write(Boolean.toString(m.EventGoal_ran)+",")#boolean EventGoal_ran = False
        out.write(Boolean.toString(m.EventExit_ran)+",")#boolean EventExit_ran = False
        #out.write(m.maptitle+",")
        #save the map name, and triggers, (intro, quest, and saw enemy)
        #Its still running the intro.
        out.write("")
    def savePlayerMapData(self, ht, slot): #save your progress
        try: #putting the relevent map, player, follower, monster info into a file.
            fstream = FileWriter("Save" + slot + ".txt")
            out = BufferedWriter(fstream)
            #out.write("String newmap")
            out.write(ht.map.mapname + "")
            saveActorData(ht.map.player, ht.map, "player" ,out)
            for i in range(0, ht.tiledmap.follower_max): #(int i=0 i < ht.map.follower_max i++):
                saveActorData(ht.tledmap.follower[i], ht.map, "follower", out)
            for i in range(0, ht.tiledmap.monster_max):
                saveActorData(ht.tiledmap.monster[i], ht.map, "monster", out)
            out.close()
        except:#Catch exception if any
            print("Error: " + e.getMessage())
    def readFile(self, fileName):
        try:
            file = File(fileName)
            scanner = Scanner(file)
            while (scanner.hasNextLine()):
                print(scanner.nextLine()+"\n") #we have the file
            
            scanner.close()
        except: # catch (FileNotFoundException e):
            return False
        return True
    def checkForMapInSaveFile(self, filename): #if a save file exist, get the mapname
        #test if the file exists
        #String filedata
        #String mapname
        f = File(filename)
        if (f.exists() and f.isDirectory() == False):
            try:
                scanner = Scanner(f)
                #while (scanner.hasNextLine()):
                #    print(scanner.nextLine())  
                #
                filedata = scanner.next()
                t = filedata.split("")
                mapname = t[0]
                scanner.close()
                return mapname
            except: #catch (FileNotFoundException e):
                #e.printStackTrace()
                print("File not found")
        return "none" # there was no map
    
    def checkMapSettingsInSaveFile(self, ht, filename): #if a save file exist, get the mapname
        #example of what the save file looks like.
        #tutorial01.tmxplayer,Riku,9,7,follower,Takeshi,10,2,follower,none,0,0,follower,none,0,0,follower,none,0,0,monster,tutor_bully1,23,2,monster,tutor_bully0,22,5,monster,none,0,0,monster,none,0,0,monster,none,0,0,monster,none,0,0,monster,none,0,0,monster,none,0,0,monster,none,0,0,monster,none,0,0,
        #String filedata
        #String mapname
        f = File(filename)
        if (f.exists() and f.isDirectory() == False):
            try:
                i=0
                scanner = Scanner(f)
                filedata = scanner.next()
                t = filedata.split("")
                #mapname = t[0]
                #map=0player=1 i=1+(iteratthrough1+i to map.follower_max,follower=2,follower=3-5, monster=6-
                #monster= i, i= 1+map.follower_max to 1+map.follower_max+map.monster_max
                #String p[] = t[i].split(",")
                #NOTE!~ we assume that the current map has already been loaded at self point and can access follower_max and monster_max
                #^- there is no delination between monsters/followers in the parsing. although type,name,stats and you can test for "type"
                r = t[1].split(",") #player,Riku,9,7,
                if( r[0] == "player" ): #{ # its Riku
                    self.loadActorFromParsedData(ht.map, ht.map.player, t[1])
                for i in range(0, ht.tiledmap.follower_max): #(i=0 i < ht.map.follower_max i++): #loop through each follower
                    #for each follower, 
                    #String p[] = t[i+2].split(",") #split the split string
                    self.loadActorFromParsedData(ht.map, ht.tiledmap.follower[i], t[i+2])                    
                
                for i in range(0, ht.tiledmap.monster_max): #(i=0 i < ht.map.monster_max i++): #loop through each monster
                    #String m[] = t[i+2+ht.map.follower_max].split(",") #split the split string
                    self.loadActorFromParsedData(ht.tiledmap, ht.map.monster[i], t[i+2+ht.map.follower_max])
                scanner.close()
            except: # catch (FileNotFoundException e):
                print("file not found exception") #e.printStackTrace()
        #return "none" # there was no map
    def loadActorFromParsedData(self, m, a, data):
        print(data +"\n")
        r = data.split(",")
        a.name = r[1] #Actor.name
        a.tilex = Integer.parseInt(r[2]) #Actor.tilex
        a.tiley = Integer.parseInt(r[3]) #Actor.tiley
        a.dead = Boolean.valueOf(r[4])#out.write(Boolean.toString(a.dead)+",") #boolean dead        
        a.health_points = Integer.parseInt(r[5]) #out.write(Integer.toString(a.health_points)+",")#int health_points, 
        a.health_points_max = Integer.parseInt(r[6]) #out.write(Integer.toString(a.health_points_max)+",")#health_points_max
        a.fatigue_points = Integer.parseInt(r[7])#out.write(Integer.toString(a.fatigue_points)+",")#int fatigue_points, 
        a.fatigue_points_max = Integer.parseInt(r[8])#out.write(Integer.toString(a.fatigue_points_max)+",")#fatigue_points_max
        a.mental_points = Integer.parseInt(r[9])#out.write(Integer.toString(a.mental_points)+",")#int mental_points, 
        a.mental_points_max = Integer.parseInt(r[10])#out.write(Integer.toString(a.mental_points_max)+",")#mental_points_max #lose all mental, and get an attack penalty.        
        a.stat_str = Integer.parseInt(r[11])#out.write(Integer.toString(a.stat_str)+",")#int stat_str, #health(on level up) and damage
        a.stat_speed = Integer.parseInt(r[12])#out.write(Integer.toString(a.stat_speed)+",")#stat_speed, #movement and dodge
        a.stat_will = Integer.parseInt(r[13])#out.write(Integer.toString(a.stat_will)+",")#stat_will, #fatigue and health (on level up)
        a.stat_luck = Integer.parseInt(r[14])#out.write(Integer.toString(a.stat_luck)+",")#stat_luck #chance to dodge / chance to hit
        a.exp_level = Integer.parseInt(r[15])#out.write(Integer.toString(a.exp_level)+",")#int exp_level, 
        a.exp_points = Integer.parseInt(r[16])#out.write(Integer.toString(a.exp_points)+",")#exp_points #level up = exp_level+1 * exp_level+1*10
        m.EventSpotted_ran = Boolean.parseBoolean(r[17])#out.write(Boolean.toString(m.EventSpotted_ran))
        m.EventSpotted_ran1 = Boolean.parseBoolean(r[18])
        m.EventGoal_ran = Boolean.parseBoolean(r[19])#out.write(Boolean.toString(m.EventGoal_ran))
        m.EventExit_ran = Boolean.parseBoolean(r[20])#out.write(Boolean.toString(m.EventExit_ran))
        
        #out.write(m.maptitle+",")
        #out.write("")
        #After loading you need to scroll to the player