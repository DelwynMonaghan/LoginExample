package login.example.com;

/**
 * Created by Delwyn on 02/05/2017.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;


public class AddStandActivity extends Activity {

    private static final String TAG = "Lab-UserInterface";

    private static TextView standView;
//possibly needs changed to a textView
    private NumberPicker mstandNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_item);

        mstandNumber = (NumberPicker) findViewById(R.id.np);

        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
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
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered resetButton.OnClickListener.onClick()");

                //TODO - Reset data fields to default values
                resetToDefault();

            }
        });


        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("Entered submitButton.OnClickListener.onClick()");

                //TODO -  Title
                String standString = mstandNumber.toString();

                // Package EventItem data into an Intent
                Intent data = new Intent();
                StandItem.packageIntent(data, standString);

                //TODO - return data Intent and finish
                // Create a new intent and save the input as an extra
                Intent intent = new Intent();
                StandItem.packageIntent(intent, standString);

                // Set Activity's result with result code RESULT_OK
                setResult(RESULT_OK, intent);
                // Finish the Activity
                finish();

            }
        });
    }



    protected void resetToDefault() {
        mstandNumber.setValue(0);
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