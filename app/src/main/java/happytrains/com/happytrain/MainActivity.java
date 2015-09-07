package happytrains.com.happytrain;

import android.app.Activity;
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

    private Spinner mDepList;
    private Spinner mArvList;
    private ArrayList<String> stationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Selected station is "+ adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
