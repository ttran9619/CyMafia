package teamsb.isumafia;

import android.content.Context;

/**
 * Created by enclark on 9/18/2015.
 */
public class Nurse extends Person{

    //Creates a new Nurse
    public Nurse(String name)
    {
        super(name);
    }

    //Returns the role of this person
    public Role who()
    {
        return Role.NURSE;
    }

    //Will protect a person
    public void doJob(Context context,Person person)
    {
        // person.protect();
        //TODO

        //A person will be protected, then the toast will confirm that the mafia member is done
        //int duration = Toast.LENGTH_LONG;
        //String text = "You have marked " + person.getName() + "for death";
        //Toast toast = new Toast(context, text, duration);
    }


}
