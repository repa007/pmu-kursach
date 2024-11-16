package com.razormist.simplecrudapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private Database database;
    private MutableLiveData<List<Book>> books;

    public BookViewModel(Application application) {
        super(application);
        database = new Database(application);
        books = new MutableLiveData<>();
        loadBooks(); // Загружаем покупки при создании ViewModel
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }

    public void loadBooks() {
        books.setValue(database.getAllBooks());
    }
}
