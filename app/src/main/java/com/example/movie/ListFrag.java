package com.example.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListFrag extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    ArrayList<Movie> movieList;


    public ListFrag() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        movieList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list, container, false);

        return view;
    }

    public void setMyAdapterNew (ArrayList<Movie> list) {
        movieList.addAll(list);
            myAdapter.notifyDataSetChanged();

    }

        public void setMyAdapter (ArrayList<Movie> list) {
            movieList.clear();
            movieList.addAll(list);
            myAdapter = new MovieAdapter(this.getContext(), movieList);
            recyclerView.setAdapter(myAdapter);
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }
}