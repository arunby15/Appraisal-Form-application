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

public class MainActivity5 extends AppCompatActivity {

    private EditText conductDetails, trainingDetails, participationDetails, naacDetails, internshipDetails, otherDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        // Initialize EditText fields
        conductDetails = findViewById(R.id.conduct_details_c);
        trainingDetails = findViewById(R.id.training_details1);
        participationDetails = findViewById(R.id.participation_details1);
        naacDetails = findViewById(R.id.naac_details1);
        internshipDetails = findViewById(R.id.internship_details1);
        otherDetails = findViewById(R.id.other_details1);

        // Initialize RadioGroups
        setupRadioGroup(findViewById(R.id.conduct_activities_group), conductDetails);
        setupRadioGroup(findViewById(R.id.training_program_group), trainingDetails);
        setupRadioGroup(findViewById(R.id.training_participation_group), participationDetails);
        setupRadioGroup(findViewById(R.id.naac_group), naacDetails);
        setupRadioGroup(findViewById(R.id.internship_group), internshipDetails);
        setupRadioGroup(findViewById(R.id.other_responsibilities_group), otherDetails);

        // Handle Next button
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity5.this, "Proceeding to the next screen...", Toast.LENGTH_SHORT).show();

            // Navigate to the next activity (e.g., MainActivity6)
            Intent intent = new Intent(MainActivity5.this, MainActivity6.class);
            startActivity(intent);
        });
    }

    private void setupRadioGroup(RadioGroup group, EditText detailsField) {
        group.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton selectedButton = findViewById(checkedId);
            if (selectedButton != null && "Yes".equals(selectedButton.getText().toString())) {
                detailsField.setVisibility(View.VISIBLE);
            } else {
                detailsField.setVisibility(View.GONE);
                detailsField.setText(""); // Clear details if hidden
            }
        });
    }
}
