module raceTrackerGUI {
	exports raceTrackerGUI;
	
	requires raceTracker;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.base;
	
	opens raceTrackerGUI to javafx.fxml;
}