package org.codeweaver.eqbeats.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.frankiesardo.icepick.annotation.Icicle;
import com.github.frankiesardo.icepick.bundle.Bundles;
import com.squareup.picasso.Picasso;

import org.codeweaver.eqbeats.BuildConfig;
import org.codeweaver.eqbeats.EqBeatsAPI;
import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.model.User;

import butterknife.InjectView;
import butterknife.Views;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserActivity extends Activity {

    private static final String TAG           = "UserActivity";
    public static final String  KEY_ARTIST_ID = "artist_id";

    @Icicle
    long                        userId;
    private User                artist;
    private ListView            listView;

    @InjectView(R.id.artist_banner)
    ImageView                   artistBanner;
    @InjectView(R.id.artist_header_image)
    ImageView                   artistImage;
    @InjectView(R.id.list_artist_title)
    TextView                    artistTitle;
    @InjectView(R.id.list_song_count_text)
    TextView                    songCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listView = (ListView) findViewById(R.id.artist_detail_list);
        View headerView = getLayoutInflater().inflate(
                R.layout.widet_list_header, null);
        listView.addHeaderView(headerView);

        Views.inject(this);
        Bundles.restoreInstanceState(this, savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY_ARTIST_ID)) {
            userId = extras.getLong(KEY_ARTIST_ID);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EqBeatsAPI.getUser(userId, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                setupView(user);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Crouton.makeText(UserActivity.this,
                        R.string.artist_lookup_error, Style.ALERT).show();
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "User look up error on " + userId, retrofitError);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundles.saveInstanceState(this, outState);
    }

    private void setupView(User artist) {
        this.artist = artist;
        Picasso.with(this)//
                .load(artist.getAvatar())//
                .resizeDimen(R.dimen.art_width, R.dimen.art_height)//
                .centerCrop().into(artistImage);
        Picasso.with(this).load(R.drawable.ic_logo).into(artistBanner);
        artistTitle.setText(artist.getName());
        songCount.setText(getString(R.string.song_count,
                artist.getTrackIds().length));
    }
}
