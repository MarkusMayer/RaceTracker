package raceTracker.view.model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import raceTracker.model.viewModel.DriverLapHistory;
import raceTracker.model.viewModel.RacePosition;

public class TestDriverLapHistory {

	
	DriverLapHistory dlh=new DriverLapHistory();
	
	List<RacePosition> racePos_1=Arrays.asList(TestLapHistory.r1_seb,TestLapHistory.r1_ham);
	List<RacePosition> racePos_2=Arrays.asList(TestLapHistory.r1_2_seb,TestLapHistory.r1_2_ham);
	List<RacePosition> racePos_3=Arrays.asList(TestLapHistory.r2_seb,TestLapHistory.r2_ham);
	
	@BeforeAll
	public static void setup() {
		
	}
	
	@Test
	public void addFirstLapData() {
		dlh.addLaps(racePos_1);
		assertEquals(2, dlh.getHistories().keySet().size());
		assertEquals(2, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
	}
	
	@Test
	public void multipleMeasurePointsInSameLapRecordsRemainUnset() {
		dlh.addLaps(racePos_1);
		dlh.addLaps(racePos_2);
		assertEquals(2, dlh.getHistories().keySet().size());
		assertEquals(2, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
	}

	@Test
	public void measurePointsOfNewLapCreateAnotherHistoryEntry() {
		dlh.addLaps(racePos_1);
		dlh.addLaps(racePos_2);
		dlh.addLaps(racePos_3);
		assertEquals(2, dlh.getHistories().keySet().size());
		assertEquals(2, dlh.getHistories().values().size());
		assertTrue(dlh.getBestLap().isSet());
	}

}
