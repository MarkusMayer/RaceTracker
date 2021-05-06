package raceTracker.model.viewModel;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

import raceTracker.model.enums.Driver;
import raceTracker.model.gameStructs.LapStruct;
import raceTracker.model.gameStructs.Participant;

public class TestDeltaTracker {
	
	private static final List<Participant> drivers = TestRaceStandings.getLiveRaceDrivers();
	private static final List<List<LapStruct>> laps = TestRaceStandings.getLiveRaceLaps();
	
	DeltaTracker delta=new DeltaTracker();
	
	@Test
	public void testTrackDeltas() {
		int i=0;
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_First_Lap_S1_Finished + 1)) {
			delta.trackProgress(aReading, drivers,LocalTime.ofNanoOfDay(i++*1_000_000));
		}		
		
		assertEquals(Duration.ZERO,delta.getDeltaBetweenCars(Driver.alexanderAlbon,Driver.alexanderAlbon));
		assertEquals(Duration.ofMillis(53),delta.getDeltaBetweenCars(Driver.sebastianVettel,Driver.alexanderAlbon));
		assertEquals(Duration.ofMillis(-53),delta.getDeltaBetweenCars(Driver.alexanderAlbon,Driver.sebastianVettel));
	}

}
