package ru.msnetworks.mymooves.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.msnetworks.mymooves.R;
import ru.msnetworks.mymooves.data.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public MovieAdapter() {

        movies = new ArrayList<>();

    }

    public void clear() {
        this.movies.clear();
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies) {

        this.movies.addAll(movies);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        if (movies.size() >= 20 && position > movies.size() - 4 && onReachEndListener != null) {

            onReachEndListener.onReachEnd();

        }
        Movie movie = movies.get(position);
        Picasso.get().load(movie.getPosterPath()).into(holder.imageViewSmallPoster);



    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            imageViewSmallPoster.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (onPosterClickListener != null) {

                        onPosterClickListener.onPosterClick(getAdapterPosition());

                    }

                }
            });

        }
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public interface OnReachEndListener {
        void onReachEnd();
    }

}
