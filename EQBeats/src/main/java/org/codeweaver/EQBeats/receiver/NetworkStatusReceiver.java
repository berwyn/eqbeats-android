package org.codeweaver.eqbeats.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.codeweaver.eqbeats.util.NetworkStatusProducerApplication;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class NetworkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ((NetworkStatusProducerApplication) context.getApplicationContext()).updateNetworkStatus();
    }
}
