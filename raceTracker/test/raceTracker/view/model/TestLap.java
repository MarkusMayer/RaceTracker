package raceTracker.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import raceTracker.model.enums.CarController;
import raceTracker.model.enums.Driver;
import raceTracker.model.enums.DriverStatus;
import raceTracker.model.enums.PitStatus;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.enums.Sector;
import raceTracker.model.enums.Team;
import raceTracker.model.enums.TelemetryVisibility;
import raceTracker.model.gameStructs.Participant;
import raceTracker.model.viewModel.Lap;
import raceTracker.model.viewModel.LapRecord;
import raceTracker.model.viewModel.RacePosition;
import raceTracker.model.viewModel.SectorRecord;

public class TestLap {
	public static final Lap unfinishedLap_Nr1_LT0_S10_S20_S30=new Lap(1,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,false);
	public static final Lap unfinishedLap_Nr1_LT0_S11_S24_S3=new Lap(1,Duration.ZERO,Duration.ofMillis(1000),Duration.ofMillis(4000),Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,false);
	public static final Lap finishedLap_Nr1_LT10_S11_S24_S35_NoRecords=new Lap(1,Duration.ofMillis(10000),Duration.ofMillis(1000),Duration.ofMillis(4000),Duration.ofMillis(5000),Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,true);
	public static final Lap finishedLap_Nr1_LT10_S11_S24_S35_Records_9_1_4_5=new Lap(1,Duration.ofMillis(10000),Duration.ofMillis(1000),Duration.ofMillis(4000),Duration.ofMillis(5000),Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,Duration.ZERO,true);
	
	public static final Participant participantSeb=new Participant(CarController.human,Driver.sebastianVettel,Team.ferrari,TelemetryVisibility.publicVisibility,0,0,"SEB");
	public static final Participant participantHam=new Participant(CarController.human,Driver.lewisHamilton,Team.mercedes,TelemetryVisibility.publicVisibility,0,0,"HAM");
	
	public static final raceTracker.model.gameStructs.Lap structLap_Lap1_LastLap0_CurL5_S12Rest0=new raceTracker.model.gameStructs.Lap(0,5,0,0,0,0,2000,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,PitStatus.none,Sector.sector1,DriverStatus.onTrack,ResultStatus.active);
	public static final raceTracker.model.gameStructs.Lap structLap_Lap1_LastLap0_CurL5_S12_S23_Rest0=new raceTracker.model.gameStructs.Lap(0,5,0,0,0,0,2000,3000,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,PitStatus.none,Sector.sector1,DriverStatus.onTrack,ResultStatus.active);
	public static final raceTracker.model.gameStructs.Lap structLap_Lap2_LastLap10_CurL3_Rest0=new raceTracker.model.gameStructs.Lap(10,3,0,0,0,0,2000,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,PitStatus.none,Sector.sector1,DriverStatus.onTrack,ResultStatus.active);

	public static final raceTracker.model.gameStructs.Lap structLap_Lap1_LastLap0_CurL5_S12Rest0_s2fast=new raceTracker.model.gameStructs.Lap(0,5,0,0,0,0,2100,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,PitStatus.none,Sector.sector1,DriverStatus.onTrack,ResultStatus.active);
	public static final raceTracker.model.gameStructs.Lap structLap_Lap1_LastLap0_CurL5_S12_S23_Rest0_s2fast=new raceTracker.model.gameStructs.Lap(0,5,0,0,0,0,2100,2900,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,PitStatus.none,Sector.sector1,DriverStatus.onTrack,ResultStatus.active);
	public static final raceTracker.model.gameStructs.Lap structLap_Lap2_LastLap10_CurL3_Rest0_s2fast=new raceTracker.model.gameStructs.Lap(10.1f,3,0,0,0,0,2000,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,PitStatus.none,Sector.sector1,DriverStatus.onTrack,ResultStatus.active);
	
	public static final LapRecord lr_8500=new LapRecord(Duration.ofMillis(8500), Driver.valtteriBottas, 2);
	public static final SectorRecord s1_900=new SectorRecord(Sector.sector1,Duration.ofMillis(900), Driver.valtteriBottas,  1);
	public static final SectorRecord s2_3900=new SectorRecord(Sector.sector2,Duration.ofMillis(3900), Driver.valtteriBottas,  1);
	public static final SectorRecord s3_6000=new SectorRecord(Sector.sector3,Duration.ofMillis(6000), Driver.valtteriBottas,  1);
	
	@Test
	public void testFinalizeLap() {
		Lap l=unfinishedLap_Nr1_LT0_S11_S24_S3.finalizeLap(new RacePosition(structLap_Lap2_LastLap10_CurL3_Rest0,participantSeb));
		assertEquals(l.getS3(), Duration.ofMillis(5000));
		assertEquals(l.getLapTime(), Duration.ofMillis(10000));
		assertEquals(l.isFinished(), true);
	}
	
	@Test
	public void testUpdateRecords() {
		Lap l=finishedLap_Nr1_LT10_S11_S24_S35_NoRecords.updateRecords(lr_8500, s1_900, s2_3900, s3_6000);
		assertEquals(l.getDeltaLapBest(),Duration.ofMillis(1500));
		assertEquals(l.getDeltaS1Best(),Duration.ofMillis(100));
		assertEquals(l.getDeltaS2Best(),Duration.ofMillis(100));
		assertEquals(l.getDeltaS3Best(),finishedLap_Nr1_LT10_S11_S24_S35_NoRecords.getDeltaS3Best());
	}
}
