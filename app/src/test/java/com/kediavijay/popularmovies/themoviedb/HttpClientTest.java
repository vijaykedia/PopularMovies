package com.kediavijay.popularmovies.themoviedb;

import android.support.annotation.NonNull;
import android.util.Log;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vijaykedia on 26/11/15.
 * This will act as Unit test for HttpClient
 */
public class HttpClientTest {

    @NonNull private static final String LOG_TAG = HttpClientTest.class.getSimpleName();

    @NonNull private static final String THE_MOVIE_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    private final HttpClient httpClient;

    public HttpClientTest() {
        httpClient = new HttpClient(THE_MOVIE_DB_IMAGE_BASE_URL + "w500" + "/uXZYawqUsChGSj54wcuBtEdUJbh.jpg");
    }

    @Test
    public void getImage() throws IOException {
        final InputStream inputStream = httpClient.get();
        if (inputStream != null) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Log.i(LOG_TAG, builder.toString());
        }
    }
}
