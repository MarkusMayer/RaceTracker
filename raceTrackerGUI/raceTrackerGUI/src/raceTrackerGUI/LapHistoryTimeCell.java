package raceTrackerGUI;

import java.util.function.Function;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import raceTracker.model.viewModel.Lap;

public class LapHistoryTimeCell<S extends Lap,T extends String> extends TableCell<S,T> {
	
	private final Function<Lap,Boolean> overallBest;
	private final Function<Lap,Boolean> personalBest;
	
	public LapHistoryTimeCell(Function<Lap,Boolean> overallBest,Function<Lap,Boolean> personalBest) {
		this.overallBest=overallBest;
		this.personalBest=personalBest;
	}
	
	private void setStyleNormal() {
		setStyle("-fx-background-color: " + TrackPositionController.colorToHex(Color.WHITE));
	}

	private void setStylePersonalBest() {
		setStyle("-fx-background-color: " + TrackPositionController.colorToHex(Color.GREEN));
	}

	private void setStyleOverallBest() {
		setStyle("-fx-background-color: " + TrackPositionController.colorToHex(Color.PURPLE)
				+ "; -fx-text-fill: " + TrackPositionController.colorToHex(Color.WHITE));
	}
	
	@Override
	protected void updateItem(T lapDesc, boolean empty) {
		super.updateItem(lapDesc, empty);

		if (empty || getTableRow().getItem() == null || lapDesc == null) {
			setText(null);
			setStyle("");
		} else {
			setText(lapDesc);
			Lap lap = getTableRow().getItem();
			if (overallBest.apply(lap)==true) {
				setStyleOverallBest();
			} else if (personalBest.apply(lap)==true) {
				setStylePersonalBest();
			} else {
				setStyleNormal();
			}
		}
	}

}
