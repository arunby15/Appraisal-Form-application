package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity6 extends AppCompatActivity {

    private EditText speakerDetails, projectsDetails, expertDetails, judgeDetails, resourceDetails;
    private RadioGroup speakerGroup, projectsGroup, expertGroup, judgeGroup, resourceGroup;
    private Button nextButton;

    // Firebase reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        // Initialize Views
        speakerGroup = findViewById(R.id.speaker_group);
        projectsGroup = findViewById(R.id.projects_group);
        expertGroup = findViewById(R.id.expert_group);
        judgeGroup = findViewById(R.id.judge_group);
        resourceGroup = findViewById(R.id.resource_group);

        speakerDetails = findViewById(R.id.speaker_details);
        projectsDetails = findViewById(R.id.projects_details);
        expertDetails = findViewById(R.id.expert_details);
        judgeDetails = findViewById(R.id.judge_details);
        resourceDetails = findViewById(R.id.resource_details);

        nextButton = findViewById(R.id.next_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Details");

        // Set up listeners for each RadioGroup
        setupRadioGroupListener(speakerGroup, speakerDetails);
        setupRadioGroupListener(projectsGroup, projectsDetails);
        setupRadioGroupListener(expertGroup, expertDetails);
        setupRadioGroupListener(judgeGroup, judgeDetails);
        setupRadioGroupListener(resourceGroup, resourceDetails);

        // Handle Next button click
        nextButton.setOnClickListener(v -> {
            if (!validateAllFields()) {
                Toast.makeText(MainActivity6.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
            } else {
                saveDataToFirebase();
                Toast.makeText(MainActivity6.this, "Data saved to Firebase. Proceeding to next screen...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity6.this, MainActivity7.class);
                startActivity(intent);
            }
        });
    }

    private void setupRadioGroupListener(RadioGroup group, final EditText detailsField) {
        group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.speaker_yes || checkedId == R.id.projects_yes ||
                    checkedId == R.id.expert_yes || checkedId == R.id.judge_yes || checkedId == R.id.resource_yes) {
                detailsField.setVisibility(View.VISIBLE);
            } else {
                detailsField.setVisibility(View.GONE);
                detailsField.setText("");  // Clear the text if No is selected
            }
        });
    }

    private boolean validateAllFields() {
        return speakerGroup.getCheckedRadioButtonId() != -1 &&
                projectsGroup.getCheckedRadioButtonId() != -1 &&
                expertGroup.getCheckedRadioButtonId() != -1 &&
                judgeGroup.getCheckedRadioButtonId() != -1 &&
                resourceGroup.getCheckedRadioButtonId() != -1;
    }

    private void saveDataToFirebase() {
        // Get details from EditText fields
        String speakerDetailsText = speakerDetails.getText().toString().trim();
        String projectsDetailsText = projectsDetails.getText().toString().trim();
        String expertDetailsText = expertDetails.getText().toString().trim();
        String judgeDetailsText = judgeDetails.getText().toString().trim();
        String resourceDetailsText = resourceDetails.getText().toString().trim();

        // Create an object to represent the user's details
        UserDetails userDetails = new UserDetails(
                getRadioButtonValue(speakerGroup), speakerDetailsText,
                getRadioButtonValue(projectsGroup), projectsDetailsText,
                getRadioButtonValue(expertGroup), expertDetailsText,
                getRadioButtonValue(judgeGroup), judgeDetailsText,
                getRadioButtonValue(resourceGroup), resourceDetailsText
        );

        // Create a unique ID for the data entry
        String userId = databaseReference.push().getKey();

        if (userId != null) {
            // Save the data to Firebase under a new unique node
            databaseReference.child(userId).setValue(userDetails)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity6.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity6.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity6.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private String getRadioButtonValue(RadioGroup group) {
        // Check the selected radio button value (Yes or No)
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId == R.id.speaker_yes || selectedId == R.id.projects_yes ||
                selectedId == R.id.expert_yes || selectedId == R.id.judge_yes || selectedId == R.id.resource_yes) {
            return "Yes";
        } else {
            return "No";
        }
    }

    // UserDetails class to store the user's information
    public static class UserDetails {
        public String speakerStatus, speakerDetails, projectsStatus, projectsDetails;
        public String expertStatus, expertDetails, judgeStatus, judgeDetails, resourceStatus, resourceDetails;

        public UserDetails() {
        }

        public UserDetails(String speakerStatus, String speakerDetails, String projectsStatus, String projectsDetails,
                           String expertStatus, String expertDetails, String judgeStatus, String judgeDetails,
                           String resourceStatus, String resourceDetails) {
            this.speakerStatus = speakerStatus;
            this.speakerDetails = speakerDetails;
            this.projectsStatus = projectsStatus;
            this.projectsDetails = projectsDetails;
            this.expertStatus = expertStatus;
            this.expertDetails = expertDetails;
            this.judgeStatus = judgeStatus;
            this.judgeDetails = judgeDetails;
            this.resourceStatus = resourceStatus;
            this.resourceDetails = resourceDetails;
        }
    }
}
