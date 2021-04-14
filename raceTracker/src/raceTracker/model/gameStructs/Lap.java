package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.model.enums.DriverStatus;
import raceTracker.model.enums.PitStatus;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.enums.Sector;

public class Lap {
	
	public static final int LAP_DATA_TOTAL_SIZE=1166;
	public static final int LAP_DATA_SIZE=53;
	
	public static List<Lap> getLaps(int numActiveCars, byte[] lapsData) throws IOException{
//		System.out.println("Laps total Data: "+F1UdpReceiver.bytesToHex(lapsData));
		List<Lap> laps=new ArrayList<>();
		ByteBuffer bb = ByteBuffer.wrap(lapsData);
		for (int i=0;i<Math.min(numActiveCars, 22);i++) {
			byte[] lapData=new byte[LAP_DATA_SIZE];
			bb.get(lapData,0,lapData.length);
			Lap mz=new Lap(lapData);
			laps.add(mz);
		}
		return laps;
	}

//	   float    m_lastLapTime;               // Last lap time in seconds
//	    float    m_currentLapTime;            // Current time around the lap in seconds
//	  
//	    //UPDATED in Beta 3:
//	    uint16   m_sector1TimeInMS;           // Sector 1 time in milliseconds
//	    uint16   m_sector2TimeInMS;           // Sector 2 time in milliseconds
//	    float    m_bestLapTime;               // Best lap time of the session in seconds
//	    uint8    m_bestLapNum;                // Lap number best time achieved on
//	    uint16   m_bestLapSector1TimeInMS;    // Sector 1 time of best lap in the session in milliseconds
//	    uint16   m_bestLapSector2TimeInMS;    // Sector 2 time of best lap in the session in milliseconds
//	    uint16   m_bestLapSector3TimeInMS;    // Sector 3 time of best lap in the session in milliseconds
//	    uint16   m_bestOverallSector1TimeInMS;// Best overall sector 1 time of the session in milliseconds
//	    uint8    m_bestOverallSector1LapNum;  // Lap number best overall sector 1 time achieved on
//	    uint16   m_bestOverallSector2TimeInMS;// Best overall sector 2 time of the session in milliseconds
//	    uint8    m_bestOverallSector2LapNum;  // Lap number best overall sector 2 time achieved on
//	    uint16   m_bestOverallSector3TimeInMS;// Best overall sector 3 time of the session in milliseconds
//	    uint8    m_bestOverallSector3LapNum;  // Lap number best overall sector 3 time achieved on
//	  
//	  
//	    float    m_lapDistance;               // Distance vehicle is around current lap in metres – could
//	                                          // be negative if line hasn’t been crossed yet
//	    float    m_totalDistance;             // Total distance travelled in session in metres – could
//	                                          // be negative if line hasn’t been crossed yet
//	    float    m_safetyCarDelta;            // Delta in seconds for safety car
//	    uint8    m_carPosition;               // Car race position
//	    uint8    m_currentLapNum;             // Current lap number
//	    uint8    m_pitStatus;                 // 0 = none, 1 = pitting, 2 = in pit area
//	    uint8    m_sector;                    // 0 = sector1, 1 = sector2, 2 = sector3
//	    uint8    m_currentLapInvalid;         // Current lap invalid - 0 = valid, 1 = invalid
//	    uint8    m_penalties;                 // Accumulated time penalties in seconds to be added
//	    uint8    m_gridPosition;              // Grid position the vehicle started the race in
//	    uint8    m_driverStatus;              // Status of driver - 0 = in garage, 1 = flying lap
//	                                          // 2 = in lap, 3 = out lap, 4 = on track
//	    uint8    m_resultStatus;              // Result status - 0 = invalid, 1 = inactive, 2 = active
//	                                          // 3 = finished, 4 = disqualified, 5 = not classified
//	                                          // 6 = retired
	
	private final float lastLapTime,currentLapTime,bestLapTime,lapDistance,totalDistance,safetyCarDelta;
	private final int sector1TimeMS,sector2TimeMS,bestLapNum,bestLapSector1TimeInMS,bestLapSector2TimeInMS,bestLapSector3TimeInMS,
	bestOverallSector1TimeInMS,bestOverallSector1LapNum,bestOverallSector2TimeInMS,bestOverallSector2LapNum,bestOverallSector3TimeInMS,bestOverallSector3LapNum,
	carPosition,currentLapNum,currentLapInvalid,penalties,gridPosition;
	
	private final PitStatus pitStatus;
	private final Sector sector;
	private final DriverStatus driverStatus;
	private final ResultStatus resultStatus;
	
	
	
