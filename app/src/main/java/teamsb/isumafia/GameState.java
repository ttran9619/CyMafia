package teamsb.isumafia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Tyler on 9/19/2015.
 */
public class GameState implements  Serializable{

    public ArrayList<Person> People;

    public boolean timeDayNight; //1 == day, 0 == night
    public boolean mafiaWin;
    public boolean citizenWin;

    public String TEST;

    private boolean[] switchHolder;

    // For Testing, please ignore
    public static byte[] transformHolder;
    public static Person dave2;


    public GameState(){
        People = new ArrayList<Person>();
        timeDayNight = false;
        mafiaWin = false;
        citizenWin = false;

        TEST = "WORK";
    }

    public GameState(GameState old){
        this.timeDayNight=old.timeDayNight;
        this.mafiaWin=old.mafiaWin;
        this.citizenWin=old.citizenWin;

        int i=0;
        for(Person peep : old.People){
            this.People.add(i, old.People.get(i));
            ++i;
        }
    }

    //Populates the person array with the players and assigns them their roles.
    public void populate(String[] ids, String[] playerNames)
    {
        //n is the number of players
        int n = playerNames.length;
        Random rand = new Random();
        //This array ensures that the number roles are distributed correctly
        int[] randomInts = new int[4];

//        ArrayList<Role> roles = new ArrayList<Role>();


        for(int i = 0; i < n; i += 1)
        {
            //This will ensure that the correct number of citizens/mafia/nurse/detectives are chosen
            //then populates the people array
//            Collections.shuffle(People);
            boolean found = false;
            while(!found)
            {
                int r = rand.nextInt(3);
                //There can only be one mafia
                if(r == 0 && randomInts[0] < 1)
                {
                    randomInts[0] += 1;
                    People.add(i, new Mafia(ids[i], playerNames[i], this));
                    found = true;
                }
                else if(r == 1 && randomInts[1] < 1)
                {
                    randomInts[1] += 1;
                    People.add(i, new Nurse(ids[i], playerNames[i], this));
                    found = true;
                }
                else if(r == 2 && randomInts[2] < 1)
                {
                    randomInts[2] += 1;
                    People.add(i, new Detective(ids[i], playerNames[i], this));
                    found = true;
                }
                else if(r == 3 && randomInts[3] < (n - 3))
                {
                    randomInts[3] += 1;
                    People.add(i, new Citizen(ids[i], playerNames[i], this));
                    found = true;
                }
            }
        }
    }

    public ArrayList<Person> getArray(){
        return People;
    }

}
