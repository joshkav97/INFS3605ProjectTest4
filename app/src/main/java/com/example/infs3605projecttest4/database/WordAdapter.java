package com.example.infs3605projecttest4.database;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605projecttest4.Model.Word;
import com.example.infs3605projecttest4.R;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>{
    private List<Word> wordsToAdapt;

    public void setData(List<Word> wordsToAdapt) {
        this.wordsToAdapt = wordsToAdapt;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.word, parent, false);

        WordViewHolder wordViewHolder = new WordViewHolder(view);
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        final Word wordAtPosition = wordsToAdapt.get(position);
        holder.bind(wordAtPosition);
    }

    @Override
    public int getItemCount() {
        return wordsToAdapt.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameTextView;


        public WordViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = v.findViewById(R.id.name);
        }

        public void bind(final Word word) {
            nameTextView.setText(word.getEnglish());

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WordDetailActivity.class);
                    intent.putExtra("english", word.getEnglish());
                    context.startActivity(intent);
                }
            });*/
        }
    }
}


