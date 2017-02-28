package login.example.com;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import login.example.com.EventItem.Status;

public class EventListAdapter extends BaseAdapter {

	// List of ToDoItems
	private final List<EventItem> mItems = new ArrayList<EventItem>();

	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public EventListAdapter(Context context) {

		mContext = context;

	}

	// Add a EventItem to the adapter
	// Notify observers that the data set has changed

	public void add(EventItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the EventItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View to display the EventItem
	// at specified position in mItems

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//TODO - Get the current EventItem
		final EventItem eventItem = (EventItem) getItem(position);

		//TODO - Inflate the View for this EventItem
		// from event_item.xmll.
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemLayout = inflater.inflate(R.layout.event_item, parent, false);

		//TODO - Fill in specific EventItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		//TODO - Display Title in TextView

		final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
		titleView.setText(eventItem.getTitle());

		// TODO - Set up Status CheckBox

		final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
		statusView.setChecked(eventItem.getStatus() == Status.DONE);

		statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				log("Entered onCheckedChanged()");

				// TODO - Set up and implement an OnCheckedChangeListener, which
				// is called when the user toggles the status checkbox
				if (isChecked){
					eventItem.setStatus(Status.DONE);
				}
				else
					eventItem.setStatus(Status.NOTDONE);
			}
		});

		//TODO - Display Weather in a TextView

		final TextView priorityView = (TextView) itemLayout.findViewById(R.id.WeatherLabel);;
		priorityView.setText(eventItem.getPriority().toString());

		// TODO - Display Time and Date.
		// Hint - use EventItem.FORMAT.format(eventItem.getDate()) to get date and time String

		final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
		dateView.setText(eventItem.getDate().toString());

		// Return the View you just created
		return itemLayout;
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
