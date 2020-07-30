package com.example.roomarchexamole;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Note.class}, version = 1, exportSchema = false)

public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDAO noteDAO();

    public static synchronized NoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO noteDao;

        private PopulateDbAsyncTask(NoteDataBase db) {
            noteDao = db.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
/*
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    // this is Room Class !
    public static NoteDataBase instance;

    public abstract NoteDAO noteDAO();

    // synchtonized for not being dublicated in multi threads
    public static synchronized NoteDataBase getInstance(Context context){

        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDataBase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    // this was called first time we created the database not any time after it !
    // we wanna access on create method inside call back to put data inside database
    // instead of using empty tables in the first time!
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDpAsyncTask(instance).execute();
        }
    };
    private static class PopulateDpAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;

        private PopulateDpAsyncTask(NoteDataBase dp){
            noteDAO = dp.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDAO.insert(new Note("Title 1", "description 1", 1));
            noteDAO.insert(new Note("Title 2", "description 2", 2));
            noteDAO.insert(new Note("Title 3", "description 3", 3));

            return null;
        }
    }
}
*/
