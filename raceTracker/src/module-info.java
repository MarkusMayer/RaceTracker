module raceTracker {
	requires com.google.common;
	requires java.desktop;
	
	exports raceTracker;
	exports raceTracker.model;
	exports raceTracker.model.enums;
	exports raceTracker.model.gameStructs;
	exports raceTracker.model.viewModel;
}