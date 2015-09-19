package teamsb.isumafia;

import android.content.Context;

/**
 * Created by enclark on 9/18/2015.
 */
public class Mafia extends Person {

    //Creates new mafia Person
    public Mafia(String name)
    {
        super(name);
    }

    //Returns the Role of this Person
    public Role who()
    {
        return Role.MAFIA;
    }

    //Performs the job of the mafia and marks a person for death
    public void doJob(Context context, Person person)
    {
        //TODO
        //person.markForDeath();

        //A person will be marked for death, then the toast will confirm that the mafia member is done
        //int duration = Toast.LENGTH_LONG;
        //String text = "You have marked " + person.getName() + "for death";
        //Toast toast = new Toast(context, text, duration);
    }

}
