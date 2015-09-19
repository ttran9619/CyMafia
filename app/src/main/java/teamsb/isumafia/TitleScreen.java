package teamsb.isumafia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TitleScreen extends BaseGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        TextView textView = (TextView) findViewById(R.id.textView);
        if (isSignedIn())
        {
            textView.setText("You are signed in");
        }
        else {
            textView.setText("You are not signed in");
        }

        final Button signoutButton = (Button) findViewById(R.id.button);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                signOut();
            }
        });

        final Button signinButton = (Button) findViewById(R.id.button2);
        signinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
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

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {
        Toast.makeText(this, "Hello", Toast.LENGTH_LONG);
    }
}
