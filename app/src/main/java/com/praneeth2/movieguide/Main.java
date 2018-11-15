package com.praneeth2.movieguide;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.praneeth2.movieguide.models.Movie;

/**
 * Created by Praneeth on 10/5/18.
 */
public class Main extends Activity implements MainActivityFragment.Callback {
    public static String TAG_FORMAT = "%s.%d";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMainFragment(getFragmentManager());
    }

    private void setUpMainFragment(FragmentManager fragmentManager) {
        MainActivityFragment moviesFragment = (MainActivityFragment) fragmentManager.findFragmentByTag(String.format(TAG_FORMAT, MainActivityFragment.class.getName(), 0));
        if (moviesFragment == null) {
            moviesFragment = new MainActivityFragment();
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

    @Override
    public void onItemSelected(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class)
                .putExtra(DetailActivityFragment.DETAIL_MOVIE, movie);
        startActivity(intent);
    }


}
