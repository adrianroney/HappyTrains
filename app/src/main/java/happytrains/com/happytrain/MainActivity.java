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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    EditText departureDateTime;
    private Button mSubmitButton;
    private Spinner mDepList;
    private Spinner mArvList;
    private ArrayList<String> stationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        departureDateTime = (EditText) findViewById(R.id.datetime_edit_text);
        Calendar now = Calendar.getInstance();
        String datetime = now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + "/" + now.get(Calendar.YEAR) + " " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
        departureDateTime.setText(datetime);

        departureDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MainActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setCancelable(false);
                dpd.setThemeDark(false);
                dpd.vibrate(false);
                dpd.setMinDate(now);
                Calendar oneyear = Calendar.getInstance();
                oneyear.add(Calendar.YEAR, 1);
                dpd.setMaxDate(oneyear);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
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

        mSubmitButton = (Button) findViewById(R.id.submit_button);

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
    protected void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
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
        Toast.makeText(getApplicationContext(), "Selected station is " + adapterView.getItemAtPosition(i), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setCancelable(false);
        tpd.setThemeDark(false);
        tpd.vibrate(false);
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                departureDateTime.setText("");
            }
        });
        view.dismiss();
        tpd.show(getFragmentManager(), "Timepickerdialog");
        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
        departureDateTime.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        String dateTime = departureDateTime.getText() + " " + hourOfDay + ":" + minute;
        departureDateTime.setText(dateTime);
    }
}
