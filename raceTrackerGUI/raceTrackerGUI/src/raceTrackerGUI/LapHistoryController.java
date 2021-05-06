package raceTrackerGUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import raceTracker.model.viewModel.Lap;
import raceTracker.model.viewModel.LapRecord;

public class LapHistoryController {

	private ViewModel vModel;

	@FXML
	public Label lblDriverName;

	@FXML
	public Label lblDriverPos;

	@FXML
	public Label lblLapNum;

	@FXML
	public Label lblDriverStatus;

	@FXML
	public TableView<Lap> tblLapHistory;

	@FXML
	public TableColumn<Lap, Integer> colLapNum;

	@FXML
	public TableColumn<Lap, String> colLapTime;

	@FXML
	public TableColumn<Lap, String> colS1;

	@FXML
	public TableColumn<Lap, String> colS2;

	@FXML
	public TableColumn<Lap, String> colS3;

	@FXML
	public Pane paneLapHistory;

	@FXML
	public Button btnShowMain;

	private MainController mc;

	public void initialize() {
	}

	public void setModel(ViewModel model, MainController mc) {
		vModel = model;
		this.mc = mc;

		vModel.lapHistoryDriverNameProperty().addListener(
				(observable, oldValue, newValue) -> Platform.runLater(() -> lblDriverName.setText(newValue)));

		vModel.lapHistoryCurPosProperty().addListener(
				(observable, oldValue, newValue) -> Platform.runLater(() -> lblDriverPos.setText(newValue.toString())));

		vModel.lapHistoryCurLapNumProperty().addListener(
				(observable, oldValue, newValue) -> Platform.runLater(() -> lblLapNum.setText(newValue.toString())));

		vModel.lapHistoryCurStatusProperty().addListener(
				(observable, oldValue, newValue) -> Platform.runLater(() -> lblDriverStatus.setText(newValue)));

		tblLapHistory.setItems(vModel.getLapHistory());
		colLapNum.setCellValueFactory(new PropertyValueFactory<>("lapNum"));
		colLapTime.setCellValueFactory(new PropertyValueFactory<>("lapTimeDescription"));
		colS1.setCellValueFactory(new PropertyValueFactory<>("s1Description"));
		colS2.setCellValueFactory(new PropertyValueFactory<>("s2Description"));
		colS3.setCellValueFactory(new PropertyValueFactory<>("s3Description"));

		colLapTime.setCellFactory(
				call -> new LapHistoryTimeCell<Lap, String>(lap -> lap.isOverallBest(), lap -> lap.isPersonalBest()));
		colS1.setCellFactory(call -> new LapHistoryTimeCell<Lap, String>(lap -> lap.isOverallBestS1(),
				lap -> lap.isPersonalBestS1()));
		colS2.setCellFactory(call -> new LapHistoryTimeCell<Lap, String>(lap -> lap.isOverallBestS2(),
				lap -> lap.isPersonalBestS2()));
		colS3.setCellFactory(call -> new LapHistoryTimeCell<Lap, String>(lap -> lap.isOverallBestS3(),
				lap -> lap.isPersonalBestS3()));

//		tblLapHistory.setRowFactory(row -> new TableRow<Lap>() {
//
//			@Override
//			public void updateItem(Lap lap, boolean empty) {
//				super.updateItem(lap, empty);
//
//				if (lap == null) {
//					for (int i = 0; i < getChildren().size(); i++) {
//						getChildren().get(i).setStyle("");
//					}
//				} else {
//					System.out.println("getChildren().size(): " + getChildren().size());
//					if (getChildren().size() > 0) {
//						if (lap.isOverallBest()) {
//							getChildren().get(1)
//									.setStyle("-fx-background-color: "
//											+ TrackPositionController.colorToHex(Color.PURPLE) + "; -fx-text-fill: "
//											+ TrackPositionController.colorToHex(Color.WHITE));
//						} else if (lap.isPersonalBest()) {
//							getChildren().get(1).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.GREEN));
//						} else {
//							getChildren().get(1).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.WHITE));
//						}
//						if (lap.isOverallBestS1()) {
//							getChildren().get(2)
//									.setStyle("-fx-background-color: "
//											+ TrackPositionController.colorToHex(Color.PURPLE) + "; -fx-text-fill: "
//											+ TrackPositionController.colorToHex(Color.WHITE));
//						} else if (lap.isPersonalBestS1()) {
//							getChildren().get(2).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.GREEN));
//						} else {
//							getChildren().get(2).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.WHITE));
//						}
//						if (lap.isOverallBestS2()) {
//							getChildren().get(3)
//									.setStyle("-fx-background-color: "
//											+ TrackPositionController.colorToHex(Color.PURPLE) + "; -fx-text-fill: "
//											+ TrackPositionController.colorToHex(Color.WHITE));
//						} else if (lap.isPersonalBestS2()) {
//							getChildren().get(3).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.GREEN));
//						} else {
//							getChildren().get(3).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.WHITE));
//						}
//						if (lap.isOverallBestS3()) {
//							getChildren().get(4)
//									.setStyle("-fx-background-color: "
//											+ TrackPositionController.colorToHex(Color.PURPLE) + "; -fx-text-fill: "
//											+ TrackPositionController.colorToHex(Color.WHITE));
//						} else if (lap.isPersonalBestS3()) {
//							getChildren().get(4).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.GREEN));
//						} else {
//							getChildren().get(4).setStyle(
//									"-fx-background-color: " + TrackPositionController.colorToHex(Color.WHITE));
//						}
//						
//					}
//				}
//			}
//
//		});

		btnShowMain.setOnAction(event ->

		{
			mc.showStandings(event);
		});
	}

}