	public Lap(float lastLapTime, float currentLapTime, float bestLapTime, float lapDistance, float totalDistance,
			float safetyCarDelta, int sector1TimeMS, int sector2TimeMS, int bestLapNum, int bestLapSector1TimeInMS,
			int bestLapSector2TimeInMS, int bestLapSector3TimeInMS, int bestOverallSector1TimeInMS,
			int bestOverallSector1LapNum, int bestOverallSector2TimeInMS, int bestOverallSector2LapNum,
			int bestOverallSector3TimeInMS, int bestOverallSector3LapNum, int carPosition, int currentLapNum,
			int currentLapInvalid, int penalties, int gridPosition, PitStatus pitStatus, Sector sector,
			DriverStatus driverStatus, ResultStatus resultStatus) {
		super();
		this.lastLapTime = lastLapTime;
		this.currentLapTime = currentLapTime;
		this.bestLapTime = bestLapTime;
		this.lapDistance = lapDistance;
		this.totalDistance = totalDistance;
		this.safetyCarDelta = safetyCarDelta;
		this.sector1TimeMS = sector1TimeMS;
		this.sector2TimeMS = sector2TimeMS;
		this.bestLapNum = bestLapNum;
		this.bestLapSector1TimeInMS = bestLapSector1TimeInMS;
		this.bestLapSector2TimeInMS = bestLapSector2TimeInMS;
		this.bestLapSector3TimeInMS = bestLapSector3TimeInMS;
		this.bestOverallSector1TimeInMS = bestOverallSector1TimeInMS;
		this.bestOverallSector1LapNum = bestOverallSector1LapNum;
		this.bestOverallSector2TimeInMS = bestOverallSector2TimeInMS;
		this.bestOverallSector2LapNum = bestOverallSector2LapNum;
		this.bestOverallSector3TimeInMS = bestOverallSector3TimeInMS;
		this.bestOverallSector3LapNum = bestOverallSector3LapNum;
		this.carPosition = carPosition;
		this.currentLapNum = currentLapNum;
		this.currentLapInvalid = currentLapInvalid;
		this.penalties = penalties;
		this.gridPosition = gridPosition;
		this.pitStatus = pitStatus;
		this.sector = sector;
		this.driverStatus = driverStatus;
		this.resultStatus = resultStatus;
	}


	public Lap(byte[] lapData) throws IOException {
//		System.out.println("Lap: "+F1UdpReceiver.bytesToHex(lapData));
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(lapData, 0, lapData.length));
		lastLapTime=leDis.readFloat();
		currentLapTime=leDis.readFloat();
		sector1TimeMS=leDis.readUnsignedShort();
		sector2TimeMS=leDis.readUnsignedShort();
		bestLapTime=leDis.readFloat();
		bestLapNum=leDis.readUnsignedByte();
		bestLapSector1TimeInMS=leDis.readUnsignedShort();
		bestLapSector2TimeInMS=leDis.readUnsignedShort();
		bestLapSector3TimeInMS=leDis.readUnsignedShort();

		bestOverallSector1TimeInMS=leDis.readUnsignedShort();
		bestOverallSector1LapNum=leDis.readUnsignedByte();
		bestOverallSector2TimeInMS=leDis.readUnsignedShort();
		bestOverallSector2LapNum=leDis.readUnsignedByte();
		bestOverallSector3TimeInMS=leDis.readUnsignedShort();
		bestOverallSector3LapNum=leDis.readUnsignedByte();
		
		lapDistance=leDis.readFloat();
		totalDistance=leDis.readFloat();
		
