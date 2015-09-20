package teamsb.isumafia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class DaytimeActivity extends BaseGameActivity {

    Button btnBack;


    ListView playerList;

    //TEMP
    String[] names;
    Person[] people = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime);

//        GoogleApiClient googleApiClient = getApiClient();
//        String currentPlayer = Games.Players.getCurrentPlayerId(googleApiClient);

        //Passed in the GameState from the Title Screen
        Intent intentStarted = getIntent();
        final GameState GS = (GameState) intentStarted.getSerializableExtra("PassedGameState");

        //TESTING
        //names = new String[]{"Mark","Luke","John"};
        names = new String[GS.getArray().size()];
        for(int i = 0; i < GS.getArray().size(); i += 1)
        {
            names[i] = new String(GS.getArray().get(i).getName());
        }


        // Creating the list and the list adapter
        playerList = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, names);
        playerList.setAdapter(adapter);

        // Button for returning to the start
        btnBack = (Button) findViewById(R.id.buttonDaytimeBack);




        // ListView Item Click Listener
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //makes sure we are only dealing with living people
                cleanNames(GS);
                GoogleApiClient googleApiClient = getApiClient();
                //Returns the current player
                String currentPlayer = Games.Players.getCurrentPlayerId(googleApiClient);

                Person currentPerson = null;
                //then we will find the person object that represents the current player
                for(int i = 0; i < GS.getArray().size(); i += 1)
                {
                    if (GS.getArray().get(i).isAlive() && currentPlayer.equals(GS.getArray().get(i).getID()))
                    {
                        currentPerson = GS.getArray().get(i);
                    }
                }

                Person victim = null;
                //Now find the victim using a similar method of names[position]
                for(int i = 0; i < GS.getArray().size(); i += 1)
                {
                    if(GS.getArray().get(i).isAlive() && names[position].equals(GS.getArray().get(i).getName()))
                    {
                        victim = GS.getArray().get(i);
                    }
                }
                //Doing the Job
                currentPerson.doJob(getApplicationContext(),victim);


            }

        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TitleScreen.class);
                startActivity(intent);
            }
        });


    }

    //Makes sure that the names are only the alive members
    private void cleanNames(GameState GS) {
        names = null;
        //Counts the number of players left in the game
        int alive = 0;
        for (int i = 0; i < GS.getArray().size(); i += 1) {
            if (GS.getArray().get(i).isAlive()) {
                alive += 1;
            }
        }
        names = new String[alive];
        //Then adds those players to the game
        for (int i = 0; i < GS.getArray().size(); i += 1) {
            if (GS.getArray().get(i).isAlive()) {
                names[i] = new String(GS.getArray().get(i).getName());
            } else {
                //This removes the dead people from the list adapter
                ArrayAdapter<String> adapter = (ArrayAdapter) playerList.getAdapter();
                adapter.remove(names[i]);
            }
        }
    }


    private Person findVictim(int position, GameState GS)
    {
            //We will find the person that was clicked on by finding their name and comparing it to the
            //names of the person objects
        Person p = null;
        for(int i = 0; i < GS.getArray().size(); i += 1)
            {
                 if (names[position].equals(GS.getArray().get(i).getName()))
                 {
                    return GS.getArray().get(i);
                 }
            }
        return p;
    }

    //At the beginning of each new round, this method will be called to kill a player
    private void killPerson(GameState GS)
    {
        //These two objects allow us to locate who the nurse wants to save, and who the mafia wants
        //to kill
        Person wantToProtect = null;
        Person wantToKill = null;
        int maxVote = 0;
        Person popular = null;
        for(int i = 0; i < people.length; i += 1)
        {

            //After we find the two people, we will unsave/unmark them so they are not
            if(people[i].getSaved())
            {
                wantToProtect = people[i];
                people[i].unSave();
            }
            if(people[i].getMarked())
            {
                wantToKill = people[i];
                people[i].unMark();
            }
            if (people[i].getVote() > maxVote)
            {
                maxVote = people[i].getVote();
                popular = people[i];
            }
            if(people[i].getVote() == maxVote)
            {
                popular = null;
            }
            people[i].voteReset();
        }
        int duration = Toast.LENGTH_LONG;
        String text;
        Toast toast;
        //This sends a toast to notify if someone dies
        if( (wantToKill != null) && wantToProtect == wantToKill)
        {
            text = "The nurse was successful; no one died last night";
            toast = Toast.makeText(getApplicationContext(), text, duration);
        }
        else if(wantToKill != null)
        {
            text = wantToKill.getName() + "died last night";
            toast = Toast.makeText(getApplicationContext(), text, duration);
            wantToKill.kill();
        }
        else if((popular != null) && wantToKill == null)
        {
            text = popular.getName() + "died last night";
            toast = Toast.makeText(getApplicationContext(), text, duration);
            popular.kill();
            if(popular.who() == Role.MAFIA)
            {
                GS.citizenWin = true;
            }
        }
        else
        {
            text = "No one died";
            toast = Toast.makeText(getApplicationContext(), text, duration);
        }
        cleanNames(GS);
        if(names.length <= 2 && !GS.citizenWin)
        {
            GS.mafiaWin = true;
        }
        anyoneWon(GS);

    }

    private void anyoneWon(GameState GS)
    {
        if(GS.mafiaWin || GS.citizenWin)
        {
            Intent intent = new Intent(getApplicationContext(), DaytimeActivity.class);
            intent.putExtra("PassedGameState", GS);
            startActivity(intent);
        }
    }


    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daytime, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}
