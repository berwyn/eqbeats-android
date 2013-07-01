package org.codeweaver.eqbeats;

import com.google.gson.Gson;

import org.codeweaver.eqbeats.model.Track;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public interface EQBeatsAPI {

	static final String	BASE_URL	= "https://eqbeats.org";

	@GET("/tracks/latest/json")
	public Track[] getLatestTracks();

	@GET("/tracks/latest/json")
	public void getLatestTracks(Callback<Track[]> callback);

	@GET("/tracks/featured/json")
	public Track[] getFeaturedTracks();

	@GET("/tracks/featured/json")
	public void getFeaturedTracks(Callback<Track[]> callback);

	@GET("/tracks/random/json")
	public Track[] getRandomTracks();

	@GET("/tracks/random/json")
	public void getRandomTracks(Callback<Track[]> callback);

	public class Builder {

		public EQBeatsAPI build() {
			return build(new Gson());
		}

		public EQBeatsAPI build(Gson gson) {
			return new RestAdapter.Builder()//
					.setServer(BASE_URL)//
					.setConverter(new GsonConverter(gson))//
					.build()//
					.create(EQBeatsAPI.class);
		}

	}

}
