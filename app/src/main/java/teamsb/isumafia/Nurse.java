package teamsb.isumafia;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by enclark on 9/18/2015.
 */
public class Nurse extends Person{

    //Creates a new Nurse
    public Nurse(String id, String name, GameState GS)
    {
        super(id, name, GS);
    }

    //Returns the role of this person
    public Role who()
    {
        return Role.NURSE;
    }

    //Will protect a person
    public void doJob(Context context,Person person)
    {
        //Assuming that day will be the variable
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
            person.save();
<<<<<<< HEAD
=======
            //TODO
>>>>>>> origin/BraveNewBranch

            //A person will be protected, then the toast will confirm that the mafia member is done
            int duration = Toast.LENGTH_LONG;
            String text = "You have marked " + person.getName() + "for death";
            Toast toast = Toast.makeText(context, text, duration);
        }
    }


}
