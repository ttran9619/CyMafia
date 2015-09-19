package teamsb.isumafia;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by enclark on 9/18/2015.
 */
public class Citizen extends Person{

    //Creates a new player
    public Citizen(String name)
    {
        super(name);
    }

    // Returns the role of the player
    public Role who()
    {
        return Role.CITIZEN;
    }

    //This is the job of the citizen
    public void doJob(Context context, Person person)
    {
        int duration = Toast.LENGTH_LONG;
        //We will create a toast so they know it worked
        Toast toast = Toast.makeText(context, "Thank you for choosing a player", duration);
        toast.show();

    }
}