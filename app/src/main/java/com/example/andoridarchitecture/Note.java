package com.example.andoridarchitecture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity is Room annotation to create Sqlite and hide boilerCode
@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    int id;

    String title;
    String description;



    int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }




}
