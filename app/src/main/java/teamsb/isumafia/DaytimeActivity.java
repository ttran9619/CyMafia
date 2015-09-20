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
    //Person[] people = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime);

        //Passed in the GameState from the Title Screen
        Intent intentStarted = getIntent();
        GameState GS = (GameState) intentStarted.getSerializableExtra("PassedGameState");

        //TESTING
        //names = new String[]{"Mark","Luke","John"};
        names = new String[GS.getArray().size()];
        for(int i = 0; i < GS.getArray().size(); i += 1)
        {
            names[i] = new String(GS.getArray().get(i).getName());
        }


        // Creating the list and the list adapter
        playerList = (ListView) findViewById(R.id.listViewDaytime);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, names);
        playerList.setAdapter(adapter);

        // Button for returning to the start
        btnBack = (Button) findViewById(R.id.buttonDaytimeBack);




        // ListView Item Click Listener
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Position will be the same as the index of the name in the name list
                // TO BE Modified

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String)  playerList.getItemAtPosition(position);

                //getCurrentPlayerId(client)

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
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

    //This goes through the name array and checks that each player is still alive and removes them
    //from the name array  if they are dead
    private void cleanNames(GameState GS)
    {
        names = null;
        //Counts the number of players left in the game
        int alive = 0;
        for(int i = 0; i < GS.getArray().size(); i += 1)
        {
            if(GS.getArray().get(i).isAlive())
            {
                alive += 1;
            }
        }
        names = new String[alive];
        //Then adds those players to the game
        for(int i = 0; i < GS.getArray().size(); i += 1)
        {
            if(GS.getArray().get(i).isAlive())
            {
                names[i] = new String(GS.getArray().get(i).getName());
            }
            else
            {
                //This removes the dead people from the list adapter
                ArrayAdapter<String> adapter= (ArrayAdapter) playerList.getAdapter();
                adapter.remove( names[i]);
            }
        }


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
        for(int i = 0; i < GS.getArray().size(); i += 1)
        {

            //After we find the two people, we will unsave/unmark them so they are not
            if(GS.getArray().get(i).getSaved())
            {
                wantToProtect = GS.getArray().get(i);
                GS.getArray().get(i).unSave();
            }
            if(GS.getArray().get(i).getMarked())
            {
                wantToKill = GS.getArray().get(i);
                GS.getArray().get(i).unMark();
            }
            if (GS.getArray().get(i).getVote() > maxVote)
            {
                maxVote = GS.getArray().get(i).getVote();
                popular = GS.getArray().get(i);
            }
            if(GS.getArray().get(i).getVote() == maxVote)
            {
                popular = null;
            }
            GS.getArray().get(i).voteReset();
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
        }
        else
        {
            text = "No one died";
            toast = Toast.makeText(getApplicationContext(), text, duration);
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
