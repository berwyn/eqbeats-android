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

package org.codeweaver.eqbeats.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.codeweaver.eqbeats.BuildConfig;
import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.model.Track;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        RequestCreator _rb;
        if (track.getDownload().getArt() != null) {
            _rb = picasso.load(track.getDownload().getArt());
        } else {
            _rb = picasso.load(R.drawable.ic_art_filler);
        }
        _rb.placeholder(R.drawable.ic_art_filler)//
                .error(R.drawable.ic_art_filler)//
                .resizeDimen(R.dimen.art_width, R.dimen.art_height)//
                .centerCrop()//
                .into(image);

        return convertView;
    }
}
