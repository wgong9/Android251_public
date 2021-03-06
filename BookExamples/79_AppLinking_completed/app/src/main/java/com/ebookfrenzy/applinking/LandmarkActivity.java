package com.ebookfrenzy.applinking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;

public class LandmarkActivity extends AppCompatActivity {

    private static final String TAG = "Database";
    private TextView titleText;
    private TextView descriptionText;
    private Button deleteButton;
    private Landmark landmark = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        titleText = findViewById(R.id.titleText);
        descriptionText = findViewById(R.id.descriptionText);
        deleteButton = findViewById(R.id.deleteButton);

        // handleIntent(getIntent());

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if (appLinkAction != null) {

            if (appLinkAction.equals("android.intent.action.VIEW")) {
                String landmarkId = appLinkData.getLastPathSegment();

                if (landmarkId != null) {
                    Log.i(TAG, "landmarkId = " + landmarkId);
                    displayLandmark(landmarkId);
                }
            }
        } else {
            handleIntent(appLinkIntent);
        }
    }

    private void handleIntent(Intent intent) {

        String landmarkId = intent.getStringExtra(AppLinkingActivity.LANDMARK_ID);
        displayLandmark(landmarkId);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }




    public void deleteLandmark(View view) {

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        if (landmark != null) {
            dbHandler.deleteLandmark(landmark.getID());
            titleText.setText("");
            descriptionText.setText("");
            deleteButton.setEnabled(false);
        }
    }

    private void displayLandmark(String landmarkId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        landmark =
                dbHandler.findLandmark(landmarkId);

        if (landmark != null) {

            if (landmark.getPersonal() == 0) {
                deleteButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
            }

            titleText.setText(landmark.getTitle());
            descriptionText.setText(landmark.getDescription());
        } else {
            titleText.setText("No Match Found");
        }
    }


}
