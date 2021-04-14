package raceTracker.model;


import java.util.ArrayList;
import java.util.List;

import raceTracker.model.enums.CarController;
import raceTracker.model.gameStructs.CarTelemetryData;
import raceTracker.model.gameStructs.Lap;
import raceTracker.model.gameStructs.MotionData;
import raceTracker.model.gameStructs.PacketCarTelemetryData;
import raceTracker.model.gameStructs.PacketMotionData;
import raceTracker.model.gameStructs.PacketParticipants;
import raceTracker.model.gameStructs.Participant;
import raceTracker.model.gameStructs.Session;
import raceTracker.model.viewModel.DriverLapHistory;
import raceTracker.model.viewModel.RaceStandings;
import raceTracker.model.viewModel.TrackPosition;

public class RaceTrackerModel {

	private static final int UPSHIFT_BEEP_RPM_THRESHOLD = 11600;
	
	private int numActiveCars, idxPlayerCar,prevRPM, prevSuggestedGear;

	private final GearSuggestionListener gearSuggestionListener;
	private final UpshiftBeepListener beepListener;
	private final SessionListener sessionListener;
	private final RaceStandingsListener raceStandingsListener;
	private final TrackPositionListener trackPositionListener;
	

	private Session curSession;
	private RaceStandings curStandings;
	private DriverLapHistory driverLapHistory;
	
	private List<Participant> curParticipants;
	private List<TrackPosition> curTrackPositions;
	
	private float maxPosX=-99999,minPosX=99999,maxPosY=-99999,minPosY=99999;
	
	
	public RaceTrackerModel(UpshiftBeepListener beepListener, GearSuggestionListener gearSuggestionListener,SessionListener sessionListener,RaceStandingsListener raceStandingsListener, TrackPositionListener trackPositionListener) {
		this.beepListener=beepListener;
		this.gearSuggestionListener=gearSuggestionListener;
		this.sessionListener=sessionListener;
		this.raceStandingsListener=raceStandingsListener;
		this.trackPositionListener=trackPositionListener;
		this.driverLapHistory=new DriverLapHistory();
	}
	
	public void receiceSessionData(Session newSessionData) {
		if (curSession ==null || !curSession.equals(newSessionData)) {
			curSession=newSessionData;
			sessionListener.updateSessionData(newSessionData);
		}
	}
	
	public void receiceLapData(List<Lap> laps) {
		RaceStandings newStandings=new RaceStandings(laps, curParticipants,curStandings);
		if (curStandings==null || !curStandings.equals(newStandings)) {
			curStandings=newStandings;
			driverLapHistory.addLaps(curStandings.getPos());
			raceStandingsListener.updateRaceStandings(newStandings,driverLapHistory);
		}
		

	}
	
	public void receiveTelemetryUpdate(PacketCarTelemetryData telemetryData) {
		CarTelemetryData playerTelemetry = telemetryData.getTelemetries().get(idxPlayerCar);
		int curRPM = playerTelemetry.getEngineRPM();
		if (prevRPM < UPSHIFT_BEEP_RPM_THRESHOLD && curRPM >= UPSHIFT_BEEP_RPM_THRESHOLD && playerTelemetry.getThrottle()>0.6) {
			beepListener.doUpshiftBeep();
		} 
			
		prevRPM=curRPM;
		
		int curSuggestedGear = telemetryData.getSuggestedGear();
//		System.out.println(
//				"prevSuggestedGear: " + prevSuggestedGear + ", curSuggestedGear: " + curSuggestedGear);
		if (prevSuggestedGear != curSuggestedGear && curSuggestedGear > 0) {
			gearSuggestionListener.suggestGear(curSuggestedGear);
		}
		prevSuggestedGear = curSuggestedGear;
	}
	
	public void receiveParticipants(PacketParticipants participants) {
		numActiveCars=participants.getNumActiveCars();
		curParticipants=participants.getParticipants();

		/*if (idxPlayerCar == -1)*/ {
			int idx = 0;
			for (Participant aParticipant : participants.getParticipants()) {
				if (aParticipant.getAiControlled() == CarController.human) {
					//TODO: Only parse Cars up to session number of cars in telemetry, lapData, participants,....
					idxPlayerCar = idx;
					System.out.println("Player found at pos: " + idx);
					break;
				}
				idx++;
			}
		}
		
	}
	
	public void receiveTrackPositions(PacketMotionData positionsStruct) {
		List<TrackPosition> newPositions=new ArrayList<>();
		
		for (int i=0; i< positionsStruct.getMotions().size();i++) {
			MotionData curMotion=positionsStruct.getMotions().get(i);
			TrackPosition newPos=new TrackPosition(curParticipants.get(i).getDriverId(), curMotion.getWorldPositionX(), curMotion.getWorldPositionY());
			maxPosX=Math.max(maxPosX, newPos.getPosX());
			minPosX=Math.min(minPosX, newPos.getPosX());
			maxPosY=Math.max(maxPosY, newPos.getPosY());
			minPosY=Math.min(minPosY, newPos.getPosY());
			System.out.println("maxPosX: "+ maxPosX+", minPosX: "+minPosX+", maxPosY: "+maxPosY+", minPosY: "+minPosY);
			
			newPositions.add(newPos);
		}
		
		curTrackPositions=newPositions;
		trackPositionListener.updateTrackPosition(curTrackPositions);
	}

	public int getNumActiveCars() {
		return numActiveCars;
	}
	
}
