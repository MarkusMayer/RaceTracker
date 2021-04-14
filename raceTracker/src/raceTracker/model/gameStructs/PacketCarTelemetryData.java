package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.model.enums.MfdPanelIndex;

public class PacketCarTelemetryData {
	
//    CarTelemetryData    m_carTelemetryData[22];
//
//    uint32              m_buttonStatus;        // Bit flags specifying which buttons are being pressed
//                                               // currently - see appendices
//
//    // Added in Beta 3:
//    uint8               m_mfdPanelIndex;       // Index of MFD panel open - 255 = MFD closed
//                                               // Single player, race – 0 = Car setup, 1 = Pits
//                                               // 2 = Damage, 3 =  Engine, 4 = Temperatures
//                                               // May vary depending on game mode
//    uint8               m_mfdPanelIndexSecondaryPlayer;   // See above
//    int8                m_suggestedGear;       // Suggested gear for the player (1-8)
//                                               // 0 if no gear suggested

	
	public static final int CAR_TELEMTRY_DATA_TOTAL_SIZE=1307-24;
	
	private final byte[] buttonStatus;
	private final MfdPanelIndex mfdPanelIndex,mfdPanelIndexSecondaryPlayer;
	private final int suggestedGear;
	private final List<CarTelemetryData> telemetries;
	
	public PacketCarTelemetryData(int numActiveCars,byte[] packetCarTelemetryData) throws IOException {
//		System.out.println("PacketCarTelemetry Data length: "+packetCarTelemetryData.length+", Data: "+F1UdpReceiver.bytesToHex(packetCarTelemetryData));		
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(packetCarTelemetryData, CarTelemetryData.CAR_TELEMTRY_DATA_TOTAL_SIZE, packetCarTelemetryData.length));

		byte[] carTelemetryTotalData=new byte[CarTelemetryData.CAR_TELEMTRY_DATA_TOTAL_SIZE];
		ByteBuffer bbTelemetryData = ByteBuffer.wrap(packetCarTelemetryData,0,packetCarTelemetryData.length);
		bbTelemetryData.get(carTelemetryTotalData,0,carTelemetryTotalData.length);

		telemetries=CarTelemetryData.getCarTelemetries(numActiveCars,carTelemetryTotalData); // ==> 124
		
		buttonStatus=leDis.readNBytes(4);
		mfdPanelIndex=MfdPanelIndex.getTypeByKey(leDis.readUnsignedByte());
		mfdPanelIndexSecondaryPlayer=MfdPanelIndex.getTypeByKey(leDis.readUnsignedByte());
		suggestedGear=leDis.readByte();
		
	}

	public static int getCarTelemtryDataTotalSize() {
		return CAR_TELEMTRY_DATA_TOTAL_SIZE;
	}

	public byte[] getButtonStatus() {
		return buttonStatus;
	}

	public MfdPanelIndex getMfdPanelIndex() {
		return mfdPanelIndex;
	}

	public MfdPanelIndex getMfdPanelIndexSecondaryPlayer() {
		return mfdPanelIndexSecondaryPlayer;
	}

	public int getSuggestedGear() {
		return suggestedGear;
	}

	public List<CarTelemetryData> getTelemetries() {
		return telemetries;
	}

	@Override
	public String toString() {
		return "PacketCarTelemetryData [buttonStatus=" + Arrays.toString(buttonStatus) + ", mfdPanelIndex="
				+ mfdPanelIndex + ", mfdPanelIndexSecondaryPlayer=" + mfdPanelIndexSecondaryPlayer + ", suggestedGear="
				+ suggestedGear + ", telemetries=" + telemetries + "]";
	}
	
	
	
}
