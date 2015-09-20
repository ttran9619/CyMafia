package teamsb.isumafia;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by enclark on 9/18/2015.
 */
public class Citizen extends Person{

    //Creates a new player
    public Citizen(String id, String name, GameState GS)
    {
        super(id,name, GS);
    }

    // Returns the role of the player
    public Role who()
    {
        return Role.CITIZEN;
    }

    //This is the job of the citizen
    public void doJob(Context context, Person person)
    {
        if(GS.timeDayNight)
        {
            //Their vote only counts if they are alive
            int duration = Toast.LENGTH_LONG;
            String text;
            if(isAlive())
            {
                //If it is day, their job is to vote for a person
                person.vote();
                duration = Toast.LENGTH_LONG;
                text = "You have voted to kill " + person.getName();
            }else
            {
                text = "Thank you for passing your turn";
            }
            Toast toast = Toast.makeText(context, text, duration);

        }
        else
        {
            int duration = Toast.LENGTH_LONG;
            //We will create a toast so they know it worked
            Toast toast = Toast.makeText(context, "Thank you for choosing a player", duration);
            toast.show();
        }

    }
}