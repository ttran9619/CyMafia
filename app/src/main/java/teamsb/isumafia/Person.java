package teamsb.isumafia;

import android.content.Context;

/**
 * Created by enclark on 9/18/2015.
 */
public abstract class Person {

    private boolean alive;
    private String name;
    //private boolean marked;
    //private boolean protected;
    protected Person(String name) // Probably pass a player's ID and Google name to it
    {
        this.name = name;
        alive = true;
        //marked = false;
        //protected = false;
        //TODO
    }

    // To be implemented by the classes, because they will all be different
    protected abstract Role who();

    //returns if the person is alive
    public boolean isAlive()
    {
        return alive;
    }

    //Each role will have a different job and needs a person object to protect or mark
    public abstract void doJob(Context context, Person person);

    //This will take care of putting the character out of the game.
    public void kill()
    {
        alive = false;
        //change the color of the text to grey
    }

    //Returns the player's Google ID
    public String getName()
    {
        return name;
    }

//    public void markForKill()
//    {
//        marked = true;
//    }

//    public void protect()
//    {
//        protected = true;
//    }


}
