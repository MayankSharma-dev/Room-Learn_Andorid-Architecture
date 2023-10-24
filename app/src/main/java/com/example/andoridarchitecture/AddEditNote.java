package com.example.andoridarchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNote extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.andoridarchitecture.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.andoridarchitecture.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.andoridarchitecture.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.example.andoridarchitecture.EXTRA_ID";

    EditText e2, e1;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        e1 = findViewById(R.id.et1);
        e2 = findViewById(R.id.et2);
        numberPicker = findViewById(R.id.numberpicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            e1.setText(intent.getStringExtra(EXTRA_TITLE));
            e2.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 0));
        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {

        String title = e1.getText().toString();
        String description = e2.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please fill the empty area.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        // getIntent() is used beacuse we are getting the value for editing
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1)/* the id will never be -1 if there is some id already */{
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.savenote:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}