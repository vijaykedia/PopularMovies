package com.kediavijay.popularmovies.themoviedb;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vijaykedia on 26/11/15.
 * This will serve as a general purpose http client
 */
public class HttpClient {

    @NonNull
    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    private final Uri.Builder builder;

    @Nullable
    protected URL url;
    @Nullable
    private HttpURLConnection httpConnection;

    public HttpClient(@NonNull final String url) {
        builder = Uri.parse(url).buildUpon();
        this.url = null;
        this.httpConnection = null;
    }

    /**
     * This will add query param to base url
     *
     * @param key   query param key
     * @param value value of query param
     */
    public void addParams(@NonNull final String key, @NonNull final String value) {
        builder.appendQueryParameter(key, value);
    }

    /**
     * This will connect to the url and return input stream
     *
     * @return input stream to read the data from url
     */
    @NonNull
    public InputStream getInputStream() throws TheMovieDBException {
        // Get final Url
        getUrl();
        if (this.url == null) {
            throw new TheMovieDBException("Failed to create url");
        }

        // Create Http connection to the Url
        try {
            this.httpConnection = (HttpURLConnection) url.openConnection();
            this.httpConnection.setRequestMethod("GET");
            this.httpConnection.connect();

            return this.httpConnection.getInputStream();
        } catch (final IOException e) {
            close();
            throw new TheMovieDBException(String.format("Failed to connect to url %s", this.url.toString()), e);
        }
    }

    @NonNull
    private Uri getUri() {
        return this.builder.build();
    }

    /**
     * The will return the completely formed url
     *
     * @return completely formed url
     */
    @Nullable
    public URL getUrl() {
        if (this.url == null) {
            try {
                this.url = new URL(getUri().toString());
            } catch (final MalformedURLException e) {
                Log.e(LOG_TAG, "Failed to create url", e);
            }
        }
        return this.url;
    }

    /**
     * This will close http connections
     */
    public void close() {
        if (this.httpConnection != null) {
            this.httpConnection.disconnect();
        }
    }
}
