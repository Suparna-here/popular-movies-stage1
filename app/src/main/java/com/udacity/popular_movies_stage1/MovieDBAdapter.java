package com.udacity.popular_movies_stage1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.popular_movies_stage1.model.MovieResponse;
import com.udacity.popular_movies_stage1.utils.ServiceGenerator;


/**
 * {@link MovieDBAdapter} exposes a list of movie details to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieDBAdapter extends RecyclerView.Adapter<MovieDBAdapter.MovieDBAdapterViewHolder>{
    private MovieResponse.Movie[] mMovieData;


    private final MovieDBAdapterOnClickHandler mClickHandler;

    public interface MovieDBAdapterOnClickHandler {
        void itemClickListener(MovieResponse.Movie movie);
    }

    public MovieDBAdapter(MovieDBAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    /**
     * Cache of the children views for a movie grid item.
     */
    public class MovieDBAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView posterThumbnailIV;

        public MovieDBAdapterViewHolder(View view) {
            super(view);
            posterThumbnailIV = (ImageView) view.findViewById(R.id.iv_poster_thumbnail);
            // Call setOnClickListener on the view passed into the constructor (use 'this' as the OnClickListener)
            view.setOnClickListener(this);
        }

        // Override onClick, passing the clicked Movie's data to mClickHandler via its onClick method
        @Override
        public void onClick(View view) {
            mClickHandler.itemClickListener(mMovieData[getAdapterPosition()]);
        }
    }

    @NonNull
    @Override
    public MovieDBAdapter.MovieDBAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieDBAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDBAdapter.MovieDBAdapterViewHolder holder, int position) {
        MovieResponse.Movie movie = mMovieData[position];
        String posteUrl= ServiceGenerator.POSTER_URL+movie.getPoster_path();
        Picasso.get()
               .load(posteUrl)
               .placeholder(R.mipmap.ic_launcher)
               .into(holder.posterThumbnailIV);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    /*
     * This method is used to set the movie data on a MovieDBAdapter
     * @param movieData The new movie data to be displayed.
     */
    public void setMovieData(MovieResponse.Movie[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

}
