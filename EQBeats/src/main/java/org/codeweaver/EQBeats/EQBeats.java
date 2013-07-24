package org.codeweaver.eqbeats;

import org.codeweaver.eqbeats.util.NetworkStatusProducerApplication;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class EqBeats extends NetworkStatusProducerApplication {

	private EqBeatsAPI api;

	public EqBeatsAPI getApi() {
		if (api == null) api = new EqBeatsAPI.Builder().build();
		return api;
	}

}
