package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private EditText semesterNo, totalAllocatedLectures, lecturesTaken, hoursAllocated, hoursTaken;
    private EditText theory1Students, theory2Students, practical1Students, practical2Students;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize fields
        semesterNo = findViewById(R.id.semester_no);
        totalAllocatedLectures = findViewById(R.id.total_allocated_lectures);
        lecturesTaken = findViewById(R.id.lectures_taken);
        hoursAllocated = findViewById(R.id.hours_allocated);
        hoursTaken = findViewById(R.id.hours_taken);
        theory1Students = findViewById(R.id.theory1_students);
        theory2Students = findViewById(R.id.theory2_students);
        practical1Students = findViewById(R.id.practical1_students);
        practical2Students = findViewById(R.id.practical2_students);
        nextButton = findViewById(R.id.next_button);

        // Set onClickListener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate user input
                if (!validateFields()) {
                    Toast.makeText(MainActivity2.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get user input values
                Intent intent2 = new Intent(MainActivity2.this, MainActivity3.class);
                intent2.putExtra("semester", semesterNo.getText().toString().trim());
                intent2.putExtra("totalLectures", totalAllocatedLectures.getText().toString().trim());
                intent2.putExtra("lecturesTaken", lecturesTaken.getText().toString().trim());
                intent2.putExtra("hoursAllocated", hoursAllocated.getText().toString().trim());
                intent2.putExtra("hoursTaken", hoursTaken.getText().toString().trim());
                intent2.putExtra("theory1", theory1Students.getText().toString().trim());
                intent2.putExtra("theory2", theory2Students.getText().toString().trim());
                intent2.putExtra("practical1", practical1Students.getText().toString().trim());
                intent2.putExtra("practical2", practical2Students.getText().toString().trim());
                startActivity(intent2);
            }
        });
    }

    private boolean validateFields() {
        return !semesterNo.getText().toString().trim().isEmpty()
                && !totalAllocatedLectures.getText().toString().trim().isEmpty()
                && !lecturesTaken.getText().toString().trim().isEmpty()
                && !hoursAllocated.getText().toString().trim().isEmpty()
                && !hoursTaken.getText().toString().trim().isEmpty()
                && !theory1Students.getText().toString().trim().isEmpty()
                && !theory2Students.getText().toString().trim().isEmpty()
                && !practical1Students.getText().toString().trim().isEmpty()
                && !practical2Students.getText().toString().trim().isEmpty();
    }
}
