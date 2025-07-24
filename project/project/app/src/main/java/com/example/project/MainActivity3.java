package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    private EditText semesterNoFeedback, theory1Feedback, theory2Feedback, practical1Feedback, practical2Feedback;
    private EditText theory1Results, theory2Results, practical1Results, practical2Results;
    private Button nextButton;

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

        // Retrieve and set initial data
        Intent intent = getIntent();
        semesterNoFeedback.setText(intent.getStringExtra("semester")); // Set semester value
        theory1Feedback.setText(""); // Clear feedback fields
        theory2Feedback.setText("");
        practical1Feedback.setText("");
        practical2Feedback.setText("");

        // Set onClickListener for Next Button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input
                if (!validateFields()) {
                    Toast.makeText(MainActivity3.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

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
}
