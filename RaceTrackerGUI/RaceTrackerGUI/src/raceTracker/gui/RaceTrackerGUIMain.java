package raceTracker.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import raceTracker.model.GearSuggestionListener;
import raceTracker.model.RaceStandingsListener;
import raceTracker.model.RaceTrackerModel;
import raceTracker.model.SessionListener;
import raceTracker.model.UpshiftBeepListener;
import raceTracker.model.gameStructs.Session;
import raceTracker.model.viewModel.RaceStandings;

public class RaceTrackerGUIMain extends Application
		{

	private Controller controller;
	RaceTrackerModel model;
	PortListener listener;
	ViewModel vModel;

	@Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("raceTrackerMainGUI.fxml"));
        Parent root = loader.load();
        controller=(Controller)loader.getController();
        vModel=new ViewModel();
        model=new RaceTrackerModel(vModel, vModel, vModel, vModel);
        controller.setModel(vModel);
        
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        
        listener=new PortListener(model);
        Thread t=new Thread(listener);
        t.start();
    }

	public static void main(String[] args) {
		launch(args);
	}

}
