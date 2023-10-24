package com.example.andoridarchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    Repository repository;
    private LiveData<List<Note>> allData;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allData = repository.getAllData();
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note)
    {
        repository.update(note);
    }
    public void delete(Note note)
    {
        repository.delete(note);
    }

    public void deleteAll()
    {
        repository.deleteAll();
    }

    public LiveData<List<Note>> getAllData() {
        return allData;
    }
}
