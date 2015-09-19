package teamsb.isumafia;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by enclark on 9/18/2015.
 */
public class Detective extends Person {

    //Creates a new Detective
    public Detective(String name)
    {
        super(name);
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
        String text = person.getName() + " is a ";
        if(person.who() == Role.MAFIA)
        {
            text += "mafioso";
        }
        else
        {
            text += "citizen";
        }
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
    }

}
