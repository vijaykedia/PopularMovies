package com.kediavijay.popularmovies.themoviedb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijaykedia on 26/11/15.
 * This will call discover api of TheMovieDB
 */
public class TheMovieDatabaseDiscover extends AuthenticatedHttpClient {

    @NonNull
    private final String LOG_TAG = TheMovieDatabaseDiscover.class.getSimpleName();

    private static final String DISCOVER_MOVIE_API_URL = "discover/%s";

    @Nullable
    private JSONObject jsonResponse = null;

    @NonNull
    public static TheMovieDatabaseDiscover createMoviesDiscover() {
        return new TheMovieDatabaseDiscover("movie");
    }

    @NonNull
    public static TheMovieDatabaseDiscover createTvDiscover() {
        return new TheMovieDatabaseDiscover("tv");
    }

    private TheMovieDatabaseDiscover(@NonNull final String api) {
        super(String.format(DISCOVER_MOVIE_API_URL, api));
    }

    public void addSortingOrder(@NonNull final String order) {
        addParams("sort_by", order);
    }

    public void execute() throws TheMovieDBException {
        this.jsonResponse = getJsonResponse();
    }

    @NonNull
    public List<String> getMovieIds() throws TheMovieDBException {
        if (this.jsonResponse != null) {
            try {
                final List<String> movieIds = new ArrayList<>();
                final JSONArray results = this.jsonResponse.getJSONArray("results");
                for (int index = 0; index < results.length(); index++) {
                    final JSONObject movieDetail = results.getJSONObject(index);
                    movieIds.add(movieDetail.getString("id"));
                }
                return movieIds;
            } catch (final JSONException e) {
                throw new TheMovieDBException("Failed to parse response", e);
            }
        }
        throw new TheMovieDBException("Response is null. Please try calling #execute() method first");
    }

    @NonNull
    public List<Pair<String, String>> getMoviePosterPath() throws TheMovieDBException {
        if (this.jsonResponse != null) {
            try {
                final List<Pair<String, String>> moviePosterPaths = new ArrayList<>();
                final JSONArray results = this.jsonResponse.getJSONArray("results");
                for (int index = 0; index < results.length(); index++) {
                    final JSONObject movieDetail = results.getJSONObject(index);
                    final String movieId = movieDetail.getString("id");
                    final String moviePosterPath = TheMovieDatabaseUtil.getImageUrl(movieDetail.getString("poster_path"));

                    moviePosterPaths.add(new Pair<>(movieId, moviePosterPath));
                }
                return moviePosterPaths;
            } catch (final JSONException e) {
                throw new TheMovieDBException("Failed to parse response", e);
            }
        }
        throw new TheMovieDBException("Response is null. Please try calling #execute() method first");
    }

    @NonNull
    public JSONObject getResponse() {
        if (this.jsonResponse != null) {
            return this.jsonResponse;
        }
        return new JSONObject();
    }
}
