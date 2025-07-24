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

public class MainActivity1 extends AppCompatActivity {

    private EditText employeeCode, name, designation, collegeInstitute, campus, department, joiningDate, assessmentPeriod;
    private Button nextButton;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("AppraisalForms");

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

                // Save data to Firebase
                saveDataToFirebase();

                // Navigate to MainActivity2
                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Save form data to Firebase Realtime Database
     */
    private void saveDataToFirebase() {
        // Create a unique key for each form
        String formId = databaseReference.push().getKey();

        // Create a data object
        AppraisalForm form = new AppraisalForm(
                employeeCode.getText().toString().trim(),
                name.getText().toString().trim(),
                designation.getText().toString().trim(),
                collegeInstitute.getText().toString().trim(),
                campus.getText().toString().trim(),
                department.getText().toString().trim(),
                joiningDate.getText().toString().trim(),
                assessmentPeriod.getText().toString().trim()
        );

        // Push data to Firebase
        if (formId != null) {
            databaseReference.child(formId).setValue(form).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity1.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity1.this, "Failed to save data. Try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * AppraisalForm class to store form data
     */
    public static class AppraisalForm {
        public String employeeCode, name, designation, collegeInstitute, campus, department, joiningDate, assessmentPeriod;

        // Default constructor for Firebase
        public AppraisalForm() {
        }

        // Constructor with parameters
        public AppraisalForm(String employeeCode, String name, String designation, String collegeInstitute,
                             String campus, String department, String joiningDate, String assessmentPeriod) {
            this.employeeCode = employeeCode;
            this.name = name;
            this.designation = designation;
            this.collegeInstitute = collegeInstitute;
            this.campus = campus;
            this.department = department;
            this.joiningDate = joiningDate;
            this.assessmentPeriod = assessmentPeriod;
        }
    }
}
