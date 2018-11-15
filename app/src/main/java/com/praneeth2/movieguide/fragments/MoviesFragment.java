package com.praneeth2.movieguide.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.praneeth2.movieguide.MainFragment;
import com.praneeth2.movieguide.R;

/**
 * Created by Praneeth on 10/5/18.
 */
public class MoviesFragment extends MainFragment {
    @Override
    protected View inflateLayout(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.movie_layout, container);
        return rootView;
    }
}
