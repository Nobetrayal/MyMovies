package ru.msnetworks.mymooves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import ru.msnetworks.mymooves.adapter.ReviewAdapter;
import ru.msnetworks.mymooves.adapter.TrailerAdapter;
import ru.msnetworks.mymooves.data.FavouriteMovie;
import ru.msnetworks.mymooves.data.MainViewModel;
import ru.msnetworks.mymooves.data.Movie;
import ru.msnetworks.mymooves.data.Review;
import ru.msnetworks.mymooves.data.Trailer;
import ru.msnetworks.mymooves.utils.JsonUtils;
import ru.msnetworks.mymooves.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private ImageView imageViewAddToFavourite;

    private int id;
    private MainViewModel viewModel;
    private Movie movie;
    private FavouriteMovie favouriteMovie;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.itemMain:
                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.itemFavourite:
                Intent intentFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentFavourite);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }

        boolean fromFavourite = false;
        if (intent != null && intent.hasExtra("id")) {
            fromFavourite = intent.getBooleanExtra("fromFavourite", false);
        }

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (fromFavourite) {
            movie = viewModel.getFavouriteMovieById(id);
        } else {
            movie = viewModel.getMovieById(id);
        }



        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(String.format("%s", movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());
        setFavourite();

        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailer);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        trailerAdapter = new TrailerAdapter();

        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {

                Intent intentPlayVideo = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentPlayVideo);

            }
        });
        
        reviewAdapter = new ReviewAdapter();
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);

        JSONObject jsonObjectTrailers = NetworkUtils.getVideosJSONFromNetwork(movie.getId());
        JSONObject jsonObjectReviews = NetworkUtils.getReviewsJSONFromNetwork(movie.getId());

        List<Trailer> trailers = JsonUtils.getTrailersFromJSON(jsonObjectTrailers);
        trailerAdapter.setTrailers(trailers);
        
        List<Review> reviews = JsonUtils.getReviewsFromJSON(jsonObjectReviews);
        reviewAdapter.setReviews(reviews);


        
    }

    public void onClickChangeFavourite(View view) {


        if (favouriteMovie == null) {

            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.msg_add_to_favourite, Toast.LENGTH_SHORT).show();

        } else {

            viewModel.deleteFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.msg_remove_from_favourite, Toast.LENGTH_SHORT).show();

        }

        setFavourite();

    }

    private void setFavourite() {

        favouriteMovie = viewModel.getFavouriteMovieById(id);
        if (favouriteMovie == null) {

            imageViewAddToFavourite.setImageResource(R.drawable.ic_yellow_star_border_48);

        } else {

            imageViewAddToFavourite.setImageResource(R.drawable.ic_yellow_star_48);

        }
    }
}