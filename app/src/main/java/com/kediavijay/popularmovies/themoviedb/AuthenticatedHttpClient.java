package com.kediavijay.popularmovies.themoviedb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kediavijay.popularmovies.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vijaykedia on 26/11/15.
 * This will serve as authenticated http client to connect to THE MOVIE DB APIs
 */
public class AuthenticatedHttpClient extends HttpClient {

    @NonNull
    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/%s";

    @Nullable
    private BufferedReader bufferedReader;

    public AuthenticatedHttpClient(@NonNull final String api) {
        super(getBaseApiUrl(api));
        bufferedReader = null;
        addKey();
    }

    /**
     * This will add api key for all outgoing requests
     */
    public void addKey() {
        addParams("api_key", BuildConfig.THE_MOVIE_DB_API_KEY);
    }

    @NonNull
    private static String getBaseApiUrl(@NonNull final String api) {
        return String.format(THE_MOVIE_DB_BASE_URL, api);
    }

    @NonNull
    public JSONObject getJsonResponse() throws TheMovieDBException {
        final InputStream inputStream = getInputStream();

        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            if (builder.length() == 0) {
                close();
                throw new TheMovieDBException(String.format("Received empty response for %s", getUrl()));
            }
            final String response = builder.toString();

            // Parse response to JSON Object
            try {
                return new JSONObject(response);
            } catch (final JSONException e) {
                close();
                throw new TheMovieDBException("Failed to parse response in json object", e);
            }

        } catch (final IOException e) {
            close();
            if (this.url != null) {
                throw new TheMovieDBException(String.format("Failed to get response from url %s", this.url.toString()), e);
            }
            // Code should not reach here, but added this check to cater to java compiler and Android studio code inspection
            throw new RuntimeException("Input url is null", e);
        }
    }

    public void close() {
        super.close();
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (final IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }
        }
    }
}
