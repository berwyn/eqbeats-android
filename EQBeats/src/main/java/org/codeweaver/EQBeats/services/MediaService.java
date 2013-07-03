package org.codeweaver.eqbeats.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;

import java.io.IOException;

/**
 * Created by Berwyn Codeweaver on 30/06/13.
 */
public class MediaService extends Service implements
		MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

	private static final String		ACTION_PLAY	= "org.codeweaver.eqbeats.action.PLAY";
	private static final String		DATA_URL	= "org.codeweaver.eqbeats.data.STREAM_URL";
	private static final String		DATA_ACTION	= "org.codeweaver.eqbeats.data.ACTION";

	private MediaPlayer				mediaPlayer	= null;
	private WifiManager.WifiLock	wifiLock	= null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getAction().equals(ACTION_PLAY)) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.setWakeMode(getApplicationContext(),
					PowerManager.PARTIAL_WAKE_LOCK);
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(intent.getStringExtra(DATA_URL));
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return START_STICKY;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
