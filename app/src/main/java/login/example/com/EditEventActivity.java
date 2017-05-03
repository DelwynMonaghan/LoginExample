package login.example.com;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import login.example.com.EventItem.Status;
import login.example.com.EventItem.Weather;

public class EditEventActivity extends Activity {

    // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
    private static final int SEVEN_DAYS = 604800000;
    private static final String TAG = "Lab-UserInterface";

    private static String timeString;
    private static String dateString;
    private static TextView dateView;
    private static TextView timeView;

    private Date mDate;
    private RadioGroup mWeatherRadioGroup;
    private RadioGroup mStatusRadioGroup;
    private EditText mEventText;
    private Spinner standSpinner;
    private Spinner claySpinner;
    private RadioButton mDefaultStatusButton;
    private RadioButton mDefaultPriorityButton;

    protected void onCreate(Bundle savedInstanceState, EventItem eventItem) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        mEventText = (EditText) findViewById(R.id.title);
        mDefaultStatusButton = (RadioButton) findViewById(R.id.statusNotDone);
        mDefaultPriorityButton = (RadioButton) findViewById(R.id.rainingWeather);
        mWeatherRadioGroup = (RadioGroup) findViewById(R.id.weatherGroup);
        mStatusRadioGroup = (RadioGroup) findViewById(R.id.statusGroup);
        dateView = (TextView) findViewById(R.id.date);
        timeView = (TextView) findViewById(R.id.time);
        standSpinner = (Spinner) findViewById(R.id.stand_spinner);
        claySpinner = (Spinner) findViewById(R.id.clay_spinner);


//        mEventText.setText(eventItem.getTitle());
//        mDefaultStatusButton.setText(eventItem.getStatus());
//        mDefaultPriorityButton.setText(eventItem.getPriority());
//        mWeatherRadioGroup.setText(eventItem.getPriority());
//        mStatusRadioGroup.setText(eventItem.getStatus());
//        dateView.setText(eventItem.getDate());
//        timeView.setText(eventItem.get);
//        standSpinner.setSelected();
//        claySpinner.setText(eventItem.getClays());


        //	addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        // Set the default date and time

        setDefaultDateTime();
        // OnClickListener for the Date button, calls showDatePickerDialog() to
        //show
        // the Date dialog

        final Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // OnClickListener for the Time button, calls showTimePickerDialog() to show
        // the Time Dialog

        final Button timePickerButton = (Button) findViewById(R.id.time_picker_button);
        timePickerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // OnClickListener for the Cancel Button,

        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered cancelButton.OnClickListener.onClick()");

                //TODO - Implement onClick().
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        //add items into sp
        //OnClickListener for the Reset Button

        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered resetButton.OnClickListener.onClick()");

                //TODO - Reset data fields to default values
                resetToDefault();

            }
        });



        // OnClickListener for the Submit Button
        // Implement onClick().

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                // Gather EventItem data

                //TODO - Get Weather
                Weather weather = getPriority();

                //TODO -  Get Status
                Status status = getStatus();

                //TODO -  Title
                String titleString = mEventText.getText().toString();

                // Date
                String fullDate = dateString + " " + timeString;

                //ClaySpinner
                String numberOfClays = String.valueOf(claySpinner.getSelectedItem());
                //StandSpinner
                String numberOfStands = String.valueOf(standSpinner.getSelectedItem());

                // Package EventItem data into an Intent
                Intent data = new Intent();
                EventItem.packageIntent(data, titleString, weather, status, fullDate, numberOfStands, numberOfClays);

                //TODO - return data Intent and finish
                // Create a new intent and save the input as an extra
                Intent intent = new Intent();
                EventItem.packageIntent(intent, titleString, weather, status, fullDate, numberOfStands, numberOfClays);

                // Set Activity's result with result code RESULT_OK
                setResult(RESULT_OK, intent);
                // Finish the Activity
                finish();

            }
        });
    }

//	// add items into spinner dynamically
//	public void addItemsOnSpinner2() {
//
//		spinner2 = (Spinner) findViewById(R.id.spinner2);
//		List<String> list = new ArrayList<String>();
//		list.add("list 1");
//		list.add("list 2");
//		list.add("list 3");
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, list);
//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner2.setAdapter(dataAdapter);
//	}

    public void addListenerOnSpinnerItemSelection() {
        standSpinner = (Spinner) findViewById(R.id.stand_spinner);
        standSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        claySpinner = (Spinner) findViewById(R.id.clay_spinner);
        claySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
//
//	// get the selected dropdown list value
//	public void addListenerOnButton() {
//
//		standSpinner = (Spinner) findViewById(R.id.stand_spinner);
//		claySpinner = (Spinner) findViewById(R.id.clay_spinner);
//		btnSubmit = (Button) findViewById(R.id.btnSubmit);
//
//		btnSubmit.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				Toast.makeText(AddEventActivity.this,
//						"OnClickListener : " +
//								"\nSpinner 1 : "+ String.valueOf(standSpinner.getSelectedItem()) +
//								"\nSpinner 2 : "+ String.valueOf(claySpinner.getSelectedItem()),
//						Toast.LENGTH_SHORT).show();
//			}
//
//		});
//	}


    protected void resetToDefault() {
        setDefaultDateTime();

        mEventText.setText("");

        mWeatherRadioGroup.check(R.id.rainingWeather);
        mStatusRadioGroup.check(R.id.statusNotDone);
    }




    // Do not modify below here

    // Use this method to set the default date and time

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateView.setText(dateString);

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.MILLISECOND));

        timeView.setText(timeString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min + ":00";
    }

    private Weather getPriority() {

        switch (mWeatherRadioGroup.getCheckedRadioButtonId()) {
            case R.id.sunnyWeather: {
                return Weather.RAINING;
            }
            case R.id.snowingWeather: {
                return Weather.SNOWING;
            }
            default: {
                return Weather.SUNNY;
            }
        }
    }

    private Status getStatus() {

        switch (mStatusRadioGroup.getCheckedRadioButtonId()) {
            case R.id.statusDone: {
                return Status.DONE;
            }
            default: {
                return Status.NOTDONE;
            }
        }
    }

    // DialogFragment used to pick a EventItem deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            dateView.setText(dateString);
        }

    }

    // DialogFragment used to pick a EventItem deadline time

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);

            timeView.setText(timeString);
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

}