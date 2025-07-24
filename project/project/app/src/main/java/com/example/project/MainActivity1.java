package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity {

    private EditText employeeCode, name, designation, collegeInstitute, campus, department, joiningDate, assessmentPeriod;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        // Initialize fields
        employeeCode = findViewById(R.id.employeeCode);
        name = findViewById(R.id.name);
        designation = findViewById(R.id.designation);
        collegeInstitute = findViewById(R.id.collegeInstitute);
        campus = findViewById(R.id.campus);
        department = findViewById(R.id.department);
        joiningDate = findViewById(R.id.joiningDate);
        assessmentPeriod = findViewById(R.id.assessmentPeriod);
        nextButton = findViewById(R.id.nextButton);

        // Set Next Button Click Listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all fields are filled
                if (employeeCode.getText().toString().trim().isEmpty() ||
                        name.getText().toString().trim().isEmpty() ||
                        designation.getText().toString().trim().isEmpty() ||
                        collegeInstitute.getText().toString().trim().isEmpty() ||
                        campus.getText().toString().trim().isEmpty() ||
                        department.getText().toString().trim().isEmpty() ||
                        joiningDate.getText().toString().trim().isEmpty() ||
                        assessmentPeriod.getText().toString().trim().isEmpty()) {

                    Toast.makeText(MainActivity1.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Navigate to MainActivity2
                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}
