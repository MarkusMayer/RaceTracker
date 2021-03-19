package raceTracker.gui;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;

import raceTracker.model.RaceTrackerModel;
import raceTracker.model.gameStructs.Header;
import raceTracker.model.gameStructs.Lap;
import raceTracker.model.gameStructs.PacketCarTelemetryData;
import raceTracker.model.gameStructs.PacketParticipants;
import raceTracker.model.gameStructs.Session;

public class PortListener implements Runnable {
	
	private static final int portNr = 20777;
	
	private DatagramSocket socket;
	private byte[] buf = new byte[8192];
	
	RaceTrackerModel model;

	public PortListener(RaceTrackerModel model) {
		this.model=model;
	}

	@Override
	public void run() {
		try {
			System.out.println("Starting Server on " + portNr);
			socket = new DatagramSocket(portNr);

			System.out.println("Listening on ");

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
	
	//					System.out.println(RaceTrackerConsoleRunner.bytesToHex(headerData) + " | " + RaceTrackerConsoleRunner.bytesToHex(participantsData));
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			socket.close();
		}
	}
}