package login.example.com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import login.example.com.StandItem.Status;


public class StandListAdapter extends BaseAdapter {

    // List of ToDoItems
    private final List<StandItem> mItems = new ArrayList<StandItem>();

    private final Context mContext;

    private static final String TAG = "Lab-UserInterface";

    public StandListAdapter(Context context) {

        mContext = context;

    }

    // Add a StandItem to the adapter
    // Notify observers that the data set has changed

    public void add(StandItem item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void remove(StandItem item) {

        mItems.remove(item);
        notifyDataSetChanged();
    }

    // C
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

    // Get the ID for the StandItem
    // In this case it's just the position

    @Override
    public long getItemId(int pos) {

        return pos;

    }

    // Create a View to display the StandItem
    // at specified position in mItems

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //TODO - Get the current StandItem
        final StandItem standItem = (StandItem) getItem(position);

        //TODO - Inflate the View for this StandItem
        // from stand_item.xmll.

// 		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View itemLayout = inflater.inflate(R.layout.stand_item, parent, false);

        final RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.stand_item, parent, false);

        //TODO - Fill in specific StandItem data
        // Remember that the data that goes in this View
        // corresponds to the user interface elements defined
        // in the layout file

        //TODO - Display Title in TextView

        final TextView titleView = (TextView) itemLayout.findViewById(R.id.titleView);
        titleView.setText(standItem.getTitle());

        // TODO - Set up Status CheckBox

        final CheckBox statusView = (CheckBox) itemLayout.findViewById(R.id.statusCheckBox);
        statusView.setChecked(standItem.getStatus() == Status.DONE);

        statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                log("Entered onCheckedChanged()");

                // TODO - Set up and implement an OnCheckedChangeListener, which
                // is called when the user toggles the status checkbox
                if (isChecked){
                    standItem.setStatus(Status.DONE);
                }
                else
                    standItem.setStatus(Status.NOTDONE);
            }
        });
        //----------------------------------------------//
        itemLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StandManagerActivity.getInstance());

                alert.setTitle("Alert!");
                alert.setMessage("Are you sure to delete record?");

                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove(standItem);
                        dialogInterface.dismiss();
                    }
                });

                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alert.show();
                return false;
            }
        });

//        //attempting to set Edit function
//        itemLayout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(StandManagerActivity.getInstance());
//
//                alert.setTitle("Alert!");
//                alert.setMessage("Do you want to edit this record?");
//
//                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        edit(standItem);
//                        dialogInterface.dismiss();
//                    }
//                });
//
//                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//
//                alert.show();
//            }
//        });

        //TODO - Display Weather in a TextView

        final TextView priorityView = (TextView) itemLayout.findViewById(R.id.WeatherLabel);;
        priorityView.setText(standItem.getPriority().toString());

        // TODO - Display Time and Date.
        // Hint - use StandItem.FORMAT.format(standItem.getDate()) to get date and time String

        final TextView dateView = (TextView) itemLayout.findViewById(R.id.dateView);
        dateView.setText(standItem.getDate().toString());
        // TODO - Dispplay Stand and Clay
        final TextView standView = (TextView) itemLayout.findViewById(R.id.standView);
        standView.setText(standItem.getStands());
        final TextView clayView = (TextView) itemLayout.findViewById(R.id.clayView);
        clayView.setText(standItem.getClays());

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
