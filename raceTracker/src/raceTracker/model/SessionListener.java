package raceTracker.model;

import raceTracker.model.gameStructs.Session;

public interface SessionListener {

	public void updateSessionData(Session newSessionData);
}
