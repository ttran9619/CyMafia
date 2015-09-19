package teamsb.isumafia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RulesPage extends AppCompatActivity implements Serializable {

    Button btnByteTransform, btnByteRead;

    TextView P1, P2, deb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page);

        global.transformHolder = new byte[100000];
        global.dave2 = new Mafia("Mike");




        btnByteTransform = (Button) findViewById(R.id.buttonTransform);
        btnByteRead = (Button) findViewById(R.id.buttonTransformRead);

        final Person dave = new Mafia("David");
        global.dave2 = new Mafia("John");


        deb = (TextView) findViewById(R.id.textDebug);

        P1 = (TextView) findViewById(R.id.textP1);
        P1.setText(dave.getName());

        P2 = (TextView) findViewById(R.id.textP2);
        P2.setText(global.dave2.getName());

        btnByteTransform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    global.transformHolder = convertToBytes(dave);
                    deb.setText("Transform Worked");

                } catch (IOException e) {

                    deb.setText("Transform Failed");
                }

            }
        });

        btnByteRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    global.dave2 = (Person) convertFromBytes(global.transformHolder);
                    P2.setText(global.dave2.getName());
                    deb.setText("Read Worked");

                }catch(Exception e){

                    deb.setText("Read Failed");
                }

            }
        });





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
        getMenuInflater().inflate(R.menu.menu_rules_page, menu);
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
