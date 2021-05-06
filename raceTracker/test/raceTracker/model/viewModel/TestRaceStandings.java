package raceTracker.model.viewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import raceTracker.model.enums.CarController;
import raceTracker.model.enums.Driver;
import raceTracker.model.enums.DriverStatus;
import raceTracker.model.enums.PitStatus;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.enums.Sector;
import raceTracker.model.enums.Team;
import raceTracker.model.enums.TelemetryVisibility;
import raceTracker.model.gameStructs.LapStruct;
import raceTracker.model.gameStructs.Participant;

public class TestRaceStandings {
	private RaceStandings rs;

	private static final List<Participant> drivers = getLiveRaceDrivers();
	private static final List<List<LapStruct>> laps = getLiveRaceLaps();
	public static final int idx_First_Lap_Start = 1;
	public static final int idx_First_Lap_S1_Finished = 508;
	public static final int idx_First_Lap_S2_Finished = 1098;
	public static final int idx_Second_Lap_Start = 1504;
	public static final int idx_Second_Lap_S1_Finished = 1826;
	public static final int idx_Second_Lap_S2_Finished = 2414;
	public static final int idx_Third_Lap_Start = 2810;
	public static final int idx_Fourth_Lap_Start = 4126;
	public static final int idx_Fifth_Lap_Start = 5444;
	public static final int idx_Before_Race_End = 6763;
	public static final int idx_After_Race_End = 6764;
	public static final int idx_End_Recording = 7070;

	public static List<Participant> getLiveRaceDrivers() {
		List<Participant> drivers = new ArrayList<>();
		drivers.add(new Participant(CarController.ai, Driver.lewisHamilton, Team.mercedes,
				TelemetryVisibility.publicVisibility, 1, 1, "lewis"));
		drivers.add(new Participant(CarController.ai, Driver.valtteriBottas, Team.mercedes,
				TelemetryVisibility.publicVisibility, 1, 1, "valteri"));
		drivers.add(new Participant(CarController.ai, Driver.maxVerstappen, Team.redBullRacing,
				TelemetryVisibility.publicVisibility, 1, 1, "max"));
		drivers.add(new Participant(CarController.human, Driver.alexanderAlbon, Team.redBullRacing,
				TelemetryVisibility.publicVisibility, 1, 1, "ale"));
		drivers.add(new Participant(CarController.ai, Driver.charlesLeclerc, Team.ferrari,
				TelemetryVisibility.publicVisibility, 1, 1, "charles"));
		drivers.add(new Participant(CarController.human, Driver.sebastianVettel, Team.ferrari,
				TelemetryVisibility.publicVisibility, 1, 1, "seb"));
		return Collections.unmodifiableList(drivers);
	}

