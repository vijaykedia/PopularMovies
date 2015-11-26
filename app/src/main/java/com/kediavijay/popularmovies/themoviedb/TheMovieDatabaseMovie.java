package com.kediavijay.popularmovies.themoviedb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kediavijay.popularmovies.themoviedb.model.MovieInfo;

import org.json.JSONObject;

/**
 * Created by vijaykedia on 27/11/15.
 *
 */
public class TheMovieDatabaseMovie extends AuthenticatedHttpClient {

    @NonNull
    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    private static final String MOVIE_INFO_API_URL = "movie/%s";

    @Nullable
    private JSONObject jsonResponse = null;

    @NonNull
    public static TheMovieDatabaseMovie createMoviesInfo(@NonNull final String movieId) {
        return new TheMovieDatabaseMovie(String.format(MOVIE_INFO_API_URL, movieId));
    }

    private TheMovieDatabaseMovie(@NonNull final String api) {
        super(api);
    }

    public void execute() throws TheMovieDBException {
        this.jsonResponse = getJsonResponse();
    }

    public MovieInfo getMovieInfo() throws TheMovieDBException {
        if (this.jsonResponse != null) {
            return MovieInfo.fromJson(this.jsonResponse);
        }
        throw new TheMovieDBException("Response is null. Please try calling #execute() method first");
    }
}
