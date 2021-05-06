package raceTracker.model.viewModel;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import raceTracker.model.enums.CarController;
import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Team;
import raceTracker.model.enums.TelemetryVisibility;
import raceTracker.model.gameStructs.LapStruct;
import raceTracker.model.gameStructs.Participant;

public class TestDriverLapHistory {

	private static final List<Participant> drivers = TestRaceStandings.getLiveRaceDrivers();
	private static final List<List<LapStruct>> laps = TestRaceStandings.getLiveRaceLaps();

	DriverLapHistory dlh = new DriverLapHistory();

	List<LapStruct> racePos_1 = Arrays.asList(TestLap.structLap_Lap1_LastLap0_CurLap5_S12Rest0,
			TestLap.structLap_Lap1_LastLap0_CurL5_S12Rest0_s2fast);
	List<LapStruct> racePos_2 = Arrays.asList(TestLap.structLap_Lap1_LastLap0_CurLap5_S12_S23_Rest0,
			TestLap.structLap_Lap1_LastLap0_CurL5_S12_S23_Rest0_s2fast);
	List<LapStruct> racePos_3 = Arrays.asList(TestLap.structLap_Lap2_LastLap10_CurLap3_Rest0,
			TestLap.structLap_Lap2_LastLap10_CurL3_Rest0_s2fast);

	List<Participant> sebHam = Arrays.asList(
			new Participant(CarController.ai, Driver.sebastianVettel, Team.ferrari,
					TelemetryVisibility.publicVisibility, 1, 1, "SEB"),
			new Participant(CarController.ai, Driver.lewisHamilton, Team.mercedes, TelemetryVisibility.publicVisibility,
					1, 1, "HAM"));

	@Before
	public void setup() {
		dlh = new DriverLapHistory();
	}

	@Test
	public void liveDataLap1Start() {
		dlh.addLaps(laps.get(0), drivers);
		assertEquals(1, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertEquals(6, dlh.getHistories().keySet().size());
		assertEquals(6, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
		assertFalse(dlh.getBestS1().isSet());
		assertFalse(dlh.getBestS2().isSet());
		assertFalse(dlh.getBestS3().isSet());
	}

	@Test
	public void liveDataLap1S1CompletedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_First_Lap_S1_Finished + 1)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(1, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(19377),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().getSecTime());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS1().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertEquals(6, dlh.getHistories().keySet().size());
		assertEquals(6, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
		assertEquals(Duration.ofMillis(19377), dlh.getBestS1().getSecTime());
		assertFalse(dlh.getBestS2().isSet());
		assertFalse(dlh.getBestS3().isSet());

		// Deltas are ZERO current Lap
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());
	}

