package com.udacity.popular_movies_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.popular_movies_stage1.model.MovieResponse;
import com.udacity.popular_movies_stage1.utils.MovieService;
import com.udacity.popular_movies_stage1.utils.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieDBAdapter.MovieDBAdapterOnClickHandler{


    private RecyclerView mRecyclerView;
    private MovieDBAdapter mMovieDBAdapter;
    private String sort_by="popularity.desc";

    private TextView mErrorMessageDisplay;
    private TextView mMovieErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;
    private static final int SPAN_COUNT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mMovieErrorMessageDisplay = (TextView) findViewById(R.id.tv_movie_error_message_display);

        /*
         * GridLayoutManager for GridView.
         * SPAN_COUNT parameter defines number of columns.
         */
        GridLayoutManager layoutManager
                = new GridLayoutManager(this, SPAN_COUNT);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Pass in 'this' as the MovieDBAdapterOnClickHandler
        /*
         * The MovieDBAdapter is responsible for linking movie data with the Views that
         * will end up displaying our movie data.
         */
        mMovieDBAdapter = new MovieDBAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMovieDBAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the movie data. */
        loadMovieData();

    }

    private void loadMovieData() {
        MovieService.MovieAPI client = ServiceGenerator.createService(MovieService.MovieAPI.class);

        Call<MovieResponse> call = client.getMovieList(BuildConfig.API_KEY,sort_by);
        call.enqueue(new Callback<MovieResponse >() {
                         @Override
                         public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                             if (response.isSuccessful()) {
                                 Log.d("Network", "SUCCESS");
                                 // user object available
                                 List<MovieResponse.Movie> movielist= response.body().getMovielist();
                                 mMovieDBAdapter.setMovieData(movielist.toArray(new MovieResponse.Movie[movielist.size()]));
                             } else {
                                 // error response, no access to resource?
                                 // user object not available
                                 if(ServiceGenerator.LOCAL_LOGD)
                                 Log.d("Network", "Error");
                                 showMovieDataErrorMessage();
                             }
                         }

                         @Override
                         public void onFailure(Call<MovieResponse> call, Throwable t) {
                             // something went completely south (like no internet connection)
                             if(ServiceGenerator.LOCAL_LOGD)
                             Log.d("Network", "No Internet"+t.getMessage());
                             showErrorMessage();
                         }
                     });

        showMovieDataView();
    }

    /**
     * This method will make the View for the movie data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMovieErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the internet connection error message visible and hide the MovieResponse
     * View.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        mMovieErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, show the internet error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    /**
     * This method will make the movie data error message visible, hide the MovieResponse View
     * and internet connection error message.
     **/
    private void showMovieDataErrorMessage() {
        /* First, hide the currently visible data and internet connection error message*/
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, show the movie data error */
        mMovieErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    //On Clicking GridItem, this method is called.
    @Override
    public void itemClickListener(final MovieResponse.Movie movie) {
        ArrayList<MovieResponse.Movie> movieList=new ArrayList<MovieResponse.Movie>();
        movieList.add(movie);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putParcelableArrayListExtra(DetailActivity.EXTRA_DATA,movieList);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popularity) {
            sort_by=ServiceGenerator.ORDER_POPULARITY;
            loadMovieData();
            return true;
        }else  if (id == R.id.action_toprated) {
            sort_by=ServiceGenerator.ORDER_TOPRATED;
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
