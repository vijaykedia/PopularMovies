package com.kediavijay.popularmovies.themoviedb;

import android.support.annotation.NonNull;

/**
 * Created by vijaykedia on 26/11/15.
 * This will be a general purpose exception thrown by theMovieDB APIs
 */
public class TheMovieDBException extends Exception {

    public TheMovieDBException(@NonNull final String message) {
        super(message);
    }

    public TheMovieDBException(@NonNull final String message, @NonNull final Throwable cause) {
        super(message, cause);
    }
}
