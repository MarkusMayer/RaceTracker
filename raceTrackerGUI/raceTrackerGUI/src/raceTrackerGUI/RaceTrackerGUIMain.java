package raceTrackerGUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import raceTracker.model.RaceTrackerModel;

public class RaceTrackerGUIMain extends Application {

	MainController controllerMainGUI;
	LapHistoryController controllerLH;
	TrackPositionController controllerTP;
	RaceTrackerModel model;
	PortListener listener;
	ViewModel vModel;
	Thread t;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/raceTrackerGUI/raceTrackerMainGUI.fxml"));
		Parent root = loader.load();
		
		FXMLLoader loaderLH = new FXMLLoader(getClass().getResource("/raceTrackerGUI/lapHistory.fxml"));
		Parent lapHistory=loaderLH.load();
		
		FXMLLoader loaderTP = new FXMLLoader(getClass().getResource("/raceTrackerGUI/trackPosition.fxml"));
		Parent trackPosition=loaderTP.load();
		
		controllerMainGUI = (MainController) loader.getController();
		controllerLH =(LapHistoryController) loaderLH.getController();
		controllerTP=(TrackPositionController) loaderTP.getController();
		
		System.out.println(controllerLH.tblLapHistory);
		
		vModel = new ViewModel();
		model = new RaceTrackerModel(vModel, vModel, vModel, vModel,vModel);
		controllerMainGUI.setModel(vModel,controllerLH,controllerTP);
		controllerLH.setModel(vModel, controllerMainGUI);
		controllerTP.setModel(vModel, controllerMainGUI);

		primaryStage.onCloseRequestProperty().setValue(e -> Platform.exit());
		primaryStage.setTitle("RaceTracker");
		primaryStage.setScene(new Scene(root, 1800, 1000));
		primaryStage.setMaximized(true);
		primaryStage.show();

		listener = new PortListener(model);
		listener.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws InterruptedException {
		listener.stop();
	}

}
