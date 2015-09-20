package teamsb.isumafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverPage extends AppCompatActivity {

    Button btnBack;
    TextView textGameOverState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_page);

        Intent intentStarted = getIntent();
        GameState GS = (GameState) intentStarted.getSerializableExtra("PassedGameState");


        btnBack = (Button) findViewById(R.id.buttonGameOverBack);


        textGameOverState = (TextView) findViewById(R.id.textGameOverState);

        if(GS.mafiaWin==true){
            textGameOverState.setText(getString(R.string.mafia_win));
        }else if(GS.citizenWin==true){
            textGameOverState.setText(getString(R.string.citizen_win));
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
}
