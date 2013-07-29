package org.codeweaver.eqbeats.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.codeweaver.eqbeats.BuildConfig;
import org.codeweaver.eqbeats.EqBeatsAPI;
import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.model.Track;
import org.codeweaver.eqbeats.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class TrackListAdapter extends BaseAdapter {

    private List<Track> tracks;
    private Context     context;
    private Picasso     picasso;

    public TrackListAdapter(Context context) {
        this.context = context;
        tracks = new ArrayList<Track>();
        picasso = Picasso.with(context);
        if (BuildConfig.DEBUG) {
            picasso.setDebugging(true);
        }
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
            picasso.load(track.getDownload().getArt())//
                    .placeholder(R.drawable.ic_logo)//
                    .error(R.drawable.ic_logo)//
                    .resizeDimen(R.dimen.art_width, R.dimen.art_height)//
                    .centerCrop()//
                    .into(image);
        } else {
            picasso.load(R.drawable.ic_logo)//
                    .resizeDimen(R.dimen.art_width, R.dimen.art_height)//
                    .centerCrop()//
                    .into(image);
        }

        return convertView;
    }
}
