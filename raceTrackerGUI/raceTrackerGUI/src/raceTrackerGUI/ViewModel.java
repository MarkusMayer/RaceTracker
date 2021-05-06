package raceTrackerGUI;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import raceTracker.RaceTrackerConsoleRunner;
import raceTracker.model.GearSuggestionListener;
import raceTracker.model.RaceStandingsListener;
import raceTracker.model.SessionListener;
import raceTracker.model.TrackPositionListener;
import raceTracker.model.UpshiftBeepListener;
import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Track;
import raceTracker.model.gameStructs.SessionStruct;
import raceTracker.model.viewModel.DriverLapHistory;
import raceTracker.model.viewModel.Lap;
import raceTracker.model.viewModel.LapHistory;
import raceTracker.model.viewModel.LapRecord;
import raceTracker.model.viewModel.RacePosition;
import raceTracker.model.viewModel.RaceStandings;
import raceTracker.model.viewModel.TrackPosition;

public class ViewModel implements UpshiftBeepListener, GearSuggestionListener, SessionListener, RaceStandingsListener,
		TrackPositionListener {
	private final File[] gearSounds;
	private final File beepFile;

	private ObjectProperty<Track> track = new SimpleObjectProperty<>();
	private ObjectProperty<LapRecord> lapHistoryLapRecord= new SimpleObjectProperty<>();
	private IntegerProperty suggestedGear = new SimpleIntegerProperty();
	private IntegerProperty totalLaps = new SimpleIntegerProperty();
	private IntegerProperty curLap = new SimpleIntegerProperty();
	private StringProperty lapDescription = new SimpleStringProperty();

	private Optional<Driver> driverIdForLapHistory = Optional.empty();

	public ImmutableObservableList<RacePosition> listRacePositions = new ImmutableObservableList<>(Collections.unmodifiableList(new ArrayList<>()));
	public ImmutableObservableList<Lap> listLapHistory = new ImmutableObservableList<>(Collections.unmodifiableList(new ArrayList<>()));
	public ImmutableObservableList<TrackPosition> listTrackPositions = new ImmutableObservableList<>(Collections.unmodifiableList(new ArrayList<>()));
	public ObservableList<TrackPosTableEntry> listTrackPosTableEntries = FXCollections.observableArrayList();
	
	
//			Comparator.nullsFirst(Comparator.comparing(TrackPosTableEntry::getCurPos)));

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
//		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())
//				+ " - ViewModel.updateRaceStandings: " + curStandings + history);
		raceStandings = curStandings;
		driverLapHistory = history;
		setCurLap(curStandings.getPos().get(0).getCurrentLapNum());
		updateLapDescription(getCurLap(), getTotalLaps());

		listRacePositions.setBackingList(curStandings.getPos());
//		this.listRacePositions.clear();
//		this.listRacePositions.addAll(curStandings.getPos());
		updateLapHistoryModel(curStandings, history);
	}

	@Override
	public void updateTrackPosition(List<TrackPosition> positions) {
//		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())
//				+ " - ViewModel.updateTrackPosition: " + positions);
		List<TrackPosTableEntry> newEntries=new ArrayList<>();
		for (TrackPosition aPos : positions) {
			newEntries.add(new TrackPosTableEntry(TrackPositionController.colors[aPos.getIdx()],
					aPos.getRacePos(), aPos.getDriverId(), aPos.getDriverId().toString()));
		}
		newEntries.sort(Comparator.nullsFirst(Comparator.comparing(TrackPosTableEntry::getCurPos)));
		listTrackPosTableEntries.clear();
		listTrackPosTableEntries.addAll(newEntries);

		listTrackPositions.setBackingList(positions);
//		listTrackPositions.clear();
//		listTrackPositions.addAll(positions);
	}

	private void updateLapHistoryModel(RaceStandings curStandings, DriverLapHistory history) {
//		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())
//				+ " - ViewModel.updateLapHistory: " + curStandings + history);
		if (driverIdForLapHistory.isPresent()) {
			LapHistory lapHistoryCurDriver = history.getHistoryForDriver(driverIdForLapHistory.get());
			RacePosition curRacePositionForDriver = curStandings.getPositionForDriver(driverIdForLapHistory.get());
			listLapHistory.setBackingList(lapHistoryCurDriver.getLaps());
			setLapHistoryCurLapNum(lapHistoryCurDriver.getCurrentLapNum());
			setLapHistoryCurPos(curRacePositionForDriver.getCurPosition());
			setLapHistoryCurStatus(curRacePositionForDriver.getResultStatusShort());
			driverIdForLapHistory.map(d -> d.name()).ifPresent(this::setLapHistoryDriverName);
		}
		setLapHistoryLapRecord(driverLapHistory.getBestLap());
	}

	private void updateLapDescription(int curLap, int totalLaps) {
		if (curLap <= totalLaps)
			lapDescription.set(String.format("Lap %02d / %02d", curLap, totalLaps));
		else
			lapDescription.set("Finished");
	}

	@Override
	public void updateSessionData(SessionStruct newSessionData) {
		setTrackName(newSessionData.getTrackId());
		setTotalLaps(newSessionData.getTotalLaps());
	}

	@Override
	public void suggestGear(int suggestedGear) {
		setSuggestedGear(suggestedGear);

		System.out.println("Suggested Gear update!!!!!!!!!!!");
//		RaceTrackerConsoleRunner.playClip(gearSounds[suggestedGear]);
		Media sound = new Media(gearSounds[suggestedGear].toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}

	@Override
	public void doUpshiftBeep() {
//		RaceTrackerConsoleRunner.playClip(beepFile);
		Media sound = new Media(beepFile.toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}

	public ObservableList<RacePosition> getCurStandings() {
		return this.listRacePositions;
	}

	public ObservableList<Lap> getLapHistory() {
		return listLapHistory;
	}

	public ObservableList<TrackPosTableEntry> getTrackPosTableEntries() {
		return listTrackPosTableEntries;
	}

	public String getTrackName() {
		return track.get().getTrackName();
	}

	public void setTrackName(Track track) {
		this.track.set(track);
	}

	public ObjectProperty<Track> trackProperty() {
		return track;
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
	
	public String getLapHistoryCurStatus() {
		return lapHistoryCurStatusProperty().get();
	}

	public void setLapHistoryCurStatus(String status) {
		lapHistoryCurStatus.set(status);
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

	public LapRecord getLapHistoryLapRecord() {
		return lapHistoryLapRecord.get();
	}

	
	public void setLapHistoryLapRecord(LapRecord aLapRecord) {
		lapHistoryLapRecord.set(aLapRecord);
	}

	public ObjectProperty<LapRecord> lapHistoryLapRecordProperty() {
		return lapHistoryLapRecord;
	}	

}
