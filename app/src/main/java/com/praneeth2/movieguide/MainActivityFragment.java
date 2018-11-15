package com.praneeth2.movieguide;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.praneeth2.movieguide.adapters.MovieGridAdapter;
import com.praneeth2.movieguide.database.MovieContract;
import com.praneeth2.movieguide.models.Movie;
import com.praneeth2.movieguide.services.Command;
import com.praneeth2.movieguide.services.VolleyServiceTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment implements Command {
    private GridView mGridView;

    private MovieGridAdapter mMovieGridAdapter;

    private VolleyServiceTask mServiceTask;
    private View view;

    private static final String SORT_SETTING_KEY = "sort_setting";
    private static final String POPULARITY_DESC = "popularity.desc";
    private static final String RATING_DESC = "vote_average.desc";
    private static final String FAVORITE = "favorite";
    private static final String MOVIES_KEY = "movies";

    private String mSortBy = POPULARITY_DESC;

    private ArrayList<Movie> mMovies = null;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_IMAGE,
            MovieContract.MovieEntry.COLUMN_IMAGE2,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_DATE
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_IMAGE = 3;
    public static final int COL_IMAGE2 = 4;
    public static final int COL_OVERVIEW = 5;
    public static final int COL_RATING = 6;
    public static final int COL_DATE = 7;

    public MainActivityFragment() {
    }

    @Override
    public void execute(String jsonData) throws JSONException {
        mMovies = (ArrayList<Movie>) getDataFromJson(jsonData);
        loadScreen(view);
    }

    public interface Callback{
        void onItemSelected(Movie movie);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main, menu);

        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);
        MenuItem action_sort_by_favorite = menu.findItem(R.id.action_sort_by_favorite);

        if (mSortBy.contentEquals(POPULARITY_DESC)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_popularity.setChecked(true);
            }
        } else if (mSortBy.contentEquals(RATING_DESC)) {
            if (!action_sort_by_rating.isChecked()) {
                action_sort_by_rating.setChecked(true);
            }
        } else if (mSortBy.contentEquals(FAVORITE)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_favorite.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = POPULARITY_DESC;
                mServiceTask.callService(mSortBy);
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = RATING_DESC;
                mServiceTask.callService(mSortBy);
                return true;
            case R.id.action_sort_by_favorite:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                mSortBy = FAVORITE;
                mServiceTask.callService(mSortBy);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.movie_layout, container, false);

        mServiceTask = new VolleyServiceTask(getActivity(), this);
        mServiceTask.callService(mSortBy);

//        loadScreen(vie);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!mSortBy.contentEquals(POPULARITY_DESC)) {
            outState.putString(SORT_SETTING_KEY, mSortBy);
        }
        if (mMovies != null) {
            outState.putParcelableArrayList(MOVIES_KEY, mMovies);
        }
        super.onSaveInstanceState(outState);
    }


    private List<Movie> getDataFromJson(String jsonStr) throws JSONException {
        JSONObject movieJson = new JSONObject(jsonStr);
        JSONArray movieArray = movieJson.getJSONArray("results");

        List<Movie> results = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            Movie movieModel = new Movie(movie);
            results.add(movieModel);
        }

        return results;
    }

    Activity getMainActivity() {
        return getActivity();
    }

    void loadScreen(View view) {

        mGridView = view.findViewById(R.id.grid_view_movies);

        mMovieGridAdapter = new MovieGridAdapter(getActivity(), mMovies);

        mGridView.setAdapter(mMovieGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovieGridAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(movie);
            }
        });

//        if (savedInstanceState != null) {
//            if (savedInstanceState.containsKey(SORT_SETTING_KEY)) {
//                mSortBy = savedInstanceState.getString(SORT_SETTING_KEY);
//            }
//
//            if (savedInstanceState.containsKey(MOVIES_KEY)) {
//                mMovies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
//                mMovieGridAdapter.setData(mMovies);
//            } else {
//                mServiceTask.callService(mSortBy);
//            }
//        } else {
//            mServiceTask.callService(mSortBy);
//        }

    }
}
