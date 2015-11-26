package com.kediavijay.popularmovies.asynctasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.kediavijay.popularmovies.themoviedb.TheMovieDBException;
import com.kediavijay.popularmovies.themoviedb.TheMovieDatabaseDiscover;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijaykedia on 26/11/15.
 * This will fetch movies info using www.themoviedb.org APIs
 */
public class FetchMoviesListAsyncTask extends AsyncTask<String, Void, List<Pair<String, String>>> {

    @NonNull private static final String LOG_TAG = FetchMoviesListAsyncTask.class.getSimpleName();

    @Override
    protected List<Pair<String, String>> doInBackground(@NonNull final String... params) {

        // Validate input params
        if (params.length != 1) {
            Log.e(LOG_TAG, String.format("Wrong number of parameters passed. Expected %d Actual %d.", 1, params.length));
            return new ArrayList<>();
        }

        // Get popular Movies based on input sorting order
        final String sortingOrder = params[0];
        final TheMovieDatabaseDiscover discoverMovies = TheMovieDatabaseDiscover.createMoviesDiscover();
        discoverMovies.addSortingOrder(sortingOrder);

        try {
            discoverMovies.execute();
            return discoverMovies.getMoviePosterPath();
        } catch (final TheMovieDBException e) {
            Log.e(LOG_TAG, "Failed to get movie data.", e);
            return new ArrayList<>();
        }
    }
}
