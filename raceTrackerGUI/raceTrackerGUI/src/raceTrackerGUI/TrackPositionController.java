package raceTrackerGUI;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Track;
import raceTracker.model.viewModel.TrackPosition;

public class TrackPositionController {

	@FXML
	public Pane paneTrackPosition;

	@FXML
	public ImageView imageViewTrackPosition;

	@FXML
	public TableView<TrackPosTableEntry> tableTrackPosLegend;

	@FXML
	public TableColumn<TrackPosTableEntry, Integer> tableTrackPosColColor;

	@FXML
	public TableColumn<TrackPosTableEntry, String> tableTrackPosColDriver;

	Canvas can;
	GraphicsContext gc;

	private MainController mc;

	private ViewModel vModel;

	private int divisor=1;
	private int offsetX=0;
	private int offsetZ=0;
	
	private static final Map<Track, TrackPositionBackground> backgrounds = Map.ofEntries(
			Map.entry(Track.shanghai, new TrackPositionBackground(2, 585, 475,"/shanghai.png")),
			Map.entry(Track.spielberg, new TrackPositionBackground(2, 545, 480,"/spielberg.png")),
			Map.entry(Track.spa, new TrackPositionBackground(4, 615, 458,"/spa.png")),			
			Map.entry(Track.melbourne, new TrackPositionBackground(4, 595, 448,"/melbourne.png")),
			Map.entry(Track.monza, new TrackPositionBackground(4, 605, 455,"/monza.png")));

	public static final Color[] colors = new Color[] { Color.CHARTREUSE, Color.ALICEBLUE, Color.BEIGE, Color.BLUEVIOLET,
			Color.CORNSILK, Color.DARKGOLDENROD, Color.DARKSALMON, Color.ORANGE, Color.LEMONCHIFFON, Color.SLATEGREY,
			Color.KHAKI, Color.LIGHTCORAL, Color.DEEPSKYBLUE, Color.MEDIUMPURPLE, Color.LIGHTSEAGREEN, Color.OLIVE,
			Color.MEDIUMAQUAMARINE, Color.PALETURQUOISE, Color.PAPAYAWHIP, Color.ROSYBROWN, Color.WHEAT,
			Color.OLIVEDRAB };
	private Map<Driver, Circle> circlePos = new HashMap<>();

	public void initialize() throws URISyntaxException {
		can = new Canvas(1000, 1000);
		paneTrackPosition.getChildren().add(can);
		gc = can.getGraphicsContext2D();
	}

	private void setTrackImage(Track trackId) {
		if (backgrounds.get(trackId) != null) {
			divisor = backgrounds.get(trackId).getDivisor();
			offsetX = backgrounds.get(trackId).getOffsetX();
			offsetZ = backgrounds.get(trackId).getOffsetZ();
			imageViewTrackPosition.setImage(backgrounds.get(trackId).getBackgroundImg());
		}
	}

	public void setModel(ViewModel model, MainController mc) {
		vModel = model;
		this.mc = mc;

		model.listTrackPositions.addListener(new ListChangeListener<TrackPosition>() {

			@Override
			public void onChanged(Change<? extends TrackPosition> c) {
//				System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())+" - TrackPositionController.onChanged Update TrackPositions ");				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
//						System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())+" - TrackPositionController.runLater Update TrackPositions ");						
						for (TrackPosition aPos : vModel.listTrackPositions) {
							updatePosition(aPos);
						}
					}
				});
			}
		}

		);
		
		vModel.trackProperty().addListener(
				(observable, oldValue, track) -> Platform.runLater(() -> setTrackImage(track)));

		tableTrackPosLegend.setItems(vModel.getTrackPosTableEntries());

