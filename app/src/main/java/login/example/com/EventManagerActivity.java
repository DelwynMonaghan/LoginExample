package login.example.com;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import login.example.com.EventItem.Status;
import login.example.com.EventItem.Weather;

public class EventManagerActivity extends ListActivity {

    // Add a EventItem Request Code
    private static final int ADD_EVENT_ITEM_REQUEST = 0;
    private static final int EDIT_EVENT_ITEM_REQUEST = 1;


    private static final String FILE_NAME = "TodoManagerActivityData.txt";
    private static final String TAG = "Lab-UserInterface";

    // IDs for menu items
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_DUMP = Menu.FIRST + 1;

    EventListAdapter mAdapter;
    protected static EventManagerActivity instance;

    public static EventManagerActivity getInstance()
    {
        return instance;
    }

    public EventManagerActivity()
    {
        super();
        instance = this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new TodoListAdapter for this ListActivity's ListView
        mAdapter = new EventListAdapter(getApplicationContext());

        // Put divider between ToDoItems and FooterView
        getListView().setFooterDividersEnabled(true);

        // TODO - Inflate footerView for footer_view.xml file
        TextView footerView = null;
        footerView = (TextView) getLayoutInflater().inflate(
                R.layout.footer_view, null);

        // TODO - Add footerView to ListView
        this.getListView().addFooterView(footerView);

        footerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                log("Entered footerView.OnClickListener.onClick()");

                // TODO - Attach Listener to FooterView. Implement onClick().
                // Start the new todo item activity
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivityForResult(intent, ADD_EVENT_ITEM_REQUEST);
            }
        });

        // TODO - Attach the adapter to this ListActivity's ListView
        setListAdapter(mAdapter); // Bind to our new adapter.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        log("Entered onActivityResult()");

        // TODO - Check result code and request code.
        // If user submitted a new EventItem
        // Create a new EventItem from the data Intent
        // and then add it to the adapter
        if (requestCode == ADD_EVENT_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                EventItem eventItem = new EventItem(data);
                mAdapter.add(eventItem);
            }
        }
        else if (requestCode == EDIT_EVENT_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                EventItem eventItem = new EventItem(data);
                //mAdapter.add(eventItem);
                Toast.makeText(instance, "In Edit on eventmanageractivity", Toast.LENGTH_SHORT).show();
                mAdapter.edit(eventItem);
            }
        }

    }

    // Do not modify below here

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary
        if (mAdapter.getCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save ToDoItems
        saveItems();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                mAdapter.clear();
                return true;
            case MENU_DUMP:
                dump();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dump() {

        for (int i = 0; i < mAdapter.getCount(); i++) {
            String data = ((EventItem) mAdapter.getItem(i)).toLog();
            log("Item " + i + ": " + data.replace(EventItem.ITEM_SEP, ","));
        }

    }

    // Load stored ToDoItems
    private void loadItems() {
        BufferedReader reader = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fis));

            String title = null;
            String priority = null;
            String status = null;
            Date date = null;
            String stands = null;
            String clays = null;

            while (null != (title = reader.readLine())) {
                priority = reader.readLine();
                status = reader.readLine();
                date = EventItem.FORMAT.parse(reader.readLine());
                stands = reader.readLine();
                clays = reader.readLine();
                mAdapter.add(new EventItem(title, Weather.valueOf(priority),
                        Status.valueOf(status), date, stands, clays));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Save ToDoItems to file
    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    fos)));

            for (int idx = 0; idx < mAdapter.getCount(); idx++) {

                writer.println(mAdapter.getItem(idx));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
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
