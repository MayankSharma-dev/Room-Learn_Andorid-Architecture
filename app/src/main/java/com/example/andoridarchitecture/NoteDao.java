package com.example.andoridarchitecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// @Dao annotation is used when we provide abstract method
// (note:- User must not define its body, it is automatically handled by Dao)
@Dao
public interface NoteDao {

    @Insert // only a abstract method is defined by the user to insert the data in the sql.
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table") // here not all the annotations arent providing for sql queries so user made a custom one.
    void deleteAll(); // delete all the entries in the table.

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllData();

}
