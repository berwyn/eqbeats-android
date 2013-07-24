package org.codeweaver.eqbeats;

import com.google.gson.Gson;

import org.codeweaver.eqbeats.model.Track;
import org.codeweaver.eqbeats.model.User;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class EqBeatsAPI {

    private static final String        BASE_URL   = "https://eqbeats.org";
    private static final ReadWriteLock TRACK_LOCK = new ReentrantReadWriteLock(
                                                          true);
    private static final ReadWriteLock USER_LOCK  = new ReentrantReadWriteLock(
                                                          true);

    private static ApiContract         api;
    private static Map<Long, User>     userCache;
    private static Map<Long, Track>    trackCache;

    public static void getUser(long id, final Callback<User> callback) {
        if (userCache.containsKey(id)) {
            TRACK_LOCK.readLock().lock();
            callback.success(userCache.get(id), new Response(200, null, null,
                    null));
            TRACK_LOCK.readLock().unlock();
        } else {
            api.getUser(id, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    TRACK_LOCK.writeLock().lock();
                    userCache.put(user.getId(), user);
                    TRACK_LOCK.writeLock().unlock();
                    callback.success(user, response);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    callback.failure(retrofitError);
                }
            });
        }
    }

    public static void getTrack(long id, final Callback<Track> callback) {
        if (trackCache.containsKey(id)) {
            USER_LOCK.readLock().lock();
            callback.success(trackCache.get(id), new Response(200, null, null,
                    null));
            USER_LOCK.readLock().unlock();
        } else {
            api.getTrack(id, new Callback<Track>() {
                @Override
                public void success(Track track, Response response) {
                    USER_LOCK.writeLock().lock();
                    trackCache.put(track.getId(), track);
                    USER_LOCK.writeLock().unlock();
                    callback.success(track, response);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    callback.failure(retrofitError);
                }
            });
        }
    }

    private interface ApiContract {

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
}
