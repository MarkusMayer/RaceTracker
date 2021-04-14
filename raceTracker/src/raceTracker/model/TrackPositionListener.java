package raceTracker.model;

import java.util.List;

import raceTracker.model.viewModel.TrackPosition;

public interface TrackPositionListener {
	
	public void updateTrackPosition(List<TrackPosition> positions);

}
