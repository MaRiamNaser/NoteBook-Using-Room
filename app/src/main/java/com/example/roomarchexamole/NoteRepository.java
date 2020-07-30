package com.example.roomarchexamole;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;
    LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){

        NoteDataBase dataBase = NoteDataBase.getInstance(application);

        noteDAO = dataBase.noteDAO();
        allNotes = noteDAO.getAllNotes();
    }

    public void insert(Note note){

        new InsertNoteAsyncTask(noteDAO).execute(note);

    }

    public void update(Note note){

        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note){

        new DeleteNoteAsyncTask(noteDAO).execute(note);

    }

    public void deleteAllNotes(){

        new DeleteAllNotesAsyncTask(noteDAO).execute();

    }

    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }

    // it has to be static so it doesn't have a reference to a repository itself
    // Otherwise this could cause a memory leaks!
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        // i want to pass a note to be inserted, so  we put Note in the first parameter
        // i don't need any progress update so i put Void in the second one.
        // and i don't need any thing back so we put Void in third one also!

        private NoteDAO noteDAO; // we create this instance because we need noteDAO
                                 // to make dataBase Operations !

        //  Because of the class is static we cannot access the noteDAo of our repository
        // directly ... we have to pass it over the constructor!
        private InsertNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override // <<<This is the only mandatory Method that i should Override !>>>
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }
    ///****************************************************************************

    // it has to be static so it doesn't have a reference to a repository itself
    // Otherwise this could cause a memory leaks!
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        // i want to pass a note to be inserted, so  we put Note in the first parameter
        // i don't need any progress update so i put Void in the second one.
        // and i don't need any thing back so we put Void in third one also!

        private NoteDAO noteDAO; // we create this instance because we need noteDAO
        // to make dataBase Operations !

        //  Because of the class is static we cannot access the noteDAo of our repository
        // directly ... we have to pass it over the constructor!
        private UpdateNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }


        // <<<This is the only mandatory Method that i should Override !>>>
        @Override
        protected Void doInBackground(Note... notes) {


            noteDAO.update(notes[0]);
            return null;
        }
    }
    ///************************************************************************************

    // it has to be static so it doesn't have a reference to a repository itself
    // Otherwise this could cause a memory leaks!
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        // i want to pass a note to be inserted, so  we put Note in the first parameter
        // i don't need any progress update so i put Void in the second one.
        // and i don't need any thing back so we put Void in third one also!

        private NoteDAO noteDAO; // we create this instance because we need noteDAO
        // to make dataBase Operations !

        //  Because of the class is static we cannot access the noteDAo of our repository
        // directly ... we have to pass it over the constructor!
        private DeleteNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }


        // <<<This is the only mandatory Method that i should Override !>>>
        @Override
        protected Void doInBackground(Note... notes) {


            noteDAO.delete(notes[0]);
            return null;
        }
    }
    ///************************************************************************************

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDAO noteDAO;

        private DeleteAllNotesAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void ... voids) {


            noteDAO.deleteAllNotes();
            return null;
        }
    }
    ///************************************************************************************

}
