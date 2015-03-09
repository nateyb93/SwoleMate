package edu.up.swolemate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class HistoryTrackingActivity extends Activity {

    protected List<BaseWorkout> workouts;

    protected WorkoutAdapter workoutAdapter;


    protected ListView workoutListView;

    protected Spinner dateSelector;

    protected Spinner workoutTypeSelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_tracking);

        //initialize
        workouts = new ArrayList<BaseWorkout>();
        setupSpinners();

        //for testing purposes only
        initTestWorkouts();

        //initialize workoutListView and its adapter
        workoutAdapter = new WorkoutAdapter(this, R.layout.list_item_workout, workouts);
        workoutListView = (ListView)findViewById(R.id.lv_history);
        setHistoryListener();
        workoutListView.setAdapter(workoutAdapter);
    }

    private void initTestWorkouts() {
        StrengthWorkout s = new StrengthWorkout().initTestValues();
        workouts.add(s);

        CardioWorkout ca = new CardioWorkout().initTestValues();
        workouts.add(ca);

        CustomWorkout cu = new CustomWorkout().initTestValues();
        workouts.add(cu);
    }

    private void setupSpinners() {
        dateSelector = (Spinner)findViewById(R.id.spin_dateSelector);

        ArrayList<String> strings = new ArrayList<String>();

        strings.add("Today");
        strings.add("This week");
        strings.add("This month");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        dateSelector.setAdapter(adapter);


        workoutTypeSelector = (Spinner)findViewById(R.id.spin_workoutTypes);
        ArrayList<String> workoutTypes = new ArrayList<String>();

        workoutTypes.add("All");
        workoutTypes.add("Strength");
        workoutTypes.add("Cardio");
        workoutTypes.add("Custom");

        ArrayAdapter<String> workoutTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, workoutTypes);
        workoutTypeSelector.setAdapter(workoutTypesAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    public void onNewWorkoutClick(View v) {
        Intent i = new Intent(this, CreateWorkoutActivity.class);
        startActivity(i);
    }


    /**
     * Initialize the listener for the history item click
     */
    public void setHistoryListener() {
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                BaseWorkout workout = (BaseWorkout)adapterView.getItemAtPosition(i);

                //Safely cast workout object
                if(workout instanceof StrengthWorkout) {
                    workout = (StrengthWorkout)workout;
                } else if(workout instanceof CardioWorkout) {
                    workout = (CardioWorkout)workout;
                } else if(workout instanceof CustomWorkout) {
                    workout = (CustomWorkout)workout;
                } else {

                }

                Log.d("Item clicked", "");
                popupViewWorkout(workout);

            }
        });
    }


    public void popupViewWorkout(BaseWorkout workout) {
        workout.setContext(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View root = inflater.inflate(R.layout.dialog_view_workout, null);

        TextView titleTextView = (TextView)root.findViewById(R.id.tv_view_workout_title);
        TextView descriptionTextView = (TextView)root.findViewById(R.id.tv_view_workout_description);

        titleTextView.setText(workout.getDisplayName());
        descriptionTextView.setText(workout.toString());

        dialog.setTitle("View Workout");
        dialog.setView(root);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //don't do anything
            }
        });

        dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteWorkout();
            }
        });

        dialog.create().show();


    }

    public void deleteWorkout() {
        AlertDialog.Builder delete = new AlertDialog.Builder(this);
        delete.setTitle("Delete");
        delete.setMessage("Are you sure you want to delete this workout?");
        delete.setNegativeButton("Cancel", null);
        delete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO: Add delete things
            }
        });

        delete.create().show();
    }
}
