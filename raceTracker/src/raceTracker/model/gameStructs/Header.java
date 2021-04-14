package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.model.enums.PacketType;

public class Header {
	
	private final int headerFormat,headerMajorVersion,headerMinorVersion,headerPacketVersion;
	private final PacketType packetType; 
	
	public Header(byte[] headerData) throws IOException {
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(headerData, 0, headerData.length));

		headerFormat = leDis.readUnsignedShort(); // 1,2
		headerMajorVersion = leDis.readUnsignedByte(); // 3
		headerMinorVersion = leDis.readUnsignedByte(); // 4
		headerPacketVersion = leDis.readUnsignedByte(); // 5
		packetType = PacketType.getTypeByKey(leDis.readUnsignedByte()); // 6
	}

	public int getHeaderFormat() {
		return headerFormat;
	}

	public int getHeaderMajorVersion() {
		return headerMajorVersion;
	}

	public int getHeaderMinorVersion() {
		return headerMinorVersion;
	}

	public int getHeaderPacketVersion() {
		return headerPacketVersion;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	@Override
	public String toString() {
		return "Header [headerFormat=" + headerFormat + ", headerMajorVersion=" + headerMajorVersion
				+ ", headerMinorVersion=" + headerMinorVersion + ", headerPacketVersion=" + headerPacketVersion
				+ ", packetType=" + packetType + "]";
	}
	
	

}
