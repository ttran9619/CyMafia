package teamsb.isumafia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TitleScreen extends BaseGameActivity {

    Button btnHost, btnLogin, btnRules;
    public static ArrayList<String> peeps = new ArrayList<String>();
    public static byte[] bytes = null;
    public static GameState gameState;





    // Used for onStop/Start
    private static final String TAG = TitleScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        ImageView logo = (ImageView) findViewById(R.id.imageTitleLogo);

//        final GameState GS = new GameState();

//        GS.TEST = "MEEP";


        btnHost = (Button) findViewById(R.id.buttonStart);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnRules = (Button) findViewById(R.id.buttonRules);


        // Button to the Host Lobby Screen
        // First turn
        btnHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DaytimeActivity.class);
                intent.putExtra("PassedGameState", gameState);
                startActivity(intent);
            }
        });

        // Button to the Rules Page
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RulesPage.class);
                startActivity(intent);
            }
        });

        // Button to the Login Page TODO
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            GoogleApiClient mGoogle = getApiClient();
            Intent intent = Games.TurnBasedMultiplayer.getSelectOpponentsIntent(mGoogle, 2, 7, true);
            startActivityForResult(intent, 1);
//                if (isSignedIn())
//                {
//                    signOut();
//                }
//                else
//                {
//                    beginUserInitiatedSignIn();
//                }
                //Intent intent = new Intent(v.getContext(), HostLobby.class);
                // startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_screen, menu);
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

    public static void initGame(TurnBasedMatch match)
    {
        gameState = new GameState();
        peeps = match.getParticipantIds();
        String ids[] = new String[peeps.size()];
        String names[] = new String[ids.length];
        int len = peeps.size();
        for(int i = 0; i < len; i += 1)
        {
            ids[i] = peeps.get(i);
            names[i] = match.getParticipant(ids[i]).getDisplayName();
        }
        gameState.populate(ids, names);

        byte[] data = null;
        try {
            data = convertToBytes(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
        global.data = data;
    }


    private static byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }


    @Override
    public void onActivityResult(int request, int response, Intent data)
    {
        super.onActivityResult(request, response, data);

        if (request == 1)
        {
            if (response != Activity.RESULT_OK) {
                // user canceled
                return;
            }

            // Get the invitee list.
            final ArrayList<String> invitees =
                    data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get auto-match criteria.
//            Bundle autoMatchCriteria = null;
//            int minAutoMatchPlayers = data.getIntExtra(
//                    Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
//            int maxAutoMatchPlayers = data.getIntExtra(
//                    Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
//            if (minAutoMatchPlayers > 0) {
//                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
//                        minAutoMatchPlayers, maxAutoMatchPlayers, 0);
//            } else {
//                autoMatchCriteria = null;
//            }

            TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(null)
                    .build();

            // Create and start the match.
            Games.TurnBasedMultiplayer
                    .createMatch(getApiClient(), tbmc)
                    .setResultCallback(new MatchInitiatedCallback());
        }
    }

    protected void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
        getApiClient().connect();
    }

    protected void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
        if (getApiClient().isConnected()) {
            getApiClient().disconnect();
        }
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}