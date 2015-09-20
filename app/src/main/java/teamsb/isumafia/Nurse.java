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
        super(id, name,GS);
    }

    //Returns the role of this person
    public Role who()
    {
        return Role.NURSE;
    }

    //Will protect a person
    public Toast doJob(Context context,Person person)
    {
        Toast toast;
        //Assuming that day will be the variable
        if(GS.timeDayNight && isAlive())
        {
            //If it is day, their job is to vote for a person
            person.vote();
            int duration = Toast.LENGTH_LONG;
            String text = "You have voted to kill " + person.getName();
            toast = Toast.makeText(context, text, duration);

        }
        else if(!GS.timeDayNight && isAlive())
        {
            person.save();
            //TODO

            //A person will be protected, then the toast will confirm that the mafia member is done
            int duration = Toast.LENGTH_LONG;
            String text = "You have marked " + person.getName() + "for death";
            toast = Toast.makeText(context, text, duration);
        }
        else
        {
            int duration = Toast.LENGTH_LONG;
            String text = "Thank you for passing turn";
            toast = Toast.makeText(context, text, duration);
        }
        return toast;
    }


}
