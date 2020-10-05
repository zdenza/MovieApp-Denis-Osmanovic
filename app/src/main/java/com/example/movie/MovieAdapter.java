package com.example.movie;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> list) {

        movies = list;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvName, tvDate, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }
    }






    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.itemView.setTag(movies.get(position));

        Picasso.get().load("https://image.tmdb.org/t/p/w92/" + movies.get(position).getPoster_path()).into(holder.ivPoster);
        holder.tvName.setText(movies.get(position).getTitle());
        holder.tvDate.setText(movies.get(position).getRelease_date());
        holder.tvDescription.setText(movies.get(position).getOverview());


    }

    @Override
    public int getItemCount() {
        return movies.size();


    }
}
