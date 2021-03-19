package raceTracker.model;

import java.util.List;

import raceTracker.model.enums.CarController;
import raceTracker.model.gameStructs.CarTelemetryData;
import raceTracker.model.gameStructs.Lap;
import raceTracker.model.gameStructs.PacketCarTelemetryData;
import raceTracker.model.gameStructs.PacketParticipants;
import raceTracker.model.gameStructs.Participant;
import raceTracker.model.gameStructs.Session;
import raceTracker.model.viewModel.RaceStandings;

public class RaceTrackerModel {

	private static final int UPSHIFT_BEEP_RPM_THRESHOLD = 11600;
	
	private int numActiveCars, idxPlayerCar,prevRPM, prevSuggestedGear;

	private final GearSuggestionListener gearSuggestionListener;
	private final UpshiftBeepListener beepListener;
	private final SessionListener sessionListener;
	private final RaceStandingsListener raceStandingsListener;
	

	private Session curSession;
	private RaceStandings curStandings;
	private List<Participant> curParticipants;
	
	
	public RaceTrackerModel(UpshiftBeepListener beepListener, GearSuggestionListener gearSuggestionListener,SessionListener sessionListener,RaceStandingsListener raceStandingsListener) {
		this.beepListener=beepListener;
		this.gearSuggestionListener=gearSuggestionListener;
		this.sessionListener=sessionListener;
		this.raceStandingsListener=raceStandingsListener;
	}
	
	public void receiceSessionData(Session newSessionData) {
		if (curSession ==null || !curSession.equals(newSessionData)) {
			curSession=newSessionData;
			sessionListener.updateSessionData(newSessionData);
		}
	}
	
	public void receiceLapData(List<Lap> laps) {
		RaceStandings newStandings=new RaceStandings(laps, curParticipants);
		if (curStandings==null || !curStandings.equals(newStandings)) {
			
			raceStandingsListener.updateRaceStandings(newStandings);
			curStandings=newStandings;
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

	public int getNumActiveCars() {
		return numActiveCars;
	}
	
	

}
