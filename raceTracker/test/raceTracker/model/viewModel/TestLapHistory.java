package raceTracker.model.viewModel;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Sector;
import raceTracker.model.viewModel.LapHistory;
import raceTracker.model.viewModel.RacePosition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLapHistory {
	
	LapHistory empty;
	
	public static final RacePosition r1_seb=new RacePosition(TestLap.structLap_Lap1_LastLap0_CurLap5_S12Rest0, TestLap.participantSeb);
	public static final RacePosition r1_2_seb=new RacePosition(TestLap.structLap_Lap1_LastLap0_CurLap5_S12_S23_Rest0, TestLap.participantSeb);
	public static final RacePosition r2_seb=new RacePosition(TestLap.structLap_Lap2_LastLap10_CurLap3_Rest0, TestLap.participantSeb);
	
	public static final RacePosition r1_ham=new RacePosition(TestLap.structLap_Lap1_LastLap0_CurL5_S12Rest0_s2fast, TestLap.participantHam);
	public static final RacePosition r1_2_ham=new RacePosition(TestLap.structLap_Lap1_LastLap0_CurL5_S12_S23_Rest0_s2fast, TestLap.participantHam);
	public static final RacePosition r2_ham=new RacePosition(TestLap.structLap_Lap2_LastLap10_CurL3_Rest0_s2fast, TestLap.participantHam);

	public static final LapRecord lapRecUnset=LapRecord.unset();
	public static final SectorRecord s1RecUnset=SectorRecord.unset(Sector.sector1);
	public static final SectorRecord s2RecUnset=SectorRecord.unset(Sector.sector2);
	public static final SectorRecord s3RecUnset=SectorRecord.unset(Sector.sector3);
	
	
	@BeforeEach
	public void setup() {
		empty=new LapHistory(Driver.sebastianVettel);
	}
	
	@Test
	public void testFirstLap() {
		empty.addLap(TestLap.structLap_Lap1_LastLap0_CurLap5_S12Rest0,lapRecUnset,s1RecUnset,s2RecUnset,s3RecUnset);	
		assertFalse(empty.getLaps().get(0).isFinished());
		assertEquals(empty.getLaps().size(),1);
	}
	
	@Test
	public void testAddMultipleSameLap() {
		empty.addLap(TestLap.structLap_Lap1_LastLap0_CurLap5_S12Rest0,lapRecUnset,s1RecUnset,s2RecUnset,s3RecUnset);
		empty.addLap(TestLap.structLap_Lap1_LastLap0_CurLap5_S12_S23_Rest0,lapRecUnset,s1RecUnset,s2RecUnset,s3RecUnset);	
		assertFalse(empty.getLaps().get(0).isFinished());
		assertEquals(empty.getLaps().size(),1);
	}
	
	@Test
	public void testFinishPreviousLap() {
		empty.addLap(TestLap.structLap_Lap1_LastLap0_CurLap5_S12Rest0,lapRecUnset,s1RecUnset,s2RecUnset,s3RecUnset);
		empty.addLap(TestLap.structLap_Lap1_LastLap0_CurLap5_S12_S23_Rest0,lapRecUnset,s1RecUnset,s2RecUnset,s3RecUnset);	
		empty.addLap(TestLap.structLap_Lap2_LastLap10_CurL3_Rest0_s2fast,lapRecUnset,s1RecUnset,s2RecUnset,s3RecUnset);	
		assertTrue(empty.getLaps().get(0).isFinished());
		assertEquals(Duration.ofMillis(5100),empty.getLaps().get(0).getS3());
		assertEquals(empty.getLaps().size(),2);
		assertEquals(Duration.ofMillis(10100), empty.getPersonalBestLap().getLapTime());
	}

}
