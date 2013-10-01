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

package org.codeweaver.eqbeats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import org.codeweaver.eqbeats.BuildConfig;
import org.codeweaver.eqbeats.EqBeatsAPI;
import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.adapter.TrackListAdapter;
import org.codeweaver.eqbeats.model.Track;

import java.util.Arrays;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the
 * ListView with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the
 * {@link OnFragmentInteractionListener} interface.
 */
public class TrackFragment extends Fragment implements
        AbsListView.OnItemClickListener {

    private static final String           BUNDLE_KEY_ENDPOINT = "endpoint";
    private static final String           TAG                 = "Track Fragment";

    private OnFragmentInteractionListener listener;
    private EqBeatsAPI api;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView                   listView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private TrackListAdapter              listAdapter;

    // TODO: Rename and change types of parameters
    public static TrackFragment newInstance(Endpoint endpoint) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_ENDPOINT, endpoint.name());
        fragment.setArguments(args);
        fragment.api = new EqBeatsAPI.Builder().build();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrackFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().containsKey(BUNDLE_KEY_ENDPOINT)) {
                Endpoint endpoint = Endpoint.valueOf(getArguments().getString(
                        BUNDLE_KEY_ENDPOINT));
                Callback<Track[]> callback = generateCallback();
                switch (endpoint) {
                    case FEATURED:
                        api.getFeaturedTracks(callback);
                        break;
                    case LATEST:
                        api.getLatestTracks(callback);
                        break;
                    case RANDOM:
                    default:
                        api.getRandomTracks(callback);
                        break;
                }
            }
        }

        listAdapter = new TrackListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_grid, container,
                false);

        // Set the adapter
        listView = (AbsListView) view.findViewById(android.R.id.list);
        listView.setAdapter(listAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        if (null != listener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // TrackPlayingNotification.notify(getActivity(), listAdapter
            // .getItem(position), new RemoteViews(getActivity()
            // .getPackageName(), R.layout.notification_tracks_playing));
            // listener.onFragmentInteraction(id);
            Intent i = new Intent(getActivity(), UserActivity.class);
            i.putExtra(UserActivity.KEY_ARTIST_ID, listAdapter
                    .getItem(position).getArtist().getId());
            startActivity(i);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = listView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public Callback<Track[]> generateCallback() {
        return new Callback<Track[]>() {
            @Override
            public void success(Track[] tracks, Response response) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG,
                            String.format("Received %1d tracks", tracks.length));
                }
                listAdapter.addTracks(Arrays.asList(tracks));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "Error fetching tracks, \n", retrofitError);
                }
                Crouton.showText(getActivity(), R.string.error_tracks_fetch,
                        Style.ALERT);
            }
        };
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated to
     * the activity and potentially other fragments contained in that activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(long id);
    }

    public enum Endpoint {
        LATEST, FEATURED, RANDOM;
    }

}
