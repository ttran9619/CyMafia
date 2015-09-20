package teamsb.isumafia;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by enclark on 9/18/2015.
 */
public class Detective extends Person {

    //Creates a new Detective
    public Detective(String id, String name)
    {
        super(id, name);
    }

    //This returns the Role of the player
    public Role who()
    {
        return Role.DETECTIVE;
    }

    //Performs the job of the detective and checks if it is a mafioso
    public void doJob(Context context, Person person)
    {
        //creates a new toast notifying the person if he is a mafioso or a citizen
        if(gs.timeDayNight)
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
            String text = person.getName() + " is a ";
            if (person.who() == Role.MAFIA) {
                text += "mafioso";
            } else {
                text += "citizen";
            }
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
        }
    }

}
