package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    private RadioGroup qualificationGroup, certificationGroup, awardsGroup, researchGroup, booksGroup;
    private EditText qualificationDetails, certificationDetails, awardsDetails, researchDetails, booksDetails;
    private Button nextButton;

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
                Toast.makeText(MainActivity4.this, "Proceeding to the next screen...", Toast.LENGTH_SHORT).show();
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
                detailsField.setText("");
            }
        });
    }

    private boolean validateAllFields() {
        if (qualificationGroup.getCheckedRadioButtonId() == -1 ||
                certificationGroup.getCheckedRadioButtonId() == -1 ||
                awardsGroup.getCheckedRadioButtonId() == -1 ||
                researchGroup.getCheckedRadioButtonId() == -1 ||
                booksGroup.getCheckedRadioButtonId() == -1) {
            return false;
        }
        return true;
    }
}
