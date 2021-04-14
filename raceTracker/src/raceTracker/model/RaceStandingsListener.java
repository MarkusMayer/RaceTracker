package raceTracker.model;

import raceTracker.model.viewModel.DriverLapHistory;
import raceTracker.model.viewModel.RaceStandings;

public interface RaceStandingsListener {

	public void updateRaceStandings(RaceStandings curStandings, DriverLapHistory history);
}