//		tableTrackPosLegend.setRowFactory(row -> new TableRow<TrackPosTableEntry>() {
//
//			@Override
//			public void updateItem(TrackPosTableEntry item, boolean empty) {
//				super.updateItem(item, empty);
//				System.out.println("getChildren().size(): "+getChildren().size());
//				if (item == null || empty) {
//					setStyle("");
//					for (int i = 0; i < Math.min(getChildren().size(), 1); i++) {
//						getChildren().get(i).setStyle("");
//					}
//				} else {
//					Color c = item.getCol();
//					for (int i = 0; i < Math.min(getChildren().size(), 1); i++) {
//						getChildren().get(i).setStyle("-fx-background-color: " + colorToHex(c));
//					}
//				}
//			}
//
//		});
		
		tableTrackPosColColor.setCellFactory(cell -> new TableCell<TrackPosTableEntry, Integer>(){
			@Override
			protected void updateItem(Integer color,boolean empty) {
				super.updateItem(color, empty);
				
		        if (empty || getTableRow().getItem()==null || color == null) {
		            // set back to look of empty cell
		            setText(null);
		            setStyle("");
		        } else {
		        	setText(color.toString());
		        	setStyle("-fx-background-color: " +colorToHex(getTableRow().getItem().getCol()));
		        }
			}
		});
		
		
		tableTrackPosColColor.setCellValueFactory(new PropertyValueFactory<>("curPos"));
		tableTrackPosColDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
	}

	public static String colorToHex(Color c) {
		Objects.requireNonNull(c);
		return String.format("#%02x%02x%02x", Math.round(c.getRed() * 255), Math.round(c.getGreen() * 255),
				Math.round(c.getBlue() * 255)).toUpperCase();
	}

// Hanoi 
// Barcelona	maxPosX: 442.22867, minPosX: -528.8149, maxPosY: -54.933895, minPosY: -85.20312, maxPosZ: 557.1662, minPosZ: -591.5087
// Baku 
// Spielberg 	maxPosX: 729.99023, minPosX: -531.3736, maxPosY: 21.253143, minPosY: -42.44414, maxPosZ: 302.61868, minPosZ: -483.33145
// Shanghai		maxPosX: 610.6758, minPosX: -613.932, maxPosY: 3.792007, minPosY: -3.2643392, maxPosZ: 537.8669, minPosZ: -534.376
// Monza 		maxPosX: 631.99817, minPosX: -631.87787, maxPosY: 6.6578794, minPosY: -6.0143247, maxPosZ: 1083.1661, minPosZ: -1085.7219
// Spa 			maxPosX: 545.11395, minPosX: -727.2065, maxPosY: 48.72256, minPosY: -53.471523, maxPosZ: 975.11774, minPosZ: -1059.23
// Melbourne	maxPosX: 734.82086, minPosX: -734.24567, maxPosY: 4.384029, minPosY: 1.8038415, maxPosZ: 876.1157, minPosZ: -876.5083

	public void updatePosition(TrackPosition aPos) {
//		System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())+" - TrackPositionController.UpdatePosition");		
		Circle aCirclePos;
		aCirclePos = circlePos.get(aPos.getDriverId());
		if (aCirclePos == null) {
			aCirclePos = new Circle(aPos.getPosX() / divisor + offsetX, aPos.getPosZ() / divisor + offsetZ, 5);
			aCirclePos.setFill(colors[circlePos.keySet().size()]);
			aCirclePos.setStroke(Color.BLACK);
			circlePos.put(aPos.getDriverId(), aCirclePos);
			paneTrackPosition.getChildren().add(aCirclePos);
		} else {
			aCirclePos.setCenterX(aPos.getPosX() / divisor + offsetX);
			aCirclePos.setCenterY(aPos.getPosZ() / divisor + offsetZ);
		}
//		gc.setStroke(Paint.valueOf("BLACK"));
//		gc.strokeOval(aPos.getPosX() / 4 + 500, aPos.getPosZ() / 4 + 500, 10, 10);
	}

	private static class TrackPositionBackground {
		private final int divisor;
		private final int offsetX;
		private final int offsetZ;
		private final Image background;
		
		public TrackPositionBackground(int divisor, int offsetX, int offsetZ,String backgroundImg) {
			super();
			Objects.requireNonNull(backgroundImg);
			this.divisor = divisor;
			this.offsetX = offsetX;
			this.offsetZ = offsetZ;
			background=new Image(TrackPositionController.class.getResourceAsStream(backgroundImg));
		}

		public int getDivisor() {
			return divisor;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public int getOffsetZ() {
			return offsetZ;
		}
		
		public Image getBackgroundImg() {
			return background;
		}

	}
}