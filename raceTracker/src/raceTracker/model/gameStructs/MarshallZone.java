package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.model.enums.Flag;

public class MarshallZone {
	public static final int MARHSHALL_ZONES_TOTAL_SIZE = 105;
	public static final int MARHSHALL_ZONE_SIZE = 5;
	private final float zoneStart;
	private final Flag zone;
	
	public static List<MarshallZone> getMarshallZones(int numMarshalZones,byte[] marshallZonesData) throws IOException{
//		System.out.println("MarshallZone total Data: "+F1UdpReceiver.bytesToHex(marshallZonesData));
		List<MarshallZone> zones=new ArrayList<>();
		ByteBuffer bb = ByteBuffer.wrap(marshallZonesData);
		for (int i=0;i<Math.min(numMarshalZones, 21);i++) {
			byte[] zoneData=new byte[MARHSHALL_ZONE_SIZE];
			bb.get(zoneData,0,zoneData.length);
			MarshallZone mz=new MarshallZone(zoneData);
			zones.add(mz);
		}
		return zones;
	}
	
	private MarshallZone(byte[] zoneData) throws IOException {
//		System.out.println("MarshallZone: "+F1UdpReceiver.bytesToHex(zoneData));
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(zoneData, 0, zoneData.length));
		zoneStart=leDis.readFloat();
		zone=Flag.getTypeByKey(leDis.readByte());
	}

	public float getZoneStart() {
		return zoneStart;
	}

	public Flag getZone() {
		return zone;
	}

	@Override
	public String toString() {
		return "MarshallZone [zoneStart=" + zoneStart + ", zone=" + zone + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
		result = prime * result + Float.floatToIntBits(zoneStart);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarshallZone other = (MarshallZone) obj;
		if (zone != other.zone)
			return false;
		if (Float.floatToIntBits(zoneStart) != Float.floatToIntBits(other.zoneStart))
			return false;
		return true;
	}
	
}
