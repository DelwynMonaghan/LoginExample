package login.example.com;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Do not modify

public class EventItem {

	public static final String ITEM_SEP = System.getProperty("line.separator");

	public enum Weather {
		RAINING, SUNNY, SNOWING
	};

	public enum Status {
		NOTDONE, DONE
	};

	public final static String EVENT = "title";
	public final static String WEATHER = "weather";
	public final static String STATUS = "status";
	public final static String DATE = "date";
	public final static String CLAYS = "clays";
	public final static String STANDS = "stands";
	public final static String FILENAME = "filename";

	public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	private String mTitle = new String();
	private Weather mWeather = Weather.RAINING;
	private Status mStatus = Status.NOTDONE;
	private Date mDate = new Date();
	private String mStands = new String();
	private String mClays = new String();

	EventItem(String title, Weather weather, Status status, Date date,String stands, String clays) {
		this.mTitle = title;
		this.mWeather = weather;
		this.mStatus = status;
		this.mDate = date;
		this.mStands = stands;
		this.mClays = clays;

	}

	// Create a new EventItem from data packaged in an Intent

	EventItem(Intent intent) {

		mTitle = intent.getStringExtra(EventItem.EVENT);
		mWeather = Weather.valueOf(intent.getStringExtra(EventItem.WEATHER));
		mStatus = Status.valueOf(intent.getStringExtra(EventItem.STATUS));
		try {
			mDate = EventItem.FORMAT.parse(intent.getStringExtra(EventItem.DATE));
		} catch (ParseException e) {
			mDate = new Date();
		}
		mStands = intent.getStringExtra(EventItem.STANDS);
		mClays = intent.getStringExtra(EventItem.CLAYS);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Weather getPriority() {
		return mWeather;
	}

	public void setPriority(Weather weather) {
		mWeather = weather;
	}

	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status status) {
		mStatus = status;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public String getStands() {
		return mStands;
	}

	public void setStands(String stands) {
		mStands = stands;
	}

	public String getClays() {
		return mClays;
	}

	public void setClays(String clays) {
		mClays = clays;
	}


	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,
									 Weather weather, Status status, String date, String stands, String clays) {

		intent.putExtra(EventItem.EVENT, title);
		intent.putExtra(EventItem.WEATHER, weather.toString());
		intent.putExtra(EventItem.STATUS, status.toString());
		intent.putExtra(EventItem.DATE, date);
		intent.putExtra(EventItem.STANDS, stands);
		intent.putExtra(EventItem.CLAYS, clays);


	}

	public String toString() {
		return mTitle + ITEM_SEP + mWeather + ITEM_SEP + mStatus + ITEM_SEP
				+ FORMAT.format(mDate) + ITEM_SEP + mStands + ITEM_SEP + mClays;
	}

	public String toLog() {
		return "Title:" + mTitle + ITEM_SEP + "Weather:" + mWeather
				+ ITEM_SEP + "Status:" + mStatus + ITEM_SEP + "Date:"
				+ FORMAT.format(mDate) + ITEM_SEP + "Stands:" + mStands + ITEM_SEP + "Clays:" + mClays;
	}

}
