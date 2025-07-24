package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity8 extends AppCompatActivity {

    private EditText professorTotalMembers, professorOutstanding, professorCompetent,
            professorGood, professorSatisfactory, professorPoor;
    private EditText associateTotalMembers, associateOutstanding, associateCompetent,
            associateGood, associateSatisfactory, associatePoor;
    private EditText assistantTotalMembers, assistantOutstanding, assistantCompetent,
            assistantGood, assistantSatisfactory, assistantPoor;
    private EditText hodSignature;
    private Button submitButton;

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

        // Set up submit button listener
        submitButton.setOnClickListener(v -> {
            if (validateInput()) {
                onSubmit();
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

    private void onSubmit() {
        // Show success message
        Toast.makeText(this, "Form Submitted Successfully!", Toast.LENGTH_SHORT).show();

        // Optionally: Perform additional actions such as saving to a database
    }
}
