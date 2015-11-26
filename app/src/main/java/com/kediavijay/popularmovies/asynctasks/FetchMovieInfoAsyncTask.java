package com.kediavijay.popularmovies.asynctasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kediavijay.popularmovies.themoviedb.TheMovieDBException;
import com.kediavijay.popularmovies.themoviedb.TheMovieDatabaseMovie;
import com.kediavijay.popularmovies.themoviedb.model.MovieInfo;

/**
 * Created by vijaykedia on 27/11/15.
 *
 */
public class FetchMovieInfoAsyncTask extends AsyncTask<String, Void, MovieInfo> {

    @NonNull
    private final String LOG_TAG = getClass().getSimpleName();

    @Nullable
    @Override
    protected MovieInfo doInBackground(final String... params) {

        if (params.length != 1) {
            Log.e(LOG_TAG, String.format("Invalid number of arguments. Expected %d Actual %d", 1, params.length));
            return null;
        }

        // Get Movie Information for the input movie Id
        final String movieId = params[0];

        final TheMovieDatabaseMovie movieInfo = TheMovieDatabaseMovie.createMoviesInfo(movieId);

        try {
            movieInfo.execute();
            return movieInfo.getMovieInfo();
        } catch (final TheMovieDBException e) {
            Log.e(LOG_TAG, String.format("Failed to get movie info for id %s", movieId), e);
            return null;
        }
    }
}
