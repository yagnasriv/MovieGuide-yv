package com.praneeth2.movieguide.services;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.praneeth2.movieguide.MainActivityFragment;
import com.praneeth2.movieguide.R;

import org.json.JSONException;

/**
 * Created by Praneeth on 11/15/18.
 */
public class VolleyServiceTask {
    private Context context;
    private Command callback;

    public VolleyServiceTask(Activity mainActivity, MainActivityFragment callback) {
        this.callback = (Command) callback;
        context = mainActivity.getApplicationContext();
    }

    public void callService(String sortBy) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        final String url;

        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String SORT_BY_PARAM = "sort_by";
        final String API_KEY_PARAM = "api_key";

        url = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY_PARAM, sortBy)
                .appendQueryParameter(API_KEY_PARAM, context.getResources().getString(R.string.tmdb_api_key))
                .build()
                .toString();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    callback.execute(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "");
            }
        });

        requestQueue.add(request);
    }
}
