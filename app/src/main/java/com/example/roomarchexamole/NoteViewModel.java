package com.example.roomarchexamole;


import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
/*
* In part 5 of the Architecture Components tutorial, we will create our Viewmodel class.
The ViewModel works as a gateway between the UI controller and the repository. It stores and processes data for the activity/fragment and it doesn't get destoyed on configuration changes, so it doesn't lose it's variable state for example when the device is rotated.
By extending AndroidViewModel, we get a handle to the application context, which we then use to instantiate our RoomDatabase.
In our activity we let the system provide us the correct ViewModel instance by calling ViewModelProviders.of, where we pass the Activity or Fragment this ViewModel's lifecycle should be scoped to.
* When our Activity/Fragment is then destroyed, the ViewModel will go through it's onCleared method and get removed from the memory.
In our activitie's onCreate method, we retrieve the LiveData that is stored in our ViewModel and call observe on it, where we pass a LifecycleOwner and an Observer.
*  In the onChange callback we get an update on our data whenever something in the corresponding database table changes.
* The LiveData will automatically start and stop sending updates to our activity at the right time in it's lifecycle and clean up any unused references*/