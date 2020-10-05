package com.example.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class MainScreenFrag extends Fragment {

//    RecyclerView recyclerView;
//    RecyclerView.Adapter myAdapter;
//    RecyclerView.LayoutManager layoutManager;
//    View view;

//    ListView lv;
//    ArrayAdapter<String> adapter;
//    ArrayList<String> suggestedList;

    public MainScreenFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }





    }
