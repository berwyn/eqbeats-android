package org.codeweaver.eqbeats;

import org.codeweaver.eqbeats.util.NetworkStatusProducerApplication;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class EQBeats extends NetworkStatusProducerApplication {

	private EQBeatsAPI	api;

	public EQBeatsAPI getApi() {
		if (api == null) api = new EQBeatsAPI.Builder().build();
		return api;
	}

}
