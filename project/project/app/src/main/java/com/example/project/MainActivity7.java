package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity7 extends AppCompatActivity {

    private EditText tlpObtained, cpdcObtained, cdlObtained, ciiObtained, iowObtained;
    private TextView totalMarksText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        // Initialize views
        tlpObtained = findViewById(R.id.tlp_obtained1);
        cpdcObtained = findViewById(R.id.cpdc_obtained2);
        cdlObtained = findViewById(R.id.cdl_obtained3);
        ciiObtained = findViewById(R.id.cii_obtained4);
        iowObtained = findViewById(R.id.iow_obtained5);
        totalMarksText = findViewById(R.id.total_marks_text);
        nextButton = findViewById(R.id.next_button);

        // Set up listener for the next button
        nextButton.setOnClickListener(v -> {
            calculateTotalMarks();  // Calculate total marks
            navigateToNextActivity(); // Navigate to MainActivity8
        });
    }

    private void calculateTotalMarks() {
        int totalMarks = 0;

        // Get obtained marks from EditText fields
        totalMarks += getObtainedMarks(tlpObtained); // Teaching Learning Process
        totalMarks += getObtainedMarks(cpdcObtained); // Professional Development
        totalMarks += getObtainedMarks(cdlObtained); // Contribution at Department Level
        totalMarks += getObtainedMarks(ciiObtained); // Contribution at Institutional Level
        totalMarks += getObtainedMarks(iowObtained); // Interaction with Outside World

        // Display total marks
        totalMarksText.setText("Total Obtained Marks: " + totalMarks);

        // Optional: Display a Toast message
        Toast.makeText(MainActivity7.this, "Total Marks Calculated!", Toast.LENGTH_SHORT).show();
    }

    private int getObtainedMarks(EditText editText) {
        int marks = 0;
        String input = editText.getText().toString().trim();

        // If input is not empty, parse it to an integer
        if (!input.isEmpty()) {
            try {
                marks = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                editText.setError("Invalid number!");
            }
        }
        return marks;
    }

    private void navigateToNextActivity() {
        // Navigate to MainActivity8
        Intent intent = new Intent(MainActivity7.this, MainActivity8.class);
        startActivity(intent);
    }
}
