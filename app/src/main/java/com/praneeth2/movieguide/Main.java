package com.praneeth2.movieguide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Praneeth on 10/5/18.
 */
public class Main extends Activity {
    public static String TAG_FORMAT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMainFragment(getFragmentManager());


    }

    private void setUpMainFragment(FragmentManager fragmentManager) {
        MoviesFragment moviesFragment = (MoviesFragment) fragmentManager.findFragmentByTag(String.format(TAG_FORMAT, MainFragment.class.getName(), 0));
        if (moviesFragment == null) {
            moviesFragment = new MoviesFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.main, moviesFragment, generateFragmentTag(moviesFragment))
                    .commit();
        }
    }

    public String generateFragmentTag(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        String className = ((Object) fragment).getClass().getName();
        int nCount = 0;
        String fragmentName = String.format(TAG_FORMAT, className, nCount);

        while (fragmentManager.findFragmentByTag(fragmentName) != null) {
            fragmentName = String.format(TAG_FORMAT, className, ++nCount);
        }

        return fragmentName;
    }
}
