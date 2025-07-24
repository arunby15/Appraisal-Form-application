package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity5 extends AppCompatActivity {

    private RadioGroup conductGroup, trainingGroup, participationGroup, naacGroup, internshipGroup, otherGroup;
    private EditText conductDetails, trainingDetails, participationDetails, naacDetails, internshipDetails, otherDetails;
    private Button nextButton;

    // Firebase reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        // Initialize Views
        conductGroup = findViewById(R.id.conduct_activities_group);
        trainingGroup = findViewById(R.id.training_program_group);
        participationGroup = findViewById(R.id.training_participation_group);
        naacGroup = findViewById(R.id.naac_group);
        internshipGroup = findViewById(R.id.internship_group);
        otherGroup = findViewById(R.id.other_responsibilities_group);

        conductDetails = findViewById(R.id.conduct_details_c);
        trainingDetails = findViewById(R.id.training_details1);
        participationDetails = findViewById(R.id.participation_details1);
        naacDetails = findViewById(R.id.naac_details1);
        internshipDetails = findViewById(R.id.internship_details1);
        otherDetails = findViewById(R.id.other_details1);

        nextButton = findViewById(R.id.next_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Details");

        // Add visibility logic for EditText fields
        setupRadioGroupListener(conductGroup, conductDetails);
        setupRadioGroupListener(trainingGroup, trainingDetails);
        setupRadioGroupListener(participationGroup, participationDetails);
        setupRadioGroupListener(naacGroup, naacDetails);
        setupRadioGroupListener(internshipGroup, internshipDetails);
        setupRadioGroupListener(otherGroup, otherDetails);

        // Set onClickListener for Next button
        nextButton.setOnClickListener(v -> {
            if (!validateAllFields()) {
                Toast.makeText(MainActivity5.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
            } else {
                saveDataToFirebase();
                Toast.makeText(MainActivity5.this, "Data saved to Firebase. Proceeding to next screen...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
                startActivity(intent);
            }
        });
    }

    private void setupRadioGroupListener(RadioGroup group, final EditText detailsField) {
        group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.conduct_yes || checkedId == R.id.training_yes ||
                    checkedId == R.id.participation_yes || checkedId == R.id.naac_yes ||
                    checkedId == R.id.internship_yes || checkedId == R.id.other_yes) {
                detailsField.setVisibility(View.VISIBLE);
            } else {
                detailsField.setVisibility(View.GONE);
                detailsField.setText(""); // Clear the text if No is selected
            }
        });
    }

    private boolean validateAllFields() {
        // Ensure that one radio button is selected from each group
        return conductGroup.getCheckedRadioButtonId() != -1 &&
                trainingGroup.getCheckedRadioButtonId() != -1 &&
                participationGroup.getCheckedRadioButtonId() != -1 &&
                naacGroup.getCheckedRadioButtonId() != -1 &&
                internshipGroup.getCheckedRadioButtonId() != -1 &&
                otherGroup.getCheckedRadioButtonId() != -1;
    }

    private void saveDataToFirebase() {
        // Get details from EditText fields
        String conductDetailsText = conductDetails.getText().toString().trim();
        String trainingDetailsText = trainingDetails.getText().toString().trim();
        String participationDetailsText = participationDetails.getText().toString().trim();
        String naacDetailsText = naacDetails.getText().toString().trim();
        String internshipDetailsText = internshipDetails.getText().toString().trim();
        String otherDetailsText = otherDetails.getText().toString().trim();

        // Create an object to represent the user's details
        UserDetails userDetails = new UserDetails(
                getRadioButtonValue(conductGroup), conductDetailsText,
                getRadioButtonValue(trainingGroup), trainingDetailsText,
                getRadioButtonValue(participationGroup), participationDetailsText,
                getRadioButtonValue(naacGroup), naacDetailsText,
                getRadioButtonValue(internshipGroup), internshipDetailsText,
                getRadioButtonValue(otherGroup), otherDetailsText
        );

        // Create a unique ID for the data entry
        String userId = databaseReference.push().getKey();

        if (userId != null) {
            // Save the data to Firebase under a new unique node
            databaseReference.child(userId).setValue(userDetails)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity5.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity5.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity5.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private String getRadioButtonValue(RadioGroup group) {
        // Check the selected radio button value (Yes or No)
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId == R.id.conduct_yes || selectedId == R.id.training_yes ||
                selectedId == R.id.participation_yes || selectedId == R.id.naac_yes ||
                selectedId == R.id.internship_yes || selectedId == R.id.other_yes) {
            return "Yes";
        } else {
            return "No";
        }
    }

    // UserDetails class to store the user's information
    public static class UserDetails {
        public String conductStatus, conductDetails, trainingStatus, trainingDetails;
        public String participationStatus, participationDetails, naacStatus, naacDetails;
        public String internshipStatus, internshipDetails, otherStatus, otherDetails;

        public UserDetails() {
        }

        public UserDetails(String conductStatus, String conductDetails, String trainingStatus, String trainingDetails,
                           String participationStatus, String participationDetails, String naacStatus, String naacDetails,
                           String internshipStatus, String internshipDetails, String otherStatus, String otherDetails) {
            this.conductStatus = conductStatus;
            this.conductDetails = conductDetails;
            this.trainingStatus = trainingStatus;
            this.trainingDetails = trainingDetails;
            this.participationStatus = participationStatus;
            this.participationDetails = participationDetails;
            this.naacStatus = naacStatus;
            this.naacDetails = naacDetails;
            this.internshipStatus = internshipStatus;
            this.internshipDetails = internshipDetails;
            this.otherStatus = otherStatus;
            this.otherDetails = otherDetails;
        }
    }
}
