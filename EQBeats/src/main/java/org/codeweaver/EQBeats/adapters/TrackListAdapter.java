package org.codeweaver.eqbeats.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.model.Track;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class TrackListAdapter extends BaseAdapter {

	private List<Track>	tracks;
	private Context		context;

	public TrackListAdapter(Context context) {
		this.context = context;
		tracks = new ArrayList<Track>();
	}

	public void addTracks(Collection<Track> tracks) {
		this.tracks.addAll(tracks);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return tracks.size();
	}

	@Override
	public Track getItem(int position) {
		return tracks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageView image;
		TextView title;
		TextView artist;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_item_track, null);
			image = (ImageView) convertView.findViewById(R.id.track_art);
			title = (TextView) convertView.findViewById(R.id.track_title);
			artist = (TextView) convertView.findViewById(R.id.track_artist);

			convertView.setTag(R.id.track_art, image);
			convertView.setTag(R.id.track_title, title);
			convertView.setTag(R.id.track_artist, artist);
		} else {
			image = (ImageView) convertView.getTag(R.id.track_art);
			title = (TextView) convertView.getTag(R.id.track_title);
			artist = (TextView) convertView.getTag(R.id.track_artist);
		}

		Resources res = context.getResources();
		Track track = getItem(position);
		title.setText(track.getTitle());
		artist.setText(res.getString(R.string.track_artist_text, track
				.getArtist().getName()));
		if (track.getDownload().getArt() != null) {
			Picasso.with(context)//
					.load(track.getDownload().getArt())//
					.placeholder(R.drawable.ic_logo)//
					.error(R.drawable.ic_logo)//
					.resizeDimen(R.dimen.art_width, R.dimen.art_width)//
					.centerCrop()//
					.into(image);
		} else {
			Picasso.with(context)//
					.load(R.drawable.ic_logo)//
					.resizeDimen(R.dimen.art_width, R.dimen.art_width)//
					.centerCrop()//
					.into(image);
		}

		return convertView;
	}
}
