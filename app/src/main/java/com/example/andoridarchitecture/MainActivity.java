package com.example.andoridarchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 101;
    public static final int EDIT_NOTE_REQUEST = 102;

    private ViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab  = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RAdapter rAdapter = new RAdapter();

        recyclerView.setAdapter(rAdapter);




        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                rAdapter.submitList(notes);
            }
        });




        //// Swipe Recycler item functionality..
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            // this is for drag and drop functionality..
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                viewHolder.getAdapterPosition();
                //Here we need the note object to delete the item(& data from the database).
                viewModel.delete(rAdapter.getNotePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "item deleted.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        // to edit the note(db)
        rAdapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClicked(Note note) {

                Log.d(">>>>>>>>>","The INTERFACE onItemClicked");
                Intent i = new Intent(MainActivity.this, AddEditNote.class);
//                i.putExtra(AddEditNote.EXTRA_TITLE,note.getTitle());
                i.putExtra(AddEditNote.EXTRA_DESCRIPTION,note.getDescription());
                i.putExtra(AddEditNote.EXTRA_PRIORITY,note.getPriority());
                i.putExtra(AddEditNote.EXTRA_ID,note.getId());
                i.putExtra(AddEditNote.EXTRA_TITLE,note.getTitle());

                startActivityForResult(i,EDIT_NOTE_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditNote.EXTRA_TITLE);
            String description  = data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNote.EXTRA_PRIORITY,1);

            Note note = new Note(title,description,priority);
            viewModel.insert(note);

            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNote.EXTRA_ID,-1);
            if(id == -1)
            {
                Toast.makeText(this, "Data Can't be Updated.", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNote.EXTRA_TITLE);
            String description  = data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNote.EXTRA_PRIORITY,1);

            Note note = new Note(title,description,priority);
            note.setId(id);
            viewModel.update(note);

        }
        else {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.deleteAll:
                viewModel.deleteAll();
                Toast.makeText(this, "All item deleted.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}