		safetyCarDelta=leDis.readFloat();
		carPosition=leDis.readUnsignedByte();
		currentLapNum=leDis.readUnsignedByte();
		pitStatus=PitStatus.getTypeByKey(leDis.readUnsignedByte());
		sector=Sector.getTypeByKey(leDis.readUnsignedByte());
		currentLapInvalid=leDis.readUnsignedByte();
		penalties=leDis.readUnsignedByte();
		gridPosition=leDis.readUnsignedByte();
		driverStatus=DriverStatus.getTypeByKey(leDis.readUnsignedByte());
		resultStatus=ResultStatus.getTypeByKey(leDis.readUnsignedByte());
	}


	public static int getLapDataSize() {
		return LAP_DATA_SIZE;
	}


	public float getLastLapTime() {
		return lastLapTime;
	}
	
	public Duration getLastLapTimeDuration() {
		return Duration.ofMillis((long)(getLastLapTime()*1000));
	}


	public float getCurrentLapTime() {
		return currentLapTime;
	}
	
	public Duration getCurrentLapTimeDuration() {
		return Duration.ofMillis((long)(getCurrentLapTime()*1000));
	}


	public float getBestLapTime() {
		return bestLapTime;
	}
	
	public Duration getBestLapTimeDuration() {
		return Duration.ofMillis((long)(getBestLapTime()*1000));
	}


	public float getLapDistance() {
		return lapDistance;
	}


	public float getTotalDistance() {
		return totalDistance;
	}


	public float getSafetyCarDelta() {
		return safetyCarDelta;
	}


	public int getSector1TimeMS() {
		return sector1TimeMS;
	}
	
	public Duration getSector1TimeMSDuration() {
		return Duration.ofMillis(sector1TimeMS);
	}


	public int getSector2TimeMS() {
		return sector2TimeMS;
	}
	
	public Duration getSector2TimeMSDuration() {
		return Duration.ofMillis(sector2TimeMS);
	}


	public int getBestLapNum() {
		return bestLapNum;
	}


	public int getBestLapSector1TimeInMS() {
		return bestLapSector1TimeInMS;
	}
	
	public Duration getBestLapSector1TimeInMSDuration() {
		return Duration.ofMillis(bestLapSector1TimeInMS);
	}


	public int getBestLapSector2TimeInMS() {
		return bestLapSector2TimeInMS;
	}

	public Duration getBestLapSector2TimeInMSDuration() {
		return Duration.ofMillis(bestLapSector2TimeInMS);
	}


	public int getBestLapSector3TimeInMS() {
		return bestLapSector3TimeInMS;
	}

	public Duration getBestLapSector3TimeInMSDuration() {
		return Duration.ofMillis(bestLapSector3TimeInMS);
	}

	public int getBestOverallSector1TimeInMS() {
		return bestOverallSector1TimeInMS;
	}
	public Duration getBestOverallSector1TimeInMSDuration() {
		return Duration.ofMillis(bestOverallSector1TimeInMS);
	}


	public int getBestOverallSector1LapNum() {
		return bestOverallSector1LapNum;
	}


	public int getBestOverallSector2TimeInMS() {
		return bestOverallSector2TimeInMS;
	}

	public Duration getBestOverallSector2TimeInMSDuration() {
		return Duration.ofMillis(bestOverallSector2TimeInMS);
	}

	public int getBestOverallSector2LapNum() {
		return bestOverallSector2LapNum;
	}


	public int getBestOverallSector3TimeInMS() {
		return bestOverallSector3TimeInMS;
	}

	public Duration getBestOverallSector3TimeInMSDuration() {
		return Duration.ofMillis(bestOverallSector3TimeInMS);
	}

	public int getBestOverallSector3LapNum() {
		return bestOverallSector3LapNum;
	}


	public int getCarPosition() {
		return carPosition;
	}


	public int getCurrentLapNum() {
		return currentLapNum;
	}


	public int getCurrentLapInvalid() {
		return currentLapInvalid;
	}


	public int getPenalties() {
		return penalties;
	}


	public int getGridPosition() {
		return gridPosition;
	}


	public PitStatus getPitStatus() {
		return pitStatus;
	}


	public Sector getSector() {
		return sector;
	}


	public DriverStatus getDriverStatus() {
		return driverStatus;
	}


	public ResultStatus getResultStatus() {
		return resultStatus;
	}
	
	public boolean driverFinished() {
		return ResultStatus.finished==getResultStatus();
	}


	@Override
	public String toString() {
		return "Lap [lastLapTime=" + lastLapTime + ", currentLapTime=" + currentLapTime + ", bestLapTime=" + bestLapTime
				+ ", lapDistance=" + lapDistance + ", totalDistance=" + totalDistance + ", safetyCarDelta="
				+ safetyCarDelta + ", sector1TimeMS=" + sector1TimeMS + ", sector2TimeMS=" + sector2TimeMS
				+ ", bestLapNum=" + bestLapNum + ", bestLapSector1TimeInMS=" + bestLapSector1TimeInMS
				+ ", bestLapSector2TimeInMS=" + bestLapSector2TimeInMS + ", bestLapSector3TimeInMS="
				+ bestLapSector3TimeInMS + ", bestOverallSector1TimeInMS=" + bestOverallSector1TimeInMS
				+ ", bestOverallSector1LapNum=" + bestOverallSector1LapNum + ", bestOverallSector2TimeInMS="
				+ bestOverallSector2TimeInMS + ", bestOverallSector2LapNum=" + bestOverallSector2LapNum
				+ ", bestOverallSector3TimeInMS=" + bestOverallSector3TimeInMS + ", bestOverallSector3LapNum="
				+ bestOverallSector3LapNum + ", carPosition=" + carPosition + ", currentLapNum=" + currentLapNum
				+ ", currentLapInvalid=" + currentLapInvalid + ", penalties=" + penalties + ", gridPosition="
				+ gridPosition + ", pitStatus=" + pitStatus + ", sector=" + sector + ", driverStatus=" + driverStatus
				+ ", resultStatus=" + resultStatus + "]";
	}
	
	
}
