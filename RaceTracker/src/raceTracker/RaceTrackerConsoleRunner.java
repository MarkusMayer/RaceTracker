package raceTracker;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;

import raceTracker.model.GearSuggestionListener;
import raceTracker.model.RaceStandingsListener;
import raceTracker.model.RaceTrackerModel;
import raceTracker.model.SessionListener;
import raceTracker.model.UpshiftBeepListener;
import raceTracker.model.enums.Flag;
import raceTracker.model.gameStructs.Header;
import raceTracker.model.gameStructs.Lap;
import raceTracker.model.gameStructs.MarshallZone;
import raceTracker.model.gameStructs.PacketCarTelemetryData;
import raceTracker.model.gameStructs.PacketParticipants;
import raceTracker.model.gameStructs.Session;
import raceTracker.model.viewModel.RacePosition;
import raceTracker.model.viewModel.RaceStandings;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RaceTrackerConsoleRunner implements UpshiftBeepListener, GearSuggestionListener,SessionListener,RaceStandingsListener {

	public static final URL beepUrl= RaceTrackerConsoleRunner.class.getResource("/beep.wav");
	public static final URL gear1Url= RaceTrackerConsoleRunner.class.getResource("/one.wav");
	public static final URL gear2Url= RaceTrackerConsoleRunner.class.getResource("/two.wav");
	public static final URL gear3Url= RaceTrackerConsoleRunner.class.getResource("/three.wav");
	public static final URL gear4Url= RaceTrackerConsoleRunner.class.getResource("/four.wav");
	public static final URL gear5Url= RaceTrackerConsoleRunner.class.getResource("/five.wav");
	public static final URL gear6Url= RaceTrackerConsoleRunner.class.getResource("/six.wav");
	public static final URL gear7Url= RaceTrackerConsoleRunner.class.getResource("/seven.wav");
	public static final URL gear8Url= RaceTrackerConsoleRunner.class.getResource("/eight.wav");
	
	private DatagramSocket socket;
	private static final int portNr = 20777;
	private byte[] buf = new byte[8192];
	private final File[] gearSounds;
	private final File beepFile;

	private static RaceTrackerModel model;
	


	public RaceTrackerConsoleRunner() throws URISyntaxException {
		beepFile=new File(beepUrl.toURI());
		gearSounds=getGearSounds();

		model = new RaceTrackerModel(this, this,this,this);
	}

	public static File [] getGearSounds() throws URISyntaxException {
		File[] result=new File[9];
		result[1] = new File(gear1Url.toURI());
		result[2] = new File(gear2Url.toURI());
		result[3] = new File(gear3Url.toURI());
		result[4] = new File(gear4Url.toURI());
		result[5] = new File(gear5Url.toURI());
		result[6] = new File(gear6Url.toURI());
		result[7] = new File(gear7Url.toURI());
		result[8] = new File(gear8Url.toURI());
		return result;
	}

	public static void main(String[] args)
			throws SocketException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException, URISyntaxException {
		RaceTrackerConsoleRunner receiver = new RaceTrackerConsoleRunner();
		receiver.receive();

	}

	private void receive() throws SocketException {
		System.out.println("Starting Server on " + portNr);
		socket = new DatagramSocket(portNr);
		try {
			System.out.println("Listening on ");
			playClip(beepFile);

			while (true) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				byte[] data = packet.getData();
				packet = new DatagramPacket(buf, buf.length, address, port);
				// String received = new String(packet.getData(), 0, packet.getLength());

				ByteBuffer bb = ByteBuffer.wrap(data);
				byte[] headerData = new byte[24];
				bb.get(headerData, 0, headerData.length);

				Header header = new Header(headerData);

				switch (header.getPacketType()) {
				case session:
					byte[] sessionData = new byte[251];
					bb.get(sessionData, 0, sessionData.length);
//					System.out.println(bytesToHex(headerData) + " | " + bytesToHex(sessionData));
					Session session = new Session(sessionData);
					System.out.println(header + " -> " + session);
					model.receiceSessionData(session);
					break;
				case lapData:
					byte[] lapsData = new byte[Lap.LAP_DATA_TOTAL_SIZE];
//					System.out.println(bb.remaining());
//					System.out.println(bytesToHex(data));
					bb.get(lapsData, 0, lapsData.length);
//					System.out.println(bytesToHex(headerData) + " | " + bytesToHex(lapsData));
					List<Lap> laps = Lap.getLaps(model.getNumActiveCars(), lapsData);
					model.receiceLapData(laps);
//					System.out.println(header + " -> " + laps);
					break;
				case participants:
					byte[] participantsData = new byte[PacketParticipants.PARTICIPANTS_DATA_SIZE];

					bb.get(participantsData, 0, participantsData.length);

					System.out.println(bytesToHex(headerData) + " | " + bytesToHex(participantsData));
					PacketParticipants participants = new PacketParticipants(participantsData);
					System.out.println(header + " -> " + participants);
					model.receiveParticipants(participants);
					break;
				case carTelemetry:
					byte[] telemetryData = new byte[PacketCarTelemetryData.CAR_TELEMTRY_DATA_TOTAL_SIZE];
//					System.out.println(bb.remaining());
//					System.out.println(bytesToHex(data));
					bb.get(telemetryData, 0, telemetryData.length);
//					System.out.println(bytesToHex(headerData) + " | " + bytesToHex(telemetryData));
					PacketCarTelemetryData carTelemetries = new PacketCarTelemetryData(model.getNumActiveCars(), telemetryData);
//					System.out.println(header + " -> " + carTelemetries);
					model.receiveTelemetryUpdate(carTelemetries);
//					System.out.println("Player idx: "+idxPlayerCar+" -> "+ playerTelemetry);
					break;
				default:
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}

	}

	private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

	public static String bytesToHex(byte[] bytes) {
		byte[] hexChars = new byte[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {

			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars, StandardCharsets.UTF_8);
	}

	public static void playClip(File clipFile) {
		try {
			class AudioListener implements LineListener {

				private boolean done = false;

				@Override
				public synchronized void update(LineEvent event) {
					Type eventType = event.getType();
					if (eventType == Type.STOP || eventType == Type.CLOSE) {
						done = true;
						notifyAll();
					}
				}

				public synchronized void waitUntilDone() throws InterruptedException {
					while (!done) {
						wait();
					}
				}
			}
			AudioListener listener = new AudioListener();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
			try {
				Clip clip = AudioSystem.getClip();
				clip.addLineListener(listener);
				clip.open(audioInputStream);
				try {
					clip.start();
					listener.waitUntilDone();
				} finally {
					clip.close();
				}
			} finally {
				audioInputStream.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void doUpshiftBeep() {
		System.out.println("BEEP!!!!!!!!!!!");

		playClip(beepFile);
	}

	@Override
	public void suggestGear(int suggestedGear) {
		System.out.println("Suggested Gear update!!!!!!!!!!!");

		playClip(gearSounds[suggestedGear]);
	}

	@Override
	public void updateSessionData(Session newSessionData) {
		System.out.println("Session Time: "+newSessionData.getSessionDuration()+", time left: "+newSessionData.getSessionTimeLeft());
		List<MarshallZone> activeZones=newSessionData.getZones().stream().filter(aZone -> (aZone.getZone()!=Flag.none )).collect(Collectors.toList());
		if (!activeZones.isEmpty()) {
			System.out.println("Active Zones: "+activeZones);
		}
	}

	@Override
	public void updateRaceStandings(RaceStandings curStandings) {
		System.out.println("!!!!!! Current Standings !!!!!!");
		for (RacePosition pos : curStandings.getPos()) {
			System.out.println(pos);
		}
	}

}
