module raceTrackerGUI {
	exports raceTrackerGUI;
	
	requires raceTracker;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	
	opens raceTrackerGUI to javafx.fxml;
}