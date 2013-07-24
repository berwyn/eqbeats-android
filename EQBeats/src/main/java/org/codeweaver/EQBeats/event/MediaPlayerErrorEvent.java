package org.codeweaver.eqbeats.event;

/**
 * Created by Berwyn Codeweaver on 11/07/13.
 */
public class MediaPlayerErrorEvent {

	private final Throwable	error;

	public MediaPlayerErrorEvent(Throwable error) {
		this.error = error;
	}

	public Throwable getError() {
		return this.error;
	}

}
