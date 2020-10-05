package com.example.movie;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    Fragment mainFrag;
    Fragment listFrag;
    SearchView svMovie;
    FragmentManager fragmentManager;
    ListFrag listfrag;
    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> suggestedList;
    Database database;
    int currPage = 1;
    int totalPages;
    String query;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().show(Objects.requireNonNull
                (fragmentManager.findFragmentById(R.id.mainFrag))).hide(Objects.requireNonNull
                (fragmentManager.findFragmentById(R.id.listFrag))).commit();

        mainFrag = new Fragment();
        listFrag = new Fragment();
        svMovie = findViewById(R.id.svMovie);
        listfrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);
        suggestedList = new ArrayList<>();
        lv = findViewById(R.id.lv);

        database = new Database(this);
        database.open();
        database.readDB(suggestedList);
        database.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestedList);
        lv.setAdapter(adapter);
        lv.setVisibility(View.GONE);





        svMovie.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b)
                lv.setVisibility(View.VISIBLE);
                else
                    lv.setVisibility(View.GONE);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String suggItem = adapter.getItem(i);
                OkHttpClient client = new OkHttpClient();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                final MovieDBAPI request = retrofit.create(MovieDBAPI.class);
                Call<MovieResponse> call = request.getMovies
                        (("2696829a81b1b5827d515ff121700838"),
                                suggItem, "1");

                call.enqueue(new Callback<MovieResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call,
                                           @NotNull Response<MovieResponse> response) {
                        assert response.body() != null;
                        if (response.body().getResults().size() == 0) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "No such movie",
                                    LENGTH_SHORT);
                            toast.show();

                        } else {
                            ArrayList<Movie> movies = (ArrayList<Movie>)
                                    response.body().getResults();
                            totalPages = (int) response.body().getTotalPages();
                            listfrag.setMyAdapter(movies);
                            fragmentManager.beginTransaction().hide(Objects.requireNonNull
                                    (fragmentManager.findFragmentById(R.id.mainFrag))).show
                                    (Objects.requireNonNull(fragmentManager.findFragmentById
                                            (R.id.listFrag))).addToBackStack(null).commit();
                            addToList(suggItem);
                            svMovie.setQuery("", false);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });

            }
        });

        svMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {

                OkHttpClient client = new OkHttpClient();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                final MovieDBAPI request = retrofit.create(MovieDBAPI.class);
                Call<MovieResponse> call = request.getMovies
                        (("2696829a81b1b5827d515ff121700838"),
                                s, "1");

                call.enqueue(new Callback<MovieResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call,
                                           @NotNull Response<MovieResponse> response) {
                        assert response.body() != null;
                                if (response.body().getResults().size() == 0) {
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "No such movie",
                                            LENGTH_SHORT);
                                    toast.show();

                                } else {
                                    ArrayList<Movie> movies = (ArrayList<Movie>)
                                            response.body().getResults();
                                    listfrag.setMyAdapter(movies);
                                    fragmentManager.beginTransaction().hide(Objects.requireNonNull
                                            (fragmentManager.findFragmentById(R.id.mainFrag))).show
                                            (Objects.requireNonNull(fragmentManager.findFragmentById
                                                    (R.id.listFrag))).addToBackStack(null).commit();
                                    addToList(s);
                                    svMovie.setQuery("", false);
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                                System.out.println(t.getMessage());
                            }
                        });

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });


    }

    public void addToList(String search) {
        boolean isInList = false;
        database.open();
        for (String listItem : suggestedList)
            if (listItem.toLowerCase().compareTo(search.toLowerCase()) == 0) {
                isInList = true;
                break;
            }

        if (!isInList) {
            if (suggestedList.size() >= 10) {
                database.deleteFirst();
                suggestedList.remove(suggestedList.size() - 1);
            }
            database.createEntry(search);
            suggestedList.add(0, search);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,suggestedList);
            lv.setAdapter(adapter);
        }
    }
}
