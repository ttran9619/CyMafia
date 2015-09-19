package teamsb.isumafia;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by enclark on 9/18/2015.
 */
public abstract class Person implements Serializable {

    private boolean won;
    private boolean alive;
    private String name;
    private boolean marked;
    private boolean saved;
    private int vote;

    protected Person(String name) // Probably pass a player's ID and Google name to it
    {
        this.name = new String(name);
        won = false;
        alive = true;
        marked = false;
        saved = false;
        //TODO
    }

    protected Person(Person old){
        this.name = new String(old.name);
        this.won = old.won;
        this.alive = old.alive;
        this.marked = old.marked;
        this.saved = old.saved;

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

    //Returns the status of if they won or lost to give them the correct message at the end
    public boolean won()
    {
        return won;
    }

    //This identifies the person who the mafia wants to kill
    public void markForKill()
    {
        marked = true;
    }

    //this identifies the person who the mafia wants to save
    public void save()
    {
        saved = true;
    }

    //This returns whether the person is saved
    public boolean getSaved()
    {
        return saved;
    }

    //This returns if the person is marked
    public boolean getMarked()
    {
        return marked;
    }

    //This resets the marked variable
    public void unMark()
    {
        marked = false;
    }

    //This resets the saved variable
    public void unSave()
    {
        saved = false;
    }

    //This indicates how many votes a person got to kill us
    public void vote()
    {
        vote += 1;
    }

    //This returns the vote so we can compare how many votes each person received
    public int getVote()
    {
        return vote;
    }

    //Resets the vote count
    public void voteReset()
    {
        vote = 0;
    }

}
