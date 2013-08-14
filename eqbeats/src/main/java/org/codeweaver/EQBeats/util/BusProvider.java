package org.codeweaver.eqbeats.util;

import com.squareup.otto.Bus;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class BusProvider {

    private static final Bus bus = new Bus();

    public static Bus get() {
        return bus;
    }

}
