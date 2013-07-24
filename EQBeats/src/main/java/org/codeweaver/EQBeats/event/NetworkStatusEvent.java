package org.codeweaver.eqbeats.event;

/**
 * Created by Berwyn Codeweaver on 11/07/13.
 */
public class NetworkStatusEvent {

	private final boolean	connected;

	public NetworkStatusEvent(boolean connected) {
		this.connected = connected;
	}

	public boolean isConnected() {
		return this.connected;
	}

}
