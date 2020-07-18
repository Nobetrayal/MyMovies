package ru.msnetworks.mymooves.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.msnetworks.mymooves.R;
import ru.msnetworks.mymooves.data.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailers;
    private OnTrailerClickListener onTrailerClickListener;

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        Trailer trailer = trailers.get(position);
        holder.textViewVideoName.setText(trailer.getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{

        TextView textViewVideoName;
        ImageView imageViewPlayVideo;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewVideoName = itemView.findViewById(R.id.textViewVideoName);
            imageViewPlayVideo = itemView.findViewById(R.id.imageViewPlayVideo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onTrailerClickListener != null) {

                        onTrailerClickListener.onTrailerClick(trailers.get(getAdapterPosition()).getKey());

                    }

                }
            });

        }
    }

    public interface OnTrailerClickListener {

        void onTrailerClick(String url);

    }

}
