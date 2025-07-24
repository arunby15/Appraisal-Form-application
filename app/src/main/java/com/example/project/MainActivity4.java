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

public class MainActivity4 extends AppCompatActivity {

    private RadioGroup qualificationGroup, certificationGroup, awardsGroup, researchGroup, booksGroup;
    private EditText qualificationDetails, certificationDetails, awardsDetails, researchDetails, booksDetails;
    private Button nextButton;

    // Firebase reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Initialize Views
        qualificationGroup = findViewById(R.id.qualification_improvements);
        certificationGroup = findViewById(R.id.certification);
        awardsGroup = findViewById(R.id.awards_recognition);
        researchGroup = findViewById(R.id.research_publications);
        booksGroup = findViewById(R.id.books_published);

        qualificationDetails = findViewById(R.id.qualification_details);
        certificationDetails = findViewById(R.id.certification_details);
        awardsDetails = findViewById(R.id.awards_details);
        researchDetails = findViewById(R.id.research_details);
        booksDetails = findViewById(R.id.books_details);

        nextButton = findViewById(R.id.next_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Details");

        // Add visibility logic for EditText fields
        setupRadioGroupListener(qualificationGroup, qualificationDetails);
        setupRadioGroupListener(certificationGroup, certificationDetails);
        setupRadioGroupListener(awardsGroup, awardsDetails);
        setupRadioGroupListener(researchGroup, researchDetails);
        setupRadioGroupListener(booksGroup, booksDetails);

        // Set onClickListener for Next button
        nextButton.setOnClickListener(v -> {
            if (!validateAllFields()) {
                Toast.makeText(MainActivity4.this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
            } else {
                saveDataToFirebase();
                Toast.makeText(MainActivity4.this, "Data saved to Firebase. Proceeding to next screen...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
                startActivity(intent);
            }
        });
    }

    private void setupRadioGroupListener(RadioGroup group, final EditText detailsField) {
        group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.qualification_yes || checkedId == R.id.certification_yes ||
                    checkedId == R.id.awards_yes || checkedId == R.id.research_yes || checkedId == R.id.books_yes) {
                detailsField.setVisibility(View.VISIBLE);
            } else {
                detailsField.setVisibility(View.GONE);
                detailsField.setText("");  // Clear the text if No is selected
            }
        });
    }

    private boolean validateAllFields() {
        // Ensure that one radio button is selected from each group
        return qualificationGroup.getCheckedRadioButtonId() != -1 &&
                certificationGroup.getCheckedRadioButtonId() != -1 &&
                awardsGroup.getCheckedRadioButtonId() != -1 &&
                researchGroup.getCheckedRadioButtonId() != -1 &&
                booksGroup.getCheckedRadioButtonId() != -1;
    }

    private void saveDataToFirebase() {
        // Get details from EditText fields
        String qualificationDetailsText = qualificationDetails.getText().toString().trim();
        String certificationDetailsText = certificationDetails.getText().toString().trim();
        String awardsDetailsText = awardsDetails.getText().toString().trim();
        String researchDetailsText = researchDetails.getText().toString().trim();
        String booksDetailsText = booksDetails.getText().toString().trim();

        // Create an object to represent the user's details
        UserDetails userDetails = new UserDetails(
                getRadioButtonValue(qualificationGroup), qualificationDetailsText,
                getRadioButtonValue(certificationGroup), certificationDetailsText,
                getRadioButtonValue(awardsGroup), awardsDetailsText,
                getRadioButtonValue(researchGroup), researchDetailsText,
                getRadioButtonValue(booksGroup), booksDetailsText
        );

        // Create a unique ID for the data entry
        String userId = databaseReference.push().getKey();

        if (userId != null) {
            // Save the data to Firebase under a new unique node
            databaseReference.child(userId).setValue(userDetails)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity4.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity4.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity4.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }


    private String getRadioButtonValue(RadioGroup group) {
        // Check the selected radio button value (Yes or No)
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId == R.id.qualification_yes || selectedId == R.id.certification_yes ||
                selectedId == R.id.awards_yes || selectedId == R.id.research_yes || selectedId == R.id.books_yes) {
            return "Yes";
        } else {
            return "No";
        }
    }

    // UserDetails class to store the user's information
    public static class UserDetails {
        public String qualificationStatus, qualificationDetails, certificationStatus, certificationDetails;
        public String awardsStatus, awardsDetails, researchStatus, researchDetails, booksStatus, booksDetails;

        public UserDetails() {
        }

        public UserDetails(String qualificationStatus, String qualificationDetails, String certificationStatus,
                           String certificationDetails, String awardsStatus, String awardsDetails,
                           String researchStatus, String researchDetails, String booksStatus, String booksDetails) {
            this.qualificationStatus = qualificationStatus;
            this.qualificationDetails = qualificationDetails;
            this.certificationStatus = certificationStatus;
            this.certificationDetails = certificationDetails;
            this.awardsStatus = awardsStatus;
            this.awardsDetails = awardsDetails;
            this.researchStatus = researchStatus;
            this.researchDetails = researchDetails;
            this.booksStatus = booksStatus;
            this.booksDetails = booksDetails;
        }
    }
}
