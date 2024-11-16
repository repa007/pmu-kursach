package com.razormist.simplecrudapplication;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_BOOK = 1;
    private static final int REQUEST_CODE_DETAIL_BOOK = 2;

    private BookViewModel bookViewModel;
    private BookListAdapter bookListAdapter;
    private RecyclerView rv_list;
    private TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_description = findViewById(R.id.tv_description);
        ImageView iv_app_image = findViewById(R.id.iv_app_image);
        Button btn_add = findViewById(R.id.btn_add);
        rv_list = findViewById(R.id.rv_list);

        tv_title.setText("Список покупок");
        tv_description.setText("Посмотри свои расходы тут!");
        tvTotalPrice = findViewById(R.id.tv_total_price);
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookListAdapter = new BookListAdapter(this, new ArrayList<>());

        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(bookListAdapter);

        bookViewModel.getBooks().observe(this, books -> {
            bookListAdapter.updateBookList(books);
            calculateTotalPrice(books); // Вызываем метод для расчета общей суммы
        });

        bookListAdapter.setOnItemClickListener(book -> {
            Intent intent = new Intent(MainActivity.this, BookDetail.class);
            intent.putExtra("book", book);
            startActivityForResult(intent, REQUEST_CODE_DETAIL_BOOK);
        });

        btn_add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_BOOK);
        });
    }


    private void calculateTotalPrice(List<Book> books) {
        double totalPrice = 0;
        for (Book book : books) {
            totalPrice += book.getPrice();
        }
        TextView tvTotalPrice = findViewById(R.id.tv_total_price);
        tvTotalPrice.setText("Общая сумма: " + totalPrice); // Обновляем TextView с общей суммой
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_BOOK && resultCode == RESULT_OK) {
            bookViewModel.loadBooks(); // Загружаем книги заново после добавления новой
        } else if (requestCode == REQUEST_CODE_DETAIL_BOOK && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("deleted", false)) {
                bookViewModel.loadBooks(); // Загружаем книги заново после удаления
            }
        }
    }
}
