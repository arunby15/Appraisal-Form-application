package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity8 extends AppCompatActivity {

    private EditText professorTotalMembers, professorOutstanding, professorCompetent,
            professorGood, professorSatisfactory, professorPoor;
    private EditText associateTotalMembers, associateOutstanding, associateCompetent,
            associateGood, associateSatisfactory, associatePoor;
    private EditText assistantTotalMembers, assistantOutstanding, assistantCompetent,
            assistantGood, assistantSatisfactory, assistantPoor;
    private EditText hodSignature;
    private Button submitButton;

    // Firebase reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        // Initialize Professor section
        professorTotalMembers = findViewById(R.id.professor_total_members);
        professorOutstanding = findViewById(R.id.professor_outstanding);
        professorCompetent = findViewById(R.id.professor_competent);
        professorGood = findViewById(R.id.professor_good);
        professorSatisfactory = findViewById(R.id.professor_satisfactory);
        professorPoor = findViewById(R.id.professor_poor);

        // Initialize Associate Professor section
        associateTotalMembers = findViewById(R.id.associate_total_members);
        associateOutstanding = findViewById(R.id.associate_outstanding);
        associateCompetent = findViewById(R.id.associate_competent);
        associateGood = findViewById(R.id.associate_good);
        associateSatisfactory = findViewById(R.id.associate_satisfactory);
        associatePoor = findViewById(R.id.associate_poor);

        // Initialize Assistant Professor section
        assistantTotalMembers = findViewById(R.id.assistant_total_members);
        assistantOutstanding = findViewById(R.id.assistant_outstanding);
        assistantCompetent = findViewById(R.id.assistant_competent);
        assistantGood = findViewById(R.id.assistant_good);
        assistantSatisfactory = findViewById(R.id.assistant_satisfactory);
        assistantPoor = findViewById(R.id.assistant_poor);

        // Initialize HOD Signature section
        hodSignature = findViewById(R.id.hod_signature);

        // Initialize Submit Button
        submitButton = findViewById(R.id.submit_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("FacultyData");

        // Set up submit button listener
        submitButton.setOnClickListener(v -> {
            if (validateInput()) {
                saveDataToFirebase();  // Save the input data to Firebase
                onSubmit();  // Handle successful submission
            }
        });
    }

    private boolean validateInput() {
        return validateSection("Professor", professorTotalMembers, professorOutstanding,
                professorCompetent, professorGood, professorSatisfactory, professorPoor) &&
                validateSection("Associate Professor", associateTotalMembers, associateOutstanding,
                        associateCompetent, associateGood, associateSatisfactory, associatePoor) &&
                validateSection("Assistant Professor", assistantTotalMembers, assistantOutstanding,
                        assistantCompetent, assistantGood, assistantSatisfactory, assistantPoor);
    }

    private boolean validateSection(String sectionName, EditText totalEditText, EditText outstanding,
                                    EditText competent, EditText good, EditText satisfactory, EditText poor) {
        int total = parseInt(totalEditText);
        int sum = parseInt(outstanding) + parseInt(competent) + parseInt(good) +
                parseInt(satisfactory) + parseInt(poor);

        if (total != sum) {
            Toast.makeText(this, sectionName + " total does not match the sum of its subcategories!", Toast.LENGTH_SHORT).show();
            totalEditText.setError("Total must equal the sum of subcategories!");
            return false;
        }

        return true;
    }

    private int parseInt(EditText editText) {
        String input = editText.getText().toString().trim();
        if (!input.isEmpty()) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                editText.setError("Invalid number!");
            }
        }
        return 0;
    }

    private void saveDataToFirebase() {
        // Get values from the EditText fields
        int professorTotal = parseInt(professorTotalMembers);
        int professorOutstandingValue = parseInt(professorOutstanding);
        int professorCompetentValue = parseInt(professorCompetent);
        int professorGoodValue = parseInt(professorGood);
        int professorSatisfactoryValue = parseInt(professorSatisfactory);
        int professorPoorValue = parseInt(professorPoor);

        int associateTotal = parseInt(associateTotalMembers);
        int associateOutstandingValue = parseInt(associateOutstanding);
        int associateCompetentValue = parseInt(associateCompetent);
        int associateGoodValue = parseInt(associateGood);
        int associateSatisfactoryValue = parseInt(associateSatisfactory);
        int associatePoorValue = parseInt(associatePoor);

        int assistantTotal = parseInt(assistantTotalMembers);
        int assistantOutstandingValue = parseInt(assistantOutstanding);
        int assistantCompetentValue = parseInt(assistantCompetent);
        int assistantGoodValue = parseInt(assistantGood);
        int assistantSatisfactoryValue = parseInt(assistantSatisfactory);
        int assistantPoorValue = parseInt(assistantPoor);

        String hodSign = hodSignature.getText().toString().trim();

        // Create an object to represent the data
        FacultyData facultyData = new FacultyData(professorTotal, professorOutstandingValue,
                professorCompetentValue, professorGoodValue, professorSatisfactoryValue, professorPoorValue,
                associateTotal, associateOutstandingValue, associateCompetentValue, associateGoodValue,
                associateSatisfactoryValue, associatePoorValue,
                assistantTotal, assistantOutstandingValue, assistantCompetentValue, assistantGoodValue,
                assistantSatisfactoryValue, assistantPoorValue, hodSign);

        // Push data to Firebase
        String dataId = databaseReference.push().getKey();
        if (dataId != null) {
            databaseReference.child(dataId).setValue(facultyData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity8.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity8.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity8.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void onSubmit() {
        // Show success message
        Toast.makeText(this, "Form Submitted Successfully!", Toast.LENGTH_SHORT).show();
    }

    // Data model class to store faculty information
    public static class FacultyData {
        public int professorTotal, professorOutstanding, professorCompetent, professorGood, professorSatisfactory, professorPoor;
        public int associateTotal, associateOutstanding, associateCompetent, associateGood, associateSatisfactory, associatePoor;
        public int assistantTotal, assistantOutstanding, assistantCompetent, assistantGood, assistantSatisfactory, assistantPoor;
        public String hodSignature;

        public FacultyData() {
            // Default constructor required for Firebase
        }

        public FacultyData(int professorTotal, int professorOutstanding, int professorCompetent, int professorGood, int professorSatisfactory, int professorPoor,
                           int associateTotal, int associateOutstanding, int associateCompetent, int associateGood, int associateSatisfactory, int associatePoor,
                           int assistantTotal, int assistantOutstanding, int assistantCompetent, int assistantGood, int assistantSatisfactory, int assistantPoor,
                           String hodSignature) {
            this.professorTotal = professorTotal;
            this.professorOutstanding = professorOutstanding;
            this.professorCompetent = professorCompetent;
            this.professorGood = professorGood;
            this.professorSatisfactory = professorSatisfactory;
            this.professorPoor = professorPoor;
            this.associateTotal = associateTotal;
            this.associateOutstanding = associateOutstanding;
            this.associateCompetent = associateCompetent;
            this.associateGood = associateGood;
            this.associateSatisfactory = associateSatisfactory;
            this.associatePoor = associatePoor;
            this.assistantTotal = assistantTotal;
            this.assistantOutstanding = assistantOutstanding;
            this.assistantCompetent = assistantCompetent;
            this.assistantGood = assistantGood;
            this.assistantSatisfactory = assistantSatisfactory;
            this.assistantPoor = assistantPoor;
            this.hodSignature = hodSignature;
        }
    }
}
