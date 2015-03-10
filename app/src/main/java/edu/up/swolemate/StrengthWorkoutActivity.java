package edu.up.swolemate;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.ContentHandler;
import java.sql.SQLException;


public class StrengthWorkoutActivity extends Activity {

    /**
     * References the current workout being created
     */
    protected StrengthWorkout currentWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_workout);
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

    /**
     * Inserts the current workout into the database
     */
    private void insertCurrentWorkout() {
        FitnessDatabaseHelper db = new FitnessDatabaseHelper(this);
        db.insertWorkout(currentWorkout);
    }



    /**
     * Deletes all records from the StrengthWorkouts table.
     * Test function
     */
    private void deleteAll() {
        SQLiteDatabase db = new FitnessDatabaseHelper(this).getReadableDatabase();

        String query = "DELETE FROM StrengthWorkouts";
        db.execSQL(query);

        Log.d("Spacer", "------------------------------------");
        selectAll();
        Log.d("Spacer", "------------------------------------");
    }

    /**
     * Prints out records from the database
     * Test function
     */
    private void selectAll() {
        SQLiteDatabase db = new FitnessDatabaseHelper(this).getReadableDatabase();

        String query = "SELECT * FROM StrengthWorkouts";
        Cursor c = db.rawQuery(query, null);

        while(c.moveToNext()) {
            Log.d("Workouts in database", c.getString(0) + " : " + c.getString(1));
        }
    }
}
