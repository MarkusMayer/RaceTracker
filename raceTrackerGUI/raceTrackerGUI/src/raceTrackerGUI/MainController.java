package raceTrackerGUI;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import raceTracker.model.enums.Driver;
import raceTracker.model.viewModel.RacePosition;

public class MainController {

	private ViewModel vModel;

	@FXML
	public Label lblBestLap;
	
	@FXML
	private Label lblTrackName;

	@FXML
	private Label lblSuggestedGear;

	@FXML
	private Label lblLapDescription;

	@FXML
	public TableView<RacePosition> tblCurStandings;

	@FXML
	public TableColumn<RacePosition, Integer> colRacePos;

	@FXML
	public TableColumn<RacePosition, String> colDriverName;

	@FXML
	public TableColumn<RacePosition, String> colS1;

	@FXML
	public TableColumn<RacePosition, String> colS2;

	@FXML
	public TableColumn<RacePosition, String> colS3;

	@FXML
	public TableColumn<RacePosition, Integer> colDeltaPos;

	@FXML
	public TableColumn<RacePosition, String> colLapDescription;

	@FXML
	public TableColumn<RacePosition, String> colPitStatus;

	@FXML
	public TableColumn<RacePosition, String> colPenalties;

	@FXML
	public TableColumn<RacePosition, String> colResultStatus;

	@FXML
	public Pane paneMain;
	
	@FXML
	public Button btnShowStandings;
	
	@FXML
	public Button btnShowTrackPosition;
	
	public LapHistoryController lhc;
	public TrackPositionController tpc;

	public void initialize() {
	}

	public void setModel(ViewModel model,LapHistoryController lhc,TrackPositionController tpc) {
		vModel = model;
		this.lhc=lhc;
		this.tpc=tpc;
		
		vModel.trackProperty().addListener(
				(observable, oldValue, newValue) -> Platform.runLater(() -> lblTrackName.setText(newValue.getTrackName())));
		vModel.lapHistoryLapRecordProperty().addListener(
				(observable, oldValue, newValue) -> Platform.runLater(() -> lblBestLap.setText(newValue.getLapRecordDescription())));
		vModel.suggestedGearProperty().addListener((observable, oldValue, newValue) -> Platform
				.runLater(() -> lblSuggestedGear.setText(newValue.toString())));
		vModel.lapDescriptionProperty().addListener((observable, oldValue, newValue) -> Platform
				.runLater(() -> lblLapDescription.setText(newValue.toString())));

		tblCurStandings.setItems(vModel.getCurStandings());
		colRacePos.setCellValueFactory(new PropertyValueFactory<>("curPosition"));
		colDeltaPos.setCellValueFactory(new PropertyValueFactory<>("deltaPosition"));
		colResultStatus.setCellValueFactory(new PropertyValueFactory<>("resultStatusShort"));
		colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
		colLapDescription.setCellValueFactory(new PropertyValueFactory<>("lapDescription"));

		tblCurStandings.setRowFactory(tv -> {
			TableRow<RacePosition> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
					RacePosition clickedRacePos = row.getItem();
					showLapHistory(clickedRacePos.getDriverId());
				}
			});
			return row;
		});

		colS1.setCellValueFactory(new PropertyValueFactory<>("s1Description"));
		colS2.setCellValueFactory(new PropertyValueFactory<>("s2Description"));
		colS3.setCellValueFactory(new PropertyValueFactory<>("s3Description"));

		colPitStatus.setCellValueFactory(new PropertyValueFactory<>("pitStatusDescription"));
		colPenalties.setCellValueFactory(new PropertyValueFactory<>("penaltiesDescription"));
		
		btnShowStandings.setOnAction(event -> {showStandings(event);});
		btnShowTrackPosition.setOnAction(event -> {showTrackPosition(event);});
	}
	
	public void showLapHistory(Driver driverId) {
		vModel.setDriverForLapHistory(driverId);
		paneMain.getChildren().clear();
		paneMain.getChildren().add(lhc.paneLapHistory);
	}
	
	public void showStandings(Event e) {
		vModel.clearDriverForLapHistory();
		paneMain.getChildren().clear();
		paneMain.getChildren().add(tblCurStandings);
	}
	
	public void showTrackPosition(Event e) {
		paneMain.getChildren().clear();
		paneMain.getChildren().add(tpc.paneTrackPosition);
	}

}