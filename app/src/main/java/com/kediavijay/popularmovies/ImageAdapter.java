package com.kediavijay.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kediavijay.popularmovies.asynctasks.FetchMoviesListAsyncTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by vijaykedia on 26/11/15.
 * This will act as adapter to generate ImageView to show movies list
 */
public class ImageAdapter extends BaseAdapter {

    @NonNull private static final String LOG_TAG = ImageAdapter.class.getSimpleName();

    private final Context context;
    private List<Pair<String, String>> imagePath;

    public ImageAdapter(@NonNull final Context context) {
        super();
        this.context = context;

        final FetchMoviesListAsyncTask moviesInfo = new FetchMoviesListAsyncTask();

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String sort_order = sharedPreferences.getString(context.getString(R.string.movie_sort_order_key), context.getString(R.string.default_movie_sort_order));
        moviesInfo.execute(sort_order);

        try {
            imagePath = moviesInfo.get();
        } catch (final InterruptedException | ExecutionException e) {
            imagePath = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return imagePath.size();
    }

    @Override
    public Pair<String, String> getItem(int position) {
        return imagePath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        final ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(context).load(imagePath.get(position).second).into(imageView);
        return imageView;
    }
}
