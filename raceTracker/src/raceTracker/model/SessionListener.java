package raceTracker.model;

import raceTracker.model.gameStructs.SessionStruct;

public interface SessionListener {

	public void updateSessionData(SessionStruct newSessionData);
}
