/*
 * Copyright (C) 2013 Berwyn Codeweaver
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codeweaver.eqbeats;

import com.google.gson.Gson;

import org.codeweaver.eqbeats.model.Track;
import org.codeweaver.eqbeats.model.User;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public interface EqBeatsAPI {

    public static final String BASE_URL = "https://eqbeats.org";
    public static final EqBeatsAPI instance = new Builder().build();

    @GET("/user/{id}/json")
    public void getUser(@Path("id") long id, Callback<User> callback);

    @GET("/track/{id}/json")
    public void getTrack(@Path("id") long id, Callback<Track> callback);

    @GET("/tracks/latest/json")
    public void getLatestTracks(Callback<Track[]> callback);

    @GET("/tracks/featured/json")
    public void getFeaturedTracks(Callback<Track[]> callback);

    @GET("/tracks/random/json")
    public void getRandomTracks(Callback<Track[]> callback);

    public class Builder {

        public EqBeatsAPI build() {
            return build(new Gson());
        }

        public EqBeatsAPI build(Gson gson) {
            return new RestAdapter.Builder()//
                    .setServer(BASE_URL)//
                    .setConverter(new GsonConverter(gson))//
                    .build()//
                    .create(EqBeatsAPI.class);
        }

    }
}
