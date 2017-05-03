package com.example.android.climatehero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class TrackDiet extends AppCompatActivity {
    private int dietScore;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference scoreReference = FirebaseDatabase.getInstance().getReference(auth.getCurrentUser().getUid() + " /scores");
    private ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_diet);
        scoreAdapter = new ScoreAdapter(scoreReference);
    }

    public void vegan(View view) {
        dietScore = 2;
    }

    public void vegetarian(View view) {
        dietScore = 1;
    }

    public void omnivore(View view) {
        dietScore = 0;
    }

    public void meat(View view) {
        dietScore = -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_or_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                Toast.makeText(this, "Back to home screen", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_delete:
                Toast.makeText(this, "Diet deleted", Toast.LENGTH_SHORT).show();
                dietScore = 0;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void trackNext(View view) {

        Score s = new Score(dietScore, "Food Efficiency", R.drawable.spinach);
        String id = UUID.randomUUID().toString();
        scoreReference.child(id).setValue(s);

        Intent i = new Intent(this, DailyTravel.class);
        startActivity(i);
    }
}
