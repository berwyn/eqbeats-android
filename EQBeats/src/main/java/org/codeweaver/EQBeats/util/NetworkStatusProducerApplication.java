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
        boolean wifiConnected = false;
        boolean mobileConnected = false;
        try {
		    wifiConnected = wifi.isAvailable() && wifi.isConnected();
	        mobileConnected = mobile.isAvailable() && mobile.isConnected();
        } catch (Exception e) {
            // Do nothing, assume no network connectivity
        }
		boolean isConnected = wifiConnected || mobileConnected;

		networkStatus = new NetworkStatusEvent(isConnected);
		eventBus.post(produceNetworkStatus());
	}

	@Produce
	public NetworkStatusEvent produceNetworkStatus() {
		return networkStatus;
	}

}
