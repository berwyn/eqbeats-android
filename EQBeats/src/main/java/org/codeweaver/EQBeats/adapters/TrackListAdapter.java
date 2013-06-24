package org.codeweaver.eqbeats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.model.Media;
import org.codeweaver.eqbeats.model.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class TrackListAdapter extends BaseAdapter {

	private List<Track>	tracks;
	private Context		context;

	public TrackListAdapter(Context context) {
		this.context = context;
		tracks = new ArrayList<Track>() {
			{
				add(new Track());
				add(new Track());
				add(new Track());
				add(new Track());
				add(new Track());
				add(new Track());
				add(new Track());
				add(new Track());
			}
		};
		for (int i = 0; i < tracks.size(); i++) {
			tracks.get(i).setTitle("Track " + i);
			tracks.get(i).setId(i);
            tracks.get(i).setDownload(new Media());
            if (i % 2 == 0) {
				tracks.get(i).getDownload().setArt("http://thisisntreal.com");
			}
		}
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
		TextView text;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_item_track, null);
			image = (ImageView) convertView.findViewById(R.id.track_art);
			text = (TextView) convertView.findViewById(R.id.track_title);

			convertView.setTag(R.id.track_art, image);
			convertView.setTag(R.id.track_title, text);
		} else {
			image = (ImageView) convertView.getTag(R.id.track_art);
			text = (TextView) convertView.getTag(R.id.track_title);
		}

		Track track = getItem(position);
		text.setText(track.getTitle());
		if (track.getDownload().getArt() != null) {
			Picasso.with(context)//
					.load(track.getDownload().getArt())//
					.placeholder(R.drawable.ic_logo)//
					.error(R.drawable.ic_logo)//
					.into(image);
		} else {
			Picasso.with(context)//
					.load(R.drawable.ic_logo)//
					.into(image);
		}

		return convertView;
	}
}
