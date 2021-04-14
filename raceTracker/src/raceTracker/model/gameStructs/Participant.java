package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.RaceTrackerConsoleRunner;
import raceTracker.model.enums.CarController;
import raceTracker.model.enums.Driver;
import raceTracker.model.enums.Team;
import raceTracker.model.enums.TelemetryVisibility;

public class Participant {

	public static final int PARTICIPANTS_TOTAL_DATA_SIZE=1188;
	public static final int PARTICIPANT_DATA_SIZE=54;
	
	public static List<Participant> getParticipants(int numActiveCars,byte[] participantsData) throws IOException {
//		System.out.println("Participants-Struct Data: "+F1UdpReceiver.bytesToHex(participantsData));
		List<Participant> participants=new ArrayList<>();
		ByteBuffer bb = ByteBuffer.wrap(participantsData);
		for (int i=0;i<Math.min(numActiveCars,22);i++) {
			byte[] participantData=new byte[PARTICIPANT_DATA_SIZE];
			bb.get(participantData,0,participantData.length);
			Participant p=new Participant(participantData);
			participants.add(p);
		}
		
		return participants;
	}

//    uint8      m_aiControlled;           // Whether the vehicle is AI (1) or Human (0) controlled
//    uint8      m_driverId;               // Driver id - see appendix
//    uint8      m_teamId;                 // Team id - see appendix
//    uint8      m_raceNumber;             // Race number of the car
//    uint8      m_nationality;            // Nationality of the driver
//    char       m_name[48];               // Name of participant in UTF-8 format – null terminated
//                                         // Will be truncated with … (U+2026) if too long
//    uint8      m_yourTelemetry;          // The player's UDP setting, 0 = restricted, 1 = public
	
	private final CarController aiControlled;
	private final Driver driverId;
	private final Team teamId;
	private final TelemetryVisibility yourTelemetry;
	
	private final int raceNumber,nationality;
	private final String driverName;
	
	
	
	public Participant(CarController aiControlled, Driver driverId, Team teamId, TelemetryVisibility yourTelemetry,
			int raceNumber, int nationality, String driverName) {
		super();
		this.aiControlled = aiControlled;
		this.driverId = driverId;
		this.teamId = teamId;
		this.yourTelemetry = yourTelemetry;
		this.raceNumber = raceNumber;
		this.nationality = nationality;
		this.driverName = driverName;
	}



	private Participant(byte[] participantData) throws IOException {
//		System.out.println("Participant: "+F1UdpReceiver.bytesToHex(participantData));
		try {
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(participantData, 0, participantData.length));
		aiControlled=CarController.getTypeByKey(leDis.readUnsignedByte());
		driverId=Driver.getTypeByKey(leDis.readUnsignedByte());
		teamId=Team.getTypeByKey(leDis.readUnsignedByte());
		raceNumber=leDis.readUnsignedByte();
		nationality=leDis.readUnsignedByte();
		
		byte[] nameChars=leDis.readNBytes(48);
		Charset charSet=Charset.forName("UTF8");
		int i;
		for (i = 0; i < nameChars.length && nameChars[i] != 0; i++) { }
		driverName = new String(nameChars, 0, i, charSet);
		
		yourTelemetry=TelemetryVisibility.getTypeByKey(leDis.readUnsignedByte());
		} catch (IllegalArgumentException e) {
			System.out.println("Participant: "+RaceTrackerConsoleRunner.bytesToHex(participantData));
			throw e;
		}
	}



	public static int getParticipantsTotalDataSize() {
		return PARTICIPANTS_TOTAL_DATA_SIZE;
	}



	public static int getParticipantDataSize() {
		return PARTICIPANT_DATA_SIZE;
	}



	public CarController getAiControlled() {
		return aiControlled;
	}



	public Driver getDriverId() {
		return driverId;
	}



	public Team getTeamId() {
		return teamId;
	}



	public TelemetryVisibility getYourTelemetry() {
		return yourTelemetry;
	}



	public int getRaceNumber() {
		return raceNumber;
	}



	public int getNationality() {
		return nationality;
	}



	public String getDriverName() {
		return driverName;
	}



	@Override
	public String toString() {
		return "Participant [aiControlled=" + aiControlled + ", driverId=" + driverId + ", teamId=" + teamId
				+ ", yourTelemetry=" + yourTelemetry + ", raceNumber=" + raceNumber + ", nationality=" + nationality
				+ ", driverName=" + driverName + "]";
	}

}
