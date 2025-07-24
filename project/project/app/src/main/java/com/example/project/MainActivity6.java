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

public class MainActivity6 extends AppCompatActivity {

    private EditText speakerDetails, projectsDetails, expertDetails, judgeDetails, resourceDetails;
    private RadioGroup speakerGroup, projectsGroup, expertGroup, judgeGroup, resourceGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        // Initialize RadioGroups and EditTexts
        speakerGroup = findViewById(R.id.speaker_group);
        projectsGroup = findViewById(R.id.projects_group);
        expertGroup = findViewById(R.id.expert_group);
        judgeGroup = findViewById(R.id.judge_group);
        resourceGroup = findViewById(R.id.resource_group);

        speakerDetails = findViewById(R.id.speaker_details);
        projectsDetails = findViewById(R.id.projects_details);
        expertDetails = findViewById(R.id.expert_details);
        judgeDetails = findViewById(R.id.judge_details);
        resourceDetails = findViewById(R.id.resource_details);

        // Set listeners for RadioGroups
        setupRadioGroupListener(speakerGroup, speakerDetails);
        setupRadioGroupListener(projectsGroup, projectsDetails);
        setupRadioGroupListener(expertGroup, expertDetails);
        setupRadioGroupListener(judgeGroup, judgeDetails);
        setupRadioGroupListener(resourceGroup, resourceDetails);

        // Handle Next button click
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {
            if (validateFields()) {
                Toast.makeText(MainActivity6.this, "Proceeding to the next screen...", Toast.LENGTH_SHORT).show();
                 //Navigate to the next screen
                Intent intent = new Intent(MainActivity6.this, MainActivity7.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity6.this, "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRadioGroupListener(RadioGroup group, EditText detailsField) {
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

    private boolean validateFields() {
        return speakerGroup.getCheckedRadioButtonId() != -1 &&
                projectsGroup.getCheckedRadioButtonId() != -1 &&
                expertGroup.getCheckedRadioButtonId() != -1 &&
                judgeGroup.getCheckedRadioButtonId() != -1 &&
                resourceGroup.getCheckedRadioButtonId() != -1;
    }
}
