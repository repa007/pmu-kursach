package com.razormist.simplecrudapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBook extends AppCompatActivity {
    private EditText et_title, et_author, et_description, et_price;
    private Button btn_save;
    private Database db;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        db = new Database(this);
        book = (Book) getIntent().getSerializableExtra("book");

        et_title = findViewById(R.id.et_title);
        et_author = findViewById(R.id.et_author);
        et_description = findViewById(R.id.et_description);
        et_price = findViewById(R.id.et_price);
        btn_save = findViewById(R.id.btn_save);

        et_title.setText(book.getTitle());
        et_author.setText(book.getAuthor());
        et_description.setText(book.getDescription());
        et_price.setText(String.valueOf(book.getPrice()));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String author = et_author.getText().toString();
                String description = et_description.getText().toString();
                String price = et_price.getText().toString(); // Получение цены из EditText

                if (title.isEmpty() || author.isEmpty() || description.isEmpty() || price.isEmpty()) {
                    Toast.makeText(UpdateBook.this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setDescription(description);
                    book.setPrice(Double.parseDouble(price)); // Обновление цены
                    db.updateBook(book);
                    Toast.makeText(UpdateBook.this, "Покупка обновлена", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateBook.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}