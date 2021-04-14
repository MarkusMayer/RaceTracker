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
	public TableColumn<Lap, Integer> colLapTime;

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

	public void setModel(ViewModel model,MainController mc) {
		vModel = model;
		this.mc=mc;

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

		btnShowMain.setOnAction(event -> {mc.showStandings(event);});
	}
	

}