package raceTrackerGUI;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import raceTracker.model.enums.Driver;
import raceTracker.model.viewModel.TrackPosition;

public class TrackPositionController {

	@FXML
	public Pane paneTrackPosition;
	
	Canvas can;
	GraphicsContext gc;

	private MainController mc;

	private ViewModel vModel;
	private final Color[] colors = new Color[] { Color.CHARTREUSE, Color.ALICEBLUE, Color.BEIGE, Color.BLUEVIOLET,
			Color.CORNSILK, Color.DARKGOLDENROD, Color.DARKSALMON, Color.ORANGE, Color.LEMONCHIFFON, Color.SLATEGREY,
			Color.KHAKI, Color.LIGHTCORAL, Color.DEEPSKYBLUE, Color.MEDIUMPURPLE, Color.LIGHTSEAGREEN, Color.OLIVE,
			Color.MEDIUMAQUAMARINE, Color.PALETURQUOISE, Color.PAPAYAWHIP,Color.ROSYBROWN,Color.WHEAT,Color.OLIVEDRAB };
	private Map<Driver, Circle> circlePos = new HashMap<>();

	public void initialize() {
		can=new Canvas(1000,1000);
		paneTrackPosition.getChildren().add(can);
		gc=can.getGraphicsContext2D();
	}

	public void setModel(ViewModel model, MainController mc) {
		vModel = model;
		this.mc = mc;

		model.listTrackPositions.addListener(new ListChangeListener<TrackPosition>() {

			@Override
			public void onChanged(Change<? extends TrackPosition> c) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						for (TrackPosition aPos : vModel.listTrackPositions) {
							updatePosition(aPos);
						}
					}
				});
			}
		}

		);

//		Circle circle = new Circle(10, 300, 300);
//		Text text = new Text(20, 20, "HI");
//		circle.setFill(Color.WHITE);
//		circle.setStroke(Color.BLACK);
//
//		paneTrackPosition.getChildren().addAll(circle, text);

	}
	
// Hanoi maxPosX: 740.9332, minPosX: -640.6594, maxPosY: 0.3737122, minPosY: 0.25868684
// Barcelona	maxPosX: 742.0292, minPosX: -644.8073, maxPosY: 0.34667775, minPosY: -85.21367
// Baku maxPosX: 941.85284, minPosX: -1183.7283, maxPosY: 26.357504, minPosY: -85.21367
// Spielberg maxPosX: 732.7896, minPosX: -536.332, maxPosY: 21.654469, minPosY: -42.40302
	public void updatePosition(TrackPosition aPos) {
		Circle aCirclePos;
		aCirclePos = circlePos.get(aPos.getDriverId());
		if (aCirclePos == null) {
			aCirclePos = new Circle((aPos.getPosX()+650)/1.5, (aPos.getPosY()*15000)+650, 5);
			aCirclePos.setFill(colors[circlePos.keySet().size()]);
			aCirclePos.setStroke(Color.BLACK);
			circlePos.put(aPos.getDriverId(), aCirclePos);
			paneTrackPosition.getChildren().add(aCirclePos);
		} else

		aCirclePos.setCenterX((aPos.getPosX()+650)/1.5);
		aCirclePos.setCenterY((aPos.getPosY()*2000));

		gc.strokeOval((aPos.getPosX()+650)/1.5, (aPos.getPosY()*2000), 10, 10);
	}

}