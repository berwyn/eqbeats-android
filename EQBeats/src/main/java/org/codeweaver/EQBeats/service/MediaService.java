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

package org.codeweaver.eqbeats.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import org.codeweaver.eqbeats.event.MediaPlayerErrorEvent;
import org.codeweaver.eqbeats.util.BusProvider;

import java.io.IOException;

/**
 * Created by Berwyn Codeweaver on 30/06/13.
 */
public class MediaService extends Service implements
		MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

	private static final String		ACTION_PLAY	= "org.codeweaver.eqbeats.action.PLAY";
	private static final String		DATA_URL	= "org.codeweaver.eqbeats.data.STREAM_URL";
	private static final String		DATA_ACTION	= "org.codeweaver.eqbeats.data.ACTION";

	private final Binder			binder		= new MediaServiceBinder();

	private MediaPlayer				mediaPlayer	= null;
	private WifiManager.WifiLock	wifiLock	= null;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		mediaPlayer.reset();
		return true;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {

	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class MediaServiceBinder extends Binder {

		public MediaService getService() {
			return MediaService.this;
		}

	}

	private void initialisePlayer(String uri) {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setWakeMode(getApplicationContext(),
				PowerManager.PARTIAL_WAKE_LOCK);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(uri);
			mediaPlayer.prepareAsync();
		} catch (IOException e) {
			BusProvider.get().post(new MediaPlayerErrorEvent(e));
		}
	}
}
