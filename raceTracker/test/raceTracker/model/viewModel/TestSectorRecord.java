package raceTracker.model.viewModel;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Sector;
import raceTracker.model.viewModel.SectorRecord;

public class TestSectorRecord {

	public static final SectorRecord aRecord_0_8secs = new SectorRecord(Sector.sector1, Duration.ofMillis(800),
			Driver.sebastianVettel, 1);
	public static final SectorRecord aRecord_5secs = new SectorRecord(Sector.sector1, Duration.ofMillis(5000),
			Driver.sebastianVettel, 1);

	@Test
	public void testUnsetRecord() {
		assertFalse(SectorRecord.unset(Sector.sector1).isSet());
		assertFalse(SectorRecord.unset(Sector.sector1)
				.update(TestLap.unfinishedLap_Nr1_LT0_S10_S20_S30.getS1(), Driver.sebastianVettel, 1).isSet());
	}

	@Test
	public void testSetRecord() {
		assertTrue(aRecord_0_8secs.isSet());
	}

	@Test
	public void testIsSlower() {
		assertEquals(aRecord_0_8secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords.getS1(),
				Driver.sebastianVettel, 1), aRecord_0_8secs);
		assertTrue(aRecord_0_8secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords.getS1(),
				Driver.sebastianVettel, 1) == aRecord_0_8secs);
	}

	@Test
	public void testLapNotFinished() {
		assertNotEquals(
				aRecord_5secs.update(TestLap.unfinishedLap_Nr1_LT0_S11_S24_S3.getS1(), Driver.sebastianVettel, 1),
				aRecord_5secs);
		assertTrue(aRecord_5secs.update(TestLap.unfinishedLap_Nr1_LT0_S11_S24_S3.getS1(), Driver.sebastianVettel, 1)
				.getSecTime() == TestLap.unfinishedLap_Nr1_LT0_S11_S24_S3.getS1());
	}

	@Test
	public void testLapEmptySector() {
		assertEquals(
				aRecord_5secs.update(TestLap.unfinishedLap_Nr1_LT0_S10_S20_S30.getS1(), Driver.sebastianVettel, 1),
				aRecord_5secs);
		assertTrue(aRecord_5secs.update(TestLap.unfinishedLap_Nr1_LT0_S10_S20_S30.getS1(), Driver.sebastianVettel, 1)
				.getSecTime() != TestLap.unfinishedLap_Nr1_LT0_S10_S20_S30.getS1());
	}

	@Test
	public void testLapIsFaster() {
		assertEquals(aRecord_5secs
				.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords.getS1(), Driver.sebastianVettel, 1)
				.getSecTime(), Duration.ofMillis(1000));
		assertTrue(aRecord_5secs.update(TestLap.finishedLap_Nr1_LT10_S11_S24_S35_NoRecords.getS1(),
				Driver.sebastianVettel, 1) != aRecord_5secs);
	}

}
