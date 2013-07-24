package org.codeweaver.eqbeats.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import org.codeweaver.eqbeats.event.NetworkStatusEvent;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public abstract class NetworkStatusProducerApplication extends Application {

	protected Bus					eventBus	= BusProvider.get();
	protected NetworkStatusEvent	networkStatus;

	public void updateNetworkStatus() {
		final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		final NetworkInfo wifi = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final NetworkInfo mobile = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		boolean wifiConnected = wifi.isAvailable() && wifi.isConnected();
		boolean mobileConnected = mobile.isAvailable() && mobile.isConnected();
		boolean isConnected = wifiConnected || mobileConnected;

		networkStatus = new NetworkStatusEvent(isConnected);

		eventBus.post(produceNetworkStatus());
	}

	@Produce
	public NetworkStatusEvent produceNetworkStatus() {
		return networkStatus;
	}

}
