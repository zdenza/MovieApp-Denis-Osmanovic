package com.example.movie;

public class Movie {

    private String poster_path;
    private String title;
    private String release_date;
    private String overview;

    public Movie(String poster_path, String title, String release_date, String overview) {
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }
    public String getTitle() {
        return title;
    }
    public String getRelease_date() {
        return release_date;
    }
    public String getOverview() { return overview; }

}