package com.example.andoridarchitecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class Repository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allData;

    public Repository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allData = noteDao.getAllData();
    }

    //////////
    // These methods are the APIs which are exposed to the outside to operations.....
    // This way the Abstraction Layer is been created..

    public void insert(Note note){
        // create the instance of the InsetnoteAsyncTask
        // where we pass our noteDao nad execute it..

        new InsertnoteAsyncTask(noteDao).execute(note);

    }
    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAll()
    {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllData() {
        return allData;
    }

    ////////// \\

    private static class InsertnoteAsyncTask extends AsyncTask<Note,Void ,Void>{

        private NoteDao noteDao; // required to make database operations..

        // since the class is static we cannot access noteDao of the repository class directly,
        // we need to create the constructor to pass the it..
        private InsertnoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void ,Void>{

        private NoteDao noteDao; // required to make database operations..

        // since the class is static we cannot access noteDao of the repository class
        // we need to create the constructor to pass the it..

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void ,Void>{

        private NoteDao noteDao; // required to make database operations..

        // since the class is static we cannot access noteDao of the repository class
        // we need to create the constructor to pass the it..

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void ,Void>{

        private NoteDao noteDao; // required to make database operations..

        // since the class is static we cannot access noteDao of the repository class
        // we need to create the constructor to pass the it..

        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }


}
