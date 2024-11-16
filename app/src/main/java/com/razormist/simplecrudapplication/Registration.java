package com.razormist.simplecrudapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Registration extends AppCompatActivity {
    private EditText et_title, et_author, et_description;
    private Button btn_add, btn_choose_image;
    private ImageView imgGallery;
    private Database db;

    private byte[] dataImage;
    private EditText et_price; // Добавьте переменную для EditText цены


    private final int GALLERY_REQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_title = findViewById(R.id.et_title);
        et_author = findViewById(R.id.et_author);
        et_description = findViewById(R.id.et_description);
        btn_add = findViewById(R.id.btn_add);
        btn_choose_image = findViewById(R.id.btn_choose_image);
        imgGallery = findViewById(R.id.imgGallery);
        et_price = findViewById(R.id.et_price);

        db = new Database(this);

        btn_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQ_CODE);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString().trim();
                String author = et_author.getText().toString().trim();
                String description = et_description.getText().toString().trim();
                String price = et_price.getText().toString().trim(); // Получаем цену из EditText

                if (title.isEmpty() || author.isEmpty() || description.isEmpty() || price.isEmpty()) {
                    Toast.makeText(Registration.this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setDescription(description);
                    book.setIsBookRead(false);
                    book.setImage(dataImage);
                    book.setPrice(Double.parseDouble(price)); // Устанавливаем цену

                    db.addBook(book);
                    Toast.makeText(Registration.this, "Покупка добавлена", Toast.LENGTH_SHORT).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("Покупка добавлена", true); // Добавляем флаг для успешного добавления
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                dataImage = getBitmapAsByteArray(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imgGallery.setImageURI(imageUri);
        }
    }
}
