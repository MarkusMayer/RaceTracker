package raceTracker.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import raceTracker.model.viewModel.RacePosition;

public class Controller {
	
	private ViewModel vModel;

    @FXML
    private Label lblTrackName;
    
    @FXML
    private Label lblSuggestedGear;
    
    @FXML
    private Label lblCurLap;
    
    @FXML
    private Label lblTotalLaps;
    
    @FXML
    public TableView<RacePosition> curStandings;
    
    @FXML
    public TableColumn<RacePosition, Integer> colRacePos;
    
    @FXML
    public TableColumn<RacePosition, String> colDriverName;

    @FXML
    public TableColumn<RacePosition, String> colSector1;

    @FXML
    public TableColumn<RacePosition, String> colSector2;
    
    @FXML
    public TableColumn<RacePosition, Integer> colDeltaPos;
    
    @FXML
    public TableColumn<RacePosition, Float> colLastLapTime;
    
    @FXML
    public TableColumn<RacePosition, Float> colBestLapTime;
    
    @FXML
    public TableColumn<RacePosition, Float> colDeltaLastBest;



    
    public void initialize() {
    }
    
    public void setModel(ViewModel model) {
    	vModel=model;
    	vModel.trackNameProperty().addListener((observable,oldValue,newValue) -> Platform.runLater(() -> lblTrackName.setText(newValue)));
    	vModel.suggestedGearProperty().addListener((observable,oldValue,newValue) -> Platform.runLater(() -> lblSuggestedGear.setText(newValue.toString())));
    	vModel.totalLapsProperty().addListener((observable,oldValue,newValue) -> Platform.runLater(() -> lblTotalLaps.setText(newValue.toString())));
    	vModel.curLapProperty().addListener((observable,oldValue,newValue) -> Platform.runLater(() -> lblCurLap.setText(newValue.toString())));

    	curStandings.setItems(vModel.getCurStandings());
    	colRacePos.setCellValueFactory(new PropertyValueFactory<>("curPosition"));
    	colDeltaPos.setCellValueFactory(new PropertyValueFactory<>("deltaPosition"));
    	colDriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
    	colLastLapTime.setCellValueFactory(new PropertyValueFactory<>("lastLapTime"));
    	colBestLapTime.setCellValueFactory(new PropertyValueFactory<>("bestLapTime"));
    	colDeltaLastBest.setCellValueFactory(new PropertyValueFactory<>("deltaLastBest"));
    	
    	
    	colSector1.setCellValueFactory(new PropertyValueFactory<>("sector1"));
    	colSector2.setCellValueFactory(new PropertyValueFactory<>("sector2"));
    }

}