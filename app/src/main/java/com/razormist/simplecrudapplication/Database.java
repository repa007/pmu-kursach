package com.razormist.simplecrudapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 5; // Обновление версии бд

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE books (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, author TEXT, description TEXT, isBookRead INTEGER, imageBook BLOB, price REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        onCreate(db);
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("description", book.getDescription());
        values.put("isBookRead", book.getIsBookRead() ? 1 : 0);
        values.put("imageBook", book.getImage());
        values.put("price", book.getPrice()); // Добавляем цену
        db.insert("books", null, values);
        db.close();
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("books", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(0));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setDescription(cursor.getString(3));
                book.setIsBookRead(cursor.getInt(4) == 1);
                book.setImage(cursor.getBlob(5));
                book.setPrice(cursor.getDouble(6)); // Получаем цену
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("books", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("description", book.getDescription());
        values.put("isBookRead", book.getIsBookRead() ? 1 : 0);
        values.put("imageBook", book.getImage());
        values.put("price", book.getPrice()); // Обновляем цену
        db.update("books", values, "id = ?", new String[]{String.valueOf(book.getId())});
        db.close();
    }
}