package com.praneeth2.movieguide;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Praneeth on 10/5/18.
 */
public abstract class MainFragment extends Fragment {

    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle extras = initializeBundle(savedInstanceState);
        if (extras != null) {
            initializeFromBundle(extras);
        }
        if (rootView == null) {
            rootView = inflateLayout(inflater, container);
        }
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        saveInstanceStateToBundle(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected abstract View inflateLayout(android.view.LayoutInflater inflater, android.view.ViewGroup container);

    protected void initializeFromBundle(Bundle bundle) {
    }

    protected void saveInstanceStateToBundle(Bundle bundle) {
    }

    public Bundle initializeBundle(Bundle savedInstanceState) {
        return initializeBundle(savedInstanceState, this);
    }


    public static Bundle initializeBundle(Bundle savedInstanceState, Fragment fragment) {
        Bundle extras = savedInstanceState;
        if (extras == null) {
            extras = fragment.getArguments();
        }
        return extras;
    }


}
