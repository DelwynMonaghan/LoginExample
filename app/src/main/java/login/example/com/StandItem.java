package login.example.com;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Do not modify

public class StandItem {

    public static final String ITEM_SEP = System.getProperty("line.separator");


    public final static String STANDNUMBER = "standNumber";
    public final static String FILENAME2 = "filename2";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mStandNumber = new String();

    StandItem(String standNumber) {
        this.mStandNumber = standNumber;

    }

    // Create a new Standitem from data packaged in an Intent

    StandItem(Intent intent) {

        mStandNumber = intent.getStringExtra(StandItem.STANDNUMBER);
    }

    public String getStandnumber() {
        return mStandNumber;
    }

    public void setStandnumber(String Standnumber) {
        mStandNumber = Standnumber;
    }

    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String standNumber) {

        intent.putExtra(StandItem.STANDNUMBER, standNumber);
    }

    public String toString() {
        return mStandNumber;
    }

    public String toLog() {
        return "StandNumber:" + mStandNumber;
    }

}
