package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.RaceTrackerConsoleRunner;

public class PacketParticipants {

	public static final int PARTICIPANTS_DATA_SIZE=1189;
	
	private final int numActiveCars;
	private final List<Participant> participants;
	
	public PacketParticipants(byte[] participantsData) throws IOException {
//		System.out.println("Participants length: "+participantsData.length+", Data: "+F1UdpReceiver.bytesToHex(participantsData));		
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(participantsData, 0, participantsData.length));
		numActiveCars=leDis.readUnsignedByte();
		
		byte[] participantStructData=new byte[Participant.PARTICIPANTS_TOTAL_DATA_SIZE];
		ByteBuffer bbZoneData = ByteBuffer.wrap(participantsData,1,participantStructData.length);
		bbZoneData.get(participantStructData,0,participantStructData.length);

		participants=Participant.getParticipants(numActiveCars,participantStructData); // ==> 124
	}

	public static int getParticipantsDataSize() {
		return PARTICIPANTS_DATA_SIZE;
	}

	public int getNumActiveCars() {
		return numActiveCars;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	@Override
	public String toString() {
		return "Participants [numActiveCars=" + numActiveCars + ", participants=" + participants + "]";
	}
	
	

}
