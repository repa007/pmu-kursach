package com.razormist.simplecrudapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;
    private OnItemClickListener onItemClickListener;
    private Database db;

    public BookListAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList != null ? bookList : new ArrayList<>();
        this.db = new Database(context);
    }

    public void updateBookList(List<Book> newBookList) {
        this.bookList = newBookList != null ? newBookList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvDescription.setText(book.getDescription());
        holder.tvPrice.setText(String.valueOf(book.getPrice())); // Отображение цены
        Bitmap bmp = BitmapFactory.decodeByteArray(book.getImage(), 0, book.getImage().length);
        holder.imageBookView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false));
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvDescription,tvPrice;
        ImageView imageBookView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPrice = itemView.findViewById(R.id.tv_price);
            imageBookView = itemView.findViewById(R.id.image_book_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}