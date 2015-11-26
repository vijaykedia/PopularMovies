package com.kediavijay.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kediavijay.popularmovies.asynctasks.FetchMovieInfoAsyncTask;
import com.kediavijay.popularmovies.themoviedb.model.MovieInfo;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieInfoActivityFragment extends Fragment {

    @NonNull
    private final String LOG_TAG = getClass().getSimpleName();

    public MovieInfoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);

        final Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            final String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);

            final FetchMovieInfoAsyncTask fetchMovieInfoAsyncTask = new FetchMovieInfoAsyncTask();
            fetchMovieInfoAsyncTask.execute(movieId);


            final MovieInfo movieInfo;
            try {
                movieInfo = fetchMovieInfoAsyncTask.get();
            } catch (final InterruptedException | ExecutionException e) {
                Log.e(LOG_TAG, "Failed to get movies info");
                return null;
            }

            // Setting the View
            ((TextView) rootView.findViewById(R.id.movie_info_title_text_view)).setText(movieInfo.getTitle());

            final ImageView imageView = (ImageView)rootView.findViewById(R.id.movie_info_poster_image_view);
            Picasso.with(getContext()).load(movieInfo.getPosterPath()).into(imageView);

            ((TextView) rootView.findViewById(R.id.movie_info_release_date_text_view)).setText(movieInfo.getReleaseData());
            ((TextView)rootView.findViewById(R.id.movie_info_user_rating_text_view)).setText(movieInfo.getUserRating());
            ((TextView)rootView.findViewById(R.id.movie_info_synopsis_text_view)).setText(movieInfo.getSynopsis());
        }
        return rootView;
    }
}
