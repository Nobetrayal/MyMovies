package ru.msnetworks.mymooves.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavouriteMovie>> favouriteMovies;

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.movieDAO().getAllMovies();
        favouriteMovies = database.movieDAO().getAllFavouriteMovies();

    }

    public Movie getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllMovies() {

        new DeleteAllMoviesTask().execute();

    }

    public void insertMovie(Movie movie) {

        new InsertMovieTask().execute(movie);

    }

    public void deleteMovie(Movie movie) {

        new DeleteMovieTask().execute(movie);

    }

    public FavouriteMovie getFavouriteMovieById(int id) {
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertFavouriteMovie(FavouriteMovie movie){

        new InsertFavouriteMovieTask().execute(movie);

    }

    public void deleteFavouriteMovie(FavouriteMovie movie){

        new DeleteFavouriteMovieTask().execute(movie);

    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Movie> {


        @Override
        protected Movie doInBackground(Integer... integers) {

            if (integers != null && integers.length > 0) {

                return database.movieDAO().getMovieById(integers[0]);

            }
            return null;
        }
    }

    private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            database.movieDAO().deleteAllMovies();
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {

                database.movieDAO().insertMovie(movies[0]);

            }
            return null;
        }
    }private static class DeleteMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {

                database.movieDAO().deleteMovie(movies[0]);

            }
            return null;
        }
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteMovie> {


        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {

            if (integers != null && integers.length > 0) {

                return database.movieDAO().getFavouriteMovieById(integers[0]);

            }
            return null;
        }
    }

    private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {

                database.movieDAO().insertFavouriteMovie(movies[0]);

            }
            return null;
        }
    }private static class DeleteFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {

                database.movieDAO().deleteFavouriteMovie(movies[0]);

            }
            return null;
        }
    }

}
