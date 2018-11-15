package com.praneeth2.movieguide.services;

import org.json.JSONException;

/**
 * Created by Praneeth on 11/15/18.
 */
public interface Command {
    void execute(String jsonData) throws JSONException;
}
