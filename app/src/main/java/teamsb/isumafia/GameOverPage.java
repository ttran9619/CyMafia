package teamsb.isumafia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GameOverPage extends BaseGameActivity {

    Button btnBack;
    TextView textGameOverState;
    Person currentPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_page);

        Intent intentStarted = getIntent();
        GameState GS = (GameState) intentStarted.getSerializableExtra("PassedGameState");


        btnBack = (Button) findViewById(R.id.buttonGameOverBack);

        GoogleApiClient googleApiClient = getApiClient();
        //Returns the current player
        String currentPlayer = Games.Players.getCurrentPlayerId(googleApiClient);

        currentPerson = null;
        //then we will find the person object that represents the current player
        for(int i = 0; i < GS.getArray().size(); i += 1)
        {
            if (currentPlayer.equals(GS.getArray().get(i).getID()))
            {
                currentPerson = GS.getArray().get(i);
            }
        }




        textGameOverState = (TextView) findViewById(R.id.textGameOverState);

        if(GS.mafiaWin==true){
            textGameOverState.setText(getString(R.string.mafia_win));

            if(currentPerson.who()==Role.MAFIA){

                Games.Achievements.increment(getApiClient(), "CgkIxP26lfwBEAIQAw", 1);
            }
        }else if(GS.citizenWin==true){
            textGameOverState.setText(getString(R.string.citizen_win));

            if(currentPerson.who()!=Role.MAFIA){

                Games.Achievements.increment(getApiClient(), "CgkIxP26lfwBEAIQAw", 1);
            }
        }


        if(currentPerson.isAlive()){
            Games.Achievements.unlock(getApiClient(), "CgkIxP26lfwBEAIQBQ");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TitleScreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over_page, menu);
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
