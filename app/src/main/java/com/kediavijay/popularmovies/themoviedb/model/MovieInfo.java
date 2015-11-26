package com.kediavijay.popularmovies.themoviedb.model;

import android.support.annotation.NonNull;

import com.kediavijay.popularmovies.themoviedb.TheMovieDBException;
import com.kediavijay.popularmovies.themoviedb.TheMovieDatabaseUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vijaykedia on 26/11/15.
 * This class will contain all movie Info which will be used in MovieInfoActivity

 */
public class MovieInfo {

    @NonNull private final String title;
    @NonNull private final String posterPath;
    @NonNull private final String synopsis;
    @NonNull private final String userRating;
    @NonNull private final String releaseData;

    @NonNull
    public static MovieInfo fromJson(@NonNull final JSONObject response) throws TheMovieDBException {
        try {
            final String title = response.getString("title");
            final String posterPath = response.getString("poster_path");
            final String qualifiedPosterPath = TheMovieDatabaseUtil.getImageUrl(posterPath);
            final String synopsis = response.getString("overview");
            final String userRating = response.getString("vote_average");
            final String releaseData = response.getString("release_date");

            return new MovieInfo(title, qualifiedPosterPath, synopsis, userRating, releaseData);
        } catch (final JSONException e) {
            throw new TheMovieDBException("Failed to parse response", e);
        }
    }

    public MovieInfo(@NonNull final String title, @NonNull final String posterPath, @NonNull final String synopsis, @NonNull final String userRating, @NonNull final String releaseData) {
        this.title = title;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseData = releaseData;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getPosterPath() {
        return posterPath;
    }

    @NonNull
    public String getSynopsis() {
        return synopsis;
    }

    @NonNull
    public String getUserRating() {
        return userRating;
    }

    @NonNull
    public String getReleaseData() {
        return releaseData;
    }
}
