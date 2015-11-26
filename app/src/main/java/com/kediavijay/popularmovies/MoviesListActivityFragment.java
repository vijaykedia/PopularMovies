package com.kediavijay.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesListActivityFragment extends Fragment {

    public MoviesListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        final ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        final GridView gridView = (GridView) rootView.findViewById(R.id.movie_list_grid_view);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Pair<String, String> item = imageAdapter.getItem(position);

                final Intent intent = new Intent(getActivity(), MovieInfoActivity.class).putExtra(Intent.EXTRA_TEXT, item.first);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
