package com.example.andoridarchitecture;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1) // Note.class is the Entity class and version defines the schema of the database(or its revisions)
public abstract class NoteDatabase extends RoomDatabase {

    public static NoteDatabase instance;

    public abstract NoteDao noteDao(); // to access database operation methods

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null)
        {
            /* // This is Correct.
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration().build(); // fallbackToDestructiveMigration() prevents the
                                                        // app crash when we tries to increase the version number of the database improperly.

             */

            //////// This one is for PrePopulate Database
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_databse")
                    .fallbackToDestructiveMigration().addCallback(callback)
                    .build();
            //////// \\
        }
        return instance;
    }

    ////////////
    //This all is done to PrePopulate Database (some automatic database entry on creation).

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PrepopulateDBAsync(instance).execute();
        }
    };

    private static class PrepopulateDBAsync extends AsyncTask<Void,Void,Void>
    {
        NoteDao noteDao;

        PrepopulateDBAsync(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","Description1",1));
            noteDao.insert(new Note("Title2","Description2",2));
            return null;
        }
    }

    //////////// \\

}
