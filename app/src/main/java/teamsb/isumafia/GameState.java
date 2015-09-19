package teamsb.isumafia;

import java.util.ArrayList;

/**
 * Created by Tyler on 9/19/2015.
 */
public class GameState {

    public ArrayList<Person> People;

    public boolean timeDayNight; //0 == day, 1 == night
    public boolean mafiaWin;
    public boolean citizenWin;

    // For Testing, please ignore
    public static byte[] transformHolder;
    public static Person dave2;


    public GameState(){
        ArrayList<Person> People = new ArrayList<Person>();
        timeDayNight = false;
        mafiaWin = false;
        citizenWin = false;
    }

    public GameState(GameState old){
        this.timeDayNight=old.timeDayNight;
        this.mafiaWin=old.mafiaWin;
        this.citizenWin=old.citizenWin;

        int i=0;
        for(Person peep : old.People){
            this.People.set(i,old.People.get(i));
            ++i;
        }
    }




}
