package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity3 extends AppCompatActivity {

    private EditText semesterNoFeedback, theory1Feedback, theory2Feedback, practical1Feedback, practical2Feedback;
    private EditText theory1Results, theory2Results, practical1Results, practical2Results;
    private Button nextButton;

    // Firebase reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Initialize EditTexts
        semesterNoFeedback = findViewById(R.id.semester_no_feedback);
        theory1Feedback = findViewById(R.id.theory1_students2);
        theory2Feedback = findViewById(R.id.theory2_students2);
        practical1Feedback = findViewById(R.id.practical1_students2);
        practical2Feedback = findViewById(R.id.practical2_students2);
        theory1Results = findViewById(R.id.theory1_students_r);
        theory2Results = findViewById(R.id.theory2_students_r);
        practical1Results = findViewById(R.id.practical1_students_r);
        practical2Results = findViewById(R.id.practical2_students_r);
        nextButton = findViewById(R.id.next_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("FeedbackForms");

        // Retrieve and set initial data from previous activity
        Intent intent = getIntent();
        semesterNoFeedback.setText(intent.getStringExtra("semester")); // Set semester value

        // Set onClickListener for Next Button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input
                if (!validateFields()) {
                    Toast.makeText(MainActivity3.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save data to Firebase
                saveDataToFirebase();

                // Pass data to the next activity
                Intent nextIntent = new Intent(MainActivity3.this, MainActivity4.class);
                nextIntent.putExtra("semester", semesterNoFeedback.getText().toString().trim());
                nextIntent.putExtra("theory1", theory1Feedback.getText().toString().trim());
                nextIntent.putExtra("theory2", theory2Feedback.getText().toString().trim());
                nextIntent.putExtra("practical1", practical1Feedback.getText().toString().trim());
                nextIntent.putExtra("practical2", practical2Feedback.getText().toString().trim());
                nextIntent.putExtra("theory1Results", theory1Results.getText().toString().trim());
                nextIntent.putExtra("theory2Results", theory2Results.getText().toString().trim());
                nextIntent.putExtra("practical1Results", practical1Results.getText().toString().trim());
                nextIntent.putExtra("practical2Results", practical2Results.getText().toString().trim());
                startActivity(nextIntent);
            }
        });
    }

    private boolean validateFields() {
        return !semesterNoFeedback.getText().toString().trim().isEmpty()
                && !theory1Feedback.getText().toString().trim().isEmpty()
                && !theory2Feedback.getText().toString().trim().isEmpty()
                && !practical1Feedback.getText().toString().trim().isEmpty()
                && !practical2Feedback.getText().toString().trim().isEmpty()
                && !theory1Results.getText().toString().trim().isEmpty()
                && !theory2Results.getText().toString().trim().isEmpty()
                && !practical1Results.getText().toString().trim().isEmpty()
                && !practical2Results.getText().toString().trim().isEmpty();
    }

    /**
     * Save form data to Firebase Realtime Database
     */
    private void saveDataToFirebase() {
        try {
            // Create a unique key for the entry in Firebase
            String formId = databaseReference.push().getKey();

            if (formId != null) {
                // Convert EditText values to integers and handle potential errors
                int semesterNo = Integer.parseInt(semesterNoFeedback.getText().toString().trim());
                int theory1FeedbackValue = Integer.parseInt(theory1Feedback.getText().toString().trim());
                int theory2FeedbackValue = Integer.parseInt(theory2Feedback.getText().toString().trim());
                int practical1FeedbackValue = Integer.parseInt(practical1Feedback.getText().toString().trim());
                int practical2FeedbackValue = Integer.parseInt(practical2Feedback.getText().toString().trim());
                int theory1ResultsValue = Integer.parseInt(theory1Results.getText().toString().trim());
                int theory2ResultsValue = Integer.parseInt(theory2Results.getText().toString().trim());
                int practical1ResultsValue = Integer.parseInt(practical1Results.getText().toString().trim());
                int practical2ResultsValue = Integer.parseInt(practical2Results.getText().toString().trim());

                // Create an object to represent the form data
                FeedbackForm feedback = new FeedbackForm(
                        semesterNo, theory1FeedbackValue, theory2FeedbackValue,
                        practical1FeedbackValue, practical2FeedbackValue,
                        theory1ResultsValue, theory2ResultsValue,
                        practical1ResultsValue, practical2ResultsValue
                );

                // Push the data to Firebase Realtime Database
                databaseReference.child(formId).setValue(feedback)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity3.this, "Feedback data saved successfully to Firebase!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity3.this, "Failed to save data. Try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } catch (NumberFormatException e) {
            // If the input is not a valid integer, show an error message
            Toast.makeText(MainActivity3.this, "Please enter valid integer values!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * FeedbackForm class to store feedback and results data
     */
    public static class FeedbackForm {
        public int semesterNo, theory1Feedback, theory2Feedback, practical1Feedback, practical2Feedback;
        public int theory1Results, theory2Results, practical1Results, practical2Results;

        // Default constructor for Firebase
        public FeedbackForm() {}

        // Constructor with parameters
        public FeedbackForm(int semesterNo, int theory1Feedback, int theory2Feedback,
                            int practical1Feedback, int practical2Feedback,
                            int theory1Results, int theory2Results,
                            int practical1Results, int practical2Results) {
            this.semesterNo = semesterNo;
            this.theory1Feedback = theory1Feedback;
            this.theory2Feedback = theory2Feedback;
            this.practical1Feedback = practical1Feedback;
            this.practical2Feedback = practical2Feedback;
            this.theory1Results = theory1Results;
            this.theory2Results = theory2Results;
            this.practical1Results = practical1Results;
            this.practical2Results = practical2Results;
        }
    }
}