	public static List<List<LapStruct>> getLiveRaceLaps() throws RuntimeException {
		List<List<LapStruct>> laps = new ArrayList<>();
		//TODO: read from classpath
		//InputStream is=TestRaceStandings.class.getClassLoader().getResourceAsStream("/lapData.csv");
		try (BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\markus\\git\\RaceTracker\\raceTracker\\testResources\\lapData.csv")))) {
			br.readLine();
			int i = 0;
			List<LapStruct> readings = new ArrayList<>();
			for (String aLine; (aLine = br.readLine()) != null;) {
				String[] cols = aLine.split(",");
				int driverIdx = Integer.valueOf(cols[0]);
				LapStruct aLap = new LapStruct(Float.valueOf(cols[1]), 0, Float.valueOf(cols[2]),
						Float.valueOf(cols[3]), Float.valueOf(cols[4]), Float.valueOf(cols[5]),
						Integer.valueOf(cols[6]), Integer.valueOf(cols[7]), Integer.valueOf(cols[8]),
						Integer.valueOf(cols[9]), Integer.valueOf(cols[10]), Integer.valueOf(cols[11]),
						Integer.valueOf(cols[12]), Integer.valueOf(cols[13]), Integer.valueOf(cols[14]),
						Integer.valueOf(cols[15]), Integer.valueOf(cols[16]), Integer.valueOf(cols[17]),
						Integer.valueOf(cols[18]), Integer.valueOf(cols[19]), Integer.valueOf(cols[20]),
						Integer.valueOf(cols[21]), Integer.valueOf(cols[22]), PitStatus.valueOf(cols[23]),
						Sector.valueOf(cols[24]), DriverStatus.valueOf(cols[25]), ResultStatus.valueOf(cols[26]));
				readings.add(aLap);
				if (i % 6 == 5) {
					laps.add(Collections.unmodifiableList(readings));
					readings = new ArrayList<>();
				}
				i++;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return Collections.unmodifiableList(laps);
	}

	@Before
	public void setup() {
		rs = new RaceStandings();
	}

	@Test
	public void testRaceStart() {
		rs = new RaceStandings(laps.get(0), drivers, rs);
		assertEquals(1, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS2());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(0, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(0, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(0, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap1FinishSector1() {
		for (List<LapStruct> aReading : laps.subList(0, idx_First_Lap_S1_Finished + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(1, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ofMillis(19377), rs.getPos().get(0).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS2());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(0, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(0, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap1FinishSector2() {
		for (List<LapStruct> aReading : laps.subList(0, idx_First_Lap_S2_Finished + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(1, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ofMillis(19377), rs.getPos().get(0).getS1());
		assertEquals(Duration.ofMillis(30431), rs.getPos().get(0).getS2());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(0, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap2StartLap() {
		for (List<LapStruct> aReading : laps.subList(0, idx_Second_Lap_Start + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(2, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(70341), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS2());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20531), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20531), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap2FinishedSector1() {
		for (List<LapStruct> aReading : laps.subList(0, idx_Second_Lap_S1_Finished + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(2, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(70341), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ofMillis(16984), rs.getPos().get(0).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(1).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS2());
		assertEquals(Duration.ofMillis(-2393), rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20531), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20531), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap2FinishedSector2() {
		for (List<LapStruct> aReading : laps.subList(0, idx_Second_Lap_S2_Finished + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(2, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(70341), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ofMillis(16984), rs.getPos().get(0).getS1());
		assertEquals(Duration.ofMillis(30303), rs.getPos().get(0).getS2());
		assertEquals(Duration.ofMillis(-2393), rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ofMillis(-128), rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20531), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20531), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(1, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap3StartLap() {
		for (List<LapStruct> aReading : laps.subList(0, idx_Third_Lap_Start + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(3, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(67656), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS2());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap5StartLap() {
		for (List<LapStruct> aReading : laps.subList(0, idx_Fifth_Lap_Start + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(5, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(67927), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ofMillis(271), rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS1());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getS2());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ZERO, rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap5BeforeRaceFinish() {
		for (List<LapStruct> aReading : laps.subList(0, idx_Before_Race_End + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(5, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(67927), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ofMillis(271), rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ofMillis(17129), rs.getPos().get(0).getS1());
		assertEquals(Duration.ofMillis(30538), rs.getPos().get(0).getS2());
		assertEquals(Duration.ofMillis(145), rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ofMillis(145), rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ofMillis(235), rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ofMillis(235), rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.active, rs.getPos().get(0).getResultStatus());
	}

	@Test
	public void testLap5AfterRaceFinish() {
		System.out.println("testLap5AfterRaceFinish: " + laps.get(idx_After_Race_End + 1));
		for (List<LapStruct> aReading : laps.subList(0, idx_After_Race_End + 1)) {
			rs = new RaceStandings(aReading, drivers, rs);
		}
		assertEquals(5, rs.getPos().get(0).getCurrentLapNum());
		assertEquals(Duration.ofMillis(68081), rs.getPos().get(0).getLastLapTime());
		assertEquals(Duration.ofMillis(425), rs.getPos().get(0).getDeltaLastBestLap());
		assertEquals(Duration.ofMillis(17129), rs.getPos().get(0).getS1());
		assertEquals(Duration.ofMillis(30538), rs.getPos().get(0).getS2());
		assertEquals(Duration.ofMillis(145), rs.getPos().get(0).getDeltaPersonalS1BestLap());
		assertEquals(Duration.ofMillis(145), rs.getPos().get(0).getDeltaPersonalS1Best());
		assertEquals(Duration.ofMillis(235), rs.getPos().get(0).getDeltaPersonalS2BestLap());
		assertEquals(Duration.ofMillis(235), rs.getPos().get(0).getDeltaPersonalS2Best());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestLapS3Time());
		assertEquals(Duration.ofMillis(20369), rs.getPos().get(0).getPersonalBestS3Time());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS1LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS2LapNum());
		assertEquals(2, rs.getPos().get(0).getPersonalBestS3LapNum());
		assertEquals(ResultStatus.finished, rs.getPos().get(0).getResultStatus());
	}

}
