package raceTrackerGUI;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import raceTracker.model.TrackPositionListener;
import raceTracker.model.UpshiftBeepListener;
import raceTracker.model.enums.Driver;
import raceTracker.model.gameStructs.Session;
import raceTracker.model.viewModel.DriverLapHistory;
import raceTracker.model.viewModel.Lap;
import raceTracker.model.viewModel.LapHistory;
import raceTracker.model.viewModel.RacePosition;
import raceTracker.model.viewModel.RaceStandings;
import raceTracker.model.viewModel.TrackPosition;

public class ViewModel implements UpshiftBeepListener, GearSuggestionListener, SessionListener, RaceStandingsListener,TrackPositionListener {
	private final File[] gearSounds;
	private final File beepFile;

	private StringProperty trackName = new SimpleStringProperty();
	private IntegerProperty suggestedGear = new SimpleIntegerProperty();
	private IntegerProperty totalLaps = new SimpleIntegerProperty();
	private IntegerProperty curLap = new SimpleIntegerProperty();
	private StringProperty lapDescription = new SimpleStringProperty();

	private Optional<Driver> driverIdForLapHistory = Optional.empty();

	public ObservableList<RacePosition> listRacePositions = FXCollections.observableArrayList();
	public ObservableList<Lap> listLapHistory = FXCollections.observableArrayList();
	public ObservableList<TrackPosition> listTrackPositions = FXCollections.observableArrayList();
	
	private RaceStandings raceStandings;
	private DriverLapHistory driverLapHistory;

	private StringProperty lapHistoryDriverName = new SimpleStringProperty();
	private IntegerProperty lapHistoryCurLapNum = new SimpleIntegerProperty();
	private IntegerProperty lapHistoryCurPos = new SimpleIntegerProperty();
	private StringProperty lapHistoryCurStatus = new SimpleStringProperty();

	public ViewModel() throws URISyntaxException {
		beepFile = new File(RaceTrackerConsoleRunner.beepUrl.toURI());
		gearSounds = RaceTrackerConsoleRunner.getGearSounds();
	}

	@Override
	public void updateRaceStandings(RaceStandings curStandings, DriverLapHistory history) {
		raceStandings=curStandings;
		driverLapHistory=history;
		setCurLap(curStandings.getPos().get(0).getCurrentLapNum());
		updateLapDescription(getCurLap(), getTotalLaps());

		this.listRacePositions.clear();
		this.listRacePositions.addAll(curStandings.getPos());
		updateLapHistoryModel(curStandings,history);
	}

	@Override
	public void updateTrackPosition(List<TrackPosition> positions) {
		System.out.println("ViewModel.updateTrackPosition: "+positions);
		listTrackPositions.clear();
		listTrackPositions.addAll(positions);
	}

	private void updateLapHistoryModel(RaceStandings curStandings,DriverLapHistory history) {
		listLapHistory.clear();
		setLapHistoryCurLapNum(0);
		setLapHistoryCurPos(0);
		setLapHistoryCurStatus("");
		setLapHistoryDriverName("");

		if (driverIdForLapHistory.isPresent()) {
			LapHistory lapHistoryCurDriver = history.getHistories().get(driverIdForLapHistory.get());
			RacePosition curRacePositionForDriver = curStandings.getPositionForDriver(driverIdForLapHistory.get());
			listLapHistory.addAll(lapHistoryCurDriver.getLaps());
			setLapHistoryCurLapNum(lapHistoryCurDriver.getCurrentLapNum());
			setLapHistoryCurPos(curRacePositionForDriver.getCurPosition());
			setLapHistoryCurStatus(curRacePositionForDriver.getResultStatusShort());
			driverIdForLapHistory.map(d -> d.name()).ifPresent(this::setLapHistoryDriverName);
		}
	}

	private void updateLapDescription(int curLap, int totalLaps) {
		if (curLap <= totalLaps)
			lapDescription.set(String.format("Lap %02d / %02d", curLap, totalLaps));
		else
			lapDescription.set("Finished");
	}

	@Override
	public void updateSessionData(Session newSessionData) {
		setTrackName(newSessionData.getTrackId().getTrackName());
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
		return this.listRacePositions;
	}

	public ObservableList<Lap> getLapHistory() {
		return listLapHistory;
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

	public int getCurLap() {
		return curLap.get();
	}

	public void setCurLap(int curLap) {
		this.curLap.set(curLap);
	}

	public IntegerProperty curLapProperty() {
		return curLap;
	}

	public StringProperty lapDescriptionProperty() {
		return lapDescription;
	}

	public void clearDriverForLapHistory() {
		driverIdForLapHistory = Optional.empty();
	}

	public void setDriverForLapHistory(Driver driverId) {
		driverIdForLapHistory = Optional.ofNullable(driverId);
		updateLapHistoryModel(raceStandings, driverLapHistory);
	}

	public Optional<Driver> getDriverForLapHistory() {
		return driverIdForLapHistory;
	}

	public void setLapHistoryDriverName(String driverName) {
		lapHistoryDriverName.set(driverName);
	}

	public StringProperty lapHistoryDriverNameProperty() {
		return lapHistoryDriverName;
	}

	public void setLapHistoryCurStatus(String status) {
		lapHistoryCurStatus.set("");
	}

	public StringProperty lapHistoryCurStatusProperty() {
		return lapHistoryCurStatus;
	}

	public void setLapHistoryCurLapNum(int curLapNum) {
		lapHistoryCurLapNum.set(curLapNum);
	}
	
	public IntegerProperty lapHistoryCurLapNumProperty() {
		return lapHistoryCurLapNum;
	}

	public void setLapHistoryCurPos(int curPos) {
		lapHistoryCurPos.set(curPos);
	}

	
	public IntegerProperty lapHistoryCurPosProperty() {
		return lapHistoryCurPos;
	}

	
}
