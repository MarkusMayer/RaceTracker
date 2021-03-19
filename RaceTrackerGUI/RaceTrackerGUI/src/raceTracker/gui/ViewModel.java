package raceTracker.gui;

import java.io.File;
import java.net.URISyntaxException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import raceTracker.RaceTrackerConsoleRunner;
import raceTracker.model.GearSuggestionListener;
import raceTracker.model.RaceStandingsListener;
import raceTracker.model.SessionListener;
import raceTracker.model.UpshiftBeepListener;
import raceTracker.model.gameStructs.Session;
import raceTracker.model.viewModel.RacePosition;
import raceTracker.model.viewModel.RaceStandings;

public class ViewModel implements UpshiftBeepListener, GearSuggestionListener, SessionListener, RaceStandingsListener {
	private final File[] gearSounds;
	private final File beepFile;
	
	private StringProperty trackName=new SimpleStringProperty();
	
	private IntegerProperty suggestedGear=new SimpleIntegerProperty();
	
	private IntegerProperty totalLaps=new SimpleIntegerProperty();
	
	private IntegerProperty curLap=new SimpleIntegerProperty();
	
	public ObservableList<RacePosition> curStandings=FXCollections.observableArrayList();
	
	
	public ViewModel() throws URISyntaxException {
		beepFile=new File(RaceTrackerConsoleRunner.beepUrl.toURI());
		gearSounds=RaceTrackerConsoleRunner.getGearSounds();
	}
	
	@Override
	public void updateRaceStandings(RaceStandings curStandings) {
		setCurLap(curStandings.getPos().get(0).getCurrentLapNum());
		
		this.curStandings.clear();
		this.curStandings.addAll(curStandings.getPos());
//		controller.curStandings.getItems().clear();
//		controller.curStandings.getItems().addAll(curStandings.getPos());
//		System.out.println(controller.curStandings.getItems().size());
	}

	@Override
	public void updateSessionData(Session newSessionData) {
		setTrackName(newSessionData.getTrackId().toString());
		setTotalLaps(newSessionData.getTotalLaps());
	}

	@Override
	public void suggestGear(int suggestedGear) {
		setSuggestedGear(suggestedGear);
		
		System.out.println("Suggested Gear update!!!!!!!!!!!");
		RaceTrackerConsoleRunner.playClip(gearSounds[suggestedGear]);
	}

	@Override
	public void doUpshiftBeep() {
		RaceTrackerConsoleRunner.playClip(beepFile);
	}


	public ObservableList<RacePosition> getCurStandings() {
		return this.curStandings;
	}
	
	public String getTrackName() {
		return trackName.get();
	}

	public void setTrackName(String trackName) {
		this.trackName.set(trackName);
	}
	
	public StringProperty trackNameProperty() {
		return trackName;
	}
	
	public int getSuggestedGear() {
		return suggestedGear.get();
	}
	
	public void setSuggestedGear(int suggestedGear) {
		this.suggestedGear.set(suggestedGear);
	}
	
	public IntegerProperty suggestedGearProperty() {
		return suggestedGear;
	}
	
	public int getTotalLaps() {
		return totalLaps.get();
	}
	
	public void setTotalLaps(int totalLaps) {
		this.totalLaps.set(totalLaps);
	}
	
	public IntegerProperty totalLapsProperty() {
		return totalLaps;
	}
	
	public int getCurLaps() {
		return totalLaps.get();
	}
	
	public void setCurLap(int curLap) {
		this.curLap.set(curLap);
	}
	
	public IntegerProperty curLapProperty() {
		return curLap;
	}
	
}
