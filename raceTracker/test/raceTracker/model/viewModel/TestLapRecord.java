package raceTracker.model.viewModel;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import raceTracker.model.enums.Driver;
import raceTracker.model.viewModel.LapRecord;

public class TestLapRecord {
	
	public static final LapRecord aRecord_12secs=new LapRecord(Duration.ofMillis(12000), Driver.sebastianVettel, 1);
	public static final LapRecord aRecord_5secs=new LapRecord(Duration.ofMillis(5000), Driver.sebastianVettel, 1);
	
	@Test
	public void testUnsetRecord() {
		assertFalse(LapRecord.unset().isSet());
		assertFalse(LapRecord.unset().update(TestLap.unfinishedLap_Nr1_LT0_S11_S24_S3,Driver.sebastianVettel).isSet());
		assertTrue(LapRecord.unset().update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords,Driver.sebastianVettel).isSet());
	}

	@Test
	public void testSetRecord() {
		assertTrue(aRecord_12secs.isSet());
	}

	
	@Test
	public void testIsSlower() {
		assertEquals(aRecord_5secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords,Driver.sebastianVettel),aRecord_5secs);
		assertTrue(aRecord_5secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords,Driver.sebastianVettel)==aRecord_5secs);
	}
	
	@Test
	public void testLapNotFinished() {
		assertEquals(aRecord_5secs.update(TestLap.unfinishedLap_Nr1_LT0_S11_S24_S3,Driver.sebastianVettel),aRecord_5secs);
		assertTrue(aRecord_5secs.update(TestLap.unfinishedLap_Nr1_LT0_S11_S24_S3,Driver.sebastianVettel)==aRecord_5secs);
	}

	@Test
	public void testLapIsFaster() {
		assertEquals(aRecord_12secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords,Driver.sebastianVettel).getLapTime(),Duration.ofMillis(10000));
		assertTrue(aRecord_12secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords,Driver.sebastianVettel)!=aRecord_12secs);
	}

	
}
