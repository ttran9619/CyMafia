package teamsb.isumafia;


import android.content.Intent;
import android.os.RemoteException;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 9/19/2015.
 */
public class MatchInitiatedCallback implements ResultCallback<TurnBasedMultiplayer.InitiateMatchResult> {

    @Override
    public void onResult(TurnBasedMultiplayer.InitiateMatchResult initiateMatchResult) {
        // Check if the status code is not success.
        Status status = initiateMatchResult.getStatus();
        if (!status.isSuccess()) {
            Log.v("Error: ", "" + status.getStatusCode());
            return;
        }

        global.match = initiateMatchResult.getMatch();

        // If this player is not the first player in this match, continue.
        if (global.match.getData() != null) {
//            showTurnUI(match);
            return;
        }

        // Otherwise, this is the first player. Initialize the game state.
        TitleScreen.initGame(global.match);

        // Let the player take the first turn
//        startFirstTurn(match, data);
//        Games.TurnBasedMultiplayer.takeTurn();
    }

}
