package ru.msnetworks.mymooves.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.msnetworks.mymooves.R;
import ru.msnetworks.mymooves.data.Movie;
import ru.msnetworks.mymooves.data.Review;
import ru.msnetworks.mymooves.data.Trailer;

public class JsonUtils {

    private static final String KEY_RESULTS = "results";

    // Фильм
    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";

    // Отзывы.
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    // Видео.
    private static final String KEY_VIDEO_KEY = "key";
    private static final String KEY_NAME = "name";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";



    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185/";
    public static final String BIG_POSTER_SIZE = "w780/";

    public static List<Review> getReviewsFromJSON(JSONObject jsonObject) {

        List<Review> result = new ArrayList<>();

        if (jsonObject == null) {
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieObject = jsonArray.getJSONObject(i);
                String author = movieObject.getString(KEY_AUTHOR);
                String content = movieObject.getString(KEY_CONTENT);
                Review review = new Review(author, content);

                result.add(review);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static List<Trailer> getTrailersFromJSON(JSONObject jsonObject) {

        List<Trailer> result = new ArrayList<>();

        if (jsonObject == null) {
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieObject = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL + movieObject.getString(KEY_VIDEO_KEY);
                String name = movieObject.getString(KEY_NAME);
                Trailer trailer = new Trailer(key, name);

                result.add(trailer);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Movie> getMoviesFromJSON(JSONObject jsonObject) {

        List<Movie> result = new ArrayList<>();

        if (jsonObject == null) {
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject movieObject = jsonArray.getJSONObject(i);
                int id = movieObject.getInt(KEY_ID);
                int voteCount = movieObject.getInt(KEY_VOTE_COUNT);
                String title = movieObject.getString(KEY_TITLE);
                String originalTitle = movieObject.getString(KEY_ORIGINAL_TITLE);
                String overview = movieObject.getString(KEY_OVERVIEW);
                String posterPath = BASE_IMG_URL + SMALL_POSTER_SIZE + movieObject.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_IMG_URL + BIG_POSTER_SIZE + movieObject.getString(KEY_POSTER_PATH);
                String backdropPath = movieObject.getString(KEY_BACKDROP_PATH);
                double voteAverage = movieObject.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = movieObject.getString(KEY_RELEASE_DATE);

                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);

                result.add(movie);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