	@Test
	public void liveDataLap1S2CompletedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_First_Lap_S2_Finished + 1)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(1, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(19377), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS1());
		assertEquals(Duration.ofMillis(30431), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS2());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS3());
		assertEquals(Duration.ofMillis(19377),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(30431),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().getSecTime());
		// seb ... [5]
		assertEquals(Duration.ofMillis(22110),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS1().getSecTime());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertEquals(6, dlh.getHistories().keySet().size());
		assertEquals(6, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
		assertEquals(Duration.ofMillis(19377), dlh.getBestS1().getSecTime());
		assertEquals(Duration.ofMillis(30431), dlh.getBestS2().getSecTime());
		assertFalse(dlh.getBestS3().isSet());

		// Deltas are ZERO current Lap
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());

		// ALB S1,S2 overall/personal Best, Lap / S3 false because ALB is not finished
		// yet
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS3());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS3());

		// SEB S1,S2,S3 overall Best false, Personal Best S1 true, Lap / S2 / S3 false
		// because SEB not finished yet
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS3());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS3());
	}

	@Test
	public void liveDataLap2StartedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_Second_Lap_Start + 1)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(2, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(70341),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().getLapTime());
		assertEquals(Duration.ofMillis(19377), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS1());
		assertEquals(Duration.ofMillis(30431), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS2());
		assertEquals(Duration.ofMillis(20533), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS3());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS1());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS2());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS3());
		assertEquals(Duration.ofMillis(30431),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().getSecTime());
		// seb ... [5]
		assertEquals(1, dlh.getHistoryForDriver(Driver.sebastianVettel).getCurrentLapNum());
		assertEquals(Duration.ofMillis(22110),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(33707),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS2().getSecTime());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertEquals(6, dlh.getHistories().keySet().size());
		assertEquals(6, dlh.getHistories().values().size());
		assertEquals(Duration.ofMillis(70341), dlh.getBestLap().getLapTime());
		assertEquals(Duration.ofMillis(19377), dlh.getBestS1().getSecTime());
		assertEquals(Duration.ofMillis(30431), dlh.getBestS2().getSecTime());
		assertEquals(Duration.ofMillis(20533), dlh.getBestS3().getSecTime());

		// Deltas are ZERO last and current Lap
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3PersonalBest());

		// delta times SEB to ALB
		assertEquals(Duration.ofMillis(2733),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(3276),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS2Best());

		// S3 SEB is not finished therefore S3 delta for SEB still ZERO
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isS3Finished());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS3Best());

		// ALB Lap, S1,S2,S3 overall/personal Best
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS3());

		// SEB S1,S2,S3 overall Best false, Personal Best S1,S2 true, S3 false because
		// S3 for SEB not finished yet
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS3());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS3());
	}

	@Test
	public void liveDataLap2S1CompletedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_Second_Lap_S1_Finished + 1)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(2, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertEquals(Duration.ofMillis(20533), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS3());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(70341),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().getLapTime());
		assertFalse(Duration.ZERO==dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getLapTime());
		assertTrue(Duration.ZERO==dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getLapTime());
		// Old Sector times remain unchanged
		assertEquals(Duration.ofMillis(19377), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS1());
		assertEquals(Duration.ofMillis(30431), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS2());
		assertEquals(Duration.ofMillis(20533), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getS3());

		// Cur Lap Sector time is updated
		assertEquals(Duration.ofMillis(16984), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS1());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS2());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS3());

		// Personal and overall S1 Record get updated
		assertEquals(Duration.ofMillis(16984),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(16984), dlh.getBestS1().getSecTime());
		// Personal S2, S3 Records and S1 of best completed lap remain unchanged
		assertEquals(Duration.ofMillis(30431),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().getSecTime());
		assertEquals(Duration.ofMillis(20533),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().getSecTime());
		// Deltas of old lap S1 is updated
		assertEquals(Duration.ofMillis(2393),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(2393),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());
		// Deltas of old lap S2,S3 remain ZERO because S2/S3 are not finished yet in
		// current Lap
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());

		// Deltas of current are all ZERO because S1 is fastest and S2/S3 are not
		// finished yet
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3PersonalBest());

		// seb also entered second lap... [5]
		assertEquals(2, dlh.getHistoryForDriver(Driver.sebastianVettel).getCurrentLapNum());
		assertEquals(Duration.ofMillis(22110),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(33707),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS2().getSecTime());
		assertEquals(Duration.ofMillis(21954),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().getSecTime());

		// delta times SEB to ALB, s1 changed S2/S3 remain unchanged
		assertEquals(Duration.ofMillis(5126),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(3276),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ofMillis(1421),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS3Best());

		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());

		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertEquals(6, dlh.getHistories().keySet().size());
		assertEquals(6, dlh.getHistories().values().size());

		// Overall S1 record is changed
		assertEquals(Duration.ofMillis(16984), dlh.getBestS1().getSecTime());
		// rest remains unchanged
		assertEquals(Duration.ofMillis(70341), dlh.getBestLap().getLapTime());
		assertEquals(Duration.ofMillis(30431), dlh.getBestS2().getSecTime());
		assertEquals(Duration.ofMillis(20533), dlh.getBestS3().getSecTime());

		// ALB best S1 is now in Lap2
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS3());

		// SEB Lap,S1,S2,S3 overall Best false, Personal Best Lap and S3 now true
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBestS3());
	}

	@Test
	public void liveDataLap2S2CompletedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_Second_Lap_S2_Finished + 1)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(2, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isFinished());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isFinished());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(70341), dlh.getBestLap().getLapTime());
		assertEquals(Duration.ofMillis(70341),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getLapTime());
		assertEquals(Duration.ofMillis(70341),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().getLapTime());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaLapBest());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaLapPersonalBest());

		assertEquals(Duration.ofMillis(77771),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getLapTime());
		assertEquals(Duration.ofMillis(77771),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestLap().getLapTime());
		assertEquals(Duration.ofMillis(7430),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaLapBest());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaLapPersonalBest());

		// Cur Lap Sector time is updated
		assertEquals(Duration.ofMillis(16984), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS1());
		assertEquals(Duration.ofMillis(30303), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS2());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getS3());

		// Personal and overall S1 Record get updated
		assertEquals(Duration.ofMillis(16984),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(16984), dlh.getBestS1().getSecTime());
		// Personal S2 updated
		assertEquals(Duration.ofMillis(30303),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().getSecTime());
		assertEquals(Duration.ofMillis(30303), dlh.getBestS2().getSecTime());
		assertEquals(Duration.ofMillis(20533),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().getSecTime());

		// Deltas of old lap S1 is updated
		assertEquals(Duration.ofMillis(2393),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(2393),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());

		// Deltas of old lap S2 is updated
		assertEquals(Duration.ofMillis(128),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ofMillis(128),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());

		// Deltas of old lap S3 remain ZERO because S3 are not finished yet in
		// current Lap
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());

		// Deltas of current are all ZERO because S1 is fastest and S2/S3 are not
		// finished yet
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2PersonalBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3PersonalBest());

		// seb also entered second lap... [5]
		assertEquals(2, dlh.getHistoryForDriver(Driver.sebastianVettel).getCurrentLapNum());
		assertEquals(Duration.ofMillis(17898),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(33707),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS2().getSecTime());
		assertEquals(Duration.ofMillis(21954),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().getSecTime());

		// delta times SEB to ALB, s1 changed S2/S3 remain unchanged
		assertEquals(Duration.ofMillis(5126),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(3404),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ofMillis(1421),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS3Best());

		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());

		// Overall S2 record is changed
		assertEquals(Duration.ofMillis(30303), dlh.getBestS2().getSecTime());
		// rest remains unchanged
		assertEquals(Duration.ofMillis(70341), dlh.getBestLap().getLapTime());
		assertEquals(Duration.ofMillis(16984), dlh.getBestS1().getSecTime());
		assertEquals(Duration.ofMillis(20533), dlh.getBestS3().getSecTime());

		// ALB best S1 is now in Lap2
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBestS3());

		// SEB Lap,S1,S2,S3 overall Best false, Personal Best Lap and S3 now true
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isPersonalBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isPersonalBestS3());
	}

	@Test
	public void liveDataLap4S2CompletedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_Before_Race_End)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(5, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).isFinished());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).isFinished());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(70341),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getLapTime());
		assertEquals(Duration.ofMillis(67656),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getLapTime());
		assertEquals(Duration.ofMillis(67829),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(2).getLapTime());
		assertEquals(Duration.ofMillis(67927),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).getLapTime());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).getLapTime());

		assertEquals(Duration.ofMillis(67656),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().getLapTime());
		assertEquals(Duration.ofMillis(67656), dlh.getBestLap().getLapTime());

		assertEquals(Duration.ofMillis(2685),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaLapBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaLapBest());
		assertEquals(Duration.ofMillis(173),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(2).getDeltaLapBest());
		assertEquals(Duration.ofMillis(271),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).getDeltaLapBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).getDeltaLapBest());

		assertEquals(Duration.ofMillis(2685),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaLapPersonalBest());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaLapPersonalBest());
		assertEquals(Duration.ofMillis(173),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(2).getDeltaLapPersonalBest());
		assertEquals(Duration.ofMillis(271),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).getDeltaLapPersonalBest());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).getDeltaLapPersonalBest());

		// Personal and overall S1 Record get updated
		assertEquals(Duration.ofMillis(16984),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().getSecTime());
		// VER has absolute best S1 in Lap 5 
		assertEquals(Duration.ofMillis(16952), dlh.getBestS1().getSecTime());
		// Personal S2 updated
		assertEquals(Duration.ofMillis(30303),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().getSecTime());
		// VER has absolute best S2 in Lap 5
		assertEquals(Duration.ofMillis(30274), dlh.getBestS2().getSecTime());
		
		// ALB set new Personal best S3 in Lap 2
		assertEquals(Duration.ofMillis(20369),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().getSecTime());

		// Deltas of old lap S1 is updated, VER new absolute best S1 ==> deltaBestS1 changed, personalBestS1 unchanged
		assertEquals(Duration.ofMillis(2425),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(2393),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());

		// Deltas of old lap S2 is updated, VER new absolute best S2 ==> deltaBestS2 changed, personalBestS2 unchanged
		assertEquals(Duration.ofMillis(157),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ofMillis(128),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());

		// ALB set Personal best S3 in Lap 2 ==> Delta Lap1 S3 to absoluteBestS3 (of Lap 2) 
		// current Lap
		assertEquals(Duration.ofMillis(164),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());
		assertEquals(Duration.ofMillis(164), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());

		// VER absolute best S1, personalBest in Lap 2 remains ZERO
		assertEquals(Duration.ofMillis(32), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1PersonalBest());
		// VER absolute best S2, personalBest in Lap 2 remains ZERO
		assertEquals(Duration.ofMillis(29), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2PersonalBest());

		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3PersonalBest());

		assertEquals(5, dlh.getHistoryForDriver(Driver.sebastianVettel).getCurrentLapNum());
		assertEquals(Duration.ofMillis(17422),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS1().getSecTime());
		assertEquals(Duration.ofMillis(32159),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS2().getSecTime());
		assertEquals(Duration.ofMillis(21020),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().getSecTime());

		// delta times SEB to ALB, s1 changed S2/S3 remain unchanged
		assertEquals(Duration.ofMillis(5158),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(3433),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ofMillis(1585),
				dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).getDeltaS3Best());

		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getPersonalBestS3().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().isSet());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().isSet());

		// Overall records are changed
		assertEquals(Duration.ofMillis(67656), dlh.getBestLap().getLapTime());
		assertEquals(Duration.ofMillis(16952), dlh.getBestS1().getSecTime());
		assertEquals(Duration.ofMillis(30274), dlh.getBestS2().getSecTime());
		assertEquals(Duration.ofMillis(20369), dlh.getBestS3().getSecTime());

		// ALB best S1 is now in Lap2
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS3());


		// SEB Lap,S1,S2,S3 overall Best false, Personal Best Lap and S3 now true
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(2).isPersonalBest());
		
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isPersonalBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(4).isPersonalBestS1());
		
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isOverallBestS2());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(2).isPersonalBestS2());

		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isOverallBestS3());
		assertFalse(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(1).isPersonalBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.sebastianVettel).getLaps().get(2).isPersonalBestS3());
	}
	
	@Test
	public void liveDataLap4RaceCompletedByRaceLeader() {
		for (List<LapStruct> aReading : laps.subList(0, TestRaceStandings.idx_After_Race_End+1)) {
			dlh.addLaps(aReading, drivers);
		}
		assertEquals(5, dlh.getHistoryForDriver(Driver.alexanderAlbon).getCurrentLapNum());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).isFinished());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).isFinished());
		
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().isSet());
		assertEquals(Duration.ofMillis(70341),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getLapTime());
		assertEquals(Duration.ofMillis(67656),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getLapTime());
		assertEquals(Duration.ofMillis(67829),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(2).getLapTime());
		assertEquals(Duration.ofMillis(67927),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).getLapTime());
		assertEquals(Duration.ofMillis(68081), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).getLapTime());

		assertEquals(Duration.ofMillis(67656),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestLap().getLapTime());
		assertEquals(Duration.ofMillis(67656), dlh.getBestLap().getLapTime());

		assertEquals(Duration.ofMillis(2685),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaLapBest());
		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaLapBest());
		assertEquals(Duration.ofMillis(173),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(2).getDeltaLapBest());
		assertEquals(Duration.ofMillis(271),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).getDeltaLapBest());
		assertEquals(Duration.ofMillis(425), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).getDeltaLapBest());

		assertEquals(Duration.ofMillis(2685),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaLapPersonalBest());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaLapPersonalBest());
		assertEquals(Duration.ofMillis(173),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(2).getDeltaLapPersonalBest());
		assertEquals(Duration.ofMillis(271),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(3).getDeltaLapPersonalBest());
		assertEquals(Duration.ofMillis(425),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(4).getDeltaLapPersonalBest());

		// Personal and overall S1 Record get updated
		assertEquals(Duration.ofMillis(16984),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS1().getSecTime());
		// VER has absolute best S1 in Lap 5 
		assertEquals(Duration.ofMillis(16952), dlh.getBestS1().getSecTime());
		// Personal S2 updated
		assertEquals(Duration.ofMillis(30303),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS2().getSecTime());
		// VER has absolute best S2 in Lap 5
		assertEquals(Duration.ofMillis(30274), dlh.getBestS2().getSecTime());
		
		// ALB set new Personal best S3 in Lap 2
		assertEquals(Duration.ofMillis(20369),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getPersonalBestS3().getSecTime());

		// Deltas of old lap S1 is updated, VER new absolute best S1 ==> deltaBestS1 changed, personalBestS1 unchanged
		assertEquals(Duration.ofMillis(2425),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1Best());
		assertEquals(Duration.ofMillis(2393),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS1PersonalBest());

		// Deltas of old lap S2 is updated, VER new absolute best S2 ==> deltaBestS2 changed, personalBestS2 unchanged
		assertEquals(Duration.ofMillis(157),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2Best());
		assertEquals(Duration.ofMillis(128),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS2PersonalBest());

		// ALB set Personal best S3 in Lap 2 ==> Delta Lap1 S3 to absoluteBestS3 (of Lap 2) 
		// current Lap
		assertEquals(Duration.ofMillis(164),
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3PersonalBest());
		assertEquals(Duration.ofMillis(164), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).getDeltaS3Best());

		// VER absolute best S1, personalBest in Lap 2 remains ZERO
		assertEquals(Duration.ofMillis(32), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS1PersonalBest());
		// VER absolute best S2, personalBest in Lap 2 remains ZERO
		assertEquals(Duration.ofMillis(29), dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS2PersonalBest());

		assertEquals(Duration.ZERO, dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3Best());
		assertEquals(Duration.ZERO,
				dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).getDeltaS3PersonalBest());

		// Overall records are unchanged
		assertEquals(Duration.ofMillis(67656), dlh.getBestLap().getLapTime());
		assertEquals(Duration.ofMillis(16952), dlh.getBestS1().getSecTime());
		assertEquals(Duration.ofMillis(30274), dlh.getBestS2().getSecTime());
		assertEquals(Duration.ofMillis(20369), dlh.getBestS3().getSecTime());

		// ALB best S1 is now in Lap2
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isPersonalBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBest());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBest());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(0).isOverallBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS1());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS1());
		assertFalse(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS2());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isOverallBestS3());
		assertTrue(dlh.getHistoryForDriver(Driver.alexanderAlbon).getLaps().get(1).isPersonalBestS3());
	}

	@Test
	public void addFirstLapData() {
		dlh.addLaps(racePos_1, sebHam);
		assertEquals(2, dlh.getHistories().keySet().size());
		assertEquals(2, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
	}

	@Test
	public void multipleMeasurePointsInSameLapRecordsRemainUnset() {
		dlh.addLaps(racePos_1, sebHam);
		dlh.addLaps(racePos_2, sebHam);
		assertEquals(2, dlh.getHistories().keySet().size());
		assertEquals(2, dlh.getHistories().values().size());
		assertFalse(dlh.getBestLap().isSet());
	}

	@Test
	public void measurePointsOfNewLapCreateAnotherHistoryEntry() {
		dlh.addLaps(racePos_1, sebHam);
		dlh.addLaps(racePos_2, sebHam);
		dlh.addLaps(racePos_3, sebHam);
		assertEquals(2, dlh.getHistories().keySet().size());
		assertEquals(2, dlh.getHistories().values().size());
		assertTrue(dlh.getBestLap().isSet());
	}

}
