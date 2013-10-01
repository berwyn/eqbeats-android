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
