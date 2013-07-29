package org.codeweaver.eqbeats;

import com.google.gson.Gson;

import org.codeweaver.eqbeats.model.Track;
import org.codeweaver.eqbeats.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private static final String          BASE_URL        = "https://eqbeats.org";
    private static final ReadWriteLock   TRACK_LOCK      = new ReentrantReadWriteLock(
                                                                 true);
    private static final ReadWriteLock   USER_LOCK       = new ReentrantReadWriteLock(
                                                                 true);
    private static final ExecutorService THREAD_POOL     = Executors
                                                                 .newFixedThreadPool(10);

    private static final int             LATEST_TRACKS   = 0;
    private static final int             FEATURED_TRACKS = 1;
    private static final int             RANDOM_TRACKS   = 2;

    private static ApiContract           api;
    private static Map<Long, User>       userCache;
    private static Map<Long, Track>      trackCache;

    static {
        api = new ApiContract.Builder().build();
        userCache = new HashMap<Long, User>(100);
        trackCache = new HashMap<Long, Track>(200);
    }

    private static void storeUsers(final User... users) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (User u : users) {
                    Track[] tracks = u.getTracks();
                    storeTracks(tracks);
                    u.setTracks(null);
                    long[] ids = new long[tracks.length];
                    for (int i = 0; i < tracks.length; i++) {
                        ids[i] = tracks[i].getId();
                    }
                    u.setTrackIds(ids);

                    USER_LOCK.writeLock().lock();
                    userCache.put(u.getId(), u);
                    USER_LOCK.readLock().unlock();
                }
            }
        };
        THREAD_POOL.submit(r);
    }

    private static void storeTracks(final Track... tracks) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (Track t : tracks) {
                    TRACK_LOCK.writeLock().lock();
                    trackCache.put(t.getId(), t);
                    TRACK_LOCK.writeLock().unlock();
                }
            }
        };
        THREAD_POOL.submit(r);
    }

    public static void getUser(long id, final Callback<User> callback) {
        if (userCache.containsKey(id)) {
            USER_LOCK.readLock().lock();
            callback.success(userCache.get(id), new Response(200, null, null,
                    null));
            USER_LOCK.readLock().unlock();
        } else {
            api.getUser(id, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    storeUsers(user);
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
                    storeTracks(track);
                    callback.success(track, response);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    callback.failure(retrofitError);
                }
            });
        }
    }

    private static void getTrackResource(int res,
            final Callback<Track[]> callback) {
        Callback<Track[]> inner = new Callback<Track[]>() {
            @Override
            public void success(Track[] tracks, Response response) {
                storeTracks(tracks);
                callback.success(tracks, response);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callback.failure(retrofitError);
            }
        };
        switch (res) {
            case LATEST_TRACKS:
                api.getLatestTracks(inner);
                break;
            case FEATURED_TRACKS:
                api.getFeaturedTracks(inner);
                break;
            case RANDOM_TRACKS:
            default:
                api.getRandomTracks(inner);
                break;
        }
    }

    public static void getLatestTracks(final Callback<Track[]> callback) {
        getTrackResource(LATEST_TRACKS, callback);
    }

    public static void getFeaturedTracks(final Callback<Track[]> callback) {
        getTrackResource(FEATURED_TRACKS, callback);
    }

    public static void getRandomTracks(final Callback<Track[]> callback) {
        getTrackResource(RANDOM_TRACKS, callback);
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

            public ApiContract build() {
                return build(new Gson());
            }

            public ApiContract build(Gson gson) {
                return new RestAdapter.Builder()//
                        .setServer(BASE_URL)//
                        .setConverter(new GsonConverter(gson))//
                        .build()//
                        .create(ApiContract.class);
            }

        }
    }
}
