package com.razormist.simplecrudapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetail extends AppCompatActivity {
    private TextView tv_title, tv_author, tv_description;
    private Button btn_back, btn_edit, btn_delete;
    private Database db;
    private Book book;
    private ImageView img_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        db = new Database(this);
        book = (Book) getIntent().getSerializableExtra("book");

        Bitmap bmp = BitmapFactory.decodeByteArray(book.getImage(), 0, book.getImage().length);

        img_gallery = findViewById(R.id.img_gallery);
        tv_title = findViewById(R.id.tv_title);
        tv_author = findViewById(R.id.tv_author);
        tv_description = findViewById(R.id.tv_description);
        btn_back = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);

        img_gallery.setImageBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false));
        tv_title.setText(book.getTitle());
        tv_author.setText(book.getAuthor());
        tv_description.setText(book.getDescription());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to the previous activity
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetail.this, UpdateBook.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BookDetail.this)
                        .setTitle("Удалить покупку")
                        .setMessage("Вы уверены что хотите удалить эту покупку?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteBook(book.getId());
                                Toast.makeText(BookDetail.this, "Покупка удалена", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK, new Intent().putExtra("deleted", true));
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
