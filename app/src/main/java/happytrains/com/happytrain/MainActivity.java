package happytrains.com.happytrain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
private Button mSubmitButton;
    private Spinner mDepList;
    private Spinner mArvList;
    private ArrayList<String> stationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
		getActionBar().setBackgroundDrawable(new ColorDrawable(0x00bcd4));
        stationList.add("Station A");
        stationList.add("Station B");
        stationList.add("Station C");
        stationList.add("Station D");
        stationList.add("Station E");
        mDepList = (Spinner) findViewById(R.id.departure_station_id);
        mArvList = (Spinner) findViewById(R.id.arrival_station_id);
        setupStationList();
    }

    private void setupStationList() {
        Collections.sort(stationList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                stationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDepList.setAdapter(adapter);
        mDepList.setOnItemSelectedListener(this);
        mArvList.setAdapter(adapter);
        mArvList.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mSubmitButton = (Button)findViewById(R.id.submit_button);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validationPassess()) {
                    Intent searchResultsActivity = new Intent(MainActivity.this, SearchResults.class);
                    startActivity(searchResultsActivity);
                } else {
                    showValidationError();
                }
            }
        });

        return true;
    }

    private void showValidationError() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.validation_dialog_title);
        dialogBuilder.setMessage(R.string.validation_message);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

    private boolean validationPassess() {
        return false;
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Selected station is "+ adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
