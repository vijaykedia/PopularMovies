package com.kediavijay.popularmovies.themoviedb;

import android.support.annotation.NonNull;

/**
 * Created by vijaykedia on 27/11/15.
 * This will serve as a util class
 */
public class TheMovieDatabaseUtil {

    @NonNull
    public static String getImageUrl(@NonNull final String posterPath) {
        return String.format("%s%s%s", "http://image.tmdb.org/t/p/", "w500", posterPath);
    }
